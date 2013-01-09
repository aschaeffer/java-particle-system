package de.hda.particles.renderer.types;

import de.hda.particles.AutoDependency;
import de.hda.particles.domain.Particle;
import de.hda.particles.scene.Scene;

public interface RenderType extends AutoDependency {

	void before();
	void render(Particle particle);
	void after();
	void setScene(Scene scene);
	String getName();

}
