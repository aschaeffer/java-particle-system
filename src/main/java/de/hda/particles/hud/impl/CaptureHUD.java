package de.hda.particles.hud.impl;

import static com.xuggle.xuggler.Global.*;
import static org.lwjgl.opengl.GL11.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import de.hda.particles.hud.HUD;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.apache.commons.collections.ArrayStack;
import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUnderflowException;
import org.apache.commons.collections.buffer.BoundedFifoBuffer;
import org.apache.commons.math3.util.Pair;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;

import de.hda.particles.scene.Scene;

/**
 * Capturing videos and screenshots directly from OpenGL.
 * 
 * The image is read by the rendering thread using glReadPixels, which
 * slow down the system. That is more or less unavoidable.
 * 
 * We are moving the data (BGRA 32 bit unsigned byte) to a direct buffer,
 * to save time in the rendering thread. The byte pixel buffers are pushed
 * into a FIFO buffer.
 * 
 * Multiple threads are converting the byte pixel buffers which were read
 * from OpenGL to an BufferedImage (one for each frame). The BufferedImage
 * is pushed into another FIFO buffer.
 * 
 * Because of asynchronous threading, we have to ensure the correct ordering
 * of the frames. One additional thread orders the already converted frames
 * and writes them into a file.
 * 
 * To save memory usage and increase performance, we recycle each buffer
 * for byte pixel data and BufferedImages using an ArrayStack. So instead of
 * hundreds of Buffers, we are using less than 10.
 * 
 * @author aschaeffer
 *
 */
public class CaptureHUD extends AbstractHUD implements HUD {

	private final static String PATH = "";

	private final static String SCREENSHOT_FORMAT = "PNG";
	private final static Integer SCREENSHOT_BPP = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
	
	private final static String MOVIE_FORMAT = "AVI";
	private final static Integer MOVIE_DURATION = 100;
	private final static Integer MOVIE_BPP = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
	private final static Integer FIFO_SIZE = 30;
	private final static int MOVIE_CAPTURE_FORMAT = GL12.GL_BGRA; // GL_RGB
	private final static int MOVIE_BUFFERED_IMAGE_FORMAT = BufferedImage.TYPE_3BYTE_BGR;
	private final static Integer MOVIE_CONVERTER_THREADS = 4;

	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("y-MM-dd_HH-mm-ss");

	// inputs
	private Boolean blockScreenshotSelection = false;
	private Boolean blockMovieSelection = false;

	// common
	private int width = Display.getDisplayMode().getWidth();
	private int height = Display.getDisplayMode().getHeight();

	// movie recording
	private Boolean recording = false;
	private Boolean finishing = false;
	private Boolean converterFinished = false;
	private Boolean writerFinished = false;
	private Integer frameNo = 0;
	private Integer framesConverted = 0;
	private Integer framesWritten = 0;
	private Integer framesDropped = 0;
	private Integer lastFrameId = -1;
	private long nextFrameTime = 0;
	private String movieFilename = "";
	private final long movieFrameRate = DEFAULT_TIME_UNIT.convert(MOVIE_DURATION, MILLISECONDS);
	private IMediaWriter movieWriter;
	private final Buffer pixelBufferFifo = org.apache.commons.collections.BufferUtils.synchronizedBuffer(new BoundedFifoBuffer(FIFO_SIZE));
	private final Buffer bufferedImageFifo = org.apache.commons.collections.BufferUtils.synchronizedBuffer(new BoundedFifoBuffer(FIFO_SIZE));
	private final ArrayStack pixelBufferStack = new ArrayStack();
	private final ArrayStack bufferedImageStack = new ArrayStack();

	// screenshot
	private ByteBuffer buffer;

	private final Logger logger = LoggerFactory.getLogger(CaptureHUD.class);

	public CaptureHUD() {}

	public CaptureHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void update() {
		if (recording) captureMovieFrame();
		if (finishing) finishRecordingMovie();
	}
	
	@Override
	public void input() {
		if (Keyboard.isKeyDown(Keyboard.KEY_F9)) {
			if (!blockScreenshotSelection) {
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.SCREENSHOT));
				blockScreenshotSelection = true;
			}
		} else {
			blockScreenshotSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F8)) {
			if (!blockMovieSelection) {
				if (!recording) {
					scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.MOVIE_START_RECORDING));
				} else {
					scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.MOVIE_STOP_RECORDING));
				}
				blockMovieSelection = true;
			}
		} else {
			blockMovieSelection = false;
		}
	}
	
	private void startRecordingMovie() {
		recording = true;
		finishing = false;
		converterFinished = false;
		writerFinished = false;
		frameNo = 0;
		framesConverted = 0;
		framesWritten = 0;
		framesDropped = 0;
		lastFrameId = -1;
		nextFrameTime = 0;
		movieFilename = PATH + scene.getName() + "_" + simpleDateFormat.format(new Date()) + "." + MOVIE_FORMAT.toLowerCase(); // + "_";
		width = Display.getDisplayMode().getWidth();
		height= Display.getDisplayMode().getHeight();
		movieWriter = ToolFactory.makeWriter(movieFilename);
		movieWriter.addVideoStream(0, 0, width, height);
		for (Integer id = 1; id < MOVIE_CONVERTER_THREADS; id++) {
			new MovieConverter().start();
		}
		new MovieWriter().start();
	}

	private void stopRecordingMovie() {
		recording = false;
		finishing = true;
		scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Recording stopped - still processing " + (pixelBufferFifo.size() + bufferedImageFifo.size()) + " frames"));
	}
	
	private void finishRecordingMovie() {
		if (converterFinished && writerFinished) {
			finishing = false;
			converterFinished = false;
			writerFinished = false;
			try {
				movieWriter.close();
				logger.debug("Movie saved: " + movieFilename);
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Movie saved: " + movieFilename + " (" + frameNo  +" frames)"));
			} catch (Exception e) {
				logger.error("Error in saving movie: " + e.getMessage(), e);
			}
		} else {
			logger.debug("waiting for " + (frameNo-lastFrameId) + " frames to be converted and written...");
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Recording stopped - still processing " + (frameNo-lastFrameId) + " frames"));
		}
	}
	
	@SuppressWarnings("unchecked")
	private void captureMovieFrame() {
		if (pixelBufferFifo.size() >= FIFO_SIZE) {
			framesDropped++;
			// logger.debug("dropped capturing frame " + framesDropped);
			return;
		}
		ByteBuffer pixelBuffer;
		if (pixelBufferStack.isEmpty()) {
			pixelBuffer = BufferUtils.createByteBuffer(width * height * MOVIE_BPP);
		} else {
			// logger.debug("pixelBufferStack: " + pixelBufferStack.size() + " buffers ready");
			pixelBuffer = (ByteBuffer) pixelBufferStack.pop();
		}
		glReadBuffer(GL_FRONT);
		
		// best performance: 32-bit color: GL_UNSIGNED_BYTE/GL_BGRA
		glReadPixels(0, 0, width, height, MOVIE_CAPTURE_FORMAT, GL_UNSIGNED_BYTE, pixelBuffer);
		Pair<Integer, ByteBuffer> pixelBufferPair = new Pair<Integer, ByteBuffer>(frameNo, pixelBuffer);
		pixelBufferFifo.add(pixelBufferPair);

		// logger.debug("frame " + frameNo + " captured by " + Thread.currentThread().getName());
		frameNo++;
	}
	
	
	class MovieConverter extends Thread {

		@Override
		public void run() {
			while (recording || (finishing && pixelBufferFifo.size() > 0)) {
				try {
					convertMovieFrame();
				} catch (Exception e) {
					logger.error("Error in MovieConverter: " + e.getMessage(), e);
					converterFinished = true;
					return;
				}
			}
			converterFinished = true;
		}

		@SuppressWarnings("unchecked")
		public void convertMovieFrame() {
			if (bufferedImageFifo.size() >= FIFO_SIZE) {
				framesDropped++;
				// logger.debug("dropped converting frame " + framesDropped);
				return;
			}
			if (pixelBufferFifo.size() > 0) { // dont synchronize threads, just make them not interfering
				try {
					Pair<Integer, ByteBuffer> pixelBufferPair = (Pair<Integer, ByteBuffer>) pixelBufferFifo.remove();
					Integer frameId = pixelBufferPair.getKey();
					ByteBuffer pixelBuffer = pixelBufferPair.getValue();
					BufferedImage bufferedImage;
					if (bufferedImageStack.isEmpty()) {
						bufferedImage = new BufferedImage(width, height, MOVIE_BUFFERED_IMAGE_FORMAT);
					} else {
						// logger.debug("bufferedImageStack: " + bufferedImageStack.size() + " buffers ready");
						bufferedImage = (BufferedImage) bufferedImageStack.pop();
					}

					// bufferedImage = new BufferedImage(width, height, MOVIE_BUFFERED_IMAGE_FORMAT);
					for(int x = 0; x < width; x++) {
						for(int y = 0; y < height; y++) {
							int i = (x + (width * y)) * MOVIE_BPP;
							int b = pixelBuffer.get(i) & 0xFF;
							int g = pixelBuffer.get(i + 1) & 0xFF;
							int r = pixelBuffer.get(i + 2) & 0xFF;
							bufferedImage.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
						}
					}
					Pair<Integer, BufferedImage> bufferedImagePair = new Pair<Integer, BufferedImage>(frameId, bufferedImage);
					bufferedImageFifo.add(bufferedImagePair);
					pixelBuffer.clear(); // ready for recycle
					pixelBufferStack.push(pixelBuffer);
					framesConverted++;
					// logger.debug("frame " + frameId + " converted by " + Thread.currentThread().getName());
				} catch (BufferUnderflowException e) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e1) {}
				}
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {}
			}
		}

	}

	class MovieWriter extends Thread {

		@Override
		public void run() {
			while (recording || (finishing && !converterFinished) || (finishing && bufferedImageFifo.size() > 0)) {
				try {
					writeMovieFrame();
				} catch (Exception e) {
					logger.error("Error in MovieWriter: " + e.getMessage(), e);
					writerFinished = true;
					return;
				}
			}
			// be sure to write the last frames
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
			writeMovieFrame();
			writerFinished = true;
		}

		@SuppressWarnings("unchecked")
		public void writeMovieFrame() {
			if (bufferedImageFifo.size() > 0) {
				try {
					Integer frameId = lastFrameId;
					BufferedImage bufferedImage = null;
					Iterator<Pair<Integer, BufferedImage>> iterator = bufferedImageFifo.iterator();
					while (iterator.hasNext()) {
						Pair<Integer, BufferedImage> bufferedImagePair = iterator.next();
						frameId = bufferedImagePair.getKey();
						bufferedImage = bufferedImagePair.getValue();
						if (frameId == lastFrameId+1) {
							iterator.remove();
							movieWriter.encodeVideo(0, bufferedImage, nextFrameTime, DEFAULT_TIME_UNIT);
							bufferedImageStack.add(bufferedImage); // ready to recycle!
							framesWritten++;
							nextFrameTime += movieFrameRate;
							lastFrameId = frameId;
							// logger.debug("frame " + frameId + " written by " + Thread.currentThread().getName());
							return;
						}
					}
				} catch (BufferUnderflowException e) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e1) {}
				}
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {}
			}
		}
		
	}

	private void captureScreenshot() {
		try {
			width = Display.getDisplayMode().getWidth();
			height = Display.getDisplayMode().getHeight();
			buffer = BufferUtils.createByteBuffer(width * height * SCREENSHOT_BPP);
			File file = new File(PATH + scene.getName() + "_" + simpleDateFormat.format(new Date()) + "." + SCREENSHOT_FORMAT.toLowerCase());
			ImageIO.write(captureScreenshotFrame(BufferedImage.TYPE_INT_RGB), SCREENSHOT_FORMAT, file);
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Screenshot saved!"));
		} catch (IOException e) {
			logger.error("could not create screenshot: " + e.getMessage(), e);
		}
	}

	private BufferedImage captureScreenshotFrame(int type) {
		glReadBuffer(GL_FRONT);
		buffer.clear();
		glReadPixels(0, 0, width, height, GL12.GL_BGRA, GL_UNSIGNED_BYTE, buffer);
		BufferedImage image = new BufferedImage(width, height, type);
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				int i = (x + (width * y)) * SCREENSHOT_BPP;
				int b = buffer.get(i) & 0xFF;
				int g = buffer.get(i + 1) & 0xFF;
				int r = buffer.get(i + 2) & 0xFF;
				image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
			}
		}
		return image;
	}

	@Override
	public void setup() {
		super.setup();
	}

	@Override
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.SCREENSHOT) {
			captureScreenshot();
		}
		if (command.getType() == HUDCommandTypes.MOVIE_START_RECORDING) {
			startRecordingMovie();
		}
		if (command.getType() == HUDCommandTypes.MOVIE_STOP_RECORDING) {
			stopRecordingMovie();
		}
	}

}
