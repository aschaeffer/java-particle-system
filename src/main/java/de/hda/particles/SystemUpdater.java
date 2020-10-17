package de.hda.particles;

/**
 * SystemUpdaters contains and updates a system.
 * 
 * @author aschaeffer
 *
 */
public interface SystemUpdater extends Runnable {

	void stop();
	void stopIn(Integer millis);
	Boolean isFinished();

}
