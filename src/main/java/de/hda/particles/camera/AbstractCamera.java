package de.hda.particles.camera;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glLoadIdentity;

import org.lwjgl.util.vector.Vector3f;

public abstract class AbstractCamera implements Camera {

	private String name = "unknown cam";

	// 3d vector to store the camera's position in
	protected Vector3f position = null;

	// the rotation around the Y axis of the camera
	protected Float yaw = 0.0f;

	// the rotation around the X axis of the camera
	protected Float pitch = 0.0f;

	// the rotation around the X axis of the camera
	protected Float roll = 0.0f;

	public AbstractCamera() {
		position = new Vector3f(0.0f, 0.0f, 0.0f);
	}

	// Constructor that takes the starting x, y, z location of the camera
	public AbstractCamera(String name, float x, float y, float z) {
		// instantiate position Vector3f to the x y z params.
		this.name = name;
		this.position = new Vector3f(x, y, z);
	}

	// Constructor that takes the starting x, y, z location of the camera
	public AbstractCamera(String name, float x, float y, float z, float yaw, float pitch, float roll) {
		// instantiate position Vector3f to the x y z params.
		this.name = name;
		this.position = new Vector3f(x, y, z);
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
	}

	// increment the camera's current yaw rotation
	public void yaw(float amount) {
		// increment the yaw by the amount param
		yaw += amount;
	}

	// increment the camera's current pitch rotation
	public void pitch(float amount) {
		// increment the pitch by the amount param
		pitch += amount;
	}

	// increment the camera's current pitch rotation
	public void roll(float amount) {
		// increment the pitch by the amount param
		roll += amount;
	}

	// translates and rotate the matrix so that it looks through the camera
	// this dose basic what gluLookAt() does
	public void lookThrough() {
		// set the modelview matrix back to the identity
		glLoadIdentity();
		// GLU.gluPerspective(fov, (float) width / (float) height, 0.1f, 5000.0f);
		// rotate the pitch around the X axis
		glRotatef(pitch, 1.0f, 0.0f, 0.0f);
		// rotate the yaw around the Y axis
		glRotatef(yaw, 0.0f, 1.0f, 0.0f);
		// rotate the yaw around the Y axis
		glRotatef(roll, 0.0f, 0.0f, 1.0f);
		// translate to the position vector's location
		glTranslatef(position.x, position.y, position.z);
	}

	@Override
	public Boolean isFinished() {
		return false;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public Float getYaw() {
		return yaw;
	}
	
	public Float getPitch() {
		return pitch;
	}

	public Float getRoll() {
		return roll;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

	public void reset() {
		this.setPosition(new Vector3f(0.0f, 0.0f, 0.0f));
		this.setYaw(0.0f);
		this.setPitch(0.0f);
		this.setRoll(0.0f);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
