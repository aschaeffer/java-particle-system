package de.hda.particles.domain.factory;

import de.hda.particles.domain.impl.configuration.ParticleModifierConfiguration;
import de.hda.particles.modifier.PositionablePlaneModifier;
import de.hda.particles.modifier.PositionablePointModifier;
import de.hda.particles.scene.Scene;

public class CollisionPlaneConfigurationFactory {

	private CollisionPlaneConfigurationFactory() {}

	public static ParticleModifierConfiguration create(Scene scene) {
		ParticleModifierConfiguration configuration = new ParticleModifierConfiguration();
		configuration.put(PositionablePointModifier.POSITION_X, new Double(scene.getCameraManager().getPosition().x));
		configuration.put(PositionablePointModifier.POSITION_Y, new Double(scene.getCameraManager().getPosition().y));
		configuration.put(PositionablePointModifier.POSITION_Z, new Double(scene.getCameraManager().getPosition().z));
		configuration.put(PositionablePlaneModifier.NORMAL_X, 0.0);
		configuration.put(PositionablePlaneModifier.NORMAL_Y, 1.0);
		configuration.put(PositionablePlaneModifier.NORMAL_Z, 0.0);
		return configuration;
	}

}
