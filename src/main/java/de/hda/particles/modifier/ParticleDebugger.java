package de.hda.particles.modifier;

import de.hda.particles.domain.Particle;

public class ParticleDebugger extends AbstractParticleModifier implements ParticleModifier {

	public void update(Particle particle) {
		System.out.println(particle.toString());
	}

}
