package de.hda.particles;

public class SystemUpdater implements Runnable {

	private Updateable subject;
	private Boolean running = true;
	
	public SystemUpdater(Updateable subject) {
		this.subject = subject;
	}
	
	public void run() {
		while(running) {
			subject.update();
		}
	}

	public void stop() {
		running = false;
	}

}
