package de.hda.particles.modifier;

import org.lwjgl.util.vector.Vector3f;

public interface PositionablePointModifier extends ParticleModifier {

	String POSITION_X = "position_x";
	String POSITION_Y = "position_y";
	String POSITION_Z = "position_z";

	Vector3f getPosition();
	void setPosition(Vector3f position);
	float getX();
	void setX(float x);
	float getY();
	void setY(float y);
	float getZ();
	void setZ(float z);

}
