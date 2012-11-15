package de.hda.particles.timing;

public interface FpsInformation {

	void setMaxFps(Integer maxFps);
	Integer getMaxFps();
	Double getFps();
	String getSystemName();

}
