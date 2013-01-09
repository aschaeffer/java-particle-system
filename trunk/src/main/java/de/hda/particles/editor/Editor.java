package de.hda.particles.editor;

import java.util.List;

import de.hda.particles.hud.HUDEditorEntry;
import de.hda.particles.scene.Scene;

public interface Editor {

	public void setup();
	public void setScene(Scene scene);
	public Boolean accept(Class<? extends Object> clazz);
	List<HUDEditorEntry> getEditorEntries();
	void select(Object subject);
	String getTitle();
	void decrease(String fieldName);
	void setMin(String fieldName);
	void increase(String fieldName);
	void setMax(String fieldName);
	String getValue(String fieldName);

}
