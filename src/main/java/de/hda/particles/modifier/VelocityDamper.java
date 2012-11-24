package de.hda.particles.modifier;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;

public class VelocityDamper extends AbstractParticleModifier implements ParticleModifier {

	public final static Double DEFAULT_DAMPER = 0.99; // 1% speed loss per iteration

	public VelocityDamper() {}

	@Override
	public void update(Particle particle) {
		Vector3f velocity = particle.getVelocity();
		velocity.x /= DEFAULT_DAMPER;
		velocity.y /= DEFAULT_DAMPER;
		velocity.z /= DEFAULT_DAMPER;
	}

}
