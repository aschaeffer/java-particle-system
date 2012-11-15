package de.hda.particles.domain;

import java.util.HashMap;
import java.util.List;

public class ParticleSystemConfiguration {

	public String name;
	public List<String> features;
	public List<HashMap<String, Object>> emitters;
	public List<HashMap<String, Object>> modifiers;

}
