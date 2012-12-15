package de.hda.particles;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.domain.ParticleEmitterConfiguration;
import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.features.ParticleFeature;
import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.timing.FpsInformation;

/**
 * Interface for particle systems.
 * 
 * @author aschaeffer
 *
 */
public interface ParticleSystem extends Updateable, FpsInformation {

	/**
	 * Returns the current alive particles.
	 * @return List of alive particles.
	 */
	public List<Particle> getParticles();
	
	/**
	 * Returns the activated particle features of the particle system.
	 * @return List of activated particle features.
	 */
	public List<ParticleFeature> getParticleFeatures();
	
	/**
	 * Returns the emitters of the particle system.
	 * @return List of particle emitters.
	 */
	public List<ParticleEmitter> getParticleEmitters();
	
	/**
	 * Returns the modifiers of the particle system.
	 * @return List of particle modifiers.
	 */
	public List<ParticleModifier> getParticleModifiers();
	
	/**
	 * Returns the pool of death particles.
	 * @return The pool of death particles.
	 */
	public ParticlePool getParticlePool();

	/**
	 * Adds a particle emitter to the particle system.
	 * 
	 * @param clazz Emitter type.
	 * @param position Initial position of the emitter.
	 * @param velocity Initial velocity of the emitter.
	 * @param renderTypeIndex Initial renderType index of the emitter.
	 * @param rate Inital rate of particle emittations.
	 * @param lifetime Initial particle lifetime.
	 * @param configuration Emitter implementation specific configuration.
	 */
	public void addParticleEmitter(Class<? extends ParticleEmitter> clazz, Vector3f position, Vector3f velocity, Integer renderTypeIndex, Integer rate, Integer lifetime, ParticleEmitterConfiguration configuration);

	/**
	 * Adds a particle modifier to the particle system.
	 * 
	 * @param clazz Type of the modifier.
	 * @param configuration Modifier implementation specific configuration.
	 */
	public void addParticleModifier(Class<? extends ParticleModifier> clazz, ParticleModifierConfiguration configuration);
	public void addParticleListener(ParticleLifetimeListener particleListener);
	public void addParticleFeature(ParticleFeature particleFeature);
	public void addParticleFeature(Class<? extends ParticleFeature> clazz);
	public void addParticle(Particle particle);
	public void removeParticle(Particle particle);
	public void removeAllParticles();
	public void removeParticleEmitter(ParticleEmitter particleEmitter);
	public void removeParticleModifier(ParticleModifier particleModifier);
	public void removeParticleFeature(ParticleFeature particleFeature);
	public void removeParticleListener(ParticleLifetimeListener particleListener);

	/**
	 * Toggles pause state.
	 */
	public void pause();
	
	/**
	 * Mark particle system to process an single iteration step.
	 */
	public void next();
	
	/**
	 * Returns the past iterations.
	 * 
	 * @return returns the past iterations
	 */
	public Integer getPastIterations();
	
	/**
	 * Toggles emitters on / off.
	 */
	public void toggleEmitters();
	
	/**
	 * Toggles modifiers on / off.
	 */
	public void toggleModifiers();
	
	/**
	 * Returns the pause state.
	 * @return True, if particle system is in pause state.
	 */
	public Boolean isPaused();
	
	/**
	 * Return the emitters state.
	 * @return True, if particle emitters are stopped.
	 */
	public Boolean areEmittersStopped();
	
	/**
	 * Returns the modifiers state.
	 * @return True, if particle modifiers are stopped.
	 */
	public Boolean areModifiersStopped();
	
	/**
	 * Blocks the whole particle system for modification (thread synchronisation).
	 */
	public void beginModification();
	
	/**
	 * Unblocks the particle system for modifications.
	 */
	public void endModification();

}
