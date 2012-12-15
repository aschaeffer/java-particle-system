package de.hda.particles.scene.demo;

import java.io.File;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.SystemRunner;
import de.hda.particles.Updateable;
import de.hda.particles.dao.DemoDAO;
import de.hda.particles.domain.ChangeSet;
import de.hda.particles.domain.Demo;

public abstract class AbstractDemoManager implements Updateable {

	private final DemoDAO demoDAO = new DemoDAO();
	private Demo demo = new Demo();
	private DemoContext context = new DemoContext();
	private Integer currentIteration = 0;
	private Integer lastIteration = 0;
	
	private final Logger logger = LoggerFactory.getLogger(AbstractDemoManager.class);

	public void create() {
		demo = new Demo();
		context.clear();
		lastIteration = 0;
	}

	public void load(String filename) {
		try {
			demoDAO.load(demo, new File(filename));
		} catch (Exception e) {
			logger.error("could not load " + filename, e);
		}
	}
	
	public void save(String filename) {
		try {
			demoDAO.save(demo, new File(filename));
		} catch (Exception e) {
			logger.error("could not load " + filename, e);
		}
	}
	
	public void run() {
		update(); // execute all changesets of iteration 0
		List<SystemRunner> systemRunners = context.getByType(SystemRunner.class);
		logger.info("found " + systemRunners.size() + " system runners");
		ListIterator<SystemRunner> iterator = systemRunners.listIterator(0);
		while(iterator.hasNext()) {
			SystemRunner runner = iterator.next();
			runner.add("DemoManager", this); // clever trick: the demo manager itself is managed by the system runner
			try {
				logger.info("start system runner");
				runner.start();
				logger.info("system runner finished");
			} catch (InterruptedException e) {
				logger.error("...", e);
			}
		}
	}

	@Override
	public void update() {
		currentIteration++; // or get the iteration from any particle system?
		List<ChangeSet> changeSets = demo.getChangeSets();
		ListIterator<ChangeSet> iterator = changeSets.listIterator(0);
		while (iterator.hasNext()) {
			ChangeSet changeSet = iterator.next();
			if (changeSet.getIteration() >= lastIteration && changeSet.getIteration() < currentIteration) {
				// DemoHandle handle = 
				changeSet.getCommand().execute(context, changeSet.getConfiguration());
			}
		}
		lastIteration = currentIteration;
	}

	public Demo getDemo() {
		return demo;
	}

	public void setDemo(Demo demo) {
		this.demo = demo;
	}
	
	public DemoContext getContext() {
		return context;
	}

	public void setContext(DemoContext context) {
		this.context = context;
	}
	
	public void addChangeSet(ChangeSet changeSet) {
		demo.addChangeSet(changeSet);
	}

	@Override
	public void setup() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public Boolean isFinished() {
		return false;
	}
	
}
