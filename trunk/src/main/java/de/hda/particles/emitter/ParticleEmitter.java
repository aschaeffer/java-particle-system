package de.hda.particles.emitter;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.AutoDependency;
import de.hda.particles.ParticleSystem;
import de.hda.particles.Updateable;
import de.hda.particles.domain.Identifiable;
import de.hda.particles.domain.ParticleEmitterConfiguration;

public interface ParticleEmitter extends Updateable, AutoDependency, Identifiable {

	public Integer getRate();
	public void setRate(Integer rate);
	public ParticleSystem getParticleSystem();
	public void setParticleSystem(ParticleSystem particleSystem);
	public void setConfiguration(ParticleEmitterConfiguration configuration);
	public ParticleEmitterConfiguration getConfiguration();
	public void updateConfiguration(String key, Object value);
	public Vector3f getPosition();
	public Float getX();
	public Float getY();
	public Float getZ();
	public void setPosition(Vector3f position);
	public Vector3f getParticleDefaultVelocity();
	public void setParticleDefaultVelocity(Vector3f defaultVelocity);
	public Integer getParticleRendererIndex();
	public void setParticleRendererIndex(Integer particleRendererIndex);
	public Integer getFaceRendererIndex();
	public void setFaceRendererIndex(Integer faceRendererIndex);
	public long getParticleLifetime();
	public void setParticleLifetime(Integer defaultLifetime);
	public Integer getPastIterations();
	public void setPastIterations(Integer pastIterations);

}
