package de.hda.particles.domain;

import java.util.HashMap;
import java.util.List;

public class SceneConfiguration {

	public String name;
	public Integer width;
	public Integer height;
	public Boolean fullscreen;
	public List<HashMap<String, Object>> cameras;
	public List<String> huds;
	public List<String> renderTypes;
	public List<String> renderer;

}
