package de.hda.particles.scene.demo;

import de.hda.particles.domain.ChangeSet;
import de.hda.particles.domain.Demo;
import de.hda.particles.timing.FpsInformation;

public interface DemoManager extends FpsInformation {

	void run();

	void addChangeSet(ChangeSet changeSet);

	void reset();
	void load(String filename);
	void loadFromResource(String filename);
	void loadDiff(String filename);
	void loadDiffFromResource(String filename);
	void save(String filename);

	Demo getDemo();
	void setDemo(Demo demo);
	DemoContext getContext();
	void setContext(DemoContext context);
	
}
