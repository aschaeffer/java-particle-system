package de.hda.particles.emitter.impl;

import de.hda.particles.emitter.ParticleEmitter;
import java.util.Random;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleFeature;

/**
 * TODO: the ring needs a normal vector
 * 
 * @author aschaeffer
 *
 */
public class PooledSphereParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	private final Random random = new Random();
	
	public PooledSphereParticleEmitter() {}

	/**
	 * Creates new particles and adds them to the particle system
	 */
	@Override
	public void update() {
		pastIterations++;
		// create new particles (emit)
		for (Integer i = 0; i < rate; i++) {
			Particle particle = particleSystem.getParticlePool().next();
			particle.setPastIterations(0);
			particle.setMass(Particle.DEFAULT_MASS);
			particle.setVisibility(true);
			particle.setIndex(0);
			particle.clear();
			particle.setX(position.x);
			particle.setY(position.y);
			particle.setZ(position.z);
			particle.setVelX((random.nextFloat() * 2.0f - 1.0f) * particleDefaultVelocity.x);
			particle.setVelY((random.nextFloat() * 2.0f - 1.0f) * particleDefaultVelocity.y);
			particle.setVelZ((random.nextFloat() * 2.0f - 1.0f) * particleDefaultVelocity.z);
			particle.setParticleRendererIndex(particleRendererIndex);
			particle.setRemainingIterations(particleLifetime);
			for (ParticleFeature particleFeature: particleSystem.getParticleFeatures()) {
				particleFeature.init(this, particle);
			}
			particleSystem.addParticle(particle);
		}
	}

	@Override
	public void setup() {}

	@Override
	public void destroy() {}

}
