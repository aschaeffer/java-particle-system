package de.hda.particles.scene;

import java.util.List;

import de.hda.particles.ParticleSystem;
import de.hda.particles.Updateable;
import de.hda.particles.camera.CameraManager;
import de.hda.particles.hud.HUDManager;
import de.hda.particles.overlay.TextOverlayManager;
import de.hda.particles.renderer.RendererManager;
import de.hda.particles.renderer.types.RenderTypeManager;
import de.hda.particles.textures.TextureManager;
import de.hda.particles.timing.FpsInformation;

public interface Scene extends Updateable, FpsInformation {

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
	
	void exit();
	
	ParticleSystem getParticleSystem();
	HUDManager getHudManager();
	CameraManager getCameraManager();
	RendererManager getRendererManager();
	RenderTypeManager getRenderTypeManager();
	TextureManager getTextureManager();
	TextOverlayManager getTextOverlayManager();
	
	List<FpsInformation> getFpsInformationInstances();
	void addFpsInformationInstance(FpsInformation fpsInformation);

}


/**
 * TODO:
 * 8. ein paar scenes vorbereiten
 * 16. sprite sheet (sauerbraten flames) / loading grayscale textures
 * 20. movement paths (cameras)
 * 21. Mapping physics render type class <-> scene render type index
 * 22. Frame Rate independent physics modification: http://thecodinguniverse.com/lwjgl-frame-rate-independent-movement/
 * 
 * 
 * DONE 1. ---draw method per particle class--- render method index
 * DONE 2a. particle render type manager
 * DONE 2b. particle render type: 3d point
 * DONE 3. particle render type: point sprites = billboard rendering
 * DONE 4a. scene loader
 * DONE 4b. scene saver
 * DONE 5a. scene editor
 * DONE 5b. moveables
 * DONE 6a. particle system loader
 * DONE 6b. particle system saver
 * DONE 7. particle system editor
 * DONE 9. maus zauberstab (emitter movement statt camera movement)
 * DONE 10. gravity points
 * DONE 11. gravity planes
 * DONE 12. spherischer emitter
 * DONE 13. fov / zoom
 * DONE 14. crosshair
 * DONE 15. fullscreen
 * DONE 17. multiple physics threads
 * DONE 18. picking
 * DONE 19. movement von selectable
 * 
 **/
