package de.hda.particles.modifier;

import de.hda.particles.domain.Particle;

public class DeathParticleRemover extends AbstractParticleModifier implements ParticleModifier {

	public void update(Particle particle) {
		if (!particle.isAlive()) this.particleSystem.removeParticle(particle);
	}

}
