package de.hda.particles.emitter;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.AutoDependency;
import de.hda.particles.ParticleSystem;
import de.hda.particles.Updatable;
import de.hda.particles.domain.Identifiable;
import de.hda.particles.configuration.impl.ParticleEmitterConfiguration;

public interface ParticleEmitter extends Updatable, AutoDependency, Identifiable {

	Integer getRate();
	void setRate(Integer rate);

	ParticleSystem getParticleSystem();
	void setParticleSystem(ParticleSystem particleSystem);

	void setConfiguration(ParticleEmitterConfiguration configuration);
	ParticleEmitterConfiguration getConfiguration();
	void updateConfiguration(String key, Object value);

	Float getX();
	Float getY();
	Float getZ();

	Vector3f getPosition();
	void setPosition(Vector3f position);

	Vector3f getParticleDefaultVelocity();
	void setParticleDefaultVelocity(Vector3f defaultVelocity);

	Integer getParticleRendererIndex();
	void setParticleRendererIndex(Integer particleRendererIndex);

	Integer getFaceRendererIndex();
	void setFaceRendererIndex(Integer faceRendererIndex);

	long getParticleLifetime();
	void setParticleLifetime(Integer defaultLifetime);

	Integer getPastIterations();
	void setPastIterations(Integer pastIterations);

}
