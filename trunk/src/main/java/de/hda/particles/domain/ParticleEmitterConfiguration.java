package de.hda.particles.domain;

import java.util.HashMap;

public class ParticleEmitterConfiguration extends HashMap<String, Object> {

	private static final long serialVersionUID = -2469636233684594893L;

	public ParticleEmitterConfiguration() {
		super();
	}
	
	public ParticleEmitterConfiguration(String key, Object value) {
		super();
		this.put(key, value);
	}

}
