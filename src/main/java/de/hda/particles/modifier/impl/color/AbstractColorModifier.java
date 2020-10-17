package de.hda.particles.modifier.impl.color;

import de.hda.particles.features.impl.ParticleColor;
import de.hda.particles.modifier.impl.AbstractParticleModifier;

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
