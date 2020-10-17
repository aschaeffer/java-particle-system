package de.hda.particles.hud;

import de.hda.particles.renderer.Renderer;
import de.hda.particles.scene.Scene;

public interface HUD extends Renderer, HUDCommandListener {

	@Override
	void setScene(Scene scene);
	
	/**
	 * first render pass.
	 */
	void render1();
	
	/**
	 * second render pass.
	 */
	void render2();
	
	/**
	 * input handling pass.
	 */
	void input();

}
