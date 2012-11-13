package de.hda.particles.hud;

import org.lwjgl.input.Keyboard;

import de.hda.particles.scene.Scene;

public class ParticleSystemControlHUD extends AbstractHUD implements HUD {

	private Boolean blockPauseSelection = false;
	private Boolean blockEmitterSelection = false;
	private Boolean blockModifierSelection = false;

	public ParticleSystemControlHUD() {}

	public ParticleSystemControlHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
			if (!blockPauseSelection) {
				scene.getParticleSystem().pause();
				blockPauseSelection = true;
			}
		} else {
			blockPauseSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			if (!blockEmitterSelection) {
				scene.getParticleSystem().toggleEmitters();
				blockEmitterSelection = true;
			}
		} else {
			blockEmitterSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
			if (!blockModifierSelection) {
				scene.getParticleSystem().toggleModifiers();
				blockModifierSelection = true;
			}
		} else {
			blockModifierSelection = false;
		}

		String text = String.format("particles: %d", scene.getParticleSystem().particles.size());
        font.drawString(scene.getWidth() - font.getWidth(text) - 20, scene.getHeight() - 20, text);
	}

	@Override
	public void setup() {
		super.setup();
	}

}
