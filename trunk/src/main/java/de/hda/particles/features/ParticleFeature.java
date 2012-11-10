package de.hda.particles.features;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;

public interface ParticleFeature {

	public void init(ParticleEmitter emitter, Particle particle);

}
