package de.hda.particles.emitter;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.DefaultHashMapParticle;
import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleFeature;

/**
 * This particle emitter is  
 * @author aschaeffer
 *
 */
public class SphereParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	private final Random random = new Random();
	
	public SphereParticleEmitter() {}

	/**
	 * Creates new particles and adds them to the particle system
	 */
	@Override
	public void update() {
		pastIterations++;
		// create new particles (emit)
		for (Integer i = 0; i < rate; i++) {
			Vector3f velocity = new Vector3f(random.nextFloat() * 2.0f - 1.0f, random.nextFloat() * 2.0f - 1.0f, random.nextFloat() * 2.0f - 1.0f);
			Particle particle = new DefaultHashMapParticle(position, velocity, particleRenderTypeIndex, particleLifetime);
			for (ParticleFeature particleFeature: particleSystem.getParticleFeatures()) {
				particleFeature.init(this, particle);
			}
			particleSystem.addParticle(particle);
		}
	}

	@Override
	public void setup() {
	}

	@Override
	public void destroy() {
	}

}
