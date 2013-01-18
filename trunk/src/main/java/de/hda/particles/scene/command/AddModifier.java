package de.hda.particles.scene.command;

import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.renderer.particles.ParticleRenderer;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public class AddModifier implements Command {

	private final Logger logger = LoggerFactory.getLogger(AddModifier.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		String type = (String) configuration.get("class");
		try {
			Class<? extends ParticleRenderer> clazz = (Class<? extends ParticleRenderer>) Class.forName(type);
			List<Scene> scenes = context.getByType(Scene.class);
			ListIterator<Scene> iterator = scenes.listIterator(0);
			while (iterator.hasNext()) {
				Scene scene = iterator.next();
				scene.getParticleSystem().beginModification();
				ParticleModifier modifier = (ParticleModifier) clazz.newInstance();
				// modifier.setId((Integer) configuration.get("id"));
				ParticleModifierConfiguration c = null;
				if (configuration.containsKey("configuration")) {
					c = (ParticleModifierConfiguration) configuration.get("configuration");
				} else if (configuration.containsKey("factoryClass")) {
					Class configurationFactoryClass = Class.forName((String) configuration.get("factoryClass"));
					if (configurationFactoryClass != null) {
						try {
							c = (ParticleModifierConfiguration) configurationFactoryClass.getMethod("create", Scene.class).invoke(null, scene);
						} catch (Exception e) {}
					}
					if (c == null) {
						c = new ParticleModifierConfiguration();
					}
				} else {
					c = new ParticleModifierConfiguration();
				}
				scene.getParticleSystem().addParticleModifier(modifier, c);
				scene.getParticleSystem().endModification();
				context.add(modifier);
				logger.info("created new modifier of type " + type);
			}
		} catch (Exception e) {
			logger.error("could not create new modifier of type " + type);
		}
		return null;
	}
	
}
