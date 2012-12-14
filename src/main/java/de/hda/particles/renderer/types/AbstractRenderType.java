package de.hda.particles.renderer.types;

import de.hda.particles.scene.Scene;

public abstract class AbstractRenderType implements RenderType {

	protected Scene scene;
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}

}