package de.hda.particles.domain.initializer;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.configuration.impl.ParticleEmitterConfiguration;
import de.hda.particles.emitter.impl.PooledSoftBodyEmitter;
import de.hda.particles.features.impl.ParticleColor;
import de.hda.particles.scene.Scene;

public class PooledSoftBodyEmitterInitializer {

	private PooledSoftBodyEmitterInitializer() {}

	public static void init(Scene scene, Object subject) {
		PooledSoftBodyEmitter emitter = (PooledSoftBodyEmitter) subject;
		emitter.setParticleLifetime(200000); // really long!
		emitter.setRate(1);
		emitter.setParticleRendererIndex(1);
		emitter.setFaceRendererIndex(1);
		emitter.setParticleDefaultVelocity(new Vector3f(0.0f, 0.0f, 0.0f));
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
