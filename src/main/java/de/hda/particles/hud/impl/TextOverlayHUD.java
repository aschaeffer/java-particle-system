package de.hda.particles.hud.impl;

import de.hda.particles.hud.HUD;
import org.lwjgl.input.Keyboard;

import de.hda.particles.scene.Scene;

public class TextOverlayHUD extends AbstractHUD implements HUD {

	private Boolean blockTextOverlaySelection = false;

	public TextOverlayHUD() {}

	public TextOverlayHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void input() {
		if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
			if (!blockTextOverlaySelection) {
				Boolean newState = !scene.getTextOverlayManager().getEnabled();
				scene.getTextOverlayManager().setEnabled(newState);
				if (newState) {
					scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Enabled Text Overlay"));
				} else {
					scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Disabled Text Overlay"));
				}
				blockTextOverlaySelection = true;
			}
		} else {
			blockTextOverlaySelection = false;
		}
	}

	@Override
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.SHOW_TEXT_OVERLAY) {
			scene.getTextOverlayManager().setEnabled(true);
		} else if (command.getType() == HUDCommandTypes.HIDE_TEXT_OVERLAY) {
			scene.getTextOverlayManager().setEnabled(false);
		}
	}

	@Override
	public void update() {}
	
}
