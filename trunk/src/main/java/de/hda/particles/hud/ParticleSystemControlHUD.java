package de.hda.particles.hud;

import org.lwjgl.input.Keyboard;

import de.hda.particles.scene.Scene;

public class ParticleSystemControlHUD extends AbstractHUD implements HUD {

	private Boolean blockPauseSelection = false;
	private Boolean blockEmitterSelection = false;
	private Boolean blockModifierSelection = false;
	private Boolean blockClearSelection = false;

	public ParticleSystemControlHUD() {}

	public ParticleSystemControlHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
			if (!blockPauseSelection) {
				scene.getParticleSystem().pause();
				if (scene.getParticleSystem().isPaused()) {
					scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Paused"));
				} else {
					scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Resumed"));
				}
				blockPauseSelection = true;
			}
		} else {
			blockPauseSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			if (!blockEmitterSelection) {
				scene.getParticleSystem().toggleEmitters();
				if (scene.getParticleSystem().areEmittersStopped()) {
					scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Emitters stopped"));
				} else {
					scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Emitters started"));
				}
				blockEmitterSelection = true;
			}
		} else {
			blockEmitterSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
			if (!blockModifierSelection) {
				scene.getParticleSystem().toggleModifiers();
				if (scene.getParticleSystem().areModifiersStopped()) {
					scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Modifiers stopped"));
				} else {
					scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Modifiers started"));
				}
				blockModifierSelection = true;
			}
		} else {
			blockModifierSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DELETE)) {
			if (!blockClearSelection) {
				scene.getParticleSystem().removeAllParticles();
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "All particles removed"));
				blockClearSelection = true;
			}
		} else {
			blockClearSelection = false;
		}

		String text = String.format("particles: %d", scene.getParticleSystem().getParticles().size());
        font.drawString(scene.getWidth() - font.getWidth(text) - 20, scene.getHeight() - 20, text);
	}

	@Override
	public void setup() {
		super.setup();
	}

}
