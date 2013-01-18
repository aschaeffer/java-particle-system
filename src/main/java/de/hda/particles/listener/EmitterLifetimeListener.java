package de.hda.particles.listener;

import de.hda.particles.emitter.ParticleEmitter;

/**
 * Listener interface for emitter lifetime changes. Informs
 * listener about emitter creation and removal.
 * 
 * @author aschaeffer
 *
 */
public interface EmitterLifetimeListener {

	/**
	 * Called on emitter creation.
	 * @param emitter The newly created emitter.
	 */
	void onEmitterCreation(ParticleEmitter emitter);
	
	/**
	 * Called on emitter removal.
	 * @param emitter The emitter being removed.
	 */
	void onEmitterDeath(ParticleEmitter emitter);

}
