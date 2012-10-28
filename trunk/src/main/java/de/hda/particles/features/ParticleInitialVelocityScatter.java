package de.hda.particles.features;

import java.util.Random;

import de.hda.particles.domain.Particle;
import de.hda.particles.domain.Vector3;

/**
 * This particle feature scatters the initial velocity.
 * 
 * @author aschaeffer
 *
 */
public class ParticleInitialVelocityScatter implements ParticleFeature {

	Vector3 scatter = new Vector3();
	Random random = new Random();

	public ParticleInitialVelocityScatter(Vector3 scatter) {
		this.scatter = scatter;
	}

	public void init(Particle particle) {
		Vector3 velocity = particle.getVelocity();
		velocity.setX(velocity.getX() + scatter.getX() * random.nextFloat() - scatter.getX() / 2.0f);
		velocity.setY(velocity.getY() + scatter.getY() * random.nextFloat() - scatter.getY() / 2.0f);
		velocity.setZ(velocity.getZ() + scatter.getZ() * random.nextFloat() - scatter.getZ() / 2.0f);
		particle.setVelocity(velocity);
	}

}
