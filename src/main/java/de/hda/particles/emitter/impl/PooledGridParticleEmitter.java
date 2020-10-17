package de.hda.particles.emitter.impl;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.features.ParticleFeature;

public class PooledGridParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	private float dx = 0.0f;
	private float dz = 0.0f;
	
	public PooledGridParticleEmitter() {}

	@Override
	public void update() {
		pastIterations++;
		dx = (particleDefaultVelocity.x * rate) / 2.0f;
		dz = (particleDefaultVelocity.y * rate) / 2.0f;
		for (Integer x = 0; x < rate; x++) {
			for (Integer z = 0; z < rate; z++) {
				createParticle(x, z);
			}
		}
	}
	
	private void createParticle(Integer x, Integer z) {
		Particle particle = particleSystem.getParticlePool().next();
		particle.setPastIterations(0);
		particle.setMass(Particle.DEFAULT_MASS);
		particle.setVisibility(true);
		particle.setIndex(0);
		particle.clear();
		particle.setX(position.x + (x * particleDefaultVelocity.x) - dx);
		particle.setY(position.y);
		particle.setZ(position.z + (z * particleDefaultVelocity.z) - dz);
		particle.setVelX(0.0f);
		particle.setVelY(particleDefaultVelocity.y);
		particle.setVelZ(0.0f);
		particle.setParticleRendererIndex(particleRendererIndex);
		particle.setRemainingIterations(particleLifetime);
		for (ParticleFeature particleFeature: particleSystem.getParticleFeatures()) {
			particleFeature.init(this, particle);
		}
		particleSystem.addParticle(particle);
	}

	@Override
	public void setup() {}

	@Override
	public void destroy() {}

}
