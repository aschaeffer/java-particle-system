package de.hda.particles.overlay;

import de.hda.particles.Updatable;
import de.hda.particles.scene.Scene;

public interface TextOverlay extends Updatable {

	void setScene(Scene scene);
	void setVisible(Boolean visibility);
	Boolean isVisible();

}
