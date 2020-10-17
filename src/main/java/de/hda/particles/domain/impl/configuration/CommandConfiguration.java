package de.hda.particles.domain.impl.configuration;

import java.util.HashMap;

public class CommandConfiguration extends HashMap<String, Object> {

	private static final long serialVersionUID = -353579370830309007L;

	public CommandConfiguration() {
		super();
	}
	
	public CommandConfiguration(String key, Object value) {
		super();
		this.put(key, value);
	}

	public CommandConfiguration(String key1, Object value1, String key2, Object value2) {
		super();
		this.put(key1, value1);
		this.put(key2, value2);
	}

}
