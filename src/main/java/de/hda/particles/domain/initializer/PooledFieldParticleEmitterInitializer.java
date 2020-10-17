package de.hda.particles.domain.initializer;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.impl.configuration.ParticleEmitterConfiguration;
import de.hda.particles.emitter.impl.PooledFieldParticleEmitter;
import de.hda.particles.features.impl.ParticleColor;
import de.hda.particles.scene.Scene;

public class PooledFieldParticleEmitterInitializer {

	private PooledFieldParticleEmitterInitializer() {}

	public static void init(Scene scene, Object subject) {
		PooledFieldParticleEmitter emitter = (PooledFieldParticleEmitter) subject;
		emitter.setParticleLifetime(10);
		emitter.setRate(7);
		emitter.setParticleRendererIndex(1);
		emitter.setFaceRendererIndex(0);
		emitter.setParticleDefaultVelocity(new Vector3f(20.0f, 20.0f, 20.0f));
		ParticleEmitterConfiguration configuration = emitter.getConfiguration();
		configuration.put(ParticleColor.START_COLOR_R, 255);
		configuration.put(ParticleColor.START_COLOR_G, 200);
		configuration.put(ParticleColor.START_COLOR_B, 0);
		configuration.put(ParticleColor.START_COLOR_A, 16);
		configuration.put(ParticleColor.END_COLOR_R, 0);
		configuration.put(ParticleColor.END_COLOR_G, 200);
		configuration.put(ParticleColor.END_COLOR_B, 255);
		configuration.put(ParticleColor.END_COLOR_A, 8);
		emitter.setConfiguration(configuration);
	}

}
