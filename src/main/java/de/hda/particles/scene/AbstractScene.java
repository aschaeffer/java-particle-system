package de.hda.particles.scene;

import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.ParticleSystem;
import de.hda.particles.camera.CameraManager;
import de.hda.particles.hud.HUDManager;
import de.hda.particles.renderer.RendererManager;
import de.hda.particles.renderer.types.RenderTypeManager;
import de.hda.particles.textures.TextureManager;
import de.hda.particles.timing.FpsInformation;
import de.hda.particles.timing.FpsLimiter;

public abstract class AbstractScene extends FpsLimiter implements Scene {
	
	public final static Integer DEFAULT_WIDTH = 800;
	public final static Integer DEFAULT_HEIGHT = 600;

	protected ParticleSystem particleSystem;
	
	protected HUDManager hudManager = new HUDManager();
	protected CameraManager cameraManager = new CameraManager();
	protected RendererManager rendererManager = new RendererManager();
	protected RenderTypeManager renderTypeManager = new RenderTypeManager();
	protected TextureManager textureManager = new TextureManager();
	
	protected List<FpsInformation> fpsInformationInstances = new ArrayList<FpsInformation>();

	protected String name = "Particle System";
	protected Integer width = DEFAULT_WIDTH;
	protected Integer height = DEFAULT_HEIGHT;
	protected Boolean fullscreen = false;
	protected Boolean running = true;

    private final Logger logger = LoggerFactory.getLogger(AbstractScene.class);

	public AbstractScene() {
	}

	public AbstractScene(Integer width, Integer height) {
		this.width = width;
		this.height = height;
	}
	
	public void update() {
		calcFps();
		limitFps();
	}

	public void fullscreen() {
		try {
			if (fullscreen) {
				if (Display.isFullscreen()) {
					logger.warn("already in fullscreen!");
					return;
				}
				if (Mouse.isGrabbed()) Mouse.setGrabbed(false);
				DisplayMode mode = Display.getDesktopDisplayMode();
				if (mode.isFullscreenCapable()) {
					setWidth(mode.getWidth());
					setHeight(mode.getHeight());
					logger.debug("set width: " + width + " height: " + height);
					Display.setDisplayMode(mode);
				} else {
					logger.warn("mode is not able for fullscreen");
					fullscreen = !fullscreen;
					return;
				}
			} else {
				if (!Display.isFullscreen()) {
					logger.warn("already not in fullscreen!");
					return;
				}
				if (Mouse.isGrabbed()) Mouse.setGrabbed(false);
				setWidth(DEFAULT_WIDTH);
				setHeight(DEFAULT_HEIGHT);
				logger.debug("set width: " + width + " height: " + height);
				Display.setDisplayMode(new DisplayMode(width, height));
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
			glViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
			Display.setFullscreen(fullscreen);
			logger.debug("set fullscreen state... width: " + width + " height: " + height);
			Mouse.setGrabbed(true);
			
			// glLoadIdentity();
			// glViewport(0, 0, width, height);
			// Display.setVSyncEnabled(fullscreen);
			// glViewport(0, 0, width, height);
		} catch (LWJGLException e) {
			logger.error("couldn't change fullscreen mode", e);
		}
	}

	public ParticleSystem getParticleSystem() {
		return particleSystem;
	}

	public HUDManager getHudManager() {
		return hudManager;
	}

	public void setHudManager(HUDManager hudManager) {
		this.hudManager = hudManager;
	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	public void setCameraManager(CameraManager cameraManager) {
		this.cameraManager = cameraManager;
	}

	public RendererManager getRendererManager() {
		return rendererManager;
	}

	public void setRendererManager(RendererManager rendererManager) {
		this.rendererManager = rendererManager;
	}

	public RenderTypeManager getRenderTypeManager() {
		return renderTypeManager;
	}

	public void setRenderTypeManager(RenderTypeManager renderTypeManager) {
		this.renderTypeManager = renderTypeManager;
	}

	public TextureManager getTextureManager() {
		return textureManager;
	}

	public void setTextureManager(TextureManager textureManager) {
		this.textureManager = textureManager;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Boolean getFullscreen() {
		return fullscreen;
	}

	public void setFullscreen(Boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	
	public List<FpsInformation> getFpsInformationInstances() {
		return fpsInformationInstances;
	}
	
	public void addFpsInformationInstance(FpsInformation fpsInformation) {
		fpsInformationInstances.add(fpsInformation);
	}

}
