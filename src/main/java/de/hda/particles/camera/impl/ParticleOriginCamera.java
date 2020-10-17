package de.hda.particles.camera.impl;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.camera.Camera;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.glu.GLU;

import de.hda.particles.domain.Particle;

public class ParticleOriginCamera extends AbstractCamera implements Camera {

	private Particle particle;

	private Integer deltaWheel = 0;
	private Float movementModifier = 1.0f;

	private final Random random = new Random();
	
	public ParticleOriginCamera() {}

	@Override
	public void update() {
		if (particle == null || !particle.isAlive()) {
			getNextParticle();
			if (!particle.isAlive()) return; // wait for next iteration
		}
		position.x = particle.getX();
		position.y = particle.getY();
		position.z = particle.getZ();

		// when passing in the distance to move
		// we times the movementSpeed with dt this is a time scale
		// so if its a slow frame u move more then a fast frame
		// so on a slow computer you move just as fast as on a fast computer
		movementModifier = 1.0f;

		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			movementModifier = movementModifier * 10.0f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			movementModifier = movementModifier * 10.0f;
		}

		deltaWheel = Mouse.getDWheel();
		if (deltaWheel < 0) {
			zoom(movementModifier * -deltaWheel / 60.0f);
		} else if (deltaWheel > 0) {
			zoom(movementModifier * -deltaWheel / 60.0f);
		}

		// look through the camera before you draw anything
		lookThrough();
	}

	@Override
	public void setup() {
	    Mouse.setGrabbed(true);
	}

	@Override
	public void destroy() {
	    Mouse.setGrabbed(false);
	}
	
	@Override
	public void nextMode() {
		getNextParticle();
	}

	@Override
	public String getModeName() {
		return "Particle To Origin (" + particle.getRemainingIterations() + " iterations left)";
	}

	public void getNextParticle() {
		try {
			List<Particle> particles = scene.getParticleSystem().getParticles();
			Integer randomParticleIndex = random.nextInt(particles.size());
			particle = particles.get(randomParticleIndex);
		} catch (Exception e) {}
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
