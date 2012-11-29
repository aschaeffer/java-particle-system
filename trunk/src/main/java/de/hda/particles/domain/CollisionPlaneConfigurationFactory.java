package de.hda.particles.domain;

import de.hda.particles.modifier.CollisionPlane;
import de.hda.particles.scene.Scene;

public class CollisionPlaneConfigurationFactory {

	private CollisionPlaneConfigurationFactory() {}

	public static ParticleModifierConfiguration create(Scene scene) {
		ParticleModifierConfiguration configuration = new ParticleModifierConfiguration();
		configuration.put(CollisionPlane.POSITION_X, new Double(scene.getCameraManager().getPosition().x));
		configuration.put(CollisionPlane.POSITION_Y, new Double(scene.getCameraManager().getPosition().y));
		configuration.put(CollisionPlane.POSITION_Z, new Double(scene.getCameraManager().getPosition().z));
		configuration.put(CollisionPlane.NORMAL_X, 0.0);
		configuration.put(CollisionPlane.NORMAL_Y, 1.0);
		configuration.put(CollisionPlane.NORMAL_Z, 0.0);
		return configuration;
	}

}
