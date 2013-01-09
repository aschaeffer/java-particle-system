package de.hda.particles.scene.command;

import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.ParticleSystem;
import de.hda.particles.SystemRunner;
import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public class CreateSystemRunner implements Command {

	private final Logger logger = LoggerFactory.getLogger(CreateSystemRunner.class);

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		SystemRunner systemRunner = new SystemRunner();

		List<ParticleSystem> particleSystems = context.getByType(ParticleSystem.class);
		logger.debug("found " + particleSystems.size() + " particle systems");
		ListIterator<ParticleSystem> iteratorParticleSystems = particleSystems.listIterator(0);
		while (iteratorParticleSystems.hasNext()) {
			ParticleSystem particleSystem = iteratorParticleSystems.next();
			systemRunner.add(particleSystem.getSystemName(), particleSystem);
			logger.debug("added particle system to system runner");
		}

		List<Scene> scenes = context.getByType(Scene.class);
		ListIterator<Scene> iteratorScenes = scenes.listIterator(0);
		while (iteratorScenes.hasNext()) {
			Scene scene = iteratorScenes.next();
			systemRunner.add(scene.getSystemName(), scene);
		}
		return context.add(systemRunner);
	}
	
}
