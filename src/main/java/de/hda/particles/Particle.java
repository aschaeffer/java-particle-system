package de.hda.particles;

public class Particle {

	private Vector3 position = new Vector3();
	private Vector3 velocity = new Vector3();
	private Integer remainingIterations;
	
	public Particle(Float p_x, Float p_y, Float p_z, Float v_x, Float v_y, Float v_z, Integer lifetime) {
		this.position.x = p_x;
		this.position.y = p_y;
		this.position.z = p_z;
		
		this.velocity.x = v_x;
		this.velocity.y = v_y;
		this.velocity.z = v_z;
		
		this.remainingIterations = lifetime;
	}

	public Particle(Vector3 position, Vector3 velocity, Integer lifetime) {
		this.position = position;
		this.velocity = velocity;
		this.remainingIterations = lifetime;
	}

	public void setPosition(Float x, Float y, Float z) {
		position.x = x;
		position.y = y;
		position.z = z;
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

	public Vector3 getVelocity() {
		return velocity;
	}

	public void setVelocity(Float x, Float y, Float z) {
		velocity.x = x;
		velocity.y = y;
		velocity.z = z;
	}

	public void setVelocity(Vector3 velocity) {
		this.velocity = velocity;
	}

	public Boolean isAlive() {
		return (remainingIterations <= 0);
	}

}
