package de.hda.particles;

import de.hda.particles.domain.impl.configuration.ParticleEmitterConfiguration;
import de.hda.particles.domain.impl.configuration.ParticleModifierConfiguration;
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
public interface ParticleSystem extends Updateable, Blockable, FpsInformation {

	/**
	 * Returns the current alive particles.
	 * @return List of alive particles.
	 */
	public List<Particle> getParticles();
	
	/**
	 * Returns the current fixed points.
	 * @return List of fixed points.
	 */
	public List<FixedPoint> getFixedPoints();
	
	/**
	 * Returns the current faces.
	 * @return List of current faces.
	 */
	public List<Face> getFaces();
	
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
	 * Returns the pool of death faces.
	 * @return The pool of death faces.
	 */
	public FacePool getFacePool();

	public ParticleEmitter addParticleEmitter(ParticleEmitter emitter);
	public ParticleEmitter addParticleEmitter(ParticleEmitter emitter, Vector3f position, Vector3f velocity, Integer particleRendererIndex, Integer rate, Integer lifetime, ParticleEmitterConfiguration configuration);

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
	public ParticleEmitter addParticleEmitter(Class<? extends ParticleEmitter> clazz, Vector3f position, Vector3f velocity, Integer particleRendererIndex, Integer rate, Integer lifetime, ParticleEmitterConfiguration configuration);

	/**
	 * Adds a particle modifier to the particle system.
	 * 
	 * @param clazz Type of the modifier.
	 * @param configuration Modifier implementation specific configuration.
	 */
	public ParticleModifier addParticleModifier(ParticleModifier modifier, ParticleModifierConfiguration configuration);
	public ParticleModifier addParticleModifier(ParticleModifier modifier);
	public ParticleModifier addParticleModifier(Class<? extends ParticleModifier> clazz, ParticleModifierConfiguration configuration);
	public ParticleModifier addParticleModifier(Class<? extends ParticleModifier> clazz);
	public void addFrameListener(FrameListener frameListener);
	public void addParticleListener(ParticleLifetimeListener particleListener);
	public void addFeatureListener(FeatureListener featureListener);
	public void addFaceListener(FaceLifetimeListener faceListener);
	public void addEmitterListener(EmitterLifetimeListener emitterListener);
	public void addModifierListener(ModifierLifetimeListener modifierListener);
	public ParticleFeature addParticleFeature(ParticleFeature particleFeature);
	public ParticleFeature addParticleFeature(ParticleFeature particleFeature, Boolean allowDuplicates);
	public ParticleFeature addParticleFeature(Class<? extends ParticleFeature> clazz);
	public ParticleFeature addParticleFeature(Class<? extends ParticleFeature> clazz, Boolean allowDuplicates);
	public void addParticle(Particle particle);
	public void addFixedPoint(FixedPoint fixedPoint);
	public void addFace(Face face);
	public void removeParticle(Particle particle);
	public void removeFixedPoint(FixedPoint fixedPoint);
	public void removeFace(Face face);
	public void removeAllParticles();
	public void removeAllFixedPoints();
	public void removeAllFaces();
	public void removeParticleEmitter(ParticleEmitter particleEmitter);
	public void removeParticleEmitter(Class<? extends ParticleEmitter> clazz);
	public void removeParticleModifier(ParticleModifier particleModifier);
	public void removeParticleModifier(Class<? extends ParticleModifier> clazz);
	public void removeParticleFeature(ParticleFeature particleFeature);
	public void removeParticleFeature(Class<? extends ParticleFeature> clazz);
	public void removeFrameListener(FrameListener frameListener);
	public void removeParticleListener(ParticleLifetimeListener particleListener);
	public void removeFeatureListener(FeatureListener featureListener);
	public void removeFaceListener(FaceLifetimeListener faceListener);
	public void removeEmitterListener(EmitterLifetimeListener emitterListener);
	public void removeModifierListener(ModifierLifetimeListener modifierListener);
	public Boolean hasEmitter(Class<? extends ParticleEmitter> clazz);
	public Boolean hasModifier(Class<? extends ParticleModifier> clazz);
	public Boolean hasFeature(Class<? extends ParticleFeature> clazz);

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

}
