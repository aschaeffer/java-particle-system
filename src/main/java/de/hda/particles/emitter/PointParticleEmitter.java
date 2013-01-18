package de.hda.particles.emitter;

import de.hda.particles.domain.DefaultHashMapParticle;
import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleFeature;

/**
 * This particle emitter is  
 * @author aschaeffer
 *
 */
public class PointParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	public PointParticleEmitter() {}

	/**
	 * Creates new particles and adds them to the particle system
	 */
	@Override
	public void update() {
		pastIterations++;
		// create new particles (emit)
		for (Integer i = 0; i < rate; i++) {
			Particle particle = new DefaultHashMapParticle(position, particleDefaultVelocity, particleRendererIndex, particleLifetime);
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
