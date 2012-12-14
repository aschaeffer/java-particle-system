package de.hda.particles.scene.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.ConfigurableParticleSystem;
import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public class CreateParticleSystem implements Command {

	private final Logger logger = LoggerFactory.getLogger(CreateParticleSystem.class);
	
	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration) {
		ConfigurableParticleSystem particleSystem = new ConfigurableParticleSystem();
		particleSystem.setName((String) configuration.get("name"));
		logger.debug("created particle system");
		return context.add(particleSystem);
	}
	
}
