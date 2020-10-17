package de.hda.particles.domain;

import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

/**
 * The particle is the most important data structure in the particle system.
 *
 * Because of performance all members are primitives.
 */
public interface Particle extends Map<String, Object> {

	int DEFAULT_LIFETIME = 100;
	int DEFAULT_PARTICLE_RENDERER_INDEX = 1;
	float DEFAULT_MASS = 0.1f;

	/**
	 * Returns the x position of the particle.
	 * @return The x position of the particle.
	 */
	float getX();

	/**
	 * Sets the x position of the particle.
	 * @param x The x position of the particle.
	 */
	void setX(float x);

	/**
	 * Returns the y position of the particle.
	 * @return The y position of the particle.
	 */
	float getY();

	/**
	 * Sets the y position of the particle.
	 * @param y The y position of the particle.
	 */
	void setY(float y);

	/**
	 * Returns the z position of the particle.
	 * @return The z position of the particle.
	 */
	float getZ();

	/**
	 * Sets the z position of the particle.
	 * @param z The z position of the particle.
	 */
	void setZ(float z);

	/**
	 * Returns the position of the particle.
	 * @return The position of the particle as vec3.
	 */
	Vector3f getPosition();

	/**
	 * Sets the position of the particle.
	 * @param position The position of the particle as vec3.
	 */
	void setPosition(Vector3f position);

	/**
	 * Returns the velocity of the particle in x-direction.
	 * @return The velocity of the particle in x-direction.
	 */
	float getVelX();

	/**
	 * Sets the velocity of the particle in x-direction.
	 * @param x The velocity of the particle in x-direction.
	 */
	void setVelX(float x);

	/**
	 * Returns the velocity of the particle in y-direction.
	 * @return The velocity of the particle in y-direction.
	 */
	float getVelY();

	/**
	 * Sets the velocity of the particle in y-direction.
	 * @param y The velocity of the particle in y-direction.
	 */
	void setVelY(float y);

	/**
	 * Returns the velocity of the particle in z-direction.
	 * @return The velocity of the particle in z-direction.
	 */
	float getVelZ();

	/**
	 * Sets the velocity of the particle in z-direction.
	 * @param z The velocity of the particle in z-direction.
	 */
	void setVelZ(float z);

	/**
	 * Returns the velocity of the particle.
	 * @return The velocity of the particle as vec3.
	 */
	Vector3f getVelocity();

	/**
	 * Sets the velocity of the particle.
	 * @param velocity The velocity of the particle as vec3.
	 */
	void setVelocity(Vector3f velocity);

	/**
	 * Returns the mass of the particle.
	 * @return The mass of the particle.
	 */
	float getMass();

	/**
	 * Sets the mass of the particle.
	 * @param mass The mass of the particle.
	 */
	void setMass(float mass);

	/**
	 * Returns the index of the associated particle renderer.
	 * @return The index of the associated particle renderer.
	 */
	int getParticleRendererIndex();

	/**
	 * Sets the index of the associated particle renderer.
	 * @param particleRendererIndex The index of the associated particle renderer.
	 */
	void setParticleRendererIndex(int particleRendererIndex);

	/**
	 * Returns true if the particle is alive.
	 * @return True if the particle is alive.
	 */
	boolean isAlive();

	/**
	 * Decrements the lifetime of the particle.
	 */
	void decLifetime();

	/**
	 * Returns the remaining iterations.
	 * @return The remaining iterations.
	 */
	int getRemainingIterations();

	/**
	 * Sets the remaining iterations.
	 * @param remainingIterations The remaining iterations.
	 */
	void setRemainingIterations(int remainingIterations);

	/**
	 * Returns the number of iterations since spawn.
	 * @return The number of iterations since spawn.
	 */
	int getPastIterations();

	/**
	 * Sets the number of iterations since spawn.
	 * @param pastIterations The number of iterations since spawn.
	 */
	void setPastIterations(int pastIterations);

	/**
	 * Returns the lifetime of the particle in percent [0..1].
	 * @return The lifetime of the particle in percent [0..1].
	 */
	float getLifetimePercent();

	/**
	 * Returns true, if the particle is visible.
	 * @return True, if the particle is visible.
	 */
	boolean isVisible();

	/**
	 * Sets the visibility state of the particle.
	 * @param visibility The visibility state of the particle.
	 */
	void setVisibility(boolean visibility);

	/**
	 * Returns the index.
	 * @return The index.
	 */
	int getIndex();

	/**
	 * Sets the index.
	 * @param index The index.
	 */
	void setIndex(int index);

}
