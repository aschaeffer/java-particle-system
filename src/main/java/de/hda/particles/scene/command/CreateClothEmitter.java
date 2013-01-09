package de.hda.particles.scene.command;

import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.vector.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.domain.ParticleEmitterConfiguration;
import de.hda.particles.emitter.PooledClothParticleEmitter;
import de.hda.particles.hud.HUDCommand;
import de.hda.particles.hud.HUDCommandTypes;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public class CreateClothEmitter implements Command {

	private final Logger logger = LoggerFactory.getLogger(CreateClothEmitter.class);

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		List<Scene> scenes = context.getByType(Scene.class);
		ListIterator<Scene> iterator = scenes.listIterator(0);
		while (iterator.hasNext()) {
			Scene scene = iterator.next();
			// scene.beginModification();
			scene.getParticleSystem().beginModification();
			PooledClothParticleEmitter emitter = new PooledClothParticleEmitter();
			// emitter.setId((Integer) configuration.get("id"));
			emitter.setPosition(new Vector3f(((Double) configuration.get("position_x")).floatValue(), ((Double) configuration.get("position_y")).floatValue(), ((Double) configuration.get("position_z")).floatValue()));
			emitter.setParticleDefaultVelocity(new Vector3f(((Double) configuration.get("velocity_x")).floatValue(), ((Double) configuration.get("velocity_y")).floatValue(), ((Double) configuration.get("velocity_z")).floatValue()));
			emitter.setRate((Integer) configuration.get("rate"));
			emitter.setParticleLifetime((Integer) configuration.get("lifetime"));
			emitter.setParticleRenderTypeIndex((Integer) configuration.get("renderType"));
			emitter.setFaceRendererIndex((Integer) configuration.get("faceRenderer"));
			ParticleEmitterConfiguration emitterConfiguration = new ParticleEmitterConfiguration();
			emitterConfiguration.putAll((ParticleEmitterConfiguration) configuration.get("configuration"));
			emitter.setConfiguration(emitterConfiguration);
			scene.getParticleSystem().addParticleEmitter(emitter);
			scene.getParticleSystem().endModification();
			// scene.endModification();
			context.add(emitter);
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Added Cloth Emitter"));
			logger.info("created Emitter");
		}
		return null;
	}
	
}
