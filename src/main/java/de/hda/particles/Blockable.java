package de.hda.particles;

public interface Blockable {

	/**
	 * Blocks the whole system for modification (thread synchronisation).
	 */
	void beginModification();
	
	/**
	 * Unblocks the system for modifications.
	 */
	void endModification();

}
