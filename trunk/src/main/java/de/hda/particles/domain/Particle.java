package de.hda.particles.domain;

import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

public interface Particle extends Map<String, Object> {

	public final static Integer DEFAULT_LIFETIME = 100;
	public final static Integer DEFAULT_RENDER_TYPE_INDEX = 1;
	public final static Float DEFAULT_MASS = 0.1f;
	
	public Float getX();
	public void setX(Float x);
	public Float getY();
	public void setY(Float y);
	public Float getZ();
	public void setZ(Float z);
	public Vector3f getPosition();
	public void setPosition(Vector3f position);
	public Vector3f getVelocity();
	public void setVelocity(Vector3f velocity);
	public Float getMass();
	public void setMass(Float mass);
	public Integer getRenderTypeIndex();
	public void setRenderTypeIndex(Integer renderTypeIndex);
	public Boolean isAlive();
	public void decLifetime();
	public Integer getRemainingIterations();
	public void setRemainingIterations(Integer remainingIterations);
	public Integer getPastIterations();
	public void setPastIterations(Integer pastIterations);
	public Float getLifetimePercent();
	public Boolean isVisible();
	public void setVisibility(Boolean visibility);
	public Integer getIndex();
	public void setIndex(Integer index);

}