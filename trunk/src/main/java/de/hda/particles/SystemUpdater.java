package de.hda.particles;

public interface SystemUpdater extends Runnable {

	public void stop();
	public void stopIn(Integer millis);
	public Boolean isFinished();

}
