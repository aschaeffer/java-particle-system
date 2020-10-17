package de.hda.particles.menu.dynamic;

import de.hda.particles.hud.impl.HUDCommand;
import de.hda.particles.hud.impl.HUDCommandTypes;
import java.util.ListIterator;

import org.lwjgl.input.Keyboard;

import de.hda.particles.menu.DynamicMenu;
import de.hda.particles.menu.HUDMenuEntry;

public class SelectTextureMenu implements DynamicMenu {

	@Override
	public HUDMenuEntry getMenu() {
		HUDMenuEntry menu = HUDMenuEntry.createRoot("Select Skybox", false);
		HUDMenuEntry.create(menu, "Forge Skybox", "skybox/forge", HUDCommandTypes.SELECT_SKYBOX, "forge");
		HUDMenuEntry.create(menu, "Gloom Skybox", "skybox/gloom", HUDCommandTypes.SELECT_SKYBOX, "gloom");
		HUDMenuEntry.create(menu, "Harmony Skybox", "skybox/harmony", HUDCommandTypes.SELECT_SKYBOX, "harmony");
		HUDMenuEntry.create(menu, "Interstellar Skybox", "skybox/interstellar", HUDCommandTypes.SELECT_SKYBOX, "interstellar");
		HUDMenuEntry.create(menu, "Ocean Skybox", "skybox/ocean", HUDCommandTypes.SELECT_SKYBOX, "ocean");
		HUDMenuEntry.create(menu, "Paze Skybox", "skybox/paze", HUDCommandTypes.SELECT_SKYBOX, "paze");
		HUDMenuEntry.create(menu, "Raspberry Skybox", "skybox/raspberry", HUDCommandTypes.SELECT_SKYBOX, "raspberry");
		HUDMenuEntry.create(menu, "Sleepy Hollow Skybox", "skybox/sleepyhollow", HUDCommandTypes.SELECT_SKYBOX, "sleepyhollow");
		ListIterator<HUDMenuEntry> iterator = menu.childs.listIterator(0);
		while (iterator.hasNext()) {
			HUDMenuEntry entry = iterator.next();
			entry.keyCommands.put(Keyboard.KEY_RCONTROL, new HUDCommand(HUDCommandTypes.SELECT_SKYBOX, entry.command.getPayLoad(), entry.command.getPayLoad2()));
		}
		return menu;
	}
}
