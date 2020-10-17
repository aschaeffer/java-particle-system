package de.hda.particles.hud.impl;

import de.hda.particles.hud.MenuHUD;
import de.hda.particles.menu.HUDMenuEntry;
import de.hda.particles.menu.MenuControlListener;
import de.hda.particles.scene.Scene;

public abstract class AbstractMenuHUD extends AbstractHUD implements MenuHUD, MenuControlListener {

	protected HUDMenuEntry menu;

	public AbstractMenuHUD() {
		super();
	}
	
	public AbstractMenuHUD(Scene scene) {
		super(scene);
	}
	
	public void openMenu() {
		if (!scene.getMenuManager().isMenuOpen(menu)) {
			scene.getMenuManager().openMenu(menu);
		}
	}

	public void closeMenu() {
		if (scene.getMenuManager().isMenuOpen(menu)) {
			scene.getMenuManager().closeMenu(menu);
		}
	}
	
	public void setMenu(HUDMenuEntry menu) {
		this.menu = menu;
	}
	
	@Override
	public void onOpenMenu(HUDMenuEntry menu) {}

	@Override
	public void onCloseMenu(HUDMenuEntry menu) {}

	@Override
	public void onPreviousEntry(HUDMenuEntry menu, HUDMenuEntry entry) {}

	@Override
	public void onNextEntry(HUDMenuEntry menu, HUDMenuEntry entry) {}

}
