package de.hda.particles.emitter;

import de.hda.particles.ParticleSystem;
import de.hda.particles.domain.Particle;
import de.hda.particles.domain.Vector3;

public class PointParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	private Integer rate = 3;
	private Float maxScattering = 0.0f;

	public PointParticleEmitter(ParticleSystem particleSystem, Vector3 position, Vector3 defaultVelocity, Integer defaultLifetime) {
		super(particleSystem, position, defaultVelocity, defaultLifetime);
	}

	public void update() {
		// create new particles (emit)
		for (Integer i = 0; i < rate; i++) {
			Particle particle = new Particle(position, defaultVelocity, defaultLifetime);
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
