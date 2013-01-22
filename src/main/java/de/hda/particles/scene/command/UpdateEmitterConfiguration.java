package de.hda.particles.scene.command;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.domain.ParticleEmitterConfiguration;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public class UpdateEmitterConfiguration implements Command {

	@SuppressWarnings("unchecked")
	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		Integer emitterId = (Integer) configuration.get("id");
		if (emitterId == null) return null;
		List<Scene> scenes = context.getByType(Scene.class);
		ListIterator<Scene> iterator = scenes.listIterator(0);
		while (iterator.hasNext()) {
			Scene scene = iterator.next();
			ListIterator<ParticleEmitter> emitterIterator = scene.getParticleSystem().getParticleEmitters().listIterator(0);
			while (emitterIterator.hasNext()) {
				ParticleEmitter emitter = emitterIterator.next();
				if (emitter.getId().equals(emitterId)) {
					Map<String, Object> emitterValues = (Map<String, Object>) configuration.get("configuration");
					Vector3f position = new Vector3f(emitter.getPosition());
					if (emitterValues.containsKey("position_x")) position.x = ((Double) emitterValues.get("position_x")).floatValue();
					if (emitterValues.containsKey("position_y")) position.y = ((Double) emitterValues.get("position_y")).floatValue();
					if (emitterValues.containsKey("position_z")) position.z = ((Double) emitterValues.get("position_z")).floatValue();
					emitter.setPosition(position);
					Vector3f velocity = new Vector3f(emitter.getParticleDefaultVelocity());
					if (emitterValues.containsKey("velocity_x")) velocity.x = ((Double) emitterValues.get("velocity_x")).floatValue();
					if (emitterValues.containsKey("velocity_y")) velocity.y = ((Double) emitterValues.get("velocity_y")).floatValue();
					if (emitterValues.containsKey("velocity_z")) velocity.z = ((Double) emitterValues.get("velocity_z")).floatValue();
					emitter.setParticleDefaultVelocity(velocity);
					if (emitterValues.containsKey("rate")) emitter.setRate((Integer) emitterValues.get("rate"));
					if (emitterValues.containsKey("lifetime")) emitter.setParticleLifetime((Integer) emitterValues.get("lifetime"));
					if (emitterValues.containsKey("particleRenderer")) emitter.setParticleRendererIndex((Integer) emitterValues.get("particleRenderer"));
					if (emitterValues.containsKey("faceRenderer")) emitter.setFaceRendererIndex((Integer) emitterValues.get("faceRenderer"));
					Map<String, Object> emitterConfigurationValues = (Map<String, Object>) emitterValues.get("configuration");
					ParticleEmitterConfiguration emitterConfiguration = emitter.getConfiguration();
					emitterConfiguration.putAll(emitterConfigurationValues);
				}
			}
		}
		return null;
	}
	
}
