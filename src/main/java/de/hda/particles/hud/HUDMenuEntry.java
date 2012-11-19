package de.hda.particles.hud;

import java.util.ArrayList;
import java.util.List;

public class HUDMenuEntry {

	public String title = "untitled";
	public HUDMenuEntry parent;
	public List<HUDMenuEntry> childs = new ArrayList<HUDMenuEntry>();
	public HUDCommand command = new HUDCommand(HUDCommandTypes.MENU);
	
	public static HUDMenuEntry create(HUDMenuEntry parent, String title) {
		HUDMenuEntry e = new HUDMenuEntry(parent, title);
		parent.childs.add(e);
		return e;
	}

	public static HUDMenuEntry create(HUDMenuEntry parent, String title, HUDCommandTypes commandType) {
		HUDMenuEntry e = new HUDMenuEntry(parent, title, commandType);
		parent.childs.add(e);
		return e;
	}

	public static HUDMenuEntry create(HUDMenuEntry parent, String title, HUDCommandTypes commandType, Object commandPayload) {
		HUDMenuEntry e = new HUDMenuEntry(parent, title, commandType, commandPayload);
		parent.childs.add(e);
		return e;
	}

	public static HUDMenuEntry create(HUDMenuEntry parent, String title, HUDCommandTypes commandType, Object commandPayload, Object commandPayload2) {
		HUDMenuEntry e = new HUDMenuEntry(parent, title, commandType, commandPayload, commandPayload2);
		parent.childs.add(e);
		return e;
	}

	public static HUDMenuEntry createRoot(String title) {
		HUDMenuEntry e = new HUDMenuEntry(null, title);
		e.parent = e;
		return e;
	}

	private HUDMenuEntry(HUDMenuEntry parent, String title) {
		this.parent = parent;
		this.title = title;
	}

	private HUDMenuEntry(HUDMenuEntry parent, String title, HUDCommandTypes commandType) {
		this.parent = parent;
		this.title = title;
		this.command = new HUDCommand(commandType);
	}

	private HUDMenuEntry(HUDMenuEntry parent, String title, HUDCommandTypes commandType, Object commandPayload) {
		this.parent = parent;
		this.title = title;
		this.command = new HUDCommand(commandType, commandPayload);
	}

	private HUDMenuEntry(HUDMenuEntry parent, String title, HUDCommandTypes commandType, Object commandPayload, Object commandPayload2) {
		this.parent = parent;
		this.title = title;
		this.command = new HUDCommand(commandType, commandPayload, commandPayload2);
	}

}
