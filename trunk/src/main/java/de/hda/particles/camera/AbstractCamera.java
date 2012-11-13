package de.hda.particles.camera;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glLoadIdentity;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.scene.Scene;

public abstract class AbstractCamera implements Camera {

	public final static Float DEFAULT_X = 0.0f;
	public final static Float DEFAULT_Y = 0.0f;
	public final static Float DEFAULT_Z = 0.0f;
	public final static Float DEFAULT_YAW = 0.0f;
	public final static Float DEFAULT_PITCH = 0.0f;
	public final static Float MAX_PITCH = 90.0f;
	public final static Float DEFAULT_ROLL = 0.0f;
	public final static Float MAX_ROLL = 20.0f;
	public final static Float ROLL_REDUCE = 1.5f;
	public final static Float DEFAULT_FOV = 90.0f;
	public final static Float MIN_FOV = 15.0f;
	public final static Float MAX_FOV = 150.0f;
	

	/**
	 * Every cam has a name ;-)
	 */
	private String name = "unknown cam";

	/**
	 * 3d vector to store the camera's position in
	 */
	protected Vector3f position = new Vector3f(DEFAULT_X, DEFAULT_Y, DEFAULT_Z);

	/**
	 * the rotation around the Y axis of the camera
	 */
	protected Float yaw = DEFAULT_YAW;

	/**
	 * the rotation around the X axis of the camera
	 */
	protected Float pitch = DEFAULT_PITCH;

	/**
	 * the rotation around the X axis of the camera
	 */
	protected Float roll = DEFAULT_ROLL;

	/**
	 * the field of view
	 */
	protected Float fov = DEFAULT_FOV;
	
	protected Scene scene;

	/**
	 * Default no arguments constructor.
	 */
	public AbstractCamera() {
	}
	
	// Constructor that takes the starting x, y, z location of the camera
	public AbstractCamera(String name, Scene scene, Vector3f position) {
		// instantiate position Vector3f to the x y z params.
		this.name = name;
		this.scene = scene;
		this.position = position;
	}

	// Constructor that takes the starting x, y, z location of the camera
	public AbstractCamera(String name, Scene scene, Vector3f position, Float yaw, Float pitch, Float roll, Float fov) {
		// instantiate position Vector3f to the x y z params.
		this.name = name;
		this.scene = scene;
		this.position = position;
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
		this.fov = fov;
	}

	// increment the camera's current yaw rotation
	public void yaw(Float amount) {
		// increment the yaw by the amount param
		yaw += amount;
		if (yaw > 360.0f) {
			yaw = yaw - 360.0f;
		} else if (yaw < 0.0f) {
			yaw = 360.0f - yaw;
		}
	}

	// increment the camera's current pitch rotation
	public void pitch(Float amount) {
		// increment the pitch by the amount param
		pitch += amount;
		if (pitch > MAX_PITCH) {
			pitch = MAX_PITCH;
		} else if (pitch < -MAX_PITCH) {
			pitch = -MAX_PITCH;
		}
	}

	// increment the camera's current pitch rotation
	public void roll(Float amount) {
		// increment the pitch by the amount param
		roll += amount;
		if (roll > MAX_ROLL) {
			roll = MAX_ROLL;
		} else if (roll < -MAX_ROLL) {
			roll = -MAX_ROLL;
		}
	}
	
	public void rollReduce() {
		if (roll != 0.0f) {
			roll = roll / ROLL_REDUCE;
		}
	}
	
	public void zoom(Float amount) {
		fov += amount;
		if (fov > MAX_FOV) {
			fov = MAX_FOV;
		} else if (fov < MIN_FOV) {
			fov = MIN_FOV;
		}
	}

	// translates and rotate the matrix so that it looks through the camera
	// this dose basic what gluLookAt() does
	public void lookThrough() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
		GLU.gluPerspective(fov, (float) scene.getWidth() / (float) scene.getHeight(), 0.1f, 5000.0f);

        glMatrixMode(GL_MODELVIEW);
		// set the modelview matrix back to the identity
		glLoadIdentity();
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
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public void setYaw(Float yaw) {
		this.yaw = yaw;
	}

	public Float getPitch() {
		return pitch;
	}

	public void setPitch(Float pitch) {
		this.pitch = pitch;
	}

	public Float getRoll() {
		return roll;
	}
	
	public void setRoll(Float roll) {
		this.roll = roll;
	}

	public Float getFov() {
		return fov;
	}
	
	public void setFov(Float fov) {
		this.fov = fov;
	}

	public void reset() {
		this.setPosition(new Vector3f(DEFAULT_X, DEFAULT_Y, DEFAULT_Z));
		this.setYaw(DEFAULT_YAW);
		this.setPitch(DEFAULT_PITCH);
		this.setRoll(DEFAULT_ROLL);
		this.setFov(DEFAULT_FOV);
	}

}
