package de.hda.particles.domain;

// import java.util.Map;

import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.lwjgl.util.vector.Vector3f;

public interface FixedPoint { //  extends Map<Particle, Float>

	public final static float DEFAULT_MASS = 0.5f; // TODO: adjust a suitable default value
	public final static float DEFAULT_SPRING_FRICTION = 0.01f; // TODO: adjust a suitable default value
	public final static float DEFAULT_SPRING_CONSTANT = 0.02f; // TODO: adjust a suitable default value

	public float getX();
	public void setX(float x);
	public float getY();
	public void setY(float y);
	public float getZ();
	public void setZ(float z);
	public Vector3f getPosition();
	public void setPosition(Vector3f position);
	public float getMass();
	public void setMass(float mass);
	public float getSpringFriction();
	public void setSpringFriction(float friction);
	public float getSpringConstant();
	public void setSpringConstant(float springConstant);
	public List<Pair<Particle, Float>> getSprings();
	public void setSprings(List<Pair<Particle, Float>> springs);
	public void addSpring(Pair<Particle, Float> spring);
	public void removeSpring(Particle particle);
	public boolean isVisible();
	public void setVisibility(boolean visibility);

}
