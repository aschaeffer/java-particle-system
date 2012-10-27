package de.hda.particles;

import java.util.Date;

public class SystemUpdater implements Runnable {

	private Updateable subject;
	private Boolean running = true;
	private long stopInMillis = 0;
	
	public SystemUpdater(Updateable subject) {
		this.subject = subject;
	}
	
	public void run() {
		running = true;
		while(running && stopInMillis > 0 && stopInMillis < new Date().getTime()) {
			subject.update();
		}
		running = false;
	}

	public void stop() {
		running = false;
	}
	
	public void stopIn(Integer millis) {
		stopInMillis = new Date().getTime() + millis;
	}

}
