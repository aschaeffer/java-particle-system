package de.hda.particles.modifier;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleSize;

public class LinearSizeTransformation extends AbstractParticleModifier implements ParticleModifier {

	public LinearSizeTransformation() {}

	@Override
	public void update(Particle particle) {
		Double sizeBirth = (Double) particle.get(ParticleSize.SIZE_BIRTH);
		if (sizeBirth == null) return;
		Double sizeDeath = (Double) particle.get(ParticleSize.SIZE_DEATH);
		if (sizeDeath == null) return;
		Float p = particle.getLifetimePercent();
		particle.put(ParticleSize.CURRENT_SIZE, sizeBirth * p + sizeDeath * (1.0 - p));
	}

}
