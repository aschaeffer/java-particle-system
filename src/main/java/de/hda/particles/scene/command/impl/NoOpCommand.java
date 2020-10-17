package de.hda.particles.scene.command.impl;

import de.hda.particles.configuration.impl.CommandConfiguration;
import de.hda.particles.scene.command.Command;
import de.hda.particles.scene.demo.impl.DemoContext;
import de.hda.particles.scene.demo.impl.DemoHandle;

public class NoOpCommand implements Command {

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		return new DemoHandle();
	}
	
}
