package de.hda.particles;

/**
 * SystemUpdaters contains and updates a system.
 * 
 * @author aschaeffer
 *
 */
public interface SystemUpdater extends Runnable {

	public void stop();
	public void stopIn(Integer millis);
	public Boolean isFinished();

}
