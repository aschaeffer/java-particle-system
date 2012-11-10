package de.hda.particles.domain;

import java.util.HashMap;
import org.lwjgl.util.vector.Vector3f;

public class Particle extends HashMap<String, Object> {

	private static final long serialVersionUID = -3288616878715170036L;

	private Vector3f position = new Vector3f();
	private Vector3f velocity = new Vector3f();
	private Integer remainingIterations;
	private Integer pastIterations;
	private Float mass = 0.01f;

	public Particle(Vector3f position, Vector3f velocity, Integer lifetime) {
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

	public Vector3f getPosition() {
		return new Vector3f(position);
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getVelocity() {
		return new Vector3f(velocity);
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}
	
	public Float getMass() {
		return mass;
	}

	public void setMass(Float mass) {
		this.mass = mass;
	}

	public Boolean isAlive() {
		return (remainingIterations > 0);
	}
	
	public void decLifetime() {
		remainingIterations--;
		pastIterations++;
	}
	
	public void setRemainingIterations(Integer remainingIterations) {
		this.remainingIterations = remainingIterations;
	}

	public Float getLifetimePercent() {
		if (pastIterations == 0) return 0.0f;
		return pastIterations.floatValue() / (pastIterations.floatValue() + remainingIterations.floatValue());
	}
	
	public String toString() {
		String listOfFeatures = "";
		for (String key: keySet()) listOfFeatures.concat(key + "=" + get(key).toString() + "; ");
		return "particle pos: ("+position.x+","+position.y+","+position.z+") vel: ("+velocity.x+","+velocity.y+","+velocity.z+") remaining: "+remainingIterations+ " features: "+listOfFeatures;
	}

}
