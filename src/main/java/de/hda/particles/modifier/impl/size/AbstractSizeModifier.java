package de.hda.particles.modifier.impl.size;

import de.hda.particles.features.impl.ParticleSize;
import de.hda.particles.modifier.impl.AbstractParticleModifier;

public abstract class AbstractSizeModifier extends AbstractParticleModifier {

	protected Double sizeBirth;
	protected Double sizeDeath;

//	@Override
//	public Boolean expectKeys() {
//		return (configuration.containsKey(ParticleSize.SIZE_BIRTH)
//			&& configuration.containsKey(ParticleSize.SIZE_DEATH));
//	}
//
	@Override
	public void addDependencies() {
		particleSystem.addParticleFeature(ParticleSize.class);
	}

}
