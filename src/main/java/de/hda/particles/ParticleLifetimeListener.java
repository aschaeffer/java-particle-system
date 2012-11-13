package de.hda.particles;

import de.hda.particles.domain.Particle;

public interface ParticleLifetimeListener {

	void onParticleCreation(Particle particle);
	void onParticleDeath(Particle particle);

}
