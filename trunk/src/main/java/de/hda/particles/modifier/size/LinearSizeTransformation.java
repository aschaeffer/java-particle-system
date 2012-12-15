package de.hda.particles.modifier.size;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleSize;
import de.hda.particles.modifier.ParticleModifier;

public class LinearSizeTransformation extends AbstractSizeModifier implements ParticleModifier {

	public LinearSizeTransformation() {}

	@Override
	public void update(Particle particle) {
		if (!expectKeys()) return;
		sizeBirth = (Double) particle.get(ParticleSize.SIZE_BIRTH);
		sizeDeath = (Double) particle.get(ParticleSize.SIZE_DEATH);
		particle.put(ParticleSize.CURRENT_SIZE, sizeBirth * particle.getLifetimePercent() + sizeDeath * (1.0 - particle.getLifetimePercent()));
	}

}
