package de.hda.particles.scene;

import java.util.List;

import de.hda.particles.Blockable;
import de.hda.particles.ParticleSystem;
import de.hda.particles.Updateable;
import de.hda.particles.camera.CameraManager;
import de.hda.particles.editor.EditorManager;
import de.hda.particles.hud.HUDManager;
import de.hda.particles.menu.MenuManager;
import de.hda.particles.overlay.TextOverlayManager;
import de.hda.particles.renderer.RendererManager;
import de.hda.particles.renderer.faces.FaceRendererManager;
import de.hda.particles.renderer.particles.ParticleRendererManager;
import de.hda.particles.renderer.shaders.ShaderManager;
import de.hda.particles.textures.TextureManager;
import de.hda.particles.timing.FpsInformation;

public interface Scene extends Updateable, Blockable, FpsInformation {

	public final static String NAME = "name";
	public final static String WIDTH = "width";
	public final static String HEIGHT = "height";
	public final static String NEAR_PLANE = "nearPlane";
	public final static String FAR_PLANE = "farPlane";
	public final static String FULLSCREEN = "fullscreen";
	public final static String VSYNC = "vSync";

	public final static String DEFAULT_NAME = "Particle System Scene";
	public final static Integer DEFAULT_WIDTH = 800;
	public final static Integer DEFAULT_HEIGHT = 600;
	public final static Float DEFAULT_NEAR_PLANE = 0.1f;
	public final static Float DEFAULT_FAR_PLANE = 5000.0f;
	public final static Boolean DEFAULT_FULLSCREEN = false;
	public final static Boolean DEFAULT_VSYNC = true;

	String getName();
	void setName(String name);
	Integer getWidth();
	void setWidth(Integer width);
	Integer getHeight();
	void setHeight(Integer height);
	Float getNearPlane();
	void setNearPlane(Float nearPlane);
	Float getFarPlane();
	void setFarPlane(Float farPlane);
	Boolean getFullscreen();
	void setFullscreen(Boolean fullscreen);
	Boolean getVSync();
	void setVSync(Boolean vSync);
	Boolean getRunning();
	void setRunning(Boolean running);
	void applyChanges();
	
	void exit();
	
	ParticleSystem getParticleSystem();
	HUDManager getHudManager();
	EditorManager getEditorManager();
	MenuManager getMenuManager();
	CameraManager getCameraManager();
	RendererManager getRendererManager();
	ParticleRendererManager getParticleRendererManager();
	FaceRendererManager getFaceRendererManager();
	TextureManager getTextureManager();
	TextOverlayManager getTextOverlayManager();
	ShaderManager getShaderManager();
	
	List<FpsInformation> getFpsInformationInstances();
	void addFpsInformationInstance(FpsInformation fpsInformation);

}
