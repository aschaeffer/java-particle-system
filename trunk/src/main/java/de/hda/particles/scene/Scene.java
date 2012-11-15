package de.hda.particles.scene;

import java.util.List;

import de.hda.particles.ParticleSystem;
import de.hda.particles.Updateable;
import de.hda.particles.camera.CameraManager;
import de.hda.particles.hud.HUDManager;
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
	Boolean getFullscreen();
	void setFullscreen(Boolean fullscreen);
	
	ParticleSystem getParticleSystem();
	HUDManager getHudManager();
	CameraManager getCameraManager();
	RendererManager getRendererManager();
	RenderTypeManager getRenderTypeManager();
	TextureManager getTextureManager();
	
	List<FpsInformation> getFpsInformationInstances();
	void addFpsInformationInstance(FpsInformation fpsInformation);

}


/**
 * Notizen:
 * DONE 1. ---draw method per particle class--- render method index
 * DONE 2a. particle render type manager
 * DONE 2b. particle render type: 3d point
 * 3. particle render type: point sprites = billboard rendering
 * DONE 4a. scene loader
 * DONE 4b. scene saver
 * 5a. scene editor
 * 5b. moveables
 * 6a. particle system loader
 * 6b. particle system saver
 * 7. particle system editor
 * 8. ein paar scenes vorbereiten
 * 9. mouse zauberstab (emitter movement statt camera movement)
 * DONE 10. gravity points
 * DONE 11. gravity planes
 * 12. particle feature, das im emitter spherischen scatter erzeugt
 * DONE 13. fov / zoom
 * DONE 14. crosshair
 * DONE 15. fullscreen
 * 16. sprite sheet (sauerbraten fire)
 * 
 * 
 * 
 * 
		// Notiz:
		//   Masse-Feder-Emitter: Aktuell emittierter Partikel wird mit den vorigen
		//   drei ausgestoßenen partikeln verbunden -> so bildet sich eine Kette
 **/
