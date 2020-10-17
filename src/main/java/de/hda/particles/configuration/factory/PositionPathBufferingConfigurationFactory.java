package de.hda.particles.configuration.factory;

import de.hda.particles.configuration.impl.ParticleModifierConfiguration;
import de.hda.particles.scene.Scene;

public class PositionPathBufferingConfigurationFactory {

	private PositionPathBufferingConfigurationFactory() {}

	public static ParticleModifierConfiguration create(Scene scene) {
		ParticleModifierConfiguration configuration = new ParticleModifierConfiguration();
		return configuration;
	}

}
