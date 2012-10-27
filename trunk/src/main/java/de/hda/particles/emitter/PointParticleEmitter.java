package de.hda.particles.emitter;

import de.hda.particles.domain.Particle;

public class PointParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	private Integer rate = 3;
	private Float maxScattering = 0.0f;

	public void update() {
		// create new particles (emit)
		for (Integer i = 0; i < rate; i++) {
			Particle particle = new Particle(position, particleDefaultVelocity, particleLifetime);
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
