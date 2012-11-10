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

	public static final String SCATTER = "scatter";

	private Random random = new Random();

	public void init(ParticleEmitter emitter, Particle particle) {
		if (!emitter.getConfiguration().containsKey(SCATTER)) return;
		Vector3f velocity = particle.getVelocity();
		Vector3f scatter = (Vector3f) emitter.getConfiguration().get(SCATTER);
		// System.out.println("velocity before:: x:" + velocity.getX() + " y:" + velocity.getY() + " z:" + velocity.getZ());
		velocity.setX(velocity.getX() + scatter.getX() * random.nextFloat() - scatter.getX() / 2.0f);
		velocity.setY(velocity.getY() + scatter.getY() * random.nextFloat() - scatter.getY() / 2.0f);
		velocity.setZ(velocity.getZ() + scatter.getZ() * random.nextFloat() - scatter.getZ() / 2.0f);
		// System.out.println("velocity after:: x:" + velocity.getX() + " y:" + velocity.getY() + " z:" + velocity.getZ());
		particle.setVelocity(velocity);
	}

}
