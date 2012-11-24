package de.hda.particles.modifier;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;

public class BlackHole extends GravityPoint implements ParticleModifier {

	public final static Float EVENT_HORIZON_FACTOR = 200.0f;

	public BlackHole() {}

	@Override
	public void update(Particle particle) {
		if (!configuration.containsKey(POINT_X) || !configuration.containsKey(POINT_Y) || !configuration.containsKey(POINT_Z)) return;
		Double gravityPointX = (Double) this.configuration.get(POINT_X);
		Double gravityPointY = (Double) this.configuration.get(POINT_Y);
		Double gravityPointZ = (Double) this.configuration.get(POINT_Z);
		Double gravity = (Double) this.configuration.get(GRAVITY);
		if (gravity == null) gravity = DEFAULT_GRAVITY;
		Double mass = (Double) this.configuration.get(MASS);
		if (mass == null) mass = DEFAULT_MASS;
		Double maxForce = (Double) this.configuration.get(MAX_FORCE);
		if (maxForce == null) maxForce = DEFAULT_MAX_FORCE;

		Double eventHorizon = gravity * mass / EVENT_HORIZON_FACTOR;

		// remove particles which are within the event horizon
		if (Math.abs(particle.getX() - gravityPointX) < eventHorizon && Math.abs(particle.getY() - gravityPointY) < eventHorizon && Math.abs(particle.getZ() - gravityPointZ) < eventHorizon) {
			particle.setRemainingIterations(0);
			System.out.println("black hole event horizon");
			// this.configuration.put(EVENT_HORIZON, eventHorizon * 1.02f);
			this.configuration.put(MASS, new Double(mass + particle.getMass()));
		} else {
			updateParticleVelocity(particle, new Vector3f(gravityPointX.floatValue(), gravityPointY.floatValue(), gravityPointZ.floatValue()), mass.floatValue(), gravity.floatValue(), maxForce.floatValue());
		}


//		if (!configuration.containsKey(POINT_X) || !configuration.containsKey(POINT_Y) || !configuration.containsKey(POINT_Z)) return;
//		Double gravityPointX = (Double) configuration.get(POINT_X);
//		Double gravityPointY = (Double) configuration.get(POINT_Y);
//		Double gravityPointZ = (Double) configuration.get(POINT_Z);
//		Double mass = (Double) configuration.get(MASS);
//		Double gravity = (Double) configuration.get(GRAVITY);
//		if (gravity == null) gravity = DEFAULT_GRAVITY;
//		Double dx = particle.getX() - gravityPointX;
//		Double dy = particle.getY() - gravityPointY;
//		Double dz = particle.getZ() - gravityPointZ;
//		Float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
//		Double force = -(particle.getMass()) * mass * gravity / (distance * distance);
//		Vector3f totalForce = new Vector3f(
//			new Double(force * (particle.getX() - gravityPointX) / distance).floatValue(),
//			new Double(force * (particle.getY() - gravityPointY) / distance).floatValue(),
//			new Double(force * (particle.getZ() - gravityPointZ) / distance).floatValue()
//		);
//		Vector3f accelleration = new Vector3f(
//			totalForce.x / particle.getMass(),
//			totalForce.y / particle.getMass(),
//			totalForce.z / particle.getMass()
//		);
//		Vector3f newVelocity = new Vector3f();
//		Vector3f.add(particle.getVelocity(), accelleration, newVelocity);
//		particle.setVelocity(newVelocity);
	}

}