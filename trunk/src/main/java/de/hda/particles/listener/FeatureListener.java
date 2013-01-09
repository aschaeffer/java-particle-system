package de.hda.particles.listener;

import de.hda.particles.features.ParticleFeature;

/**
 * Listener interface for feature changes. Informs
 * listeners about feature creation and removal.
 * 
 * @author aschaeffer
 *
 */
public interface FeatureListener {

	/**
	 * Called on feature creation.
	 * @param feature
	 */
	void onFeatureCreation(ParticleFeature feature);
	
	/**
	 * Called on feature death.
	 * @param feature
	 */
	void onFeatureDeath(ParticleFeature feature);

}
