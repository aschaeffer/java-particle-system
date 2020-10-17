package de.hda.particles.emitter.impl;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;
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
public class PooledFieldParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	private float dx = 0.0f;
	private float dy = 0.0f;
	private float dz = 0.0f;
	
	public PooledFieldParticleEmitter() {}

	/**
	 * Fetches a particle (new or recylced) from the particle pool,
	 * set default values and constructs particle features.
	 */
	@Override
	public void update() {
		pastIterations++;
		dx = (particleDefaultVelocity.x * rate) / 2.0f;
		dy = (particleDefaultVelocity.y * rate) / 2.0f;
		dz = (particleDefaultVelocity.z * rate) / 2.0f;
		for (Integer x = 0; x < rate; x++) {
			for (Integer y = 0; y < rate; y++) {
				for (Integer z = 0; z < rate; z++) {
					createParticle(x,y,z);
				}
			}
		}
	}
	
	public void createParticle(Integer x, Integer y, Integer z) {
		Particle particle = particleSystem.getParticlePool().next();
		particle.setPastIterations(0);
		particle.setMass(Particle.DEFAULT_MASS);
		particle.setVisibility(true);
		particle.setIndex(0);
		particle.clear();
		particle.setX(position.x + (x * particleDefaultVelocity.x) - dx);
		particle.setY(position.y + (y * particleDefaultVelocity.y) - dy);
		particle.setZ(position.z + (z * particleDefaultVelocity.z) - dz);
		particle.setVelX(0.0f);
		particle.setVelY(0.0f);
		particle.setVelZ(0.0f);
		// particle.setVelocity(particleDefaultVelocity);
		particle.setParticleRendererIndex(particleRendererIndex);
		particle.setRemainingIterations(particleLifetime);
		for (ParticleFeature particleFeature: particleSystem.getParticleFeatures()) {
			particleFeature.init(this, particle);
		}
		particleSystem.addParticle(particle);
	}

	@Override
	public void setup() {
	}

	@Override
	public void destroy() {
	}

}
