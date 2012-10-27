package de.hda.particles;

public class PlaneParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	private Integer rate = 3;
	private Float maxScattering = 0.0f;

	public PlaneParticleEmitter(ParticleSystem particleSystem, Vector3 position, Vector3 defaultVelocity, Integer defaultLifetime) {
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
