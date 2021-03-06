package de.hda.particles.emitter.impl;

import de.hda.particles.domain.impl.particle.StaticParticle;
import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.features.ParticleFeature;

public class StaticPointParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	private final Boolean initParticleFeatures = false;

	public StaticPointParticleEmitter() {}

	/**
	 * Creates new particles and adds them to the particle system
	 */
	@Override
	public void update() {
		pastIterations++;
		// create new particles (emit)
		for (Integer i = 0; i < rate; i++) {
			Particle particle = new StaticParticle(position, particleDefaultVelocity, particleRendererIndex, particleLifetime);
			if (initParticleFeatures) {
				for (ParticleFeature particleFeature: particleSystem.getParticleFeatures()) {
					particleFeature.init(this, particle);
				}
			}
			particleSystem.addParticle(particle);
		}
	}

	@Override
	public void setup() {
	}

	@Override
	public void destroy() {
	}

}
