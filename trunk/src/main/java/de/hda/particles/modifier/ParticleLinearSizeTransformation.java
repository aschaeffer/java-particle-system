package de.hda.particles.modifier;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleSize;

public class ParticleLinearSizeTransformation extends AbstractParticleModifier implements ParticleModifier {

	public void update(Particle particle) {
		Float s = (Float) particle.get(ParticleSize.START_SIZE);
		Float e = (Float) particle.get(ParticleSize.END_SIZE);
		Float p = particle.getLifetimePercent();
		particle.put(ParticleSize.CURRENT_SIZE, s * p + e * (1.0f - p));
	}

}
