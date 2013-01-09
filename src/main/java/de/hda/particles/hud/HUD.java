package de.hda.particles.hud;

import de.hda.particles.renderer.Renderer;
import de.hda.particles.scene.Scene;

public interface HUD extends Renderer, HUDCommandListener {

	@Override
	public void setScene(Scene scene);
	
	/**
	 * first render pass.
	 */
	public void render1();
	
	/**
	 * second render pass.
	 */
	public void render2();
	
	/**
	 * input handling pass.
	 */
	public void input();

}
