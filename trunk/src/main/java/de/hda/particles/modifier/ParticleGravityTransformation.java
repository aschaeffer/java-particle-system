package de.hda.particles.modifier;

import java.util.ListIterator;

import de.hda.particles.domain.Particle;

public class ParticleGravityTransformation extends GravityPoint implements ParticleModifier {

	public final static Double DEFAULT_MAX_FORCE = 0.1;

	public ParticleGravityTransformation() {}

	@Override
	public void update(Particle particle) {
		ListIterator<Particle> iterator = particleSystem.getParticles().listIterator(particle.getIndex()); // start at the current index position!
		while (iterator.hasNext()) {
			Particle otherParticle = iterator.next();
			updateParticleVelocity(particle, otherParticle.getPosition(), otherParticle.getMass(), DEFAULT_GRAVITY.floatValue(), DEFAULT_MAX_FORCE.floatValue());
		}
	}

}
