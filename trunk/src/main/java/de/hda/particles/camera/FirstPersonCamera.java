package de.hda.particles.camera;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class FirstPersonCamera extends AbstractCamera implements Camera {

	private float dx = 0.0f;
	private float dy = 0.0f;
	private float dt = 0.0f; // length of frame
	private long lastTime = 0; // when the last frame was
	private long time = 0;

	float mouseSensitivity = 0.05f;
	float movementSpeed = 20.0f; // move 10 units per second

	// Constructor that takes the starting x, y, z location of the camera
	public FirstPersonCamera(String name, float x, float y, float z) {
		super(name, x, y, z);
	}

	// Constructor that takes the starting x, y, z location of the camera
	public FirstPersonCamera(String name, float x, float y, float z, float yaw, float pitch, float roll) {
		super(name, x, y, z, yaw, pitch, roll);
	}

	// moves the camera forward relative to its current rotation (yaw)
	public void walkForward(float distance) {
		position.x -= distance * (float) Math.sin(Math.toRadians(yaw));
		position.z += distance * (float) Math.cos(Math.toRadians(yaw));
	}

	// moves the camera backward relative to its current rotation (yaw)
	public void walkBackwards(float distance) {
		position.x += distance * (float) Math.sin(Math.toRadians(yaw));
		position.z -= distance * (float) Math.cos(Math.toRadians(yaw));
	}

	// strafes the camera left relitive to its current rotation (yaw)
	public void strafeLeft(float distance) {
		position.x -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
		position.z += distance * (float) Math.cos(Math.toRadians(yaw - 90));
	}

	// strafes the camera right relitive to its current rotation (yaw)
	public void strafeRight(float distance) {
		position.x -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
		position.z += distance * (float) Math.cos(Math.toRadians(yaw + 90));
	}
	
	public void moveUpwards(float distance) {
		position.y += distance / 60.0f;
	}

	public void moveDownwards(float distance) {
		position.y += distance / 60.0f;
	}

	@Override
	public void update() {
		time = Sys.getTime();
		dt = (time - lastTime) / 1000.0f;
		lastTime = time;

		// distance in mouse movement from the last getDX() call.
		dx = Mouse.getDX();
		// distance in mouse movement from the last getDY() call.
		dy = Mouse.getDY();

		// control camera yaw from x movement from the mouse
		this.yaw(dx * mouseSensitivity);
		// control camera pitch from y movement from the mouse
		this.pitch(dy * mouseSensitivity);
		
		Keyboard.next();

		// when passing in the distance to move
		// we times the movementSpeed with dt this is a time scale
		// so if its a slow frame u move more then a fast frame
		// so on a slow computer you move just as fast as on a fast computer
		float movementModifier = 1.0f;
		Boolean doRoll = false;
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			movementModifier = movementModifier * 10.0f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			movementModifier = movementModifier * 10.0f;
			doRoll = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.walkForward(movementModifier * movementSpeed * dt);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.walkBackwards(movementModifier * movementSpeed * dt);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.strafeLeft(movementModifier * movementSpeed * dt);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.strafeRight(movementModifier * movementSpeed * dt);
		}

		float dw = Mouse.getDWheel();
		if (dw < 0) {
			if (doRoll) {
				this.roll(dw / 60.0f);
			} else {
				this.moveDownwards(movementModifier * dw / 60.0f);
			}
		} else if (dw > 0) {
			if (doRoll) {
				this.roll(dw / 60.0f);
			} else {
				this.moveUpwards(movementModifier * dw / 60.0f);
			}
		}
		

		// System.out.println("camera.update() in " + dt + "ms; camera pos x=" + position.x + " y=" + position.y + " z=" + position.z + "; yaw=" + yaw + " pitch: " + pitch + "; mouse dx=" + dx + " dy=" + dy);

		// look through the camera before you draw anything
		this.lookThrough();
	}

	@Override
	public void setup() {
	    Mouse.setGrabbed(true);
	}

	@Override
	public void destroy() {
	    Mouse.setGrabbed(false);
	}

}
