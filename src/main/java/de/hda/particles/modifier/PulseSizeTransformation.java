package de.hda.particles.modifier;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleSize;

public class PulseSizeTransformation extends AbstractParticleModifier implements ParticleModifier {

	private final static Double DEFAULT_PULSE_INTERVAL_FACTOR = 25.0;

	public PulseSizeTransformation() {}

	@Override
	public void update(Particle particle) {
		Integer p = particle.getPastIterations();
		Double sizeBirth = (Double) particle.get(ParticleSize.SIZE_BIRTH);
		if (sizeBirth == null) return;
		Double sizeDeath = (Double) particle.get(ParticleSize.SIZE_DEATH);
		if (sizeDeath == null) return;
		Double diff = sizeDeath - sizeBirth;
		Double size = sizeBirth + Math.sin(p / DEFAULT_PULSE_INTERVAL_FACTOR) * diff; 
		particle.put(ParticleSize.CURRENT_SIZE, size);
	}

}
