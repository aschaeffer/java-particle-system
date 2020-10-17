package de.hda.particles.camera.impl;

import de.hda.particles.camera.Camera;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.scene.Scene;

public class FirstPersonCamera extends AbstractDeltaTimeCamera implements Camera {
	
	public final static Integer MAX_MODES = 2;
	public final static Integer DEFAULT_MODE = 1;

	public final Float movementSpeed = 20.0f; // move 20 units per second
	public final Float mouseSensitivity = 0.05f;

	protected Float deltaX = 0.0f;
	protected Float deltaY = 0.0f;

	public FirstPersonCamera() {}

	// Constructor that takes the starting x, y, z location of the camera
	public FirstPersonCamera(String name, Scene scene, Vector3f position) {
		super(name, scene, position);
		mode = DEFAULT_MODE;
	}

	// Constructor that takes the starting x, y, z location of the camera
	public FirstPersonCamera(String name, Scene scene, Vector3f position, Float yaw, Float pitch, Float roll, Float fov) {
		super(name, scene, position, yaw, pitch, roll, fov);
		mode = DEFAULT_MODE;
	}

	public void move() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			walkForward(movementModifier * movementSpeed * deltaTime);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			walkBackwards(movementModifier * movementSpeed * deltaTime);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			strafeLeft(movementModifier * movementSpeed * deltaTime);
			roll(-1.0f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			strafeRight(movementModifier * movementSpeed * deltaTime);
			roll(1.0f);
		}
	}

	// moves the camera forward relative to its current rotation (yaw)
	public void walkForward(float distance) {
		switch (mode) {
			default:
			case 0:
				position.x += distance * (float) Math.sin(Math.toRadians(yaw));
				position.z -= distance * (float) Math.cos(Math.toRadians(yaw));
				break;
			case 1:
				position.x += distance * (float) Math.sin(Math.toRadians(yaw));
				position.y -= distance * (float) Math.sin(Math.toRadians(pitch));
				position.z -= distance * (float) Math.cos(Math.toRadians(yaw));
				break;
		}
	}

	// moves the camera backward relative to its current rotation (yaw)
	public void walkBackwards(float distance) {
		switch (mode) {
			default:
			case 0:
				position.x -= distance * (float) Math.sin(Math.toRadians(yaw));
				position.z += distance * (float) Math.cos(Math.toRadians(yaw));
				break;
			case 1:
				position.x -= distance * (float) Math.sin(Math.toRadians(yaw));
				position.y += distance * (float) Math.sin(Math.toRadians(pitch));
				position.z += distance * (float) Math.cos(Math.toRadians(yaw));
				break;
		}
	}

	// strafes the camera left relative to its current rotation (yaw)
	public void strafeLeft(float distance) {
		position.x += distance * (float) Math.sin(Math.toRadians(yaw - 90));
		position.z -= distance * (float) Math.cos(Math.toRadians(yaw - 90));
	}

	// strafes the camera right relative to its current rotation (yaw)
	public void strafeRight(float distance) {
		position.x += distance * (float) Math.sin(Math.toRadians(yaw + 90));
		position.z -= distance * (float) Math.cos(Math.toRadians(yaw + 90));
	}

	@Override
	public void update() {
		updateTime();

		// distance in mouse movement from the last getDX() / getDY() call.
		deltaX = new Integer(Mouse.getDX()).floatValue();
		deltaY = new Integer(Mouse.getDY()).floatValue();

		// control camera yaw from x movement from the mouse
		yaw(deltaX * mouseSensitivity);
		// control camera pitch from y movement from the mouse
		pitch(deltaY * mouseSensitivity);

		updateMovementModifier();

		move();

		zoom();

		// look through the camera before you draw anything
		lookThrough();

		// reduce roll
		reduceRoll();
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
				return "First Person Movement Mode";
			case 1:
				return "Fly Movement Mode";
		}
	}

}
