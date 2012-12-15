package de.hda.particles.modifier.size;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleSize;
import de.hda.particles.modifier.ParticleModifier;

public class PulseSizeTransformation extends AbstractSizeModifier implements ParticleModifier {

	private final static Double DEFAULT_PULSE_INTERVAL_FACTOR = 25.0;

	public PulseSizeTransformation() {}

// TODO: make the pulse interval configurable
//	public void prepare() {
//	}

	@Override
	public void update(Particle particle) {
		if (!expectKeys()) return;
		sizeBirth = (Double) particle.get(ParticleSize.SIZE_BIRTH);
		sizeDeath = (Double) particle.get(ParticleSize.SIZE_DEATH);
		particle.put(ParticleSize.CURRENT_SIZE, sizeBirth + Math.sin(particle.getPastIterations() / DEFAULT_PULSE_INTERVAL_FACTOR) * (sizeDeath - sizeBirth));
	}

}
