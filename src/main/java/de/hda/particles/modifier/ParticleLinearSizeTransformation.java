package de.hda.particles.modifier;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleSize;

public class ParticleLinearSizeTransformation extends AbstractParticleModifier implements ParticleModifier {

	public ParticleLinearSizeTransformation() {}

	public void update(Particle particle) {
		Float s = (Float) particle.get(ParticleSize.START_SIZE);
		if (s == null) return;
		Float e = (Float) particle.get(ParticleSize.END_SIZE);
		if (e == null) return;
		Float p = particle.getLifetimePercent();
		particle.put(ParticleSize.CURRENT_SIZE, s * p + e * (1.0f - p));
	}

}
