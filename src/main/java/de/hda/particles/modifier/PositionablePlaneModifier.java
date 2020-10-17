package de.hda.particles.modifier;

import org.lwjgl.util.vector.Vector3f;

public interface PositionablePlaneModifier extends PositionablePointModifier {

	String NORMAL_X = "normal_x";
	String NORMAL_Y = "normal_y";
	String NORMAL_Z = "normal_z";

	void setNormal(Vector3f normal);

}
