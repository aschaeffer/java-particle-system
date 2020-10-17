package de.hda.particles;

import org.apache.commons.collections.ArrayStack;

import de.hda.particles.domain.impl.particle.DefaultHashMapParticle;
import de.hda.particles.domain.Particle;
import de.hda.particles.listener.ParticleLifetimeListener;

/**
 * The creation of thousands of particle objects per seconds slows
 * down the whole system and increases memory consumption dramatically.
 * 
 * Therefore we have to reduce the creation of new particle objects by
 * creating a pool of particles. The pool is implemented as a ArrayStack.
 * 
 * @author aschaeffer
 *
 */
public class ParticlePool extends ArrayStack implements ParticleLifetimeListener {

	/**
	 * The serial.
	 */
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

	/**
	 * Not used.
	 */
	@Override
	public void onParticleCreation(Particle particle) {
	}

}
