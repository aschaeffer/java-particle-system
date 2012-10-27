package de.hda.particles.emitter;

import de.hda.particles.ParticleSystem;
import de.hda.particles.Updateable;
import de.hda.particles.domain.Vector3;

public interface ParticleEmitter extends Updateable {

	public void setParticleSystem(ParticleSystem particleSystem);
	public Vector3 getPosition();
	public void setPosition(Vector3 position);
	public Vector3 getParticleDefaultVelocity();
	public void setParticleDefaultVelocity(Vector3 defaultVelocity);
	public long getParticleLifetime();
	public void setParticleLifetime(Integer defaultLifetime);
	
}
