package de.hda.particles.renderer.faces;

import de.hda.particles.AutoDependency;
import de.hda.particles.domain.Face;
import de.hda.particles.scene.Scene;

public interface FaceRenderer extends AutoDependency {

	void before();
	void render(Face face);
	void after();
	void setScene(Scene scene);
	String getName();

}
