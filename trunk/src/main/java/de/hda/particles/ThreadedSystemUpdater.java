package de.hda.particles;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadedSystemUpdater implements SystemUpdater {

	private Updateable subject;
	private Boolean running = true;
	private long stopInMillis = 0;
	private Integer doneIterations = 0;
	private String name = "SystemUpdater";
	
	private final Logger logger = LoggerFactory.getLogger(ThreadedSystemUpdater.class);
	
	public ThreadedSystemUpdater(String name, Updateable subject) {
		this.subject = subject;
		this.name = name;
	}
	
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

	public void stop() {
		running = false;
	}
	
	public void stopIn(Integer millis) {
		stopInMillis = (new Date().getTime()) + millis;
	}
	
	public Boolean isFinished() {
		return !running;
	}

}
