package de.hda.particles.features;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;

public class ParticleSize implements ParticleFeature {

	public static final String START_SIZE = "startSize";
	public static final String END_SIZE = "endSize";
	public static final String CURRENT_SIZE = "endSize";

	public void init(ParticleEmitter emitter, Particle particle) {
		particle.put(START_SIZE, emitter.getConfiguration().get(START_SIZE));
		particle.put(END_SIZE, emitter.getConfiguration().get(END_SIZE));
		particle.put(CURRENT_SIZE, emitter.getConfiguration().get(START_SIZE));
	}

}
