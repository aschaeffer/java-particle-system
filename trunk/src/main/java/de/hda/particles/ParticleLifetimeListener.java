package de.hda.particles;

import de.hda.particles.domain.Particle;

/**
 * Listener interface for particle lifetime changes. Informs
 * listener about particle creation and particle death.
 * 
 * @author aschaeffer
 *
 */
public interface ParticleLifetimeListener {

	/**
	 * Called on particle creation.
	 * @param particle
	 */
	void onParticleCreation(Particle particle);
	
	/**
	 * Called on particle death.
	 * @param particle
	 */
	void onParticleDeath(Particle particle);

}
