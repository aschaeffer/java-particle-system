package de.hda.particles.modifier.velocity;

import de.hda.particles.domain.Particle;
import de.hda.particles.modifier.AbstractParticleModifier;
import de.hda.particles.modifier.ParticleModifier;

public class VelocityTransformation extends AbstractParticleModifier implements ParticleModifier {

	public VelocityTransformation() {}

	/**
	 * Applies the velocity transformation (sets the new particle
	 * position based on the old position and the velocity vector).
	 * Performance notice: doesn't create new objects!
	 */
	@Override
	public void update(Particle particle) {
		particle.setX(particle.getX() + particle.getVelX());
		particle.setY(particle.getY() + particle.getVelY());
		particle.setZ(particle.getZ() + particle.getVelZ());
	}

}
