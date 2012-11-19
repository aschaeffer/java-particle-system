package de.hda.particles.hud;

public class HUDEditorEntry {

	public String label;
	public String key;
	
	public static HUDEditorEntry create(String key, String label) {
		HUDEditorEntry e = new HUDEditorEntry();
		e.key = key;
		e.label = label;
		return e;
	}

}
