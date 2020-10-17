package de.hda.particles.scene;

import java.util.List;

import de.hda.particles.Blockable;
import de.hda.particles.ParticleSystem;
import de.hda.particles.Updatable;
import de.hda.particles.camera.impl.CameraManagerImpl;
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

public interface Scene extends Updatable, Blockable, FpsInformation {

	String NAME = "name";
	String WIDTH = "width";
	String HEIGHT = "height";
	String NEAR_PLANE = "nearPlane";
	String FAR_PLANE = "farPlane";
	String FULLSCREEN = "fullscreen";
	String VSYNC = "vSync";

	String DEFAULT_NAME = "Particle System Scene";
	Integer DEFAULT_WIDTH = 800;
	Integer DEFAULT_HEIGHT = 600;
	Float DEFAULT_NEAR_PLANE = 0.1f;
	Float DEFAULT_FAR_PLANE = 5000.0f;
	Boolean DEFAULT_FULLSCREEN = false;
	Boolean DEFAULT_VSYNC = true;

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
	CameraManagerImpl getCameraManager();
	RendererManager getRendererManager();
	ParticleRendererManager getParticleRendererManager();
	FaceRendererManager getFaceRendererManager();
	TextureManager getTextureManager();
	TextOverlayManager getTextOverlayManager();
	ShaderManager getShaderManager();
	
	List<FpsInformation> getFpsInformationInstances();
	void addFpsInformationInstance(FpsInformation fpsInformation);

}
