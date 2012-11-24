package de.hda.particles.domain;

import de.hda.particles.modifier.GravityPoint;
import de.hda.particles.scene.Scene;

public class BlackHoleConfigurationFactory {

	private BlackHoleConfigurationFactory() {}

	public static ParticleModifierConfiguration create(Scene scene) {
		ParticleModifierConfiguration configuration = new ParticleModifierConfiguration();
		configuration.put(GravityPoint.POINT_X, new Double(scene.getCameraManager().getPosition().x));
		configuration.put(GravityPoint.POINT_Y, new Double(scene.getCameraManager().getPosition().y));
		configuration.put(GravityPoint.POINT_Z, new Double(scene.getCameraManager().getPosition().z));
		configuration.put(GravityPoint.GRAVITY, GravityPoint.DEFAULT_GRAVITY);
		configuration.put(GravityPoint.MASS, GravityPoint.DEFAULT_MASS);
		configuration.put(GravityPoint.MAX_FORCE, GravityPoint.DEFAULT_MAX_FORCE);
		return configuration;
	}

}
