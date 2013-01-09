package de.hda.particles.timing;

public interface FpsInformation {

	public final static String MAX_FPS = "maxFps";

	public final static Integer DEFAULT_MAX_FPS = 60;
	
	void setMaxFps(Integer maxFps);
	Integer getMaxFps();
	Double getFps();
	String getSystemName();

}
