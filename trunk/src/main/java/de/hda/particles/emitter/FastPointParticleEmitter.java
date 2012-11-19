package de.hda.particles.emitter;

import de.hda.particles.domain.DefaultParticle;
import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleFeature;

/**
 * This particle emitter is  
 * @author aschaeffer
 *
 */
public class FastPointParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	private final Boolean initParticleFeatures = false;

	public FastPointParticleEmitter() {}

	/**
	 * Creates new particles and adds them to the particle system
	 */
	@Override
	public void update() {
		pastIterations++;
		// create new particles (emit)
		for (Integer i = 0; i < rate; i++) {
			Particle particle = new DefaultParticle(position, particleDefaultVelocity, particleRenderTypeIndex, particleLifetime);
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
