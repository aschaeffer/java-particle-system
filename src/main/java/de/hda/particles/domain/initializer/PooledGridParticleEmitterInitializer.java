package de.hda.particles.domain.initializer;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.ParticleEmitterConfiguration;
import de.hda.particles.emitter.PooledGridParticleEmitter;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.scene.Scene;

public class PooledGridParticleEmitterInitializer {

	private PooledGridParticleEmitterInitializer() {}

	public static void init(Scene scene, Object subject) {
		PooledGridParticleEmitter emitter = (PooledGridParticleEmitter) subject;
		emitter.setParticleLifetime(100);
		emitter.setRate(7);
		emitter.setParticleRendererIndex(1);
		emitter.setFaceRendererIndex(0);
		emitter.setParticleDefaultVelocity(new Vector3f(30.0f, -3.5f, 30.0f));
		ParticleEmitterConfiguration configuration = emitter.getConfiguration();
		configuration.put(ParticleColor.START_COLOR_R, 0);
		configuration.put(ParticleColor.START_COLOR_G, 128);
		configuration.put(ParticleColor.START_COLOR_B, 255);
		configuration.put(ParticleColor.START_COLOR_A, 16);
		configuration.put(ParticleColor.END_COLOR_R, 0);
		configuration.put(ParticleColor.END_COLOR_G, 100);
		configuration.put(ParticleColor.END_COLOR_B, 128);
		configuration.put(ParticleColor.END_COLOR_A, 8);
		emitter.setConfiguration(configuration);
	}

}
