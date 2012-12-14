package de.hda.particles;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.vector.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.Particle;
import de.hda.particles.domain.ParticleEmitterConfiguration;
import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.features.ParticleFeature;
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
	protected ParticlePool pool = new ParticlePool();
	
	/**
	 * The list of particle lifetime listeners.
	 */
	protected List<ParticleLifetimeListener> listeners = new ArrayList<ParticleLifetimeListener>();

	/**
	 * Paused state of the particle system.
	 */
	protected Boolean paused = false;
	
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
	 * Number of past iterations.
	 */
	protected Integer pastIterations = 0;
	
	private final Logger logger = LoggerFactory.getLogger(AbstractParticleSystem.class);

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
	@Override
	public void addParticleEmitter(Class<? extends ParticleEmitter> clazz, Vector3f position, Vector3f velocity, Integer renderTypeIndex, Integer rate, Integer lifetime, ParticleEmitterConfiguration configuration) {
		ParticleEmitter emitter;
		try {
			emitter = clazz.newInstance();
			emitter.setParticleSystem(this);
			emitter.setPosition(position);
			emitter.setParticleDefaultVelocity(velocity);
			emitter.setParticleRenderTypeIndex(renderTypeIndex);
			emitter.setRate(rate);
			emitter.setParticleLifetime(lifetime);
			emitter.setConfiguration(configuration);
			emitters.add(emitter);
		} catch (Exception e) {
			logger.error("could not add particle emitter", e);
		}
	}

	/**
	 * Adds a particle modifier to the particle system.
	 * 
	 * @param clazz Type of the modifier.
	 * @param configuration Modifier implementation specific configuration.
	 */
	@Override
	public void addParticleModifier(Class<? extends ParticleModifier> clazz, ParticleModifierConfiguration configuration) {
		ParticleModifier modifier;
		try {
			modifier = clazz.newInstance();
			modifier.setParticleSystem(this);
			modifier.setConfiguration(configuration);
			modifiers.add(modifier);
		} catch (Exception e) {
			logger.error("could not add particle modifier", e);
		}
	}
	
	@Override
	public void addParticleListener(ParticleLifetimeListener particleListener) {
		listeners.add(particleListener);
	}

	@Override
	public void addParticleFeature(ParticleFeature particleFeature) {
		this.particleFeatures.add(particleFeature);
	}
	
	@Override
	public void addParticleFeature(Class<? extends ParticleFeature> clazz) {
		try {
			ParticleFeature feature = clazz.newInstance();
			particleFeatures.add(feature);
		} catch (Exception e) {
			logger.error("could not add particle feature", e);
		}
	}

	@Override
	public void addParticle(Particle particle) {
		particles.add(particle);
		ListIterator<ParticleLifetimeListener> iterator = listeners.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().onParticleCreation(particle);
		}
	}
	
	@Override
	public void removeParticle(Particle particle) {
		particles.remove(particle);
		ListIterator<ParticleLifetimeListener> iterator = listeners.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().onParticleDeath(particle);
		}
	}
	
	@Override
	public void removeAllParticles() {
		clearParticlesAtNextIteration = true;
	}

	@Override
	public void removeParticleEmitter(ParticleEmitter particleEmitter) {
		emitters.remove(particleEmitter);
	}

	@Override
	public void removeParticleModifier(ParticleModifier particleModifier) {
		modifiers.remove(particleModifier);
	}

	@Override
	public void removeParticleFeature(ParticleFeature particleFeature) {
		particleFeatures.remove(particleFeature);
	}

	@Override
	public void removeParticleListener(ParticleLifetimeListener particleListener) {
		listeners.remove(particleListener);
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
		limitFps();
		
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
		}

		if (clearParticlesAtNextIteration) clearParticles(); // Thread Safety

		// decrease particle lifetimes and remove death particles
		ListIterator<Particle> particlesIterator = particles.listIterator();
		while (particlesIterator.hasNext()) {
			Particle particle = particlesIterator.next();
			particle.decLifetime();
			if (particle.getRemainingIterations() <= 0) {
				particlesIterator.remove();
			}
		}
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
		return pool;
	}
	
	protected void clearParticles() {
		for (Integer pIndex = 0; pIndex < particles.size(); pIndex++) {
			particles.get(pIndex).setRemainingIterations(0);
		}
		clearParticlesAtNextIteration = false;
	}

	@Override
	public void beginModification() {
		if (!isPaused()) {
			paused = true;
			while (!idle) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
		}
	}
	
	@Override
	public void endModification() {
		paused = false;
	}
	
	
}
