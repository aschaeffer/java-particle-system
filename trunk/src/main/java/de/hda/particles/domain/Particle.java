package de.hda.particles.domain;

import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

public interface Particle extends Map<String, Object> {

	public final static int DEFAULT_LIFETIME = 100;
	public final static int DEFAULT_RENDER_TYPE_INDEX = 1;
	public final static float DEFAULT_MASS = 0.1f;
	
	public float getX();
	public void setX(float x);
	public float getY();
	public void setY(float y);
	public float getZ();
	public void setZ(float z);
	public Vector3f getPosition();
	public void setPosition(Vector3f position);
	public Vector3f getVelocity();
	public float getVelX();
	public void setVelX(float x);
	public float getVelY();
	public void setVelY(float y);
	public float getVelZ();
	public void setVelZ(float z);
	public void setVelocity(Vector3f velocity);
	public float getMass();
	public void setMass(float mass);
	public int getRenderTypeIndex();
	public void setRenderTypeIndex(int renderTypeIndex);
	public boolean isAlive();
	public void decLifetime();
	public int getRemainingIterations();
	public void setRemainingIterations(int remainingIterations);
	public int getPastIterations();
	public void setPastIterations(int pastIterations);
	public float getLifetimePercent();
	public boolean isVisible();
	public void setVisibility(boolean visibility);
	public int getIndex();
	public void setIndex(int index);

}
