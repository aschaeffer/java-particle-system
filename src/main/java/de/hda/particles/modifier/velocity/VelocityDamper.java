package de.hda.particles.modifier.velocity;

import de.hda.particles.domain.Particle;
import de.hda.particles.modifier.AbstractParticleModifier;
import de.hda.particles.modifier.ParticleModifier;

public class VelocityDamper extends AbstractParticleModifier implements ParticleModifier {

	public final static String GLOBAL_VELOCITY_DAMPER = "velocityDamper";

	public final static Double DEFAULT_GLOBAL_VELOCITY_DAMPER = 0.99; // 1% speed loss per iteration

	protected float damper = DEFAULT_GLOBAL_VELOCITY_DAMPER.floatValue();

	public VelocityDamper() {}

	@Override
	public void prepare() {
		Double d = (Double) this.configuration.get(GLOBAL_VELOCITY_DAMPER);
		if (d == null) d = DEFAULT_GLOBAL_VELOCITY_DAMPER;
		damper = d.floatValue();
	}

	/**
	 * Applies a damper factor to the velocity.
	 * Performance notice: doesn't create new objects!
	 */
	@Override
	public void update(Particle particle) {
		particle.setVelX(particle.getVelX() * damper);
		particle.setVelY(particle.getVelY() * damper);
		particle.setVelZ(particle.getVelZ() * damper);
	}

}
