package de.hda.particles.scene.demo.impl;

import java.util.UUID;

public class DemoHandle {

	private String uuid;
	
	public DemoHandle() {
		this.setUuid(UUID.randomUUID().toString());
	}

	public String getUuid() {
		return uuid;
	}

	protected void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
