package de.hda.particles.listener;

import de.hda.particles.modifier.ParticleModifier;

/**
 * Listener interface for modifier lifetime changes. Informs
 * listener about modifier creation and removal.
 * 
 * @author aschaeffer
 *
 */
public interface ModifierLifetimeListener {

	/**
	 * Called on modifier creation.
	 * @param modifier The newly created modifier.
	 */
	void onModifierCreation(ParticleModifier modifier);
	
	/**
	 * Called on modifier removal.
	 * @param modifier The modifier being removed.
	 */
	void onModifierDeath(ParticleModifier modifier);

}
