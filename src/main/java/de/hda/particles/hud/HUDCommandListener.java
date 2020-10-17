package de.hda.particles.hud;

import de.hda.particles.hud.impl.HUDCommand;

public interface HUDCommandListener {

	void executeCommand(HUDCommand command);

}
