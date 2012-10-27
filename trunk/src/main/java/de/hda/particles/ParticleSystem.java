package de.hda.particles;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import de.hda.particles.domain.Particle;
import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.domain.Vector3;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.modifier.ParticleModifier;

public class ParticleSystem implements Updateable {

	List<Particle> particles = new ArrayList<Particle>();
	
	List<ParticleEmitter> emitters = new ArrayList<ParticleEmitter>();
	List<ParticleModifier> modifiers = new ArrayList<ParticleModifier>();

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
			// remove death particles
			ListIterator<Particle> pIterator = particles.listIterator(0);
			while (pIterator.hasNext()) {
				Particle particle = pIterator.next();
				modifier.update(particle);
			}
		}
	}
	
	public void addParticleEmitter(Class<? extends ParticleEmitter> clazz, Vector3 position, Vector3 velocity, Integer lifetime) {
		ParticleEmitter emitter;
		try {
			emitter = clazz.newInstance();
			emitter.setParticleSystem(this);
			emitter.setPosition(position);
			emitter.setParticleDefaultVelocity(velocity);
			emitter.setParticleLifetime(lifetime);
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
	
	public void addParticle(Particle particle) {
		particles.add(particle);
	}
	
	public void removeParticle(Particle particle) {
		particles.remove(particle);
	}
	
}
