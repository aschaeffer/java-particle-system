package de.hda.particles.camera.impl;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.camera.Camera;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

public class ParticlePOVCamera extends AbstractParticleCamera implements Camera {

	/**
	 * The current camera direction.
	 */
	private final Vector3f direction = new Vector3f();

	public ParticlePOVCamera() {}

	@Override
	public void update() {
		if (updateParticle()) {
			return;
		}
		updatePosition();
		particle.getVelocity().normalise(direction);
		yaw = - (float) Math.toDegrees(Math.atan2(particle.getVelX(), -particle.getVelZ()));
		pitch = (float) Math.toDegrees(Math.atan2(particle.getVelY(), Math.sqrt(particle.getVelX() * particle.getVelX() + particle.getVelZ() * particle.getVelZ())));

		updateMovementModifier();

		zoom();

		// look through the camera before you draw anything
		lookThrough();
	}

	@Override
	public String getModeName() {
		return "Particle POV (" + particle.getRemainingIterations() + " iterations left)";
	}

	@Override
	public void lookThrough() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(fov, (float) scene.getWidth() / (float) scene.getHeight(), scene.getNearPlane(), scene.getFarPlane());
		// set the modelview matrix back to the identity
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		GLU.gluLookAt(
			position.x, position.y, position.z,
			position.x + direction.x, position.y + direction.y, position.z + direction.z,
			0.0f, -1.0f, 0.0f
		);
	}


}
