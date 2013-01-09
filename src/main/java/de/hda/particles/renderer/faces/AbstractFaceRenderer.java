package de.hda.particles.renderer.faces;

import de.hda.particles.scene.Scene;

public abstract class AbstractFaceRenderer implements FaceRenderer {

	protected Scene scene;
	
	@Override
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	@Override
	public void addDependencies() {
	}

}
