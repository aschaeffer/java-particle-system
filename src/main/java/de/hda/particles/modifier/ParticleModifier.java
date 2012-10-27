package de.hda.particles.modifier;

import de.hda.particles.ParticleSystem;
import de.hda.particles.domain.ParticleModifierConfiguration;

public interface ParticleModifier extends ParticleUpdater {

	public void setParticleSystem(ParticleSystem particleSystem);
	public void setConfiguration(ParticleModifierConfiguration configuration);
	public void updateConfiguration(String key, Object value);

}
