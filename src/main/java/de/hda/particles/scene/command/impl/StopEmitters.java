package de.hda.particles.scene.command.impl;

import de.hda.particles.scene.command.Command;
import java.util.List;
import java.util.ListIterator;

import de.hda.particles.configuration.impl.CommandConfiguration;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.impl.DemoContext;
import de.hda.particles.scene.demo.impl.DemoHandle;

public class StopEmitters implements Command {

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		List<Scene> scenes = context.getByType(Scene.class);
		ListIterator<Scene> iterator = scenes.listIterator(0);
		while (iterator.hasNext()) {
			Scene scene = iterator.next();
			Boolean stopped = scene.getParticleSystem().areEmittersStopped();
			if (!stopped) {
				scene.getParticleSystem().beginModification();
				scene.getParticleSystem().toggleEmitters();
				scene.getParticleSystem().endModification();
			}
		}
		return null;
	}
	
}
