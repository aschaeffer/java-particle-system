package de.hda.particles.overlay;

import de.hda.particles.Updateable;
import de.hda.particles.scene.Scene;

public interface TextOverlay extends Updateable {

	void setScene(Scene scene);
	void setVisible(Boolean visibility);
	Boolean isVisible();

}
