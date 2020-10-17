package de.hda.particles.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hda.particles.hud.impl.HUDCommand;
import de.hda.particles.hud.impl.HUDCommandTypes;

public class HUDMenuEntry {

	public String title = "untitled";
	public HUDMenuEntry parent;
	public Boolean vertical = true;
	public String icon = null;
	public List<HUDMenuEntry> childs = new ArrayList<HUDMenuEntry>();
	public HUDCommand command = new HUDCommand(HUDCommandTypes.MENU);
	public Map<Integer, HUDCommand> keyCommands = new HashMap<Integer, HUDCommand>();
	
	public static HUDMenuEntry create(HUDMenuEntry parent, String title) {
		HUDMenuEntry e = new HUDMenuEntry(parent, title);
		parent.childs.add(e);
		return e;
	}

	public static HUDMenuEntry create(HUDMenuEntry parent, String title, String icon) {
		HUDMenuEntry e = new HUDMenuEntry(parent, title, icon);
		parent.childs.add(e);
		return e;
	}

	public static HUDMenuEntry create(HUDMenuEntry parent, String title, HUDCommandTypes commandType) {
		HUDMenuEntry e = new HUDMenuEntry(parent, title, commandType);
		parent.childs.add(e);
		return e;
	}

	public static HUDMenuEntry create(HUDMenuEntry parent, String title, String icon, HUDCommandTypes commandType) {
		HUDMenuEntry e = new HUDMenuEntry(parent, title, icon, commandType);
		parent.childs.add(e);
		return e;
	}

	public static HUDMenuEntry create(HUDMenuEntry parent, String title, HUDCommandTypes commandType, Object commandPayload) {
		HUDMenuEntry e = new HUDMenuEntry(parent, title, commandType, commandPayload);
		parent.childs.add(e);
		return e;
	}

	public static HUDMenuEntry create(HUDMenuEntry parent, String title, String icon, HUDCommandTypes commandType, Object commandPayload) {
		HUDMenuEntry e = new HUDMenuEntry(parent, title, icon, commandType, commandPayload);
		parent.childs.add(e);
		return e;
	}

	public static HUDMenuEntry create(HUDMenuEntry parent, String title, HUDCommandTypes commandType, Object commandPayload, Object commandPayload2) {
		HUDMenuEntry e = new HUDMenuEntry(parent, title, commandType, commandPayload, commandPayload2);
		parent.childs.add(e);
		return e;
	}

	public static HUDMenuEntry create(HUDMenuEntry parent, String title, String icon, HUDCommandTypes commandType, Object commandPayload, Object commandPayload2) {
		HUDMenuEntry e = new HUDMenuEntry(parent, title, icon, commandType, commandPayload, commandPayload2);
		parent.childs.add(e);
		return e;
	}

	public static HUDMenuEntry create(HUDMenuEntry parent, String title, HUDCommandTypes commandType, Object commandPayload, Object commandPayload2, Object commandPayload3) {
		HUDMenuEntry e = new HUDMenuEntry(parent, title, commandType, commandPayload, commandPayload2, commandPayload3);
		parent.childs.add(e);
		return e;
	}

	public static HUDMenuEntry create(HUDMenuEntry parent, String title, String icon, HUDCommandTypes commandType, Object commandPayload, Object commandPayload2, Object commandPayload3) {
		HUDMenuEntry e = new HUDMenuEntry(parent, title, icon, commandType, commandPayload, commandPayload2, commandPayload3);
		parent.childs.add(e);
		return e;
	}

	public static HUDMenuEntry createRoot(String title) {
		HUDMenuEntry e = new HUDMenuEntry(null, title);
		e.parent = e;
		return e;
	}

	public static HUDMenuEntry createRoot(String title, Boolean vertical) {
		HUDMenuEntry e = new HUDMenuEntry(null, title);
		e.parent = e;
		e.vertical = vertical;
		return e;
	}

	private HUDMenuEntry(HUDMenuEntry parent, String title) {
		this.parent = parent;
		this.title = title;
	}

	private HUDMenuEntry(HUDMenuEntry parent, String title, String icon) {
		this.parent = parent;
		this.title = title;
		this.icon = icon;
	}

	private HUDMenuEntry(HUDMenuEntry parent, String title, HUDCommandTypes commandType) {
		this.parent = parent;
		this.title = title;
		this.command = new HUDCommand(commandType);
	}

	private HUDMenuEntry(HUDMenuEntry parent, String title, String icon, HUDCommandTypes commandType) {
		this.parent = parent;
		this.title = title;
		this.icon = icon;
		this.command = new HUDCommand(commandType);
	}

	private HUDMenuEntry(HUDMenuEntry parent, String title, HUDCommandTypes commandType, Object commandPayload) {
		this.parent = parent;
		this.title = title;
		this.command = new HUDCommand(commandType, commandPayload);
	}

	private HUDMenuEntry(HUDMenuEntry parent, String title, String icon, HUDCommandTypes commandType, Object commandPayload) {
		this.parent = parent;
		this.title = title;
		this.icon = icon;
		this.command = new HUDCommand(commandType, commandPayload);
	}

	private HUDMenuEntry(HUDMenuEntry parent, String title, HUDCommandTypes commandType, Object commandPayload, Object commandPayload2) {
		this.parent = parent;
		this.title = title;
		this.command = new HUDCommand(commandType, commandPayload, commandPayload2);
	}

	private HUDMenuEntry(HUDMenuEntry parent, String title, String icon, HUDCommandTypes commandType, Object commandPayload, Object commandPayload2) {
		this.parent = parent;
		this.title = title;
		this.icon = icon;
		this.command = new HUDCommand(commandType, commandPayload, commandPayload2);
	}

	private HUDMenuEntry(HUDMenuEntry parent, String title, HUDCommandTypes commandType, Object commandPayload, Object commandPayload2, Object commandPayload3) {
		this.parent = parent;
		this.title = title;
		this.command = new HUDCommand(commandType, commandPayload, commandPayload2, commandPayload3);
	}

	private HUDMenuEntry(HUDMenuEntry parent, String title, String icon, HUDCommandTypes commandType, Object commandPayload, Object commandPayload2, Object commandPayload3) {
		this.parent = parent;
		this.title = title;
		this.icon = icon;
		this.command = new HUDCommand(commandType, commandPayload, commandPayload2, commandPayload3);
	}

}
