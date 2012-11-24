package de.hda.particles.editor;

import java.util.List;

import de.hda.particles.hud.HUDEditorEntry;

public interface Editor {

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
