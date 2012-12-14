package de.hda.particles.camera;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.renderer.Renderer;
import de.hda.particles.scene.Scene;

public interface Camera extends Renderer {

	void reset();
	void lookThrough();
	void nextMode();

	@Override
	void setScene(Scene scene);
	String getName();
	void setName(String name);
	Vector3f getPosition();
	Float getX();
	Float getY();
	Float getZ();
	void setPosition(Vector3f position);
	Float getYaw();
	void setYaw(Float yaw);
	Float getPitch();
	void setPitch(Float pitch);
	Float getRoll();
	void setRoll(Float roll);
	Float getFov();
	void setFov(Float fov);
	Vector3f getDirectionVector();
	void setMode(Integer mode);
	Integer getMode();
	String getModeName();

}
