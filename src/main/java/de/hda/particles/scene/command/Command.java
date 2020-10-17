package de.hda.particles.scene.command;

import de.hda.particles.configuration.impl.CommandConfiguration;
import de.hda.particles.scene.demo.impl.DemoContext;
import de.hda.particles.scene.demo.impl.DemoHandle;

public interface Command {

	/**
	 * No constructors for command implementations!!!
	 *  
	 * @param context DemoContext
	 * @param configuration Command Configuration
	 * @return id
	 */
	DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations);

}
