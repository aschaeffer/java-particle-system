package de.hda.particles.hud.impl;

import de.hda.particles.configuration.impl.ParticleModifierConfiguration;
import de.hda.particles.hud.HUD;
import de.hda.particles.modifier.ParticleModifier;
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
			Class<? extends ParticleModifier> clazz = (Class<? extends ParticleModifier>) command.getPayLoad();
			scene.getParticleSystem().addParticleModifier(clazz, configuration);
			scene.getParticleSystem().endModification();
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Added Modifier: " + clazz.getSimpleName()));
		}
//		if (command.getType() == HUDCommandTypes.REMOVE_MODIFIER) {
//			scene.getParticleSystem().re
//		}
	}

}
