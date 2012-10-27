package de.hda.particles.emitter;

import de.hda.particles.ParticleSystem;
import de.hda.particles.domain.Vector3;

public abstract class AbstractParticleEmitter implements ParticleEmitter {

	protected Vector3 position = new Vector3();
	protected Vector3 defaultVelocity = new Vector3();
	protected Integer defaultLifetime = 5;
	
	protected ParticleSystem particleSystem;
	
	public AbstractParticleEmitter(ParticleSystem particleSystem, Vector3 position, Vector3 defaultVelocity, Integer defaultLifetime) {
		this.particleSystem = particleSystem;
		this.position = position;
		this.defaultVelocity = defaultVelocity;
		this.defaultLifetime = defaultLifetime;
	}
	
	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public Vector3 getDefaultVelocity() {
		return defaultVelocity;
	}

	public void setDefaultVelocity(Vector3 defaultVelocity) {
		this.defaultVelocity = defaultVelocity;
	}

	public long getDefaultLifetime() {
		return defaultLifetime;
	}

	public void setDefaultLifetime(Integer defaultLifetime) {
		this.defaultLifetime = defaultLifetime;
	}


}
