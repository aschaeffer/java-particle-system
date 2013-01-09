package de.hda.particles;

public interface Blockable {

	/**
	 * Blocks the whole system for modification (thread synchronisation).
	 */
	public void beginModification();
	
	/**
	 * Unblocks the system for modifications.
	 */
	public void endModification();

}
