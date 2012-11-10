package de.hda.particles.modifier;

import de.hda.particles.ParticleSystem;
import de.hda.particles.domain.ParticleModifierConfiguration;

public abstract class AbstractParticleModifier {

	protected ParticleSystem particleSystem;
	protected ParticleModifierConfiguration configuration = new ParticleModifierConfiguration();

	public void setParticleSystem(ParticleSystem particleSystem) {
		this.particleSystem = particleSystem;
	}

	public void setConfiguration(ParticleModifierConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public ParticleModifierConfiguration getConfiguration() {
		return this.configuration;
	}
	
	public void updateConfiguration(String key, Object value) {
		this.configuration.put(key, value);
	}

}
