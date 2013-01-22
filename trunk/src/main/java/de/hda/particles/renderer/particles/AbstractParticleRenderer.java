package de.hda.particles.renderer.particles;

import de.hda.particles.scene.Scene;

public abstract class AbstractParticleRenderer implements ParticleRenderer {

	protected Scene scene;
	
	@Override
	public void setDirty() {
	}

	@Override
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	@Override
	public void addDependencies() {
	}

}
