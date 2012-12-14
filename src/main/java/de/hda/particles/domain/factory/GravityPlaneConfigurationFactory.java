package de.hda.particles.domain.factory;

import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.modifier.gravity.GravityPlane;
import de.hda.particles.scene.Scene;

public class GravityPlaneConfigurationFactory {

	private GravityPlaneConfigurationFactory() {}

	public static ParticleModifierConfiguration create(Scene scene) {
		ParticleModifierConfiguration configuration = new ParticleModifierConfiguration();
		configuration.put(GravityPlane.POSITION_X, new Double(scene.getCameraManager().getPosition().x));
		configuration.put(GravityPlane.POSITION_Y, new Double(scene.getCameraManager().getPosition().y));
		configuration.put(GravityPlane.POSITION_Z, new Double(scene.getCameraManager().getPosition().z));
		configuration.put(GravityPlane.NORMAL_X, 0.0);
		configuration.put(GravityPlane.NORMAL_Y, 1.0);
		configuration.put(GravityPlane.NORMAL_Z, 0.0);
		configuration.put(GravityPlane.GRAVITY, GravityPlane.DEFAULT_GRAVITY);
		configuration.put(GravityPlane.MASS, GravityPlane.DEFAULT_MASS);
		configuration.put(GravityPlane.MAX_FORCE, GravityPlane.DEFAULT_MAX_FORCE);
		return configuration;
	}

}
