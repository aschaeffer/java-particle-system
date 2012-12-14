package de.hda.particles.scene.command;

import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public class NoOpCommand implements Command {

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration) {
		return new DemoHandle();
	}
	
}
