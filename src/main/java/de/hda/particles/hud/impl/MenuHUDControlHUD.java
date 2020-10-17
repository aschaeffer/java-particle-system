package de.hda.particles.hud.impl;

import de.hda.particles.hud.HUD;
import de.hda.particles.hud.MenuHUD;
import org.lwjgl.input.Keyboard;

import de.hda.particles.menu.DynamicMenu;
import de.hda.particles.menu.HUDMenuEntry;

public class MenuHUDControlHUD extends AbstractHUD implements HUD {

	private final static Integer KEYPRESS_REPEAT_THRESHOLD = 10;

	private Boolean blockEscSelection = false;
	private Integer blockUpSelection = 0;
	private Integer blockDownSelection = 0;
	private Boolean blockInSelection = false;
	private Boolean blockOutSelection = false;
	private Boolean blockSelectSelection = false;

	@Override
	public void input() {
		if (scene.getMenuManager().isBlocked2()) return;
		Boolean isMenuOpen = scene.getMenuManager().isMenuOpen();
		HUDMenuEntry currentMenu = scene.getMenuManager().getCurrentMenu();
		HUDMenuEntry currentMenuRootNode = scene.getMenuManager().getCurrentMenuRootNode();
	    Integer selectedIndex = scene.getMenuManager().getSelectedIndex();
	    
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			if (!blockEscSelection) {
				if (isMenuOpen) {
					scene.getMenuManager().closeMenu();
					scene.getMenuManager().blockFor(KEYPRESS_REPEAT_THRESHOLD);
				}
				blockEscSelection = true;
			}
		} else {
			blockEscSelection = false;
		}
		if (currentMenuRootNode.vertical && Keyboard.isKeyDown(Keyboard.KEY_UP) || !currentMenuRootNode.vertical && Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if (isMenuOpen && (blockUpSelection == 0 || blockUpSelection > KEYPRESS_REPEAT_THRESHOLD)) {
				scene.getMenuManager().selectPreviousEntry();
			}
			blockUpSelection++;
		} else {
			blockUpSelection = 0;
		}
		if (currentMenuRootNode.vertical && Keyboard.isKeyDown(Keyboard.KEY_DOWN) || !currentMenuRootNode.vertical && Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if (isMenuOpen && (blockDownSelection == 0 || blockDownSelection > KEYPRESS_REPEAT_THRESHOLD)) {
				scene.getMenuManager().selectNextEntry();
			}
			blockDownSelection++;
		} else {
			blockDownSelection = 0;
		}
		if (currentMenuRootNode.vertical && Keyboard.isKeyDown(Keyboard.KEY_LEFT) || !currentMenuRootNode.vertical && Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			if (!blockInSelection) {
				if (isMenuOpen) {
					if (currentMenu.equals(currentMenu.parent)) { // detect root node
						scene.getMenuManager().closeMenu(false);
					} else { // jump into parent node and select current menu
						scene.getMenuManager().setSelectedIndex(currentMenu.parent.childs.indexOf(currentMenu));
						scene.getMenuManager().setCurrentMenu(currentMenu.parent);
					}
				}
				blockInSelection = true;
			}
		} else {
			blockInSelection = false;
		}
		if (currentMenuRootNode.vertical && Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || !currentMenuRootNode.vertical && Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			if (!blockOutSelection) {
				if (isMenuOpen) {
					if (currentMenu != null && currentMenu.childs.size() > selectedIndex) {
						if (currentMenu.childs.get(selectedIndex).command.getType().equals(HUDCommandTypes.MENU)) {
							if (currentMenu.childs.get(selectedIndex) != null) {
								currentMenu = currentMenu.childs.get(selectedIndex);
								scene.getMenuManager().setCurrentMenu(currentMenu);
								scene.getMenuManager().setSelectedIndex(0);
							}
						} else {
							scene.getHudManager().addCommand(currentMenu.childs.get(selectedIndex).command);
							scene.getMenuManager().closeMenu(false);
						}
					}
				}
				blockOutSelection = true;
			}
		} else {
			blockOutSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			if (!blockSelectSelection) {
				if (isMenuOpen) {
					if (currentMenu.childs.size() > selectedIndex) {
						if (!currentMenu.childs.get(selectedIndex).command.getType().equals(HUDCommandTypes.MENU)) {
							scene.getHudManager().addCommand(currentMenu.childs.get(selectedIndex).command);
							scene.getMenuManager().closeMenu();
						}
					}
				}
				blockSelectSelection = true;
			}
		} else {
			blockSelectSelection = false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeCommand(HUDCommand command) {
		switch (command.getType()) {
			case EDIT:
			case EDIT_DONE:
				scene.getMenuManager().blockFor(KEYPRESS_REPEAT_THRESHOLD);
				break;
			case OPEN_MENU_HUD:
				MenuHUD menuHUD = (MenuHUD) scene.getHudManager().getByType2((Class<HUD>) command.getPayLoad());
				Object payload2 = command.getPayLoad2();
				if (payload2 == null) {
					menuHUD.openMenu();
				} else {
					HUDMenuEntry menu = scene.getMenuManager().openDynamicMenu((Class<DynamicMenu>) payload2);
					menuHUD.setMenu(menu);
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void update() {}

}
