package de.hda.particles.modifier.size;

import de.hda.particles.features.ParticleSize;
import de.hda.particles.modifier.AbstractParticleModifier;

public abstract class AbstractSizeModifier extends AbstractParticleModifier {

	protected Double sizeBirth;
	protected Double sizeDeath;

	@Override
	public Boolean expectKeys() {
		return (configuration.containsKey(ParticleSize.SIZE_BIRTH)
			&& configuration.containsKey(ParticleSize.SIZE_DEATH));
	}

}
