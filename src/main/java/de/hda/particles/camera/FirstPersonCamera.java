package de.hda.particles.camera;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.scene.Scene;

public class FirstPersonCamera extends AbstractCamera implements Camera {

	private float dx = 0.0f;
	private float dy = 0.0f;
	private float dt = 0.0f; // length of frame
	private long lastTime = 0; // when the last frame was
	private long time = 0;

	float mouseSensitivity = 0.05f;
	float movementSpeed = 20.0f; // move 10 units per second

	public FirstPersonCamera() {}

	// Constructor that takes the starting x, y, z location of the camera
	public FirstPersonCamera(String name, Scene scene, Vector3f position) {
		super(name, scene, position);
	}

	// Constructor that takes the starting x, y, z location of the camera
	public FirstPersonCamera(String name, Scene scene, Vector3f position, Float yaw, Float pitch, Float roll, Float fov) {
		super(name, scene, position, yaw, pitch, roll, fov);
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

		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			movementModifier = movementModifier * 10.0f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			movementModifier = movementModifier * 10.0f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.walkForward(movementModifier * movementSpeed * dt);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.walkBackwards(movementModifier * movementSpeed * dt);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.strafeLeft(movementModifier * movementSpeed * dt);
			this.roll(-1.0f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.strafeRight(movementModifier * movementSpeed * dt);
			this.roll(1.0f);
		}

		Integer dw = Mouse.getDWheel();
		if (dw < 0) {
			this.zoom(movementModifier * -dw / 60.0f);
		} else if (dw > 0) {
			this.zoom(movementModifier * -dw / 60.0f);
		}

		// look through the camera before you draw anything
		this.lookThrough();

		// reduce roll
		this.rollReduce();
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
