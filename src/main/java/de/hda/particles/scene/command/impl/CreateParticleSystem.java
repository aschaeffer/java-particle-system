package de.hda.particles.scene.command.impl;

import de.hda.particles.scene.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.ConfigurableParticleSystem;
import de.hda.particles.configuration.impl.CommandConfiguration;
import de.hda.particles.scene.demo.impl.DemoContext;
import de.hda.particles.scene.demo.impl.DemoHandle;

public class CreateParticleSystem implements Command {

	private final Logger logger = LoggerFactory.getLogger(CreateParticleSystem.class);
	
	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		ConfigurableParticleSystem particleSystem = new ConfigurableParticleSystem();
		particleSystem.setName((String) configuration.get("name"));
		logger.debug("created particle system");
		return context.add(particleSystem);
	}
	
}
