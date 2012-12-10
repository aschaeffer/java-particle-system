package de.hda.particles.modifier.velocity;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.modifier.AbstractParticleModifier;
import de.hda.particles.modifier.ParticleModifier;

public class VelocityTransformation extends AbstractParticleModifier implements ParticleModifier {

	public VelocityTransformation() {}

	@Override
	public void update(Particle particle) {
		Vector3f newPosition = new Vector3f();
		Vector3f.add(particle.getPosition(), particle.getVelocity(), newPosition);
		particle.setPosition(newPosition);
	}

}
