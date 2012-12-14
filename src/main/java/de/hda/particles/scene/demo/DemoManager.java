package de.hda.particles.scene.demo;

import de.hda.particles.domain.ChangeSet;
import de.hda.particles.domain.Demo;

public interface DemoManager {

	void run();

	void addChangeSet(ChangeSet changeSet);

	void create();
	void load(String filename);
	void save(String filename);

	Demo getDemo();
	void setDemo(Demo demo);
	DemoContext getContext();
	void setContext(DemoContext context);
	
}
