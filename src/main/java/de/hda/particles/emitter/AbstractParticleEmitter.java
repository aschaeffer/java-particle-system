package de.hda.particles.emitter;

import de.hda.particles.ParticleSystem;
import de.hda.particles.domain.ParticleEmitterConfiguration;
import de.hda.particles.domain.Vector3;

public abstract class AbstractParticleEmitter implements ParticleEmitter {

	protected ParticleSystem particleSystem;
	protected ParticleEmitterConfiguration configuration = new ParticleEmitterConfiguration();
	protected Vector3 position = new Vector3();
	protected Vector3 particleDefaultVelocity = new Vector3();
	protected Integer particleLifetime = 5;
	
	public void setParticleSystem(ParticleSystem particleSystem) {
		this.particleSystem = particleSystem;
	}

	public void setConfiguration(ParticleEmitterConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public void updateConfiguration(String key, Object value) {
		this.configuration.put(key, value);
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public Vector3 getParticleDefaultVelocity() {
		return particleDefaultVelocity;
	}

	public void setParticleDefaultVelocity(Vector3 particleDefaultVelocity) {
		this.particleDefaultVelocity = particleDefaultVelocity;
	}

	public long getParticleLifetime() {
		return particleLifetime;
	}

	public void setParticleLifetime(Integer particleLifetime) {
		this.particleLifetime = particleLifetime;
	}
	
}
