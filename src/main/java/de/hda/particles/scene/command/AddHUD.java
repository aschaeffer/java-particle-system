package de.hda.particles.scene.command;

import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.hud.HUD;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public class AddHUD implements Command {

	private final Logger logger = LoggerFactory.getLogger(AddHUD.class);

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		String type = (String) configuration.get("class");
		try {
			Class<?> clazz = Class.forName(type);
			List<Scene> scenes = context.getByType(Scene.class);
			ListIterator<Scene> iterator = scenes.listIterator(0);
			while (iterator.hasNext()) {
				Scene scene = iterator.next();
				HUD hud = (HUD) clazz.newInstance();
				hud.setScene(scene);
				scene.getHudManager().add(hud);
				context.add(hud);
			}
			// return context.add(hud);
		} catch (Exception e) {
			logger.error("could not create new HUD of type " + type, e);
		}
		return null;
	}
	
}
