package de.hda.particles.modifier.color;

import de.hda.particles.features.ParticleColor;
import de.hda.particles.modifier.AbstractParticleModifier;

public abstract class AbstractColorModifier extends AbstractParticleModifier {

	@Override
	public Boolean expectKeys() {
		return (configuration.containsKey(ParticleColor.START_COLOR)
			&& configuration.containsKey(ParticleColor.END_COLOR));
	}

	@Override
	public void addDependencies() {
		particleSystem.addParticleFeature(ParticleColor.class);
	}

}
