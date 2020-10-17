package de.hda.particles.scene.command.impl;

import de.hda.particles.scene.command.Command;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.configuration.impl.CommandConfiguration;
import de.hda.particles.renderer.ParticleRenderer;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.impl.DemoContext;
import de.hda.particles.scene.demo.impl.DemoHandle;

public class ReplaceParticleRenderer implements Command {

	private final Logger logger = LoggerFactory.getLogger(ReplaceParticleRenderer.class);

	@SuppressWarnings("unchecked")
	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		String type = (String) configuration.get("class");
		if (type == null) return null;
		Integer index = (Integer) configuration.get("index");
		if (index == null) return null;
		try {
			Class<? extends ParticleRenderer> clazz = (Class<? extends ParticleRenderer>) Class.forName(type);
			List<Scene> scenes = context.getByType(Scene.class);
			ListIterator<Scene> iterator = scenes.listIterator(0);
			while (iterator.hasNext()) {
				Scene scene = iterator.next();
				// scene.beginModification();
				scene.getParticleSystem().beginModification();
				scene.getParticleRendererManager().replace(clazz, index);
				scene.getParticleSystem().endModification();
				// scene.endModification();
				logger.info("replaced particle renderer");
			}
		} catch (Exception e) {
			logger.error("could not create new particle renderer of type " + type);
		}
		return null;
	}
	
}
