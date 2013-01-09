package de.hda.particles.listener;

import de.hda.particles.domain.Face;

/**
 * Listener interface for face lifetime changes. Informs
 * listener about face creation and face removal.
 * 
 * @author aschaeffer
 *
 */
public interface FaceLifetimeListener {

	/**
	 * Called on face creation.
	 * @param face The newly created face.
	 */
	void onFaceCreation(Face face);
	
	/**
	 * Called on face removal.
	 * @param face The face being removed.
	 */
	void onFaceDeath(Face face);

}
