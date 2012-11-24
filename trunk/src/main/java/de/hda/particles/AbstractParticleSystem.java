package de.hda.particles;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.domain.ParticleEmitterConfiguration;
import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.features.ParticleFeature;
import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.timing.FpsLimiter;

public abstract class AbstractParticleSystem extends FpsLimiter implements ParticleSystem {

	protected List<Particle> particles = new ArrayList<Particle>();
	protected List<ParticleFeature> particleFeatures = new ArrayList<ParticleFeature>();

	protected List<ParticleEmitter> emitters = new ArrayList<ParticleEmitter>();
	protected List<ParticleModifier> modifiers = new ArrayList<ParticleModifier>();
	
	protected List<ParticleLifetimeListener> listeners = new ArrayList<ParticleLifetimeListener>();

	protected Boolean paused = false;
	protected Boolean next = false;
	protected Boolean idle = true;
	protected Boolean emittersEnabled = true;
	protected Boolean modifiersEnabled = true;
	protected Boolean clearParticlesAtNextIteration = false;
	protected Integer pastIterations = 0;

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
			e.printStackTrace();
		}
	}
	
	@Override
	public void addParticleModifier(Class<? extends ParticleModifier> clazz, ParticleModifierConfiguration configuration) {
		ParticleModifier modifier;
		try {
			modifier = clazz.newInstance();
			modifier.setParticleSystem(this);
			modifier.setConfiguration(configuration);
			modifiers.add(modifier);
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
//		ListIterator<ParticleLifetimeListener> iterator = listeners.listIterator(0);
//		while (iterator.hasNext()) {
//			iterator.next().onParticleDeath(particle);
//		}
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
	
	protected void clearParticles() {
		for (Integer pIndex = 0; pIndex < particles.size(); pIndex++) {
			Particle particle = particles.get(pIndex);
			particle.setRemainingIterations(0);
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
