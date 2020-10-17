package de.hda.particles.configuration.impl;

import java.util.HashMap;

public class ParticleModifierConfiguration extends HashMap<String, Object> {

	private static final long serialVersionUID = -2469636233684594893L;

	public ParticleModifierConfiguration() {
		super();
	}
	
	public ParticleModifierConfiguration(String key, Object value) {
		super();
		this.put(key, value);
	}

}
