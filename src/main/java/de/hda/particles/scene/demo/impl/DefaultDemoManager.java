package de.hda.particles.scene.demo.impl;

import de.hda.particles.scene.demo.DemoManager;

public class DefaultDemoManager extends AbstractDemoManager implements DemoManager {

	public final static String SYSTEM_NAME = "demo manager";
    
	public DefaultDemoManager() {
		context = new DemoContext(this);
	}

	@Override
	public String getSystemName() {
		return SYSTEM_NAME;
	}
	
}
