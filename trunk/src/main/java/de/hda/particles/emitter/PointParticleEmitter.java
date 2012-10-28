package de.hda.particles.emitter;

import de.hda.particles.domain.Particle;
import de.hda.particles.domain.features.ParticleFeature;

/**
 * This particle emitter is  
 * @author aschaeffer
 *
 */
public class PointParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	private Integer rate = 3;
	private Float maxScattering = 0.0f;

	/**
	 * Creates new particles and adds them to the particle system
	 */
	public void update() {
		// create new particles (emit)
		for (Integer i = 0; i < rate; i++) {
			Particle particle = new Particle(position, particleDefaultVelocity, particleLifetime);
			for (ParticleFeature particleFeature: particleSystem.particleFeatures) {
				particleFeature.init(particle);
			}
			particleSystem.addParticle(particle);
		}
	}

	public Float getMaxScattering() {
		return maxScattering;
	}

	public void setMaxScattering(Float maxScattering) {
		this.maxScattering = maxScattering;
	}

}