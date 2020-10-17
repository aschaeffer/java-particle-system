package de.hda.particles.modifier.impl.size;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.impl.ParticleSize;
import de.hda.particles.modifier.ParticleModifier;

public class LinearSizeTransformation extends AbstractSizeModifier implements ParticleModifier {

	public LinearSizeTransformation() {}

	@Override
	public void update(Particle particle) {
		sizeBirth = (Double) particle.get(ParticleSize.SIZE_BIRTH);
		sizeDeath = (Double) particle.get(ParticleSize.SIZE_DEATH);
		if (sizeBirth == null || sizeDeath == null) return;
		particle.put(ParticleSize.CURRENT_SIZE, sizeBirth * (1.0 - particle.getLifetimePercent()) + sizeDeath * particle.getLifetimePercent());
	}

}
