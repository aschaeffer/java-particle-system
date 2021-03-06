package de.hda.particles.domain;

import java.util.List;

/**
 * A face contains 4 particles.
 * 
 * @author aschaeffer
 *
 */
public interface Face extends List<Particle> {

	int DEFAULT_FACE_RENDERER_INDEX = 1;

	/**
	 * Returns floats
	 * @return x1,y1,z1,x2,y2,z2,x3,y3,z3,x4,y4,z4,...
	 */
	float[] getVertices();

	/**
	 * Returns face renderer index of the face.
	 * 
	 * @return The face renderer index.
	 */
	int getFaceRendererIndex();
	
	/**
	 * Sets the face renderer index.
	 * 
	 * @param faceRendererIndex The face renderer index.
	 */
	void setFaceRendererIndex(int faceRendererIndex);

	/**
	 * Returns true if visible.
	 * @return True if visible.
	 */
	boolean isVisible();
	
	/**
	 * Set the visibility of the face.
	 * 
	 * @param visibility True, if the face should be visible.
	 */
	void setVisibility(boolean visibility);
	
	/**
	 * Returns true if all face particles are alive.
	 * 
	 * @return True if all face particles are alive.
	 */
	boolean isAlive();

}
