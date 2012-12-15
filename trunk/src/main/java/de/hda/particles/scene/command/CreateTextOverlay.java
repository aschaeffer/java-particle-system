package de.hda.particles.scene.command;

import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.overlay.TextOverlay;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public class CreateTextOverlay implements Command {

	private final Logger logger = LoggerFactory.getLogger(CreateTextOverlay.class);

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration) {
		String type = (String) configuration.get("class");
		try {
			Class<?> clazz = Class.forName(type);
			List<Scene> scenes = context.getByType(Scene.class);
			ListIterator<Scene> iterator = scenes.listIterator(0);
			while (iterator.hasNext()) {
				TextOverlay textOverlay = (TextOverlay) clazz.newInstance();
				Scene scene = iterator.next();
				scene.getTextOverlayManager().add(textOverlay);
				context.add(textOverlay);
			}
		} catch (Exception e) {
			logger.error("could not create new TextOverlay of type " + type);
		}
		return null;
	}
	
}
