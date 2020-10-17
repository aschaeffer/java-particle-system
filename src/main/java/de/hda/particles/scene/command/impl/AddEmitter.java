package de.hda.particles.scene.command.impl;

import de.hda.particles.scene.command.Command;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.vector.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.configuration.impl.CommandConfiguration;
import de.hda.particles.configuration.impl.ParticleEmitterConfiguration;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.impl.DemoContext;
import de.hda.particles.scene.demo.impl.DemoHandle;

public class AddEmitter implements Command {

	private final Logger logger = LoggerFactory.getLogger(AddEmitter.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		String type = (String) configuration.get("class");
		try {
			Class<? extends ParticleEmitter> clazz = (Class<? extends ParticleEmitter>) Class.forName(type);
			List<Scene> scenes = context.getByType(Scene.class);
			ListIterator<Scene> iterator = scenes.listIterator(0);
			while (iterator.hasNext()) {
				Scene scene = iterator.next();
				scene.getParticleSystem().beginModification();
				ParticleEmitter emitter = clazz.newInstance();
				ParticleEmitterConfiguration emitterConfiguration = null;
				if (configuration.containsKey("configuration")) {
					emitterConfiguration = (ParticleEmitterConfiguration) configuration.get("configuration");
				} else if (configuration.containsKey("factoryClass")) {
					Class configurationFactoryClass = Class.forName((String) configuration.get("factoryClass"));
					if (configurationFactoryClass != null) {
						try {
							emitterConfiguration = (ParticleEmitterConfiguration) configurationFactoryClass.getMethod("create", Scene.class).invoke(null, scene);
						} catch (Exception e) {}
					}
					if (emitterConfiguration == null) {
						emitterConfiguration = new ParticleEmitterConfiguration();
					}
				} else {
					emitterConfiguration = new ParticleEmitterConfiguration();
				}
				Vector3f position = new Vector3f(scene.getCameraManager().getPosition());
				scene.getParticleSystem().addParticleEmitter(emitter, position, scene.getCameraManager().getDirectionVector(), 1, 10, 300, emitterConfiguration);
				scene.getParticleSystem().endModification();
				context.add(emitter);
				logger.info("created new emitter of type " + type);
			}
		} catch (Exception e) {
			logger.error("could not create new emitter of type " + type);
		}
		return null;
	}
	
}
