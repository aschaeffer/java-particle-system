package de.hda.particles.scene.command;

import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.hud.HUDCommand;
import de.hda.particles.hud.HUDCommandTypes;
import de.hda.particles.renderer.particles.ParticleRenderer;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public class AddParticleRenderer implements Command {

	private final Logger logger = LoggerFactory.getLogger(AddParticleRenderer.class);

	@SuppressWarnings("unchecked")
	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		String type = (String) configuration.get("class");
		try {
			Class<? extends ParticleRenderer> clazz = (Class<? extends ParticleRenderer>) Class.forName(type);
			List<Scene> scenes = context.getByType(Scene.class);
			ListIterator<Scene> iterator = scenes.listIterator(0);
			while (iterator.hasNext()) {
				Scene scene = iterator.next();
				// scene.beginModification();
				scene.getParticleSystem().beginModification();
				scene.getParticleRendererManager().add(clazz);
				scene.getParticleSystem().endModification();
				// scene.endModification();
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Added Particle Renderer: " + clazz.getSimpleName()));
				logger.info("added particle renderer of type " + type);
			}
		} catch (Exception e) {
			logger.error("could not create new particle renderer of type " + type);
		}
		return null;
	}
	
}
