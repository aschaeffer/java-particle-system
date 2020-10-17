package de.hda.particles.domain.impl.particle;

import de.hda.particles.domain.Particle;
import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;

public abstract class AbstractHashMapParticle extends HashMap<String, Object> implements Particle {

	private static final long serialVersionUID = -721118895224811054L;

	private float positionX = 0.0f;
	private float positionY = 0.0f;
	private float positionZ = 0.0f;
	private float velocityX = 0.0f;
	private float velocityY = 0.0f;
	private float velocityZ = 0.0f;
	private int remainingIterations = DEFAULT_LIFETIME;
	private int pastIterations = 0;
	private float mass = DEFAULT_MASS;
	private boolean visibility = true;
	private int index = 0; // TODO: remove?
	private int particleRendererIndex = DEFAULT_PARTICLE_RENDERER_INDEX;

	public AbstractHashMapParticle() {
	}

	public AbstractHashMapParticle(Vector3f position, Vector3f velocity, int particleRendererIndex, int lifetime) {
		positionX = position.x;
		positionY = position.y;
		positionZ = position.z;
		velocityX = velocity.x;
		velocityY = velocity.y;
		velocityZ = velocity.z;
		this.particleRendererIndex = particleRendererIndex;
		this.remainingIterations = lifetime;
	}

	@Override
	public float getX() {
		return positionX;
	}

	@Override
	public void setX(float x) {
		positionX = x;
	}

	@Override
	public float getY() {
		return positionY;
	}

	@Override
	public void setY(float y) {
		positionY = y;
	}

	@Override
	public float getZ() {
		return positionZ;
	}

	@Override
	public void setZ(float z) {
		positionZ = z;
	}

	@Override
	public Vector3f getPosition() {
		return new Vector3f(positionX, positionY, positionZ);
	}

	@Override
	public void setPosition(Vector3f position) {
		positionX = position.x;
		positionY = position.y;
		positionZ = position.z;
	}

	@Override
	public Vector3f getVelocity() {
		return new Vector3f(velocityX, velocityY, velocityZ);
	}

	@Override
	public float getVelX() {
		return velocityX;
	}

	@Override
	public void setVelX(float x) {
		velocityX = x;
	}

	@Override
	public float getVelY() {
		return velocityY;
	}

	@Override
	public void setVelY(float y) {
		velocityY = y;
	}

	@Override
	public float getVelZ() {
		return velocityZ;
	}

	@Override
	public void setVelZ(float z) {
		velocityZ = z;
	}

	@Override
	public void setVelocity(Vector3f velocity) {
		velocityX = velocity.x;
		velocityY = velocity.y;
		velocityZ = velocity.z;
	}
	
	@Override
	public float getMass() {
		return mass;
	}

	@Override
	public void setMass(float mass) {
		this.mass = mass;
	}

	@Override
	public int getParticleRendererIndex() {
		return particleRendererIndex;
	}

	@Override
	public void setParticleRendererIndex(int particleRendererIndex) {
		this.particleRendererIndex = particleRendererIndex;
	}

	@Override
	public boolean isAlive() {
		return (remainingIterations > 0);
	}
	
	@Override
	public void decLifetime() {
		remainingIterations--;
		pastIterations++;
	}
	
	@Override
	public int getRemainingIterations() {
		return remainingIterations;
	}
	
	@Override
	public void setRemainingIterations(int remainingIterations) {
		this.remainingIterations = remainingIterations;
	}
	
	@Override
	public int getPastIterations() {
		return pastIterations;
	}
	
	@Override
	public void setPastIterations(int pastIterations) {
		this.pastIterations = pastIterations;
	}

	@Override
	public float getLifetimePercent() {
		if (pastIterations == 0) return 0.0f;
		float pastIterationsF = pastIterations;
		float remainingIterationsF = remainingIterations;
		return pastIterationsF / (pastIterationsF + remainingIterationsF);
	}
	
	@Override
	public boolean isVisible() {
		return visibility;
	}
	
	@Override
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}
	
	@Override
	public int getIndex() {
		return index;
	}
	
	@Override
	public void setIndex(int index) {
		this.index = index;
	}
	
	@Override
	public String toString() {
		String listOfFeatures = "";
		for (String key: keySet()) listOfFeatures.concat(key + "=" + get(key).toString() + "; ");
		return "particle pos: ("+positionX+","+positionY+","+positionZ+") vel: ("+velocityX+","+velocityY+","+velocityZ+") remaining: "+remainingIterations+ " features: "+listOfFeatures;
	}

}
