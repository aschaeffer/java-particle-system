package de.hda.particles.emitter;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.ParticleSystem;
import de.hda.particles.Updateable;
import de.hda.particles.domain.ParticleEmitterConfiguration;

public interface ParticleEmitter extends Updateable {

	public void setParticleSystem(ParticleSystem particleSystem);
	public void setConfiguration(ParticleEmitterConfiguration configuration);
	public ParticleEmitterConfiguration getConfiguration();
	public void updateConfiguration(String key, Object value);
	public Vector3f getPosition();
	public void setPosition(Vector3f position);
	public Vector3f getParticleDefaultVelocity();
	public void setParticleDefaultVelocity(Vector3f defaultVelocity);
	public long getParticleLifetime();
	public void setParticleLifetime(Integer defaultLifetime);
	public Integer getPastIterations();
	public void setPastIterations(Integer pastIterations);

}
