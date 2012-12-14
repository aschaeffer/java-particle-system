package de.hda.particles.scene.command;

import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public interface Command {

	/**
	 * 
	 * @param context DemoContext
	 * @param configuration Command Configuration
	 * @return id
	 */
	DemoHandle execute(DemoContext context, CommandConfiguration configuration);

}
