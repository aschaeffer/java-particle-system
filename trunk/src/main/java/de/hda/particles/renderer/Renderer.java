package de.hda.particles.renderer;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.Updateable;
import de.hda.particles.scene.Scene;

public interface Renderer extends Updateable {

	void setScene(Scene scene);
	Boolean isSelectable();
	void select(Vector3f position);
	Object getSelected();
	void move(Vector3f position);
	void remove(Vector3f position);
	
	void setVisible(Boolean visibility);
	Boolean isVisible();

}
