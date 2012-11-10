package de.hda.particles.camera;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.renderer.Renderer;

public interface Camera extends Renderer {

	Vector3f getPosition();
	Float getYaw();
	Float getPitch();
	Float getRoll();
	void setPosition(Vector3f position);
	void setYaw(float yaw);
	void setPitch(float pitch);
	void setRoll(float roll);
	void reset();
	String getName();
	void setName(String name);
	void lookThrough();

}
