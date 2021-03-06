package de.hda.particles.camera.impl;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.camera.Camera;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.glu.GLU;

public class TopDownCamera extends AbstractDeltaTimeCamera implements Camera {

	private final static Integer MAX_MODES = 2;
	private final static Integer DEFAULT_MODE = 1;

	private Float deltaX = 0.0f;
	private Float deltaY = 0.0f;
	private Integer deltaWheel = 0;

	private final Float mouseSensitivity = 0.05f;
	private final Float movementSpeed = 20.0f; // move 10 units per second

	public void move() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.x += (movementModifier * movementSpeed * deltaTime);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.x -= (movementModifier * movementSpeed * deltaTime);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.z += (movementModifier * movementSpeed * deltaTime);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.z -= (movementModifier * movementSpeed * deltaTime);
		}
	}

	@Override
	public void zoom() {
		deltaWheel = Mouse.getDWheel();
		if (deltaWheel != 0) {
			position.y += (movementModifier * -deltaWheel / 60.0f);
		}
	}

	@Override
	public void update() {
		updateTime();

		// distance in mouse movement from the last getDX() / getDY() call.
		deltaX = new Integer(Mouse.getDX()).floatValue();
		deltaY = new Integer(Mouse.getDY()).floatValue();

		// control camera yaw from x movement from the mouse
		yaw(deltaX * mouseSensitivity);
		pitch = 90.0f;

		updateMovementModifier();

		move();

		zoom();

		// look through the camera before you draw anything
		lookThrough();
	}

	@Override
	public void nextMode() {
		mode++;
		if (mode > MAX_MODES - 1) {
			mode = 0;
		}
	}

	@Override
	public String getModeName() {
		switch (mode) {
			case 0:
			default:
				return "Top Down Ortho";
			case 1:
				return "Top Down Origin";
		}
	}
	
	@Override
	public void lookThrough() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(fov, (float) scene.getWidth() / (float) scene.getHeight(), scene.getNearPlane(), scene.getFarPlane());
		// set the modelview matrix back to the identity
        glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		switch (mode) {
			case 0:
			default:
				GLU.gluLookAt(
					position.x, position.y, position.z,
					position.x-0.1f, -position.y, position.z-0.1f,
					0.0f, -1.0f, 0.0f
				);
				break;
			case 1:
				// rotate the pitch around the X axis
				glRotatef(pitch, 1.0f, 0.0f, 0.0f); // look down (always +90.0)
				// rotate the yaw around the Y axis
				glRotatef(yaw, 0.0f, 1.0f, 0.0f);
				// rotate the yaw around the Y axis
				glRotatef(roll, 0.0f, 0.0f, 1.0f);
				// translate to the position vector's location
				glTranslatef(-position.x, -position.y, -position.z);
				break;
		}
	}


}
