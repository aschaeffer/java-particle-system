package de.hda.particles.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.math3.util.Pair;
import org.lwjgl.util.vector.Vector3f;

/**
 * The default implementation contains springs, because mass spring
 * systems are main focus for fixed points.
 * 
 * @author aschaeffer
 *
 */
public abstract class AbstractMassSpringFixedPoint /* extends HashMap<Particle, Float> */ implements FixedPoint {

	private float positionX = 0.0f;
	private float positionY = 0.0f;
	private float positionZ = 0.0f;
	private float mass = DEFAULT_MASS;
	private float springFriction = DEFAULT_SPRING_FRICTION;
	private float springConstant = DEFAULT_SPRING_CONSTANT;
	private List<Pair<Particle, Float>> springs = new ArrayList<Pair<Particle, Float>>();
	private boolean visibility = true;

	public AbstractMassSpringFixedPoint() {
	}

	public AbstractMassSpringFixedPoint(Vector3f position) {
		positionX = position.x;
		positionY = position.y;
		positionZ = position.z;
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
	public float getMass() {
		return mass;
	}

	@Override
	public void setMass(float mass) {
		this.mass = mass;
	}
	
	@Override
	public float getSpringFriction() {
		return springFriction;
	}
	
	@Override
	public void setSpringFriction(float springFriction) {
		this.springFriction = springFriction;
	}

	@Override
	public float getSpringConstant() {
		return springConstant;
	}

	@Override
	public void setSpringConstant(float springConstant) {
		this.springConstant = springConstant;
	}

	@Override
	public List<Pair<Particle, Float>> getSprings() {
		return springs;
	}

	@Override
	public void setSprings(List<Pair<Particle, Float>> springs) {
		this.springs = springs;
	}
	
	@Override
	public void addSpring(Pair<Particle, Float> spring) {
		springs.add(spring);
	}
	
	@Override
	public void removeSpring(Particle particle) {
		ListIterator<Pair<Particle, Float>> iterator = springs.listIterator(0);
		while (iterator.hasNext()) {
			Pair<Particle, Float> spring = iterator.next();
			if (spring.getKey().equals(particle)) {
				iterator.remove();
			}
		}
	}

	@Override
	public boolean isVisible() {
		return visibility;
	}
	
	@Override
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}


//	@Override
//	public Map<Particle, Float> getSprings() {
//		return springs;
//	}
//
//	@Override
//	public void setSprings(Map<Particle, Float> springs) {
//		this.springs = springs;
//	}
//
//	@Override
//	public void addSpring(Particle particle, Float length) {
//		springs.put(particle, length);
//	}
//	
//	@Override
//	public String toString() {
//		String listOfFeatures = "";
//		for (String key: keySet()) listOfFeatures.concat(key + "=" + get(key).toString() + "; ");
//		return "fixed point pos: ("+positionX+","+positionY+","+positionZ+") features: "+listOfFeatures;
//	}
//
}
