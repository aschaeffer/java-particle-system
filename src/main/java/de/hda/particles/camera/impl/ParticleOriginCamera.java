package de.hda.particles.camera.impl;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.camera.Camera;

import org.lwjgl.util.glu.GLU;

public class ParticleOriginCamera extends AbstractParticleCamera implements Camera {

	public ParticleOriginCamera() {}

	@Override
	public void update() {
		if (updateParticle()) {
			return;
		}
		updatePosition();

		updateMovementModifier();

		zoom();

		// look through the camera before you draw anything
		lookThrough();
	}

	@Override
	public String getModeName() {
		return "Particle To Origin (" + particle.getRemainingIterations() + " iterations left)";
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
			-position.x, -position.y, -position.z,
			0.0f, -1.0f, 0.0f
		);
	}

}
