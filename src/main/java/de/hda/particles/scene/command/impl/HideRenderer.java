package de.hda.particles.scene.command.impl;

import de.hda.particles.scene.command.Command;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.impl.configuration.CommandConfiguration;
import de.hda.particles.renderer.Renderer;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.impl.DemoContext;
import de.hda.particles.scene.demo.impl.DemoHandle;

public class HideRenderer implements Command {

	private final Logger logger = LoggerFactory.getLogger(HideRenderer.class);

	@SuppressWarnings("unchecked")
	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		String type = (String) configuration.get("class");
		try {
			Class<? extends Renderer> clazz = (Class<? extends Renderer>) Class.forName(type);
			List<Scene> scenes = context.getByType(Scene.class);
			ListIterator<Scene> iterator = scenes.listIterator(0);
			while (iterator.hasNext()) {
				Scene scene = iterator.next();
				// scene.beginModification();
				scene.getParticleSystem().beginModification();
				scene.getRendererManager().setVisibility(clazz, false);
				scene.getParticleSystem().endModification();
				// scene.endModification();
			}
		} catch (Exception e) {
			logger.error("could not hide renderer of type " + type);
		}
		return null;
	}
	
}
