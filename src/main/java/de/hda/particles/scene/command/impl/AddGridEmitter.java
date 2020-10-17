package de.hda.particles.scene.command.impl;

import de.hda.particles.scene.command.Command;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.impl.configuration.CommandConfiguration;
import de.hda.particles.domain.impl.configuration.ParticleEmitterConfiguration;
import de.hda.particles.emitter.impl.PooledGridParticleEmitter;
import de.hda.particles.hud.impl.HUDCommand;
import de.hda.particles.hud.impl.HUDCommandTypes;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.impl.DemoContext;
import de.hda.particles.scene.demo.impl.DemoHandle;

public class AddGridEmitter implements Command {

	private final Logger logger = LoggerFactory.getLogger(AddGridEmitter.class);

	@SuppressWarnings("unchecked")
	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		List<Scene> scenes = context.getByType(Scene.class);
		ListIterator<Scene> iterator = scenes.listIterator(0);
		while (iterator.hasNext()) {
			Scene scene = iterator.next();
			scene.beginModification();
			scene.getParticleSystem().beginModification();
			PooledGridParticleEmitter emitter = new PooledGridParticleEmitter();
			emitter.setId((Integer) configuration.get("id"));
			emitter.setPosition(new Vector3f(((Double) configuration.get("position_x")).floatValue(), ((Double) configuration.get("position_y")).floatValue(), ((Double) configuration.get("position_z")).floatValue()));
			emitter.setParticleDefaultVelocity(new Vector3f(((Double) configuration.get("velocity_x")).floatValue(), ((Double) configuration.get("velocity_y")).floatValue(), ((Double) configuration.get("velocity_z")).floatValue()));
			emitter.setRate((Integer) configuration.get("rate"));
			emitter.setParticleLifetime((Integer) configuration.get("lifetime"));
			emitter.setParticleRendererIndex((Integer) configuration.get("particleRenderer"));
			emitter.setFaceRendererIndex((Integer) configuration.get("faceRenderer"));
			ParticleEmitterConfiguration emitterConfiguration = new ParticleEmitterConfiguration();
			Map<String, Object> rawParticleEmitterConfiguration = (Map<String, Object>) configuration.get("configuration");
			emitterConfiguration.putAll(rawParticleEmitterConfiguration);
			emitter.setConfiguration(emitterConfiguration);
			scene.getParticleSystem().addParticleEmitter(emitter);
			scene.getParticleSystem().endModification();
			scene.endModification();
			context.add(emitter);
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Added Emitter"));
			logger.info("created Emitter");
		}
		return null;
	}
	
}
