package de.hda.particles.editor;

import de.hda.particles.editor.impl.HUDEditorEntry;
import java.util.List;

import de.hda.particles.scene.Scene;

public interface Editor {

	void setup();
	void setScene(Scene scene);
	Boolean accept(Class<?> clazz);
	Class<?> getAcceptable();
	List<HUDEditorEntry> getEditorEntries();
	void select(Object subject);
	String getTitle();
	void decrease(String fieldName);
	void setMin(String fieldName);
	void increase(String fieldName);
	void setMax(String fieldName);
	String getValue(String fieldName);
	Object getObject(String fieldName);

}
