package de.hda.particles.hud;

import de.hda.particles.features.ParticleFeature;
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

	@SuppressWarnings({ "unchecked" })
	@Override
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.ADD_FEATURE) {
			Class<? extends ParticleFeature> clazz = (Class<? extends ParticleFeature>) command.getPayLoad();
			scene.getParticleSystem().beginModification();
			scene.getParticleSystem().addParticleFeature(clazz);
			scene.getParticleSystem().endModification();
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Added Particle Feature: " + clazz.getSimpleName()));
		}
	}

}
