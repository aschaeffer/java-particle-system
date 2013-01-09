package de.hda.particles.modifier.size;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleSize;
import de.hda.particles.modifier.ParticleModifier;

public class PulseSizeTransformation extends AbstractSizeModifier implements ParticleModifier {

	public final static Double DEFAULT_PULSE_INTERVAL_FACTOR = 25.0;

	public final static String PULSE_INTERVAL_FACTOR = "pulseSizeIntervalFactor";

	private Double pulseSizeIntervalFactor = DEFAULT_PULSE_INTERVAL_FACTOR;

	public PulseSizeTransformation() {}

	@Override
	public void prepare() {
		pulseSizeIntervalFactor = (Double) configuration.get(PULSE_INTERVAL_FACTOR);
		if (pulseSizeIntervalFactor == null) pulseSizeIntervalFactor = DEFAULT_PULSE_INTERVAL_FACTOR;
	}

	@Override
	public void update(Particle particle) {
		sizeBirth = (Double) particle.get(ParticleSize.SIZE_BIRTH);
		sizeDeath = (Double) particle.get(ParticleSize.SIZE_DEATH);
		if (sizeBirth == null || sizeDeath == null) return;
		particle.put(ParticleSize.CURRENT_SIZE, sizeBirth + Math.sin(particle.getPastIterations() / pulseSizeIntervalFactor) * (sizeDeath - sizeBirth));
	}

}
