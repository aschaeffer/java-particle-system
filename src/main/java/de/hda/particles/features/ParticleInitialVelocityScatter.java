package de.hda.particles.features;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;

/**
 * This particle feature scatters the initial velocity.
 * 
 * @author aschaeffer
 *
 */
public class ParticleInitialVelocityScatter implements ParticleFeature {

	// public static final String SCATTER = "scatter";
	public static final String SCATTER_X = "scatter_x";
	public static final String SCATTER_Y = "scatter_y";
	public static final String SCATTER_Z = "scatter_z";

	private Random random = new Random();

	public void init(ParticleEmitter emitter, Particle particle) {
		if (!emitter.getConfiguration().containsKey(SCATTER_X) || !emitter.getConfiguration().containsKey(SCATTER_Y) || !emitter.getConfiguration().containsKey(SCATTER_Z)) return;
		Float scatterX = new Float((Double) emitter.getConfiguration().get(SCATTER_X));
		Float scatterY = new Float((Double) emitter.getConfiguration().get(SCATTER_Y));
		Float scatterZ = new Float((Double) emitter.getConfiguration().get(SCATTER_Z));
		Vector3f velocity = particle.getVelocity();
		velocity.setX(velocity.getX() + scatterX * random.nextFloat() - scatterX / 2.0f);
		velocity.setY(velocity.getY() + scatterY * random.nextFloat() - scatterY / 2.0f);
		velocity.setZ(velocity.getZ() + scatterZ * random.nextFloat() - scatterZ / 2.0f);
		particle.setVelocity(velocity);
	}

}
