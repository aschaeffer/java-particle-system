package de.hda.particles.emitter;

import de.hda.particles.domain.Particle;

public class PlaneParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	private Float width = 10.0f;
	private Float height = 10.0f;
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

	public Float getWidth() {
		return width;
	}

	public void setWidth(Float width) {
		this.width = width;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

}
