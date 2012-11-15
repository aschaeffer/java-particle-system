package de.hda.particles.hud;

import de.hda.particles.renderer.Renderer;
import de.hda.particles.scene.Scene;

public interface HUD extends Renderer, HUDCommandListener {

	@Override
	public void setScene(Scene scene);
	
	public void render1();
	public void render2();

}
