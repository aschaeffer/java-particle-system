package de.hda.particles.modifier;

import de.hda.particles.domain.Particle;

public class BlackHole extends GravityPoint implements ParticleModifier {

	public final static Double EVENT_HORIZON_FACTOR = 200.0;
	
	protected Double eventHorizon = 0.0;

	public BlackHole() {}

	@Override
	public void prepare() {
		super.prepare();
		eventHorizon = gravity * mass / EVENT_HORIZON_FACTOR;
		
	}
	@Override
	public void update(Particle particle) {
		if (!configuration.containsKey(POSITION_X) || !configuration.containsKey(POSITION_Y) || !configuration.containsKey(POSITION_Z)) return;

		if (Math.abs(particle.getX() - position.x) < eventHorizon
			&& Math.abs(particle.getY() - position.y) < eventHorizon
			&& Math.abs(particle.getZ() - position.z) < eventHorizon
		) {
			// remove particles which are within the event horizon
			particle.setRemainingIterations(0);
			updateConfiguration(MASS, new Double(mass + particle.getMass()));
		} else {
			// or act like gravity point
			updateParticleVelocity(particle, position, mass, gravity, maxForce);
		}
	}

}
