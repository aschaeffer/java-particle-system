package de.hda.particles.hud;

import de.hda.particles.menu.HUDMenuEntry;

public interface MenuHUD extends HUD {

	void openMenu();
	void closeMenu();
	void setMenu(HUDMenuEntry menu);

}
