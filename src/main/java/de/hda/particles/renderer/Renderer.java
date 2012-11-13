package de.hda.particles.renderer;

import de.hda.particles.Updateable;
import de.hda.particles.scene.Scene;

public interface Renderer extends Updateable {

	void setScene(Scene scene);
}
