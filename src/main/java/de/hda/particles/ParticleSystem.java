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

public class ParticleSystem extends FpsLimiter implements Updateable {

	public final static String SYSTEM_NAME = "particle physics";

	public List<Particle> particles = new ArrayList<Particle>();
	public List<ParticleFeature> particleFeatures = new ArrayList<ParticleFeature>();

	public List<ParticleEmitter> emitters = new ArrayList<ParticleEmitter>();
	public List<ParticleModifier> modifiers = new ArrayList<ParticleModifier>();
	
	public List<ParticleLifetimeListener> listeners = new ArrayList<ParticleLifetimeListener>();
	
	private Boolean paused = false;
	private Boolean emittersEnabled = true;
	private Boolean modifiersEnabled = true;
    
	public void update() {
		calcFps();
		limitFps();

		if (paused) return;

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
				List<Particle> currentParticles = new ArrayList<Particle>(particles);
				ListIterator<Particle> pIterator = currentParticles.listIterator(0);
				while (pIterator.hasNext()) modifier.update(pIterator.next());
			}
		}

		// decrease particle lifetimes and remove death particles
		List<Particle> currentParticles = new ArrayList<Particle>(particles);
		ListIterator<Particle> pIterator = currentParticles.listIterator(0);
		while (pIterator.hasNext()) {
			Particle particle = pIterator.next();
			particle.decLifetime();
			if (!particle.isAlive()) removeParticle(particle);
		}
		
	}
	
	public void addParticleEmitter(Class<? extends ParticleEmitter> clazz, Vector3f position, Vector3f velocity, Integer renderTypeIndex, Integer lifetime, ParticleEmitterConfiguration configuration) {
		ParticleEmitter emitter;
		try {
			emitter = clazz.newInstance();
			emitter.setParticleSystem(this);
			emitter.setPosition(position);
			emitter.setParticleDefaultVelocity(velocity);
			emitter.setParticleRenderTypeIndex(renderTypeIndex);
			emitter.setParticleLifetime(lifetime);
			emitter.setConfiguration(configuration);
			emitters.add(emitter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
	
	public void addParticleListener(ParticleLifetimeListener particleListener) {
		listeners.add(particleListener);
	}

	public void addParticleFeature(ParticleFeature particleFeature) {
		this.particleFeatures.add(particleFeature);
	}

	public void addParticle(Particle particle) {
		particles.add(particle);
		ListIterator<ParticleLifetimeListener> iterator = listeners.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().onParticleCreation(particle);
		}
	}
	
	public void removeParticle(Particle particle) {
		particles.remove(particle);
		ListIterator<ParticleLifetimeListener> iterator = listeners.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().onParticleDeath(particle);
		}
	}
	
	public void pause() {
		paused = !paused;
	}
	
	public void toggleEmitters() {
		emittersEnabled = !emittersEnabled;
	}
	
	public void toggleModifiers() {
		modifiersEnabled = !modifiersEnabled;
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
	public String getSystemName() {
		return SYSTEM_NAME;
	}
	
}
