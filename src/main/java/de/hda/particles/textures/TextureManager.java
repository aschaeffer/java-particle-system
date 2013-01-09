package de.hda.particles.textures;

import static org.lwjgl.opengl.GL11.GL_LINEAR;

import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defered Texture Loading
 * @author aschaeffer
 *
 */
public class TextureManager {

	private final static Logger logger = LoggerFactory.getLogger(TextureManager.class); 

	public TextureManager() {}

	public Texture load(String format, String filename) {
		try {
			return TextureLoader.getTexture(format, ResourceLoader.getResourceAsStream(filename), GL_LINEAR);
		} catch (IOException e) {
			throw new RuntimeException("texture loading error: " + filename);
		}
	}

	public void loadDeferred(String key, DeferredTextureLoaderCallback callback, String format, String filename) {
		DeferredTextureLoader deferredTextureLoader = new DeferredTextureLoader(key, callback, format, filename);
		Thread deferredTextureLoaderThread = new Thread(deferredTextureLoader);
		deferredTextureLoaderThread.start();
	}

	public class DeferredTextureLoader implements Runnable {
		
		String key;
		DeferredTextureLoaderCallback callback;
		String format;
		String filename;
		
		public DeferredTextureLoader(String key, DeferredTextureLoaderCallback callback, String format, String filename) {
			this.key = key;
			this.callback = callback;
			this.format = format;
			this.filename = filename;
		}

		@Override
		public void run() {
			try {
				InputStream resource = ResourceLoader.getResourceAsStream(filename);
				Thread.sleep(200);
				// for a short time we have to claim the opengl context (which blocks actually)
				Drawable drawable = Display.getDrawable();
				if (drawable.isCurrent()) drawable.releaseContext();
				SharedDrawable sharedDrawable = new SharedDrawable(drawable);
				sharedDrawable.makeCurrent();
				Texture texture = TextureLoader.getTexture(format, resource, GL_LINEAR);
				sharedDrawable.releaseContext();
				drawable.releaseContext();
				callback.setTexture(key, texture);
				logger.debug("texture " + filename + " loaded");
			} catch (Exception e) {
				logger.error("texture loading error: " + filename, e);
				throw new RuntimeException("texture loading error: " + filename);
			}
		}
		
	}
	
}
