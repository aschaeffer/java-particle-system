package de.hda.particles;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import de.hda.particles.domain.Particle;
import de.hda.particles.domain.Vector3;
import de.hda.particles.emitter.ParticleEmitter;

public class ParticleSystem implements Updateable {

	List<ParticleEmitter> emitters = new ArrayList<ParticleEmitter>();
	
	List<Particle> particles = new ArrayList<Particle>();
	
	public ParticleSystem() {
	}

	public void update() {
		// call every particle emitter
		ListIterator<ParticleEmitter> eIterator = emitters.listIterator(0);
		while (eIterator.hasNext()) {
			ParticleEmitter emitter = eIterator.next();
			emitter.update();
		}
		
		// remove death particles
		ListIterator<Particle> pIterator = particles.listIterator(0);
		while (pIterator.hasNext()) {
			Particle particle = pIterator.next();
			if (!particle.isAlive()) particles.remove(particle);
		}
		
		// update existing particles (position, velocity)
		pIterator = particles.listIterator(0);
		while (pIterator.hasNext()) {
			Particle particle = pIterator.next();
			updateParticle(particle);
		}
	}
	
	public void createEmitter(Class<? extends ParticleEmitter> clazz, Vector3 position, Vector3 velocity, Integer lifetime) {
		ParticleEmitter emitter;
		try {
			emitter = clazz.newInstance();
			emitter.setPosition(position);
			emitter.setDefaultVelocity(velocity);
			emitter.setDefaultLifetime(lifetime);
			emitters.add(emitter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addParticle(Particle particle) {
		particles.add(particle);
	}
	
	public void updateParticle(Particle particle) {
		//
	}
	
	public void debug() {
		ListIterator<Particle> pIterator = particles.listIterator(0);
		while (pIterator.hasNext()) {
			Particle particle = pIterator.next();
			if (!particle.isAlive()) particles.remove(particle);
		}
		
	}
	
}
