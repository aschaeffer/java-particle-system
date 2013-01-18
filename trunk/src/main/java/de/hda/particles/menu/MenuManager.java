package de.hda.particles.menu;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.hud.HUDCommandTypes;
import de.hda.particles.scene.Scene;

public class MenuManager {

	private Scene scene;
	
	private final static HUDMenuEntry dummyMenu = HUDMenuEntry.createRoot("dummy");
	
	private final Map<String, HUDMenuEntry> menus = new HashMap<String, HUDMenuEntry>();
	private final Map<String, HUDMenuEntry> menuEntries = new HashMap<String, HUDMenuEntry>();
	private final List<MenuControlListener> menuControlListeners = new ArrayList<MenuControlListener>();
	
	private HUDMenuEntry currentMenuRootNode = dummyMenu;
	private HUDMenuEntry currentMenu = dummyMenu;
	private HUDMenuEntry lastMenuRootNode = dummyMenu;
	private HUDMenuEntry lastMenu = dummyMenu;
	private Integer selectedIndex = 0;
	private Boolean menuOpenState = false;
	private Integer blockFor = 0;

	private final Logger logger = LoggerFactory.getLogger(MenuManager.class);
	
	public MenuManager() {}
	
	public MenuManager(Scene scene) {
		this.scene = scene;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void addMenu(String menuName, HUDMenuEntry menuRootNode) {
		menus.put(menuName, menuRootNode);
		menuEntries.put(menuName, menuRootNode);
	}
	
	public void addMenuEntryToRoot(String menuName, String entryName, HUDMenuEntry menuEntry) {
		menus.get(menuName).childs.add(menuEntry);
		menuEntries.put(entryName, menuEntry);
	}

	public void addChildMenuEntryToEntry(String parentEntryName, String childEntryName, String title, HUDCommandTypes commandType) {
		menuEntries.put(childEntryName, HUDMenuEntry.create(menuEntries.get(parentEntryName), title, commandType)); // JAVA <3
	}
	
	public void addChildMenuEntryToEntry(String parentEntryName, String childEntryName, String title, HUDCommandTypes commandType, Object commandPayload) {
		menuEntries.put(childEntryName, HUDMenuEntry.create(menuEntries.get(parentEntryName), title, commandType, commandPayload));
	}
	
	public void addChildMenuEntryToEntry(String parentEntryName, String childEntryName, String title, HUDCommandTypes commandType, Object commandPayload, Object commandPayload2) {
		menuEntries.put(childEntryName, HUDMenuEntry.create(menuEntries.get(parentEntryName), title, commandType, commandPayload, commandPayload2));
	}
	
	public void addChildMenuEntryToEntry(String parentEntryName, String childEntryName, String title, String icon, HUDCommandTypes commandType, Object commandPayload, Object commandPayload2) {
		menuEntries.put(childEntryName, HUDMenuEntry.create(menuEntries.get(parentEntryName), title, icon, commandType, commandPayload, commandPayload2));
	}
	
	public void addMenuControlListener(MenuControlListener menuControlListener) {
		menuControlListeners.add(menuControlListener);
	}
	
	public HUDMenuEntry getMenu(String menuName) {
		return menus.get(menuName);
	}
	
	public HUDMenuEntry getMenuEntry(String entryName) {
		return menuEntries.get(entryName);
	}
	
	public void setCurrentMenu(HUDMenuEntry menu) {
		if (menuOpenState) {
			logger.debug("new current menu: " + menu.title + " (" + menu.command.getType().name() + ")");
			currentMenu = menu;
		}
	}
	
	public HUDMenuEntry getCurrentMenu() {
		return currentMenu;
	}

	public HUDMenuEntry getCurrentMenuRootNode() {
		return currentMenuRootNode;
	}

	public Boolean isMenuOpen() {
		return menuOpenState;
	}

	public Boolean isMenuOpen(HUDMenuEntry menu) {
		return (menuOpenState && menu.equals(currentMenuRootNode));
	}

	public void openMenu(HUDMenuEntry menu) {
		logger.debug("childs of " + menu.title + ": " + menu.childs.size());
		if (menu.childs.size() == 0) return; // do not open an empty menu
		menuOpenState = true;
		currentMenuRootNode = menu;
		if (menu.equals(lastMenuRootNode)) {
			currentMenu = lastMenu;
		} else {
			currentMenu = menu;
		}
		selectedIndex = 0;
		ListIterator<MenuControlListener> iterator = menuControlListeners.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().onOpenMenu(menu);
		}
	}
	
	public void closeMenu() {
		closeMenu(true);
	}

	public void closeMenu(Boolean preserve) {
		if (isMenuOpen()) {
			menuOpenState = false;
			if (preserve) {
				lastMenuRootNode = currentMenuRootNode;
				lastMenu = currentMenu;
			} else {
				lastMenuRootNode = dummyMenu;
				lastMenu = dummyMenu;
			}
			currentMenuRootNode = dummyMenu;
			currentMenu = dummyMenu;
			ListIterator<MenuControlListener> iterator = menuControlListeners.listIterator(0);
			while (iterator.hasNext()) {
				iterator.next().onCloseMenu(currentMenu);
			}
			currentMenu = dummyMenu;
		}
	}

	public void closeMenu(HUDMenuEntry menu) {
		if (isMenuOpen(menu)) {
			menuOpenState = false;
			lastMenuRootNode = currentMenuRootNode;
			lastMenu = currentMenu;
			currentMenuRootNode = dummyMenu;
			currentMenu = dummyMenu;
			ListIterator<MenuControlListener> iterator = menuControlListeners.listIterator(0);
			while (iterator.hasNext()) {
				iterator.next().onCloseMenu(menu);
			}
		}
	}

	public Integer getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(Integer selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
	
	public void selectPreviousEntry() {
		if (selectedIndex > 0) {
			selectedIndex--;
		} else {
			selectedIndex = currentMenu.childs.size() - 1;
		}
		ListIterator<MenuControlListener> iterator = menuControlListeners.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().onPreviousEntry(currentMenuRootNode, currentMenu.childs.get(selectedIndex));
		}
	}
	
	public void selectNextEntry() {
		if (selectedIndex+1 < currentMenu.childs.size()) {
			selectedIndex++;
		} else {
			selectedIndex = 0;
		}
		ListIterator<MenuControlListener> iterator = menuControlListeners.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().onNextEntry(currentMenuRootNode, currentMenu.childs.get(selectedIndex));
		}
	}

	public Boolean isBlocked() {
		return blockFor > 0;
	}

	public Boolean isBlocked2() {
		if (blockFor > 0) {
			blockFor--;
			return true;
		} else {
			return false;
		}
	}

	public void blockFor(Integer blockFor) {
		this.blockFor = this.blockFor + blockFor;
	}
	
	public void unblock() {
		this.blockFor = 0;
	}
	
}
