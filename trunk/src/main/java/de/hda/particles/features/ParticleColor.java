package de.hda.particles.features;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;

public class ParticleColor implements ParticleFeature {

	public static final String START_COLOR = "startColor";
	public static final String END_COLOR = "endColor";
	public static final String CURRENT_COLOR = "currentColor";

	public void init(ParticleEmitter emitter, Particle particle) {
		particle.put(START_COLOR, emitter.getConfiguration().get(START_COLOR));
		particle.put(END_COLOR, emitter.getConfiguration().get(END_COLOR));
		particle.put(CURRENT_COLOR, emitter.getConfiguration().get(START_COLOR));
	}

}
