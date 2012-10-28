package de.hda.particles.domain;

import de.hda.particles.domain.features.ParticleFeatures;

public class Particle {

	private Vector3 position = new Vector3();
	private Vector3 velocity = new Vector3();
	private Integer remainingIterations;
	private Integer pastIterations;
	private ParticleFeatures features = new ParticleFeatures();

	public Particle(Vector3 position, Vector3 velocity, Integer lifetime) {
		this.position = position;
		this.velocity = velocity;
		this.remainingIterations = lifetime;
		this.pastIterations = 0;
	}

	public Float getX() {
		return position.x;
	}

	public void setX(Float x) {
		position.x = x;
	}

	public Float getY() {
		return position.y;
	}

	public void setY(Float y) {
		position.y = y;
	}

	public Float getZ() {
		return position.z;
	}

	public void setZ(Float z) {
		position.z = z;
	}

	public Vector3 getPosition() {
		return position.clone();
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public Vector3 getVelocity() {
		return velocity.clone();
	}

	public void setVelocity(Vector3 velocity) {
		this.velocity = velocity;
	}

	public void setFeatures(ParticleFeatures features) {
		this.features = features;
	}
	
	public Object getFeature(String key) {
		return this.features.get(key);
	}

	public void setFeature(String key, Object value) {
		this.features.put(key, value);
	}

	public Boolean isAlive() {
		return (remainingIterations <= 0);
	}
	
	public void decLifetime() {
		remainingIterations--;
	}

	public Float getLifetimePercent() {
		if (pastIterations == 0) return 0.0f;
		return (pastIterations.floatValue() + remainingIterations.floatValue()) / pastIterations.floatValue();
	}
	
	public String toString() {
		String listOfFeatures = "";
		for (String key: features.keySet()) listOfFeatures.concat(key + "=" + features.get(key).toString() + "; ");
		return "particle pos: ("+position.x+","+position.y+","+position.z+") vel: ("+velocity.x+","+velocity.y+","+velocity.z+") remaining: "+remainingIterations+ " features: "+listOfFeatures;
	}

}
