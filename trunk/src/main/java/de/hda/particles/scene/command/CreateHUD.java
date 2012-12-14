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

public class CreateHUD implements Command {

	private final Logger logger = LoggerFactory.getLogger(CreateHUD.class);

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration) {
		String type = (String) configuration.get("class");
		try {
			Class<?> clazz = Class.forName(type);
			List<Scene> scenes = context.getByType(Scene.class);
			ListIterator<Scene> iterator = scenes.listIterator(0);
			while (iterator.hasNext()) {
				HUD hud = (HUD) clazz.newInstance();
				Scene scene = iterator.next();
				scene.getHudManager().add(hud);
				hud.setScene(scene);
				context.add(hud);
			}
			// return context.add(hud);
		} catch (Exception e) {
			logger.error("could not create new HUD of type " + type);
		}
		return null;
	}
	
}