package de.hda.particles.editor;

import java.util.HashMap;
import java.util.Map;

import de.hda.particles.hud.HUDCommand;

public class HUDEditorEntry {

	public String label;
	public String key;
	public Map<Integer, HUDCommand> keyCommands;
	
	public static HUDEditorEntry create(String key, String label) {
		HUDEditorEntry e = new HUDEditorEntry();
		e.key = key;
		e.label = label;
		e.keyCommands = new HashMap<Integer, HUDCommand>();
		return e;
	}

	public static HUDEditorEntry create(String key, String label, Map<Integer, HUDCommand> keyCommands) {
		HUDEditorEntry e = new HUDEditorEntry();
		e.key = key;
		e.label = label;
		e.keyCommands = keyCommands;
		return e;
	}

}
