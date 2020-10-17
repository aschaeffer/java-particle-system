package de.hda.particles.modifier.impl;

import de.hda.particles.ParticleSystem;
import de.hda.particles.domain.impl.configuration.ParticleModifierConfiguration;
import de.hda.particles.modifier.ParticleModifier;

public abstract class AbstractParticleModifier implements ParticleModifier {

	protected ParticleSystem particleSystem;
	protected ParticleModifierConfiguration configuration = new ParticleModifierConfiguration();

	@Override
	public void prepare() {
	}

	@Override
	public void setParticleSystem(ParticleSystem particleSystem) {
		this.particleSystem = particleSystem;
	}

	@Override
	public void setConfiguration(ParticleModifierConfiguration configuration) {
		this.configuration = configuration;
	}
	
	@Override
	public ParticleModifierConfiguration getConfiguration() {
		return this.configuration;
	}
	
	@Override
	public void updateConfiguration(String key, Object value) {
		this.configuration.put(key, value);
	}
	
	public Boolean expectKeys() {
		return true;
	}

	@Override
	public void addDependencies() {
	}

}
