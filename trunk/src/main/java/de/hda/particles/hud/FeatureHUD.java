package de.hda.particles.hud;

import de.hda.particles.scene.Scene;

public class FeatureHUD extends AbstractHUD implements HUD {

	public FeatureHUD() {}

	public FeatureHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void update() {
	}

	@Override
	public void setup() {
		super.setup();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.ADD_FEATURE) {
			scene.getParticleSystem().beginModification();
			scene.getParticleSystem().addParticleFeature((Class) command.getPayLoad());
			scene.getParticleSystem().endModification();
		}
	}

}
