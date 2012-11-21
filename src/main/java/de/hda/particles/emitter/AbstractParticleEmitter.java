package de.hda.particles.emitter;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.ParticleSystem;
import de.hda.particles.domain.ParticleEmitterConfiguration;

public abstract class AbstractParticleEmitter implements ParticleEmitter {

	protected ParticleSystem particleSystem;
	protected ParticleEmitterConfiguration configuration = new ParticleEmitterConfiguration();
	protected Integer rate = 3;
	protected Vector3f position = new Vector3f();
	protected Vector3f particleDefaultVelocity = new Vector3f();
	protected Integer particleRenderTypeIndex = 0;
	protected Integer particleLifetime = 5;
	protected Integer pastIterations = 0;
	
	@Override
	public Integer getRate() {
		return rate;
	}
	
	@Override
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	
	@Override
	public ParticleSystem getParticleSystem() {
		return particleSystem;
	}

	@Override
	public void setParticleSystem(ParticleSystem particleSystem) {
		this.particleSystem = particleSystem;
	}
	
	@Override
	public ParticleEmitterConfiguration getConfiguration() {
		return this.configuration;
	}

	@Override
	public void setConfiguration(ParticleEmitterConfiguration configuration) {
		this.configuration = configuration;
	}
	
	@Override
	public void updateConfiguration(String key, Object value) {
		this.configuration.put(key, value);
	}

	@Override
	public Vector3f getPosition() {
		return position;
	}

	@Override
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	@Override
	public Vector3f getParticleDefaultVelocity() {
		return particleDefaultVelocity;
	}

	@Override
	public void setParticleDefaultVelocity(Vector3f particleDefaultVelocity) {
		this.particleDefaultVelocity = particleDefaultVelocity;
	}

	@Override
	public Integer getParticleRenderTypeIndex() {
		return particleRenderTypeIndex;
	}

	@Override
	public void setParticleRenderTypeIndex(Integer particleRenderTypeIndex) {
		this.particleRenderTypeIndex = particleRenderTypeIndex;
	}

	@Override
	public long getParticleLifetime() {
		return particleLifetime;
	}

	@Override
	public void setParticleLifetime(Integer particleLifetime) {
		this.particleLifetime = particleLifetime;
	}

	@Override
	public Integer getPastIterations() {
		return pastIterations;
	}

	@Override
	public void setPastIterations(Integer pastIterations) {
		this.pastIterations = pastIterations;
	}

	@Override
	public Boolean isFinished() {
		return false;
	}

}
