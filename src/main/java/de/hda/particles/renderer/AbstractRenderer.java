package de.hda.particles.renderer;

import de.hda.particles.scene.Scene;

public abstract class AbstractRenderer implements Renderer {

	protected Scene scene;

	@Override
	public void setup() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public Boolean isFinished() {
		return false;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}

}
