package de.hda.particles;

/**
 * Defines components which are updatable.
 */
public interface Updatable {

	/**
	 * Called on update.
	 */
	void update();

	/**
	 * Called on initialization.
	 */
	void setup();

	/**
	 * Called on destructution.
	 */
	void destroy();

	/**
	 * Returns true if the component is finished.
	 * @return
	 */
	Boolean isFinished();

}
