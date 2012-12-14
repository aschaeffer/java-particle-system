package de.hda.particles.modifier.size;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleSize;
import de.hda.particles.modifier.AbstractParticleModifier;
import de.hda.particles.modifier.ParticleModifier;

public class PulseSizeTransformation extends AbstractParticleModifier implements ParticleModifier {

	private final static Double DEFAULT_PULSE_INTERVAL_FACTOR = 25.0;
	
	public PulseSizeTransformation() {}

// TODO: make the pulse interval configurable
//	public void prepare() {
//	}

	@Override
	public void update(Particle particle) {
		Integer p = particle.getPastIterations();
		Double sizeBirth = (Double) particle.get(ParticleSize.SIZE_BIRTH);
		if (sizeBirth == null) return;
		Double sizeDeath = (Double) particle.get(ParticleSize.SIZE_DEATH);
		if (sizeDeath == null) return;
		Double size = sizeBirth + Math.sin(p / DEFAULT_PULSE_INTERVAL_FACTOR) * (sizeDeath - sizeBirth); 
		particle.put(ParticleSize.CURRENT_SIZE, size);
	}

}