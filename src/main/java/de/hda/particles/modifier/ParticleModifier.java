package de.hda.particles.modifier;

import de.hda.particles.AutoDependency;
import de.hda.particles.ParticleSystem;
import de.hda.particles.domain.impl.configuration.ParticleModifierConfiguration;

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
	public void prepare();
	
	/**
	 * Updates a configuration key.
	 * 
	 * @param key The key of the configuration item to change.
	 * @param value The new configuration item value.
	 */
	public void updateConfiguration(String key, Object value);

	public void setParticleSystem(ParticleSystem particleSystem);
	public ParticleModifierConfiguration getConfiguration();
	public void setConfiguration(ParticleModifierConfiguration configuration);

}
