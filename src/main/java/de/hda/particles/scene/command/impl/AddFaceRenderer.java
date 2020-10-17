package de.hda.particles.scene.command.impl;

import de.hda.particles.scene.command.Command;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.impl.configuration.CommandConfiguration;
import de.hda.particles.hud.impl.HUDCommand;
import de.hda.particles.hud.impl.HUDCommandTypes;
import de.hda.particles.renderer.FaceRenderer;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.impl.DemoContext;
import de.hda.particles.scene.demo.impl.DemoHandle;

public class AddFaceRenderer implements Command {

	private final Logger logger = LoggerFactory.getLogger(AddFaceRenderer.class);

	@SuppressWarnings("unchecked")
	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		String type = (String) configuration.get("class");
		try {
			Class<? extends FaceRenderer> clazz = (Class<? extends FaceRenderer>) Class.forName(type);
			List<Scene> scenes = context.getByType(Scene.class);
			ListIterator<Scene> iterator = scenes.listIterator(0);
			while (iterator.hasNext()) {
				Scene scene = iterator.next();
				// scene.beginModification();
				scene.getParticleSystem().beginModification();
				scene.getFaceRendererManager().add(clazz);
				scene.getParticleSystem().endModification();
				// scene.endModification();
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Added Face Renderer: " + clazz.getSimpleName()));
				logger.info("added faceRenderer of type " + type);
			}
		} catch (Exception e) {
			logger.error("could not create new faceRenderer of type " + type);
		}
		return null;
	}
	
}
