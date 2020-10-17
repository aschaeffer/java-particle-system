package de.hda.particles.scene.command.impl;

import de.hda.particles.scene.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.dao.DemoDAO;
import de.hda.particles.configuration.impl.CommandConfiguration;
import de.hda.particles.domain.Demo;
import de.hda.particles.scene.demo.impl.DemoContext;
import de.hda.particles.scene.demo.impl.DemoHandle;
import de.hda.particles.scene.demo.DemoManager;

public class LoadChangeSet implements Command {

	private DemoDAO demoDAO = new DemoDAO();
	private final Logger logger = LoggerFactory.getLogger(LoadChangeSet.class);

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		String filename = (String) configuration.get("filename");
		if (filename == null) return null;
		Integer iteration = (Integer) configuration.get("iteration");
		try {
			DemoManager demoManager = context.getByType(DemoManager.class).get(0);
			if (iteration == null) {
				iteration = demoManager.getPastIterations() + 1;
			}
			Demo demo = demoManager.getDemo();
			demoDAO.loadDiff(demo, filename, iteration);
		} catch (Exception e) {
			logger.error("Could not load changeset from file " + filename + "! Error: "+ e.getMessage(), e);
		}
		return null;
	}
	
}
