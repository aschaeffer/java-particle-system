package de.hda.particles.domain.factory;

import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.modifier.gravity.GravityPoint;
import de.hda.particles.scene.Scene;

public class BlackHoleConfigurationFactory {

	private BlackHoleConfigurationFactory() {}

	public static ParticleModifierConfiguration create(Scene scene) {
		ParticleModifierConfiguration configuration = new ParticleModifierConfiguration();
		configuration.put(GravityPoint.POSITION_X, new Double(scene.getCameraManager().getPosition().x));
		configuration.put(GravityPoint.POSITION_Y, new Double(scene.getCameraManager().getPosition().y));
		configuration.put(GravityPoint.POSITION_Z, new Double(scene.getCameraManager().getPosition().z));
		configuration.put(GravityPoint.GRAVITY, GravityPoint.DEFAULT_GRAVITY);
		configuration.put(GravityPoint.MASS, GravityPoint.DEFAULT_MASS);
		configuration.put(GravityPoint.MAX_FORCE, GravityPoint.DEFAULT_MAX_FORCE);
		return configuration;
	}

}
