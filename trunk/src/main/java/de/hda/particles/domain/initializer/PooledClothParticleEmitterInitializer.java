package de.hda.particles.domain.initializer;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.ParticleEmitterConfiguration;
import de.hda.particles.emitter.PooledClothParticleEmitter;
import de.hda.particles.features.MassSpring;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.scene.Scene;

public class PooledClothParticleEmitterInitializer {

	private PooledClothParticleEmitterInitializer() {}

	public static void init(Scene scene, Object subject) {
		PooledClothParticleEmitter emitter = (PooledClothParticleEmitter) subject;
		emitter.setRate(50);
		emitter.setParticleLifetime(1000);
		emitter.setParticleDefaultVelocity(new Vector3f(0.0f, 0.0f, 3.0f));
		emitter.setParticleRendererIndex(0);
		emitter.setFaceRendererIndex(1);
		ParticleEmitterConfiguration configuration = emitter.getConfiguration();
		configuration.put(MassSpring.SPRING_LENGTH, 40.0);
		configuration.put(MassSpring.SPRING_FRICTION, 0.05);
		configuration.put(MassSpring.SPRING_CONSTANT, 0.02);
		configuration.put(ParticleColor.START_COLOR_R, 0);
		configuration.put(ParticleColor.START_COLOR_G, 255);
		configuration.put(ParticleColor.START_COLOR_B, 0);
		configuration.put(ParticleColor.START_COLOR_A, 64);
		configuration.put(ParticleColor.END_COLOR_R, 0);
		configuration.put(ParticleColor.END_COLOR_G, 0);
		configuration.put(ParticleColor.END_COLOR_B, 255);
		configuration.put(ParticleColor.END_COLOR_A, 64);
		configuration.put(PooledClothParticleEmitter.COLORED_CLOTH, true);
		emitter.setConfiguration(configuration);
	}

}
