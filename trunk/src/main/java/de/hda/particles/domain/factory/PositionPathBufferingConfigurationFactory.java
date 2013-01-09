package de.hda.particles.domain.factory;

import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.scene.Scene;

public class PositionPathBufferingConfigurationFactory {

	private PositionPathBufferingConfigurationFactory() {}

	public static ParticleModifierConfiguration create(Scene scene) {
		ParticleModifierConfiguration configuration = new ParticleModifierConfiguration();
		return configuration;
	}

}
