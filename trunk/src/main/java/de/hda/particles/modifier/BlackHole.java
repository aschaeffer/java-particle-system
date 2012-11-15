package de.hda.particles.modifier;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;

public class BlackHole extends AbstractParticleModifier implements ParticleModifier {

	public final static String POINT = "point";
	public final static String MASS = "mass";
	public final static String GRAVITY = "gravity";
	public final static Float EVENT_HORIZON_FACTOR = 200.0f;

	public BlackHole() {}

	public void update(Particle particle) {
		Vector3f gravityPoint = (Vector3f) this.configuration.get(POINT);
		Float mass = (Float) this.configuration.get(MASS);
		Float gravity = (Float) this.configuration.get(GRAVITY);
		Float eventHorizon = gravity * mass / EVENT_HORIZON_FACTOR;
		// Float eventHorizon = (Float) this.configuration.get(EVENT_HORIZON);

		// remove particles which are 
		if (Math.abs(particle.getX() - gravityPoint.x) < eventHorizon && Math.abs(particle.getY() - gravityPoint.y) < eventHorizon && Math.abs(particle.getZ() - gravityPoint.z) < eventHorizon) {
			particle.setRemainingIterations(0);
			System.out.println("black hole event horizon");
			// this.configuration.put(EVENT_HORIZON, eventHorizon * 1.02f);
			this.configuration.put(MASS, mass + particle.getMass());
		}
		
		Float dx = particle.getX() - gravityPoint.x;
		Float dy = particle.getY() - gravityPoint.y;
		Float dz = particle.getZ() - gravityPoint.z;
		Float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
		Float force = -(particle.getMass()) * mass * gravity / (distance * distance);
		Vector3f totalForce = new Vector3f(
			force * (particle.getX() - gravityPoint.x) / distance,
			force * (particle.getY() - gravityPoint.y) / distance,
			force * (particle.getZ() - gravityPoint.z) / distance
		);
		Vector3f accelleration = new Vector3f(
			totalForce.x / particle.getMass(),
			totalForce.y / particle.getMass(),
			totalForce.z / particle.getMass()
		);
		Vector3f newVelocity = new Vector3f();
		Vector3f.add(particle.getVelocity(), accelleration, newVelocity);
		particle.setVelocity(newVelocity);
	}

}
