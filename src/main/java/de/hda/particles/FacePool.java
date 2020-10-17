package de.hda.particles;

import org.apache.commons.collections.ArrayStack;

import de.hda.particles.domain.impl.face.DefaultFace;
import de.hda.particles.domain.Face;
import de.hda.particles.listener.FaceLifetimeListener;

/**
 * The creation of thousands of face objects per seconds slows
 * down the whole system and increases memory consumption dramatically.
 * 
 * Therefore we have to reduce the creation of new face objects by
 * creating a pool of faces. The pool is implemented as an ArrayStack.
 * 
 * @author aschaeffer
 *
 */
public class FacePool extends ArrayStack implements FaceLifetimeListener {

	/**
	 * The serial.
	 */
	private static final long serialVersionUID = 3602697554779890223L;

	/**
	 * Returns the next particle from the pool (or creates a new
	 * particle object).
	 * 
	 * @return
	 */
	public Face next() {
		if (empty()) {
			return new DefaultFace();
		} else {
			return (Face) pop();
		}
	}

	/**
	 * Inserts a death particle to the pool.
	 */
	@Override
	public void onFaceDeath(Face face) {
		push(face);
	}

	/**
	 * Not used.
	 */
	@Override
	public void onFaceCreation(Face face) {
	}

}
