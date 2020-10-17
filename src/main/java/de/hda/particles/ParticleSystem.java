package de.hda.particles;

import de.hda.particles.configuration.impl.ParticleEmitterConfiguration;
import de.hda.particles.configuration.impl.ParticleModifierConfiguration;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.*;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.features.ParticleFeature;
import de.hda.particles.listener.*;
import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.timing.FpsInformation;

/**
 * Interface for particle systems.
 * 
 * @author aschaeffer
 *
 */
public interface ParticleSystem extends Updatable, Blockable, FpsInformation {

	/**
	 * Returns the current alive particles.
	 * @return List of alive particles.
	 */
	List<Particle> getParticles();
	
	/**
	 * Returns the current fixed points.
	 * @return List of fixed points.
	 */
	List<FixedPoint> getFixedPoints();
	
	/**
	 * Returns the current faces.
	 * @return List of current faces.
	 */
	List<Face> getFaces();
	
	/**
	 * Returns the activated particle features of the particle system.
	 * @return List of activated particle features.
	 */
	List<ParticleFeature> getParticleFeatures();
	
	/**
	 * Returns the emitters of the particle system.
	 * @return List of particle emitters.
	 */
	List<ParticleEmitter> getParticleEmitters();
	
	/**
	 * Returns the modifiers of the particle system.
	 * @return List of particle modifiers.
	 */
	List<ParticleModifier> getParticleModifiers();
	
	/**
	 * Returns the pool of death particles.
	 * @return The pool of death particles.
	 */
	ParticlePool getParticlePool();

	/**
	 * Returns the pool of death faces.
	 * @return The pool of death faces.
	 */
	FacePool getFacePool();

	ParticleEmitter addParticleEmitter(ParticleEmitter emitter);
	ParticleEmitter addParticleEmitter(ParticleEmitter emitter, Vector3f position, Vector3f velocity, Integer particleRendererIndex, Integer rate, Integer lifetime, ParticleEmitterConfiguration configuration);

	/**
	 * Adds a particle emitter to the particle system.
	 * 
	 * @param clazz Emitter type.
	 * @param position Initial position of the emitter.
	 * @param velocity Initial velocity of the emitter.
	 * @param particleRendererIndex Initial particle renderer index of the emitter.
	 * @param rate Inital rate of particle emittations.
	 * @param lifetime Initial particle lifetime.
	 * @param configuration Emitter implementation specific configuration.
	 */
	ParticleEmitter addParticleEmitter(Class<? extends ParticleEmitter> clazz, Vector3f position, Vector3f velocity, Integer particleRendererIndex, Integer rate, Integer lifetime, ParticleEmitterConfiguration configuration);

	/**
	 * Adds a particle modifier to the particle system.
	 * 
	 * @param clazz Type of the modifier.
	 * @param configuration Modifier implementation specific configuration.
	 */
	ParticleModifier addParticleModifier(ParticleModifier modifier, ParticleModifierConfiguration configuration);
	ParticleModifier addParticleModifier(ParticleModifier modifier);
	ParticleModifier addParticleModifier(Class<? extends ParticleModifier> clazz, ParticleModifierConfiguration configuration);
	ParticleModifier addParticleModifier(Class<? extends ParticleModifier> clazz);
	void addFrameListener(FrameListener frameListener);
	void addParticleListener(ParticleLifetimeListener particleListener);
	void addFeatureListener(FeatureListener featureListener);
	void addFaceListener(FaceLifetimeListener faceListener);
	void addEmitterListener(EmitterLifetimeListener emitterListener);
	void addModifierListener(ModifierLifetimeListener modifierListener);
	ParticleFeature addParticleFeature(ParticleFeature particleFeature);
	ParticleFeature addParticleFeature(ParticleFeature particleFeature, Boolean allowDuplicates);
	ParticleFeature addParticleFeature(Class<? extends ParticleFeature> clazz);
	ParticleFeature addParticleFeature(Class<? extends ParticleFeature> clazz, Boolean allowDuplicates);
	void addParticle(Particle particle);
	void addFixedPoint(FixedPoint fixedPoint);
	void addFace(Face face);
	void removeParticle(Particle particle);
	void removeFixedPoint(FixedPoint fixedPoint);
	void removeFace(Face face);
	void removeAllParticles();
	void removeAllFixedPoints();
	void removeAllFaces();
	void removeParticleEmitter(ParticleEmitter particleEmitter);
	void removeParticleEmitter(Class<? extends ParticleEmitter> clazz);
	void removeParticleModifier(ParticleModifier particleModifier);
	void removeParticleModifier(Class<? extends ParticleModifier> clazz);
	void removeParticleFeature(ParticleFeature particleFeature);
	void removeParticleFeature(Class<? extends ParticleFeature> clazz);
	void removeFrameListener(FrameListener frameListener);
	void removeParticleListener(ParticleLifetimeListener particleListener);
	void removeFeatureListener(FeatureListener featureListener);
	void removeFaceListener(FaceLifetimeListener faceListener);
	void removeEmitterListener(EmitterLifetimeListener emitterListener);
	void removeModifierListener(ModifierLifetimeListener modifierListener);
	Boolean hasEmitter(Class<? extends ParticleEmitter> clazz);
	Boolean hasModifier(Class<? extends ParticleModifier> clazz);
	Boolean hasFeature(Class<? extends ParticleFeature> clazz);

	/**
	 * Toggles pause state.
	 */
	void pause();
	
	/**
	 * Mark particle system to process an single iteration step.
	 */
	void next();
	
	/**
	 * Returns the past iterations.
	 * 
	 * @return returns the past iterations
	 */
	Integer getPastIterations();
	
	/**
	 * Toggles emitters on / off.
	 */
	void toggleEmitters();
	
	/**
	 * Toggles modifiers on / off.
	 */
	void toggleModifiers();
	
	/**
	 * Returns the pause state.
	 * @return True, if particle system is in pause state.
	 */
	Boolean isPaused();
	
	/**
	 * Return the emitters state.
	 * @return True, if particle emitters are stopped.
	 */
	Boolean areEmittersStopped();
	
	/**
	 * Returns the modifiers state.
	 * @return True, if particle modifiers are stopped.
	 */
	Boolean areModifiersStopped();

}
