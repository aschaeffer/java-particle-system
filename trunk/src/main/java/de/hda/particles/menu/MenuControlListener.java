package de.hda.particles.menu;

public interface MenuControlListener {

	public void onOpenMenu(HUDMenuEntry menu);
	public void onCloseMenu(HUDMenuEntry menu);
	public void onPreviousEntry(HUDMenuEntry menu, HUDMenuEntry entry);
	public void onNextEntry(HUDMenuEntry menu, HUDMenuEntry entry);

}
