package de.hda.particles.scene.command;

import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.renderer.types.RenderType;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public class ReplaceRenderType implements Command {

	private final Logger logger = LoggerFactory.getLogger(ReplaceRenderType.class);

	@SuppressWarnings("unchecked")
	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		String type = (String) configuration.get("class");
		try {
			Class<? extends RenderType> clazz = (Class<? extends RenderType>) Class.forName(type);
			List<Scene> scenes = context.getByType(Scene.class);
			ListIterator<Scene> iterator = scenes.listIterator(0);
			while (iterator.hasNext()) {
				Scene scene = iterator.next();
				// scene.beginModification();
				scene.getParticleSystem().beginModification();
				scene.getRenderTypeManager().replace(clazz, (Integer) configuration.get("index"));
				scene.getParticleSystem().endModification();
				// scene.endModification();
				logger.info("replaced render type");
			}
		} catch (Exception e) {
			logger.error("could not create new renderType of type " + type);
		}
		return null;
	}
	
}
