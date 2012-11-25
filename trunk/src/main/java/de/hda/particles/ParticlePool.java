package de.hda.particles;

import org.apache.commons.collections.ArrayStack;

import de.hda.particles.domain.DefaultHashMapParticle;
import de.hda.particles.domain.Particle;

/**
 * The creation of thousands of particle objects per seconds slows
 * down the whole system and increases memory consumption dramatically.
 * 
 * Therefore we have to reduce the creation of new particle objects by
 * creating a pool of particles.
 * 
 * @author aschaeffer
 *
 */
public class ParticlePool extends ArrayStack implements ParticleLifetimeListener {

	private static final long serialVersionUID = 3602697554779890223L;

	/**
	 * Returns the next particle from the pool (or creates a new
	 * particle object).
	 * 
	 * @return
	 */
	public Particle next() {
		if (empty()) {
			return new DefaultHashMapParticle();
		} else {
			return (Particle) pop();
		}
	}

	/**
	 * Inserts a death particle to the pool.
	 */
	@Override
	public void onParticleDeath(Particle particle) {
		push(particle);
	}

	@Override
	public void onParticleCreation(Particle particle) {
	}

}
