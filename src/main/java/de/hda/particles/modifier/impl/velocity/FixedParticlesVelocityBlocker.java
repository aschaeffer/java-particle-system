package de.hda.particles.modifier.impl.velocity;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.impl.FixedPosition;
import de.hda.particles.modifier.impl.AbstractParticleModifier;
import de.hda.particles.modifier.ParticleModifier;

/**
 * Negates the velocity transformation for particles that
 * are marked as positional fixed.
 *  
 * @author aschaeffer
 *
 */
public class FixedParticlesVelocityBlocker extends AbstractParticleModifier implements ParticleModifier {

	public FixedParticlesVelocityBlocker() {}

	@Override
	public void update(Particle particle) {
		if (particle.containsKey(FixedPosition.POSITION_FIXED)) {
			if ((Boolean) particle.get(FixedPosition.POSITION_FIXED)) {
				particle.setX(particle.getX() - particle.getVelX());
				particle.setY(particle.getY() - particle.getVelY());
				particle.setZ(particle.getZ() - particle.getVelZ());
			}
		}
	}

}
