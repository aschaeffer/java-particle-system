package de.hda.particles.modifier;

import org.lwjgl.util.vector.Vector3f;

public interface PositionablePlaneModifier extends PositionablePointModifier {

	public final static String NORMAL_X = "normal_x";
	public final static String NORMAL_Y = "normal_y";
	public final static String NORMAL_Z = "normal_z";

	void setNormal(Vector3f normal);

}
