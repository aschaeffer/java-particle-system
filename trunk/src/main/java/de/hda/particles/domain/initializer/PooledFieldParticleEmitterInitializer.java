package de.hda.particles.domain.initializer;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.emitter.PooledFieldParticleEmitter;
import de.hda.particles.scene.Scene;

public class PooledFieldParticleEmitterInitializer {

	private PooledFieldParticleEmitterInitializer() {}

	public static void init(Scene scene, Object subject) {
		PooledFieldParticleEmitter emitter = (PooledFieldParticleEmitter) subject;
		emitter.setRate(5);
		emitter.setParticleLifetime(1000);
		emitter.setParticleDefaultVelocity(new Vector3f(20.0f, 20.0f, 20.0f));
	}

}
