package de.hda.particles.emitter;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleFeature;

/**
 * This emitter is like the point particle emitter, but it uses
 * the particle pool for emittation of particles. This leads to
 * less memory consumption, less garbage collection and more
 * performance.
 * 
 * @author aschaeffer
 *
 */
public class PooledPointParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	public PooledPointParticleEmitter() {}

	/**
	 * Fetches a particle (new or recylced) from the particle pool,
	 * set default values and constructs particle features.
	 */
	@Override
	public void update() {
		pastIterations++;
		for (Integer i = 0; i < rate; i++) {
			Particle particle = particleSystem.getParticlePool().next();
			particle.setPastIterations(0);
			particle.setMass(Particle.DEFAULT_MASS);
			particle.setVisibility(true);
			particle.setIndex(0);
			particle.clear();
			particle.setPosition(position);
			particle.setVelocity(particleDefaultVelocity);
			particle.setParticleRendererIndex(particleRendererIndex);
			particle.setRemainingIterations(particleLifetime);
			for (ParticleFeature particleFeature: particleSystem.getParticleFeatures()) {
				particleFeature.init(this, particle);
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
