package de.hda.particles.modifier;

import de.hda.particles.AutoDependency;
import de.hda.particles.ParticleSystem;
import de.hda.particles.configuration.impl.ParticleModifierConfiguration;

/**
 * A particle modifier changes the state of a specific particle or
 * the whole particle system.
 * 
 * @author aschaeffer
 *
 */
public interface ParticleModifier extends ParticleUpdater, AutoDependency {

	/**
	 * Called once every iteration.
	 */
	void prepare();
	
	/**
	 * Updates a configuration key.
	 * 
	 * @param key The key of the configuration item to change.
	 * @param value The new configuration item value.
	 */
	void updateConfiguration(String key, Object value);

	void setParticleSystem(ParticleSystem particleSystem);
	ParticleModifierConfiguration getConfiguration();
	void setConfiguration(ParticleModifierConfiguration configuration);

}
