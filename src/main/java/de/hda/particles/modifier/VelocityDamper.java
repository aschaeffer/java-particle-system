package de.hda.particles.modifier;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;

public class VelocityDamper extends AbstractParticleModifier implements ParticleModifier {

	public final static String GLOBAL_VELOCITY_DAMPER = "velocityDamper";

	public final static Double DEFAULT_GLOBAL_VELOCITY_DAMPER = 0.99; // 1% speed loss per iteration

	protected Double damper = DEFAULT_GLOBAL_VELOCITY_DAMPER;

	public VelocityDamper() {}

	@Override
	public void prepare() {
		Double d = (Double) this.configuration.get(GLOBAL_VELOCITY_DAMPER);
		if (d == null) d = DEFAULT_GLOBAL_VELOCITY_DAMPER;
		damper = d;
	}

	@Override
	public void update(Particle particle) {
		Vector3f velocity = particle.getVelocity();
		velocity.x *= damper;
		velocity.y *= damper;
		velocity.z *= damper;
		particle.setVelocity(velocity);
	}

}
