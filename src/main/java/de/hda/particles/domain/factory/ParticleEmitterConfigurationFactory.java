package de.hda.particles.domain.factory;

import de.hda.particles.domain.impl.configuration.ParticleEmitterConfiguration;
import de.hda.particles.scene.Scene;

public class ParticleEmitterConfigurationFactory {

	private ParticleEmitterConfigurationFactory() {}

	public static ParticleEmitterConfiguration create(Scene scene) {
		ParticleEmitterConfiguration configuration = new ParticleEmitterConfiguration();
		return configuration;
	}

}
