package de.hda.particles.camera;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.scene.Scene;

public class FirstPersonCamera extends AbstractCamera implements Camera {
	
	private final static Integer MAX_MODES = 2;
	private final static Integer DEFAULT_MODE = 1;

	private Float deltaX = 0.0f;
	private Float deltaY = 0.0f;
	private Integer deltaWheel = 0;
	private Float deltaTime = 0.0f; // length of frame
	private long lastTime = 0; // when the last frame was
	private long time = 0;
	private Float movementModifier = 1.0f;

	private final Float mouseSensitivity = 0.05f;
	private final Float movementSpeed = 20.0f; // move 10 units per second

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
	
	public void moveUpwards(float distance) {
		position.y += distance / 60.0f;
	}

	public void moveDownwards(float distance) {
		position.y += distance / 60.0f;
	}

	@Override
	public void update() {
		time = Sys.getTime();
		deltaTime = (time - lastTime) / 1000.0f;
		lastTime = time;

		// distance in mouse movement from the last getDX() / getDY() call.
		deltaX = new Integer(Mouse.getDX()).floatValue();
		deltaY = new Integer(Mouse.getDY()).floatValue();

		// control camera yaw from x movement from the mouse
		this.yaw(deltaX * mouseSensitivity);
		// control camera pitch from y movement from the mouse
		this.pitch(deltaY * mouseSensitivity);

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
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.walkForward(movementModifier * movementSpeed * deltaTime);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.walkBackwards(movementModifier * movementSpeed * deltaTime);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.strafeLeft(movementModifier * movementSpeed * deltaTime);
			this.roll(-1.0f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.strafeRight(movementModifier * movementSpeed * deltaTime);
			this.roll(1.0f);
		}

		deltaWheel = Mouse.getDWheel();
		if (deltaWheel < 0) {
			this.zoom(movementModifier * -deltaWheel / 60.0f);
		} else if (deltaWheel > 0) {
			this.zoom(movementModifier * -deltaWheel / 60.0f);
		}

		// look through the camera before you draw anything
		this.lookThrough();

		// reduce roll
		this.reduceRoll();
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
		mode++;
		if (mode > MAX_MODES - 1) mode = 0;
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
