package de.hda.particles.scene.impl;

import static org.lwjgl.opengl.GL11.glViewport;

import de.hda.particles.scene.Scene;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.opengl.LoadableImageData;
import org.newdawn.slick.opengl.TGAImageData;
import org.newdawn.slick.util.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.ParticleSystem;
import de.hda.particles.camera.impl.CameraManager;
import de.hda.particles.editor.impl.EditorManager;
import de.hda.particles.hud.impl.HUDManager;
import de.hda.particles.menu.MenuManager;
import de.hda.particles.overlay.impl.TextOverlayManager;
import de.hda.particles.renderer.impl.RendererManager;
import de.hda.particles.renderer.impl.faces.FaceRendererManager;
import de.hda.particles.renderer.impl.particles.ParticleRendererManager;
import de.hda.particles.renderer.impl.shaders.ShaderManager;
import de.hda.particles.textures.TextureManager;
import de.hda.particles.timing.FpsInformation;
import de.hda.particles.timing.FpsLimiter;

public abstract class AbstractScene extends FpsLimiter implements Scene {

	// The particle system
	protected ParticleSystem particleSystem;

	// Scene manages multiple managers
	protected HUDManager hudManager = new HUDManager();
	protected EditorManager editorManager = new EditorManager();
	protected MenuManager menuManager = new MenuManager();
	protected CameraManager cameraManager = new CameraManager();
	protected RendererManager rendererManager = new RendererManager();
	protected ParticleRendererManager particleRendererManager = new ParticleRendererManager();
	protected FaceRendererManager faceRendererManager = new FaceRendererManager();
	protected TextureManager textureManager = new TextureManager();
	protected TextOverlayManager textOverlayManager = new TextOverlayManager();
	protected ShaderManager shaderManager = new ShaderManager();

	// Scene keeps fps informations of underlying systems
	protected List<FpsInformation> fpsInformationInstances = new ArrayList<FpsInformation>();

	// Scene settings
	protected String name = DEFAULT_NAME;
	protected Integer width = DEFAULT_WIDTH;
	protected Integer height = DEFAULT_HEIGHT;
	protected Float nearPlane = DEFAULT_NEAR_PLANE;
	protected Float farPlane = DEFAULT_FAR_PLANE;
	protected Boolean fullscreen = DEFAULT_FULLSCREEN;
	protected Boolean vSync = DEFAULT_VSYNC;

	// Scene states
	protected Boolean running = true;
	protected Boolean idle = true;
	protected Boolean blocked = false;

    private final Logger logger = LoggerFactory.getLogger(AbstractScene.class);

	public AbstractScene() {
	}

	public AbstractScene(final Integer width, final Integer height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void update() {
		calcFps();
		limitFps();
	}
	
	@Override
	public void applyChanges() {
		Boolean changeMode = false;
		Boolean changeFullscreen = false;
		if (width != Display.getWidth()) changeMode = true;
		if (height != Display.getHeight()) changeMode = true;
		if (fullscreen != Display.isFullscreen()) changeFullscreen = true;
		if (changeFullscreen) {
			fullscreen();
		} else if (changeMode) {
			changeMode();
		}
	}
	
	public void changeMode() {
		try {
			if (Mouse.isGrabbed()) Mouse.setGrabbed(false);
			DisplayMode mode = new DisplayMode(width, height);
			Display.setDisplayMode(mode);
			glViewport(0, 0, width, height);
			Display.setFullscreen(fullscreen);
			Mouse.setGrabbed(true);
		} catch (LWJGLException e) {
			logger.error("Could not set window display mode: " + e.getMessage(), e);
		}
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
					// setWidth(mode.getWidth());
					// setHeight(mode.getHeight());
					logger.debug("set width: " + mode.getWidth() + " height: " + mode.getHeight());
					Display.setVSyncEnabled(vSync);
					Display.setDisplayMode(mode);
				} else {
					logger.warn("mode is not capable for fullscreen");
					fullscreen = !fullscreen;
					return;
				}
			} else {
				if (!Display.isFullscreen()) {
					logger.warn("already not in fullscreen!");
					return;
				}
				if (Mouse.isGrabbed()) Mouse.setGrabbed(false);
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
		} catch (LWJGLException e) {
			logger.error("couldn't change fullscreen mode", e);
		}
	}
	
	@Override
	public ParticleSystem getParticleSystem() {
		return particleSystem;
	}

	@Override
	public HUDManager getHudManager() {
		return hudManager;
	}

	public void setHudManager(HUDManager hudManager) {
		this.hudManager = hudManager;
	}

	@Override
	public EditorManager getEditorManager() {
		return editorManager;
	}

	public void setEditorManager(EditorManager editorManager) {
		this.editorManager = editorManager;
	}

	@Override
	public MenuManager getMenuManager() {
		return menuManager;
	}

	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}

	@Override
	public CameraManager getCameraManager() {
		return cameraManager;
	}

	public void setCameraManager(CameraManager cameraManager) {
		this.cameraManager = cameraManager;
	}

	@Override
	public RendererManager getRendererManager() {
		return rendererManager;
	}

	public void setRendererManager(RendererManager rendererManager) {
		this.rendererManager = rendererManager;
	}

	@Override
	public ParticleRendererManager getParticleRendererManager() {
		return particleRendererManager;
	}

	public void setparticleRendererManager(ParticleRendererManager particleRendererManager) {
		this.particleRendererManager = particleRendererManager;
	}

	@Override
	public FaceRendererManager getFaceRendererManager() {
		return faceRendererManager;
	}

	public void setFaceRendererManager(FaceRendererManager faceRendererManager) {
		this.faceRendererManager = faceRendererManager;
	}

	@Override
	public TextureManager getTextureManager() {
		return textureManager;
	}

	public void setTextureManager(TextureManager textureManager) {
		this.textureManager = textureManager;
	}

	@Override
	public TextOverlayManager getTextOverlayManager() {
		return textOverlayManager;
	}

	public void setTextOverlayManager(TextOverlayManager textOverlayManager) {
		this.textOverlayManager = textOverlayManager;
	}
	
	@Override
	public ShaderManager getShaderManager() {
		return shaderManager;
	}
	
	public void setShaderManager(ShaderManager shaderManager) {
		this.shaderManager = shaderManager;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
		try {
//			if (Display.isCreated()) {
				Display.setTitle(name);
//			}
		} catch (Exception e) {
		}
	}

	@Override
	public Integer getWidth() {
		return width;
	}

	@Override
	public void setWidth(Integer width) {
		this.width = width;
	}

	@Override
	public Integer getHeight() {
		return height;
	}

	@Override
	public void setHeight(Integer height) {
		this.height = height;
	}
	
	@Override
	public Float getNearPlane() {
		return nearPlane;
	}
	
	@Override
	public void setNearPlane(Float nearPlane) {
		this.nearPlane = nearPlane;
	}

	@Override
	public Float getFarPlane() {
		return farPlane;
	}
	
	@Override
	public void setFarPlane(Float farPlane) {
		this.farPlane = farPlane;
	}

	@Override
	public Boolean getFullscreen() {
		return fullscreen;
	}

	@Override
	public void setFullscreen(Boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	
	@Override
	public Boolean getVSync() {
		return vSync;
	}
	
	@Override
	public void setVSync(Boolean vSync) {
		this.vSync = vSync;
	}

	@Override
	public Boolean getRunning() {
		return running;
	}
	
	@Override
	public void setRunning(Boolean running) {
		this.running = running;
	}

	@Override
	public List<FpsInformation> getFpsInformationInstances() {
		return fpsInformationInstances;
	}
	
	@Override
	public void addFpsInformationInstance(FpsInformation fpsInformation) {
		fpsInformationInstances.add(fpsInformation);
	}
	
	@Override
	public void exit() {
		running = false;
	}

	@Override
	public void beginModification() {
		if (!blocked) {
			blocked = true;
			while (!idle) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
		}
	}
	
	@Override
	public void endModification() {
		blocked = false;
	}
	
	public void setIcon(String filename) {
		ByteBuffer[] bufs = new ByteBuffer[1];
		LoadableImageData data;
		boolean flip = true;
		if (filename.endsWith(".tga")) {
			data = new TGAImageData();
		} else {
			flip = false;
			data = new ImageIOImageData();
		}
		try {
			bufs[0] = data.loadImage(
					ResourceLoader.getResourceAsStream(filename), flip, false,
					null);
			Display.setIcon(bufs);
		} catch (Exception e) {
			logger.error("Could not load icon.", e);
		}
	}

}
