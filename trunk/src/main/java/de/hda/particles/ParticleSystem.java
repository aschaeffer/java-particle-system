package de.hda.particles;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import de.hda.particles.domain.Particle;
import de.hda.particles.domain.ParticleEmitterConfiguration;
import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.domain.Vector3;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.features.ParticleFeature;
import de.hda.particles.modifier.ParticleModifier;

public class ParticleSystem implements Updateable {

	public List<Particle> particles = new ArrayList<Particle>();
	public List<ParticleFeature> particleFeatures = new ArrayList<ParticleFeature>();

	public List<ParticleEmitter> emitters = new ArrayList<ParticleEmitter>();
	public List<ParticleModifier> modifiers = new ArrayList<ParticleModifier>();

	public ParticleSystem() {
	}

	public void update() {
		// call every particle emitter
		ListIterator<ParticleEmitter> eIterator = emitters.listIterator(0);
		while (eIterator.hasNext()) {
			ParticleEmitter emitter = eIterator.next();
			emitter.update();
		}
		
		// modify existing particles
		ListIterator<ParticleModifier> mIterator = modifiers.listIterator(0);
		while (mIterator.hasNext()) {
			ParticleModifier modifier = mIterator.next();
			ListIterator<Particle> pIterator = particles.listIterator(0);
			while (pIterator.hasNext()) {
				Particle particle = pIterator.next();
				modifier.update(particle);
			}
		}

		// decrease particle lifetimes
		ListIterator<Particle> pIterator = particles.listIterator(0);
		while (pIterator.hasNext()) {
			pIterator.next().decLifetime();
		}
	}
	
	public void addParticleEmitter(Class<? extends ParticleEmitter> clazz, Vector3 position, Vector3 velocity, Integer lifetime, ParticleEmitterConfiguration configuration) {
		ParticleEmitter emitter;
		try {
			emitter = clazz.newInstance();
			emitter.setParticleSystem(this);
			emitter.setPosition(position);
			emitter.setParticleDefaultVelocity(velocity);
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

	public void addParticleFeature(ParticleFeature particleFeature) {
		this.particleFeatures.add(particleFeature);
	}

	public void addParticle(Particle particle) {
		particles.add(particle);
	}
	
	public void removeParticle(Particle particle) {
		particles.remove(particle);
	}
	
}
