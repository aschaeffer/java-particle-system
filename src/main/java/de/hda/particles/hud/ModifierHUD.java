package de.hda.particles.hud;

import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.scene.Scene;

public class ModifierHUD extends AbstractHUD implements HUD {

	public ModifierHUD() {}

	public ModifierHUD(Scene scene) {
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
		if (command.getType() == HUDCommandTypes.ADD_MODIFIER) {
			scene.getParticleSystem().beginModification();
			ParticleModifierConfiguration configuration = new ParticleModifierConfiguration();
			Class configurationFactoryClass = (Class) command.getPayLoad2();
			if (configurationFactoryClass != null) {
				try {
					configuration = (ParticleModifierConfiguration) configurationFactoryClass.getMethod("create", Scene.class).invoke(null, scene);
				} catch (Exception e) {}
			}
			scene.getParticleSystem().addParticleModifier((Class) command.getPayLoad(), configuration);
			scene.getParticleSystem().endModification();
		}
//		if (command.getType() == HUDCommandTypes.REMOVE_MODIFIER) {
//			scene.getParticleSystem().re
//		}
	}

}
