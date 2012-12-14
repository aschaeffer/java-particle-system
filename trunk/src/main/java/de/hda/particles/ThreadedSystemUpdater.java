package de.hda.particles;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of the SystemUpdater interface which is
 * designed for use with threads.
 * 
 * @author aschaeffer
 *
 */
public class ThreadedSystemUpdater implements SystemUpdater {

	/**
	 * The subject to be managed.
	 */
	private final Updateable subject;
	
	/**
	 * True, if the system updater is still running.
	 */
	private Boolean running = true;
	
	/**
	 * Maximum iterations or 0 to disable. 
	 */
	private long stopInMillis = 0;
	
	/**
	 * Number of iterations of the system updater.
	 */
	private Integer doneIterations = 0;
	
	/**
	 * Name of the system updater.
	 */
	private String name = "SystemUpdater";
	
	/**
	 * The logger.
	 */
	private final Logger logger = LoggerFactory.getLogger(ThreadedSystemUpdater.class);
	
	/**
	 * Creates a new ThreadedSystemUpdater.
	 * 
	 * @param name Name of the system updater.
	 * @param subject Updateable to be managed.
	 */
	public ThreadedSystemUpdater(String name, Updateable subject) {
		this.subject = subject;
		this.name = name;
	}

	/**
	 * System updater thread main loop.
	 */
	@Override
	public void run() {
		try {
			running = true;
			logger.debug(name + ": setup");
			subject.setup();
			logger.info(name + ": start");
			while(running && (stopInMillis == 0 || stopInMillis > (new Date().getTime())) && !subject.isFinished()) {
				doneIterations++;
				logger.trace("SystemUpdater: iteration " + doneIterations);
				subject.update();
			}
			logger.info(name + ": stopped");
			subject.destroy();
			logger.debug(name + ": destroyed");
			running = false;
		} catch(Exception e) {
			logger.error(name + ": exception", e);
			running = false;
			subject.destroy();
			logger.info(name + ": destroyed");
		}
	}

	/**
	 * Method to stop the system updater externally.
	 */
	@Override
	public void stop() {
		running = false;
	}

	/**
	 * Stop the system updater after a number of iterations.
	 */
	@Override
	public void stopIn(Integer millis) {
		stopInMillis = (new Date().getTime()) + millis;
	}

	/**
	 * Returns true, if the system updater is finished.
	 */
	@Override
	public Boolean isFinished() {
		return !running;
	}

}
