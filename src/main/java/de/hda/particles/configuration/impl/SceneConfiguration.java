package de.hda.particles.configuration.impl;

import java.util.HashMap;
import java.util.List;

public class SceneConfiguration {

	public String name;
	public Integer width;
	public Integer height;
	public Boolean fullscreen;
	public List<HashMap<String, Object>> cameras;
	public List<String> huds;
	public List<String> particleRenderer;
	public List<String> faceRenderers;
	public List<String> renderer;
	public List<String> textOverlays;

}
