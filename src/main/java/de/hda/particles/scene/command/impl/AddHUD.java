package de.hda.particles.scene.command.impl;

import de.hda.particles.scene.command.Command;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.configuration.impl.CommandConfiguration;
import de.hda.particles.hud.HUD;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.impl.DemoContext;
import de.hda.particles.scene.demo.impl.DemoHandle;

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
