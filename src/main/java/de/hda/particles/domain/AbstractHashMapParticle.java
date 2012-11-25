package de.hda.particles.domain;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;

public abstract class AbstractHashMapParticle extends HashMap<String, Object> implements Particle {

	private static final long serialVersionUID = -721118895224811054L;

	public Vector3f position = new Vector3f();
	public Vector3f velocity = new Vector3f();
	private Integer remainingIterations = DEFAULT_LIFETIME;
	private Integer pastIterations = 0;
	private Float mass = DEFAULT_MASS;
	private Boolean visibility = true;
	private Integer index = 0;
	private Integer renderTypeIndex = DEFAULT_RENDER_TYPE_INDEX;

	public AbstractHashMapParticle() {
	}

	public AbstractHashMapParticle(Vector3f position, Vector3f velocity, Integer renderTypeIndex, Integer lifetime) {
		this.position = position;
		this.velocity = velocity;
		this.renderTypeIndex = renderTypeIndex;
		this.remainingIterations = lifetime;
	}

	@Override
	public Float getX() {
		return position.x;
	}

	@Override
	public void setX(Float x) {
		position.x = x;
	}

	@Override
	public Float getY() {
		return position.y;
	}

	@Override
	public void setY(Float y) {
		position.y = y;
	}

	@Override
	public Float getZ() {
		return position.z;
	}

	@Override
	public void setZ(Float z) {
		position.z = z;
	}

	@Override
	public Vector3f getPosition() {
		return new Vector3f(position);
	}

	@Override
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	@Override
	public Vector3f getVelocity() {
		return new Vector3f(velocity);
	}

	@Override
	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}
	
	@Override
	public Float getMass() {
		return mass;
	}

	@Override
	public void setMass(Float mass) {
		this.mass = mass;
	}

	@Override
	public Integer getRenderTypeIndex() {
		return renderTypeIndex;
	}

	@Override
	public void setRenderTypeIndex(Integer renderTypeIndex) {
		this.renderTypeIndex = renderTypeIndex;
	}

	@Override
	public Boolean isAlive() {
		return (remainingIterations > 0);
	}
	
	@Override
	public void decLifetime() {
		remainingIterations--;
		pastIterations++;
	}
	
	@Override
	public Integer getRemainingIterations() {
		return remainingIterations;
	}
	
	@Override
	public void setRemainingIterations(Integer remainingIterations) {
		this.remainingIterations = remainingIterations;
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
	public Float getLifetimePercent() {
		if (pastIterations == 0) return 0.0f;
		return pastIterations.floatValue() / (pastIterations.floatValue() + remainingIterations.floatValue());
	}
	
	@Override
	public Boolean isVisible() {
		return visibility;
	}
	
	@Override
	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}
	
	@Override
	public Integer getIndex() {
		return index;
	}
	
	@Override
	public void setIndex(Integer index) {
		this.index = index;
	}
	
	@Override
	public String toString() {
		String listOfFeatures = "";
		for (String key: keySet()) listOfFeatures.concat(key + "=" + get(key).toString() + "; ");
		return "particle pos: ("+position.x+","+position.y+","+position.z+") vel: ("+velocity.x+","+velocity.y+","+velocity.z+") remaining: "+remainingIterations+ " features: "+listOfFeatures;
	}

}
