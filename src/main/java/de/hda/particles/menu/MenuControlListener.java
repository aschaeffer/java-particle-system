package de.hda.particles.menu;

public interface MenuControlListener {

	void onOpenMenu(HUDMenuEntry menu);
	void onCloseMenu(HUDMenuEntry menu);
	void onPreviousEntry(HUDMenuEntry menu, HUDMenuEntry entry);
	void onNextEntry(HUDMenuEntry menu, HUDMenuEntry entry);

}
