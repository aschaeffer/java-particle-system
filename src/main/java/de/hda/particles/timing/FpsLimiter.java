package de.hda.particles.timing;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

public abstract class FpsLimiter {

    private DescriptiveStatistics fpsBuffer = new DescriptiveStatistics(10);
	private long lastFrameTimeStamp = 0; // when the last frame was
	public double fps = 0.0d;
	public Integer maxFps = 45;
	public Integer lastSleep = 5;

	public void calcFps() {
		long frameTimeStamp = Sys.getTime();
		fpsBuffer.addValue(frameTimeStamp - lastFrameTimeStamp);
		fps = (1000.0f / fpsBuffer.getMean());
		lastFrameTimeStamp = frameTimeStamp;
		// logger.debug(String.format("render: %.1f fps", fps));
	}
	
	public void limitFps() {
		Display.sync(maxFps);
		
//		if (fps > maxFps - 1) {
//			lastSleep++;
//		} else if (fps < maxFps + 1 && lastSleep > 0) {
//			lastSleep--;
//		}
//		try {
//			Thread.sleep(lastSleep);
//		} catch (InterruptedException e) {
//		}
	}
	
	public void setMaxFps(Integer maxFps) {
		this.maxFps = maxFps;
	}


}
