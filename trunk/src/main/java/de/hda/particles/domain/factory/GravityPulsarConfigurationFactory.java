package de.hda.particles.domain.factory;

import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.modifier.PositionablePointModifier;
import de.hda.particles.modifier.gravity.GravityBase;
import de.hda.particles.modifier.gravity.GravityPulsar;
import de.hda.particles.scene.Scene;

public class GravityPulsarConfigurationFactory {

	private GravityPulsarConfigurationFactory() {}

	public static ParticleModifierConfiguration create(Scene scene) {
		ParticleModifierConfiguration configuration = new ParticleModifierConfiguration();
		configuration.put(PositionablePointModifier.POSITION_X, new Double(scene.getCameraManager().getPosition().x));
		configuration.put(PositionablePointModifier.POSITION_Y, new Double(scene.getCameraManager().getPosition().y));
		configuration.put(PositionablePointModifier.POSITION_Z, new Double(scene.getCameraManager().getPosition().z));
		configuration.put(GravityPulsar.MIN_GRAVITY, GravityPulsar.DEFAULT_MIN_GRAVITY);
		configuration.put(GravityPulsar.MAX_GRAVITY, GravityPulsar.DEFAULT_MAX_GRAVITY);
		configuration.put(GravityPulsar.MIN_MASS, GravityPulsar.DEFAULT_MIN_MASS);
		configuration.put(GravityPulsar.MAX_MASS, GravityPulsar.DEFAULT_MAX_MASS);
		configuration.put(GravityBase.MAX_FORCE, GravityPulsar.DEFAULT_MAX_FORCE);
		return configuration;
	}

}
