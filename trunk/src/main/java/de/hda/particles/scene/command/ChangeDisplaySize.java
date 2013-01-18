package de.hda.particles.scene.command;

import java.util.List;
import java.util.ListIterator;

import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public class ChangeDisplaySize implements Command {

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		Integer width = (Integer) configuration.get(Scene.WIDTH);
		Integer height = (Integer) configuration.get(Scene.HEIGHT);
		List<Scene> scenes = context.getByType(Scene.class);
		ListIterator<Scene> iterator = scenes.listIterator(0);
		while (iterator.hasNext()) {
			Scene scene = iterator.next();
			// scene.beginModification();
			scene.setWidth(width);
			scene.setHeight(height);
			scene.applyChanges();
			// scene.endModification();
		}
		return null;
	}
	
}
