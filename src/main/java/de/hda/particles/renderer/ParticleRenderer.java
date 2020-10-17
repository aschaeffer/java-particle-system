package de.hda.particles.renderer;

import de.hda.particles.AutoDependency;
import de.hda.particles.domain.Particle;
import de.hda.particles.scene.Scene;

public interface ParticleRenderer extends AutoDependency {

	void before();
	void render(Particle particle);
	void after();
	void setDirty();
	void setScene(Scene scene);
	String getName();

}
