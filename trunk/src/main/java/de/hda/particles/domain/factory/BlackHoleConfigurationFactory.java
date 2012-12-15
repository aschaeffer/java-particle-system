package de.hda.particles.domain.factory;

import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.modifier.PositionablePointModifier;
import de.hda.particles.modifier.gravity.GravityBase;
import de.hda.particles.modifier.gravity.GravityPoint;
import de.hda.particles.scene.Scene;

public class BlackHoleConfigurationFactory {

	private BlackHoleConfigurationFactory() {}

	public static ParticleModifierConfiguration create(Scene scene) {
		ParticleModifierConfiguration configuration = new ParticleModifierConfiguration();
		configuration.put(PositionablePointModifier.POSITION_X, new Double(scene.getCameraManager().getPosition().x));
		configuration.put(PositionablePointModifier.POSITION_Y, new Double(scene.getCameraManager().getPosition().y));
		configuration.put(PositionablePointModifier.POSITION_Z, new Double(scene.getCameraManager().getPosition().z));
		configuration.put(GravityBase.GRAVITY, GravityPoint.DEFAULT_GRAVITY);
		configuration.put(GravityBase.MASS, GravityPoint.DEFAULT_MASS);
		configuration.put(GravityBase.MAX_FORCE, GravityPoint.DEFAULT_MAX_FORCE);
		return configuration;
	}

}
