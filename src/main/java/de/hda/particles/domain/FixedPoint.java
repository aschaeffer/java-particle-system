package de.hda.particles.domain;

// import java.util.Map;

import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.lwjgl.util.vector.Vector3f;

public interface FixedPoint { //  extends Map<Particle, Float>

	float DEFAULT_MASS = 0.5f; // TODO: adjust a suitable default value
	float DEFAULT_SPRING_FRICTION = 0.01f; // TODO: adjust a suitable default value
	float DEFAULT_SPRING_CONSTANT = 0.02f; // TODO: adjust a suitable default value

	float getX();
	void setX(float x);
	float getY();
	void setY(float y);
	float getZ();
	void setZ(float z);
	Vector3f getPosition();
	void setPosition(Vector3f position);
	float getMass();
	void setMass(float mass);
	float getSpringFriction();
	void setSpringFriction(float friction);
	float getSpringConstant();
	void setSpringConstant(float springConstant);
	List<Pair<Particle, Float>> getSprings();
	void setSprings(List<Pair<Particle, Float>> springs);
	void addSpring(Pair<Particle, Float> spring);
	void removeSpring(Particle particle);
	boolean isVisible();
	void setVisibility(boolean visibility);

}
