package de.hda.particles.renderer;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import de.hda.particles.AutoDependency;
import de.hda.particles.Updateable;
import de.hda.particles.scene.Scene;

public interface Renderer extends Updateable, AutoDependency {

	void setScene(Scene scene);
	Boolean isSelectable();
	Boolean select(Vector3f position);
	List<? extends Object> select(Vector4f selectionBox);
	void unselect();
	List<? extends Object> getSelected();
	void move(Vector3f position);
	void remove(Vector3f position);
	
	void setVisible(Boolean visibility);
	Boolean isVisible();

}
