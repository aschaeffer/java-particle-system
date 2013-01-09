package de.hda.particles.timing;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

public abstract class FpsLimiter implements FpsInformation {

	// Limiter settings
	public Integer maxFps = DEFAULT_MAX_FPS;
	
	// Limiter states
    private final DescriptiveStatistics fpsBuffer = new DescriptiveStatistics(10);
	public double fps = 0.0;
	public Integer lastSleep = 5;
	private long timeThen;
	private long timeLate;
	
	private long lastFrameTimeStamp = 0; // when the last frame was
	private long frameTimeStamp = 0;

	public void calcFps() {
		frameTimeStamp = Sys.getTime();
		fpsBuffer.addValue(frameTimeStamp - lastFrameTimeStamp);
		fps = (1000.0f / fpsBuffer.getMean());
		lastFrameTimeStamp = frameTimeStamp;
	}
	
	public void limitFps() {
		Display.sync(maxFps);
	}
	
	public void limitFps2() {
		if (fps > maxFps - 1) {
			lastSleep++;
		} else if (fps < maxFps + 1 && lastSleep > 0) {
			lastSleep--;
		}
		try {
			Thread.sleep(lastSleep);
		} catch (InterruptedException e) {}
	}
	
	public void limitFps3() {
		long savedTimeLate = timeLate;
		long gapTo = Sys.getTimerResolution() / maxFps + timeThen;
		long timeNow = Sys.getTime();
		try {
			while (gapTo > timeNow + savedTimeLate) {
				Thread.sleep(1);
				timeNow = Sys.getTime();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		if (gapTo < timeNow) {
			timeLate = timeNow - gapTo;
		} else {
			timeLate = 0;
		}
		timeThen = timeNow;
	}
	
	@Override
	public Integer getMaxFps() {
		return maxFps;
	}

	@Override
	public void setMaxFps(Integer maxFps) {
		this.maxFps = maxFps;
	}
	
	@Override
	public Double getFps() {
		return fps;
	}

}
