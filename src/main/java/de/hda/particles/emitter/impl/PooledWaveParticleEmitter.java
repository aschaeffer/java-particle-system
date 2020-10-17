package de.hda.particles.emitter.impl;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.features.ParticleFeature;

/**
 * This particle emitter is  
 * @author aschaeffer
 *
 */
public class PooledWaveParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	public PooledWaveParticleEmitter() {}

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
			particle.setX(position.x + new Double(Math.sin(pastIterations * particleDefaultVelocity.x)).floatValue());
			particle.setY(position.y + new Double(Math.sin(pastIterations * particleDefaultVelocity.y)).floatValue());
			particle.setZ(position.z + new Double(Math.sin(pastIterations * particleDefaultVelocity.z)).floatValue());
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
