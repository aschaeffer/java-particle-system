package de.hda.particles.configuration.factory;

import de.hda.particles.configuration.impl.ParticleEmitterConfiguration;
import de.hda.particles.scene.Scene;

public class ParticleEmitterConfigurationFactory {

	private ParticleEmitterConfigurationFactory() {}

	public static ParticleEmitterConfiguration create(Scene scene) {
		ParticleEmitterConfiguration configuration = new ParticleEmitterConfiguration();
		return configuration;
	}

}
