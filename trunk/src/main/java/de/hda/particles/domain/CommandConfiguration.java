package de.hda.particles.domain;

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

}
