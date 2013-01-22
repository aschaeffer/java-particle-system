package de.hda.particles.scene.command;

import java.util.List;
import java.util.ListIterator;

import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public class RemoveParticles implements Command {

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		List<Scene> scenes = context.getByType(Scene.class);
		ListIterator<Scene> iterator = scenes.listIterator(0);
		while (iterator.hasNext()) {
			Scene scene = iterator.next();
			scene.getParticleSystem().removeAllParticles();
		}
		return null;
	}
	
}
