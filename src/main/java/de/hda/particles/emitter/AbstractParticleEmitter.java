package de.hda.particles.emitter;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.ParticleSystem;
import de.hda.particles.domain.ParticleEmitterConfiguration;

public abstract class AbstractParticleEmitter implements ParticleEmitter {

	protected ParticleSystem particleSystem;
	protected ParticleEmitterConfiguration configuration = new ParticleEmitterConfiguration();
	protected Vector3f position = new Vector3f();
	protected Vector3f particleDefaultVelocity = new Vector3f();
	protected Integer particleLifetime = 5;
	protected Integer pastIterations = 0;
	
	public void setParticleSystem(ParticleSystem particleSystem) {
		this.particleSystem = particleSystem;
	}

	public void setConfiguration(ParticleEmitterConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public ParticleEmitterConfiguration getConfiguration() {
		return this.configuration;
	}
	
	public void updateConfiguration(String key, Object value) {
		this.configuration.put(key, value);
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getParticleDefaultVelocity() {
		return particleDefaultVelocity;
	}

	public void setParticleDefaultVelocity(Vector3f particleDefaultVelocity) {
		this.particleDefaultVelocity = particleDefaultVelocity;
	}

	public long getParticleLifetime() {
		return particleLifetime;
	}

	public void setParticleLifetime(Integer particleLifetime) {
		this.particleLifetime = particleLifetime;
	}

	public Integer getPastIterations() {
		return pastIterations;
	}

	public void setPastIterations(Integer pastIterations) {
		this.pastIterations = pastIterations;
	}

	@Override
	public Boolean isFinished() {
		return false;
	}

}
