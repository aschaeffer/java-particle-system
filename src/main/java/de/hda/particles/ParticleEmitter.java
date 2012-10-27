package de.hda.particles;

public interface ParticleEmitter extends Updateable {

	public Vector3 getPosition();
	public void setPosition(Vector3 position);
	public Vector3 getDefaultVelocity();
	public void setDefaultVelocity(Vector3 defaultVelocity);
	public long getDefaultLifetime();
	public void setDefaultLifetime(Integer defaultLifetime);
	
}
