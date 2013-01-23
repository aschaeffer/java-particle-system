package de.hda.particles;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.vector.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.*;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.features.ParticleFeature;
import de.hda.particles.listener.*;
import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.timing.FpsLimiter;

/**
 * The core of a particle system. It manages all particles, features,
 * emitters and modifiers.
 * 
 * @author aschaeffer
 *
 */
public abstract class AbstractParticleSystem extends FpsLimiter implements ParticleSystem {

	/**
	 * List of particles (array list = fast).
	 */
	protected List<Particle> particles = new ArrayList<Particle>();
	
	/**
	 * List of fixed points (array list = fast).
	 */
	protected List<FixedPoint> fixedPoints = new ArrayList<FixedPoint>();
	
	/**
	 * List of faces (array list = fast).
	 */
	protected List<Face> faces = new ArrayList<Face>();
	
	/**
	 * List of particle features.
	 */
	protected List<ParticleFeature> particleFeatures = new ArrayList<ParticleFeature>();

	/**
	 * List of particle emitters.
	 */
	protected List<ParticleEmitter> emitters = new ArrayList<ParticleEmitter>();
	
	/**
	 * List of modifiers.
	 */
	protected List<ParticleModifier> modifiers = new ArrayList<ParticleModifier>();
	
	/**
	 * Pool of death particles which are ready for recycling.
	 */
	protected ParticlePool particlePool = new ParticlePool();

	/**
	 * Pool of death particles which are ready for recycling.
	 */
	protected FacePool facePool = new FacePool();

	/**
	 * The list of frame listeners.
	 */
	protected List<FrameListener> frameListeners = new ArrayList<FrameListener>();

	/**
	 * The list of particle lifetime listeners.
	 */
	protected List<ParticleLifetimeListener> particleListeners = new ArrayList<ParticleLifetimeListener>();

	/**
	 * The list of feature listeners.
	 */
	protected List<FeatureListener> featureListeners = new ArrayList<FeatureListener>();

	/**
	 * The list of face lifetime listeners.
	 */
	protected List<FaceLifetimeListener> faceListeners = new ArrayList<FaceLifetimeListener>();

	/**
	 * The list of emitter lifetime listeners.
	 */
	protected List<EmitterLifetimeListener> emitterListeners = new ArrayList<EmitterLifetimeListener>();

	/**
	 * The list of modifier lifetime listeners.
	 */
	protected List<ModifierLifetimeListener> modifierListeners = new ArrayList<ModifierLifetimeListener>();
	
	/**
	 * Paused state of the particle system.
	 */
	protected Boolean paused = false;
	protected Boolean pausedBeforeModification = false;
	
	/**
	 * Next step iteration state.
	 */
	protected Boolean next = false;
	
	/**
	 * True, if particle system is idle.
	 */
	protected Boolean idle = true;
	
	/**
	 * True, if emitters are enabled.
	 */
	protected Boolean emittersEnabled = true;
	
	/**
	 * True, if modifiers are enabled.
	 */
	protected Boolean modifiersEnabled = true;
	
	/**
	 * True, if particles should be removed at next iteration.
	 */
	protected Boolean clearParticlesAtNextIteration = false;
	
	/**
	 * True, if fixed points should be removed at next iteration.
	 */
	protected Boolean clearFixedPointsAtNextIteration = false;
	
	/**
	 * True, if faces should be removed at next iteration.
	 */
	protected Boolean clearFacesAtNextIteration = false;

	/**
	 * Number of past iterations.
	 */
	protected Integer pastIterations = 0;
	
	private final Logger logger = LoggerFactory.getLogger(AbstractParticleSystem.class);

	@Override
	public ParticleEmitter addParticleEmitter(ParticleEmitter emitter) {
		emitter.setParticleSystem(this);
		emitters.add(emitter);
		emitter.addDependencies();
		logger.debug("added emitter: " + emitter.getClass().getName());
		ListIterator<EmitterLifetimeListener> iterator = emitterListeners.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().onEmitterCreation(emitter);
		}
		return emitter;
	}

	@Override
	public ParticleEmitter addParticleEmitter(ParticleEmitter emitter, Vector3f position, Vector3f velocity, Integer particleRendererIndex, Integer rate, Integer lifetime, ParticleEmitterConfiguration configuration) {
		emitter.setParticleSystem(this);
		emitter.setPosition(position);
		emitter.setParticleDefaultVelocity(velocity);
		emitter.setParticleRendererIndex(particleRendererIndex);
		emitter.setRate(rate);
		emitter.setParticleLifetime(lifetime);
		emitter.setConfiguration(configuration);
		emitters.add(emitter);
		emitter.addDependencies();
		logger.debug("added emitter: " + emitter.getClass().getName());
		ListIterator<EmitterLifetimeListener> iterator = emitterListeners.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().onEmitterCreation(emitter);
		}
		return emitter;
	}

	/**
	 * Adds a particle emitter to the particle system.
	 * 
	 * @param clazz Emitter type.
	 * @param position Initial position of the emitter.
	 * @param velocity Initial velocity of the emitter.
	 * @param particleRendererIndex Initial particleRenderer index of the emitter.
	 * @param rate Inital rate of particle emittations.
	 * @param lifetime Initial particle lifetime.
	 * @param configuration Emitter implementation specific configuration.
	 */
	@Override
	public ParticleEmitter addParticleEmitter(Class<? extends ParticleEmitter> clazz, Vector3f position, Vector3f velocity, Integer particleRendererIndex, Integer rate, Integer lifetime, ParticleEmitterConfiguration configuration) {
		ParticleEmitter emitter;
		try {
			emitter = clazz.newInstance();
			emitter.setParticleSystem(this);
			emitter.setPosition(position);
			emitter.setParticleDefaultVelocity(velocity);
			emitter.setParticleRendererIndex(particleRendererIndex);
			emitter.setRate(rate);
			emitter.setParticleLifetime(lifetime);
			emitter.setConfiguration(configuration);
			emitters.add(emitter);
			emitter.addDependencies();
			logger.debug("added emitter: " + emitter.getClass().getName());
			ListIterator<EmitterLifetimeListener> iterator = emitterListeners.listIterator(0);
			while (iterator.hasNext()) {
				iterator.next().onEmitterCreation(emitter);
			}
			return emitter;
		} catch (Exception e) {
			logger.error("could not add particle emitter", e);
			return null;
		}
	}

	/**
	 * Adds a particle modifier to the particle system.
	 * 
	 * @param modifier The particle modifier.
	 * @param configuration Modifier implementation specific configuration.
	 */
	@Override
	public ParticleModifier addParticleModifier(ParticleModifier modifier, ParticleModifierConfiguration configuration) {
		modifier.setParticleSystem(this);
		modifier.setConfiguration(configuration);
		modifiers.add(modifier);
		modifier.addDependencies();
		logger.debug("added modifier: " + modifier.getClass().getName());
		ListIterator<ModifierLifetimeListener> iterator = modifierListeners.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().onModifierCreation(modifier);
		}
		return modifier;
	}

	/**
	 * Adds a particle modifier to the particle system.
	 * 
	 * @param modifier The particle modifier.
	 */
	@Override
	public ParticleModifier addParticleModifier(ParticleModifier modifier) {
		return addParticleModifier(modifier, new ParticleModifierConfiguration());
	}

	/**
	 * Adds a particle modifier to the particle system.
	 * 
	 * @param clazz Type of the modifier.
	 * @param configuration Modifier implementation specific configuration.
	 */
	@Override
	public ParticleModifier addParticleModifier(Class<? extends ParticleModifier> clazz, ParticleModifierConfiguration configuration) {
		ParticleModifier modifier;
		try {
			modifier = clazz.newInstance();
			modifier.setParticleSystem(this);
			modifier.setConfiguration(configuration);
			modifiers.add(modifier);
			modifier.addDependencies();
			logger.debug("added modifier: " + modifier.getClass().getName());
			ListIterator<ModifierLifetimeListener> iterator = modifierListeners.listIterator(0);
			while (iterator.hasNext()) {
				iterator.next().onModifierCreation(modifier);
			}
			return modifier;
		} catch (Exception e) {
			logger.error("could not add particle modifier", e);
			return null;
		}
	}

	/**
	 * Adds a particle modifier to the particle system.
	 * 
	 * @param clazz Type of the modifier.
	 * @param configuration Modifier implementation specific configuration.
	 */
	@Override
	public ParticleModifier addParticleModifier(Class<? extends ParticleModifier> clazz) {
		return addParticleModifier(clazz, new ParticleModifierConfiguration());
	}

	@Override
	public void addFrameListener(FrameListener frameListener) {
		frameListeners.add(frameListener);
	}

	@Override
	public void addParticleListener(ParticleLifetimeListener particleListener) {
		particleListeners.add(particleListener);
	}

	@Override
	public void addFeatureListener(FeatureListener featureListener) {
		featureListeners.add(featureListener);
		logger.debug("added feature listener: " + featureListener.getClass().getSimpleName());
	}

	@Override
	public void addFaceListener(FaceLifetimeListener faceListener) {
		faceListeners.add(faceListener);
	}
	
	@Override
	public void addEmitterListener(EmitterLifetimeListener emitterListener) {
		emitterListeners.add(emitterListener);
	}
	
	@Override
	public void addModifierListener(ModifierLifetimeListener modifierListener) {
		modifierListeners.add(modifierListener);
	}

	@Override
	public ParticleFeature addParticleFeature(ParticleFeature particleFeature, Boolean allowDuplicates) {
		if (!allowDuplicates) {
			ListIterator<ParticleFeature> iterator = particleFeatures.listIterator(0);
			while (iterator.hasNext()) {
				ParticleFeature feature = iterator.next();
				if (feature.getClass().getName().equals(particleFeature.getClass().getName())) {
					logger.debug("Feature already installed: " + particleFeature.getClass().getName() + " vs " + feature.getClass().getName());
					return feature;
				}
			}
		}
		logger.debug("added particle feature: " + particleFeature.getClass().getName());
		this.particleFeatures.add(particleFeature);
		ListIterator<FeatureListener> iterator = featureListeners.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().onFeatureCreation(particleFeature);
		}
		return particleFeature;
	}
	
	@Override
	public ParticleFeature addParticleFeature(ParticleFeature particleFeature) {
		return addParticleFeature(particleFeature, false);
	}

	@Override
	public ParticleFeature addParticleFeature(Class<? extends ParticleFeature> clazz, Boolean allowDuplicates) {
		if (!allowDuplicates) {
			ListIterator<ParticleFeature> iterator = particleFeatures.listIterator(0);
			while (iterator.hasNext()) {
				ParticleFeature feature = iterator.next();
				if (feature.getClass().getName().equals(clazz.getName())) {
					logger.debug("Feature already installed: " + clazz.getName() + " vs " + feature.getClass().getName());
					return feature;
				}
			}
		}
		try {
			ParticleFeature feature = clazz.newInstance();
			particleFeatures.add(feature);
			logger.debug("added particle feature: " + feature.getClass().getName());
			ListIterator<FeatureListener> iterator = featureListeners.listIterator(0);
			while (iterator.hasNext()) {
				iterator.next().onFeatureCreation(feature);
			}
			return feature;
		} catch (Exception e) {
			logger.error("could not add particle feature: " + e.getMessage(), e);
			return null;
		}
	}

	@Override
	public ParticleFeature addParticleFeature(Class<? extends ParticleFeature> clazz) {
		return addParticleFeature(clazz, false);
	}

	@Override
	public void addParticle(Particle particle) {
		particles.add(particle);
		ListIterator<ParticleLifetimeListener> iterator = particleListeners.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().onParticleCreation(particle);
		}
	}
	
	@Override
	public void addFixedPoint(FixedPoint fixedPoint) {
		fixedPoints.add(fixedPoint);
//		ListIterator<ParticleLifetimeListener> iterator = listeners.listIterator(0);
//		while (iterator.hasNext()) {
//			iterator.next().onParticleCreation(particle);
//		}
	}
	
	@Override
	public void addFace(Face face) {
		faces.add(face);
		ListIterator<FaceLifetimeListener> iterator = faceListeners.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().onFaceCreation(face);
		}
	}
	
	@Override
	public void removeParticle(Particle particle) {
		particles.remove(particle);
		ListIterator<ParticleLifetimeListener> iterator = particleListeners.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().onParticleDeath(particle);
		}
	}
	
	@Override
	public void removeFixedPoint(FixedPoint fixedPoint) {
		fixedPoints.remove(fixedPoint);
//		ListIterator<ParticleLifetimeListener> iterator = listeners.listIterator(0);
//		while (iterator.hasNext()) {
//			iterator.next().onParticleDeath(particle);
//		}
	}
	
	@Override
	public void removeFace(Face face) {
		faces.remove(face);
		ListIterator<FaceLifetimeListener> iterator = faceListeners.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().onFaceDeath(face);
		}
	}
	
	@Override
	public void removeAllParticles() {
		particles.clear();
		clearParticlesAtNextIteration = true;
	}

	@Override
	public void removeAllFixedPoints() {
		fixedPoints.clear();
		clearFixedPointsAtNextIteration = true;
	}

	@Override
	public void removeAllFaces() {
		faces.clear();
		clearFacesAtNextIteration = true;
	}

	@Override
	public void removeParticleEmitter(ParticleEmitter particleEmitter) {
		emitters.remove(particleEmitter);
		ListIterator<EmitterLifetimeListener> iterator = emitterListeners.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().onEmitterDeath(particleEmitter);
		}
	}

	@Override
	public void removeParticleEmitter(Class<? extends ParticleEmitter> clazz) {
		ListIterator<ParticleEmitter> emitterIterator = emitters.listIterator(0);
		while (emitterIterator.hasNext()) {
			ParticleEmitter emitter = emitterIterator.next();
			if (emitter.getClass().getName().equals(clazz.getName())) {
				logger.debug("removing emitter: " + clazz.getName());
				emitterIterator.remove();
				ListIterator<EmitterLifetimeListener> listenerIterator = emitterListeners.listIterator(0);
				while (listenerIterator.hasNext()) {
					listenerIterator.next().onEmitterDeath(emitter);
				}
			}
		}
	}

	@Override
	public void removeParticleModifier(ParticleModifier particleModifier) {
		modifiers.remove(particleModifier);
		ListIterator<ModifierLifetimeListener> iterator = modifierListeners.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().onModifierDeath(particleModifier);
		}
	}

	@Override
	public void removeParticleModifier(Class<? extends ParticleModifier> clazz) {
		ListIterator<ParticleModifier> modifierIterator = modifiers.listIterator(0);
		while (modifierIterator.hasNext()) {
			ParticleModifier modifier = modifierIterator.next();
			if (modifier.getClass().getName().equals(clazz.getName())) {
				logger.debug("removing modifier: " + clazz.getName());
				modifierIterator.remove();
				ListIterator<ModifierLifetimeListener> listenerIterator = modifierListeners.listIterator(0);
				while (listenerIterator.hasNext()) {
					listenerIterator.next().onModifierDeath(modifier);
				}
			}
		}
	}

	@Override
	public void removeParticleFeature(ParticleFeature particleFeature) {
		particleFeatures.remove(particleFeature);
	}

	@Override
	public void removeParticleFeature(Class<? extends ParticleFeature> clazz) {
		ListIterator<ParticleFeature> featuresIterator = particleFeatures.listIterator(0);
		while (featuresIterator.hasNext()) {
			ParticleFeature feature = featuresIterator.next();
			if (feature.getClass().getName().equals(clazz.getName())) {
				logger.debug("removing feature: " + clazz.getSimpleName());
				featuresIterator.remove();
				ListIterator<FeatureListener> listenerIterator = featureListeners.listIterator(0);
				while (listenerIterator.hasNext()) {
					listenerIterator.next().onFeatureDeath(feature);
				}
			}
		}
	}

	@Override
	public void removeFrameListener(FrameListener frameListener) {
		frameListeners.remove(frameListener);
	}

	@Override
	public void removeParticleListener(ParticleLifetimeListener particleListener) {
		particleListeners.remove(particleListener);
	}

	@Override
	public void removeFeatureListener(FeatureListener featureListener) {
		featureListeners.remove(featureListener);
	}

	@Override
	public void removeFaceListener(FaceLifetimeListener faceListener) {
		faceListeners.remove(faceListener);
	}

	@Override
	public void removeEmitterListener(EmitterLifetimeListener emitterListener) {
		emitterListeners.remove(emitterListener);
	}

	@Override
	public void removeModifierListener(ModifierLifetimeListener modifierListener) {
		modifierListeners.remove(modifierListener);
	}

	@Override
	public Boolean hasEmitter(Class<? extends ParticleEmitter> clazz) {
		ListIterator<ParticleEmitter> iterator = emitters.listIterator(0);
		while (iterator.hasNext()) {
			if (iterator.next().getClass().getName().equals(clazz.getName())) return true;
		}
		return false;
	}

	@Override
	public Boolean hasModifier(Class<? extends ParticleModifier> clazz) {
		ListIterator<ParticleModifier> iterator = modifiers.listIterator(0);
		while (iterator.hasNext()) {
			if (iterator.next().getClass().getName().equals(clazz.getName())) return true;
		}
		return false;
	}

	@Override
	public Boolean hasFeature(Class<? extends ParticleFeature> clazz) {
		ListIterator<ParticleFeature> iterator = particleFeatures.listIterator(0);
		while (iterator.hasNext()) {
			if (iterator.next().getClass().getName().equals(clazz.getName())) return true;
		}
		return false;
	}

	@Override
	public void pause() {
		paused = !paused;
	}
	
	@Override
	public void next() {
		next = true;
	}
	
	@Override
	public Integer getPastIterations() {
		return pastIterations;
	}

	@Override
	public void toggleEmitters() {
		emittersEnabled = !emittersEnabled;
	}
	
	@Override
	public void toggleModifiers() {
		modifiersEnabled = !modifiersEnabled;
	}
	
	@Override
	public Boolean isPaused() {
		return paused;
	}

	@Override
	public Boolean areEmittersStopped() {
		return !emittersEnabled;
	}

	@Override
	public Boolean areModifiersStopped() {
		return !modifiersEnabled;
	}
	
	@Override
	public void update() {
		calcFps();
		limitFps3();
		
		if (next) {
			next = false;
		} else {
			if (paused) {
				idle = true;
				return;
			}
			idle = false;
		}
		pastIterations++;

		if (emittersEnabled) {
			// call every particle emitter
			ListIterator<ParticleEmitter> eIterator = emitters.listIterator(0);
			while (eIterator.hasNext()) eIterator.next().update();
		}

		if (modifiersEnabled) {
			try {
				// modify existing particles
				ListIterator<ParticleModifier> mIterator = modifiers.listIterator(0);
				while (mIterator.hasNext()) {
					ParticleModifier modifier = mIterator.next();
					modifier.prepare();
					for (Integer pIndex = 0; pIndex < particles.size(); pIndex++) {
						particles.get(pIndex).setIndex(pIndex);
						modifier.update(particles.get(pIndex));
					}
				}
			} catch (NullPointerException e) {
				// dont care about synchronization! it's just one single frame which is missing
				// logger.error("Modification error! " + e.getMessage(), e);
			}
		}

		if (clearParticlesAtNextIteration) clearParticles(); // Thread Safety
		if (clearFixedPointsAtNextIteration) clearFixedPoints(); // Thread Safety
		if (clearFacesAtNextIteration) clearFaces(); // Thread Safety

		try {
			// decrease particle lifetimes and remove death particles
			ListIterator<Particle> particlesIterator = particles.listIterator();
			while (particlesIterator.hasNext()) {
				Particle particle = particlesIterator.next();
				particle.decLifetime();
				if (particle.getRemainingIterations() <= 0) {
					particlesIterator.remove();
				}
			}
		
			// remove faces if particles are death
			ListIterator<Face> facesIterator = faces.listIterator();
			while (facesIterator.hasNext()) {
				Face face = facesIterator.next();
				if (!face.isAlive()) {
					facesIterator.remove();
				}
			}
		} catch (ConcurrentModificationException e) {
			logger.warn("skipped frame" + e.getMessage(), e);
		} catch (NullPointerException e) {
			// python style:
			// don't check if it'll fail, just try, and if a error occurs, catch it
			// dont care about synchronization! it's just one single frame which is missing
			logger.warn("skipped frame" + e.getMessage(), e);
		}

//		// call frame listeners
//		ListIterator<FrameListener> frameListenersIterator = frameListeners.listIterator();
//		while (frameListenersIterator.hasNext()) {
//			frameListenersIterator.next().onFrame();
//		}

	}

	@Override
	public void setup() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public Boolean isFinished() {
		return false;
	}
	
	@Override
	public List<Particle> getParticles() {
		return particles;
	}

	@Override
	public List<FixedPoint> getFixedPoints() {
		return fixedPoints;
	}

	@Override
	public List<Face> getFaces() {
		return faces;
	}

	@Override
	public List<ParticleFeature> getParticleFeatures() {
		return particleFeatures;
	}
	
	@Override
	public List<ParticleEmitter> getParticleEmitters() {
		return emitters;
	}
	
	@Override
	public List<ParticleModifier> getParticleModifiers() {
		return modifiers;
	}
	
	@Override
	public ParticlePool getParticlePool() {
		return particlePool;
	}
	
	@Override
	public FacePool getFacePool() {
		return facePool;
	}
	
	protected void clearParticles() {
		for (Integer pIndex = 0; pIndex < particles.size(); pIndex++) {
			particles.get(pIndex).setRemainingIterations(0);
		}
		clearParticlesAtNextIteration = false;
	}

	protected void clearFixedPoints() {
		fixedPoints.clear(); // TODO: check for thread safety!
		clearFixedPointsAtNextIteration = false;
	}

	protected void clearFaces() {
		faces.clear(); // TODO: check for thread safety!
		clearFacesAtNextIteration = false;
	}

	/**
	 * Allows to modify the particle system more or less safely. The
	 * system is stopped for a 
	 */
	@Override
	public void beginModification() {
		if (!isPaused()) {
			paused = true;
			pausedBeforeModification = false; // remember paused state
			while (!idle) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
		} else {
			pausedBeforeModification = true;
		}
	}
	
	@Override
	public void endModification() {
		if (!pausedBeforeModification) {
			paused = false;
		}
	}
	
	
}
