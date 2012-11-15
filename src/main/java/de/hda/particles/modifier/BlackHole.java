package de.hda.particles.modifier;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;

public class BlackHole extends AbstractParticleModifier implements ParticleModifier {

	public final static String POINT_X = "point_x";
	public final static String POINT_Y = "point_y";
	public final static String POINT_Z = "point_z";
	public final static String MASS = "mass";
	public final static String GRAVITY = "gravity";
	public final static Float EVENT_HORIZON_FACTOR = 200.0f;

	public BlackHole() {}

	@Override
	public void update(Particle particle) {
		Float gravityPointX = new Float((Double) this.configuration.get(POINT_X));
		Float gravityPointY = new Float((Double) this.configuration.get(POINT_Y));
		Float gravityPointZ = new Float((Double) this.configuration.get(POINT_Z));
		Float mass = new Float((Double) this.configuration.get(MASS));
		Float gravity = new Float((Double) this.configuration.get(GRAVITY));
		Float eventHorizon = gravity * mass / EVENT_HORIZON_FACTOR;

		// remove particles which are 
		if (Math.abs(particle.getX() - gravityPointX) < eventHorizon && Math.abs(particle.getY() - gravityPointY) < eventHorizon && Math.abs(particle.getZ() - gravityPointZ) < eventHorizon) {
			particle.setRemainingIterations(0);
			System.out.println("black hole event horizon");
			// this.configuration.put(EVENT_HORIZON, eventHorizon * 1.02f);
			this.configuration.put(MASS, new Double(mass + particle.getMass()));
		}
		
		Float dx = particle.getX() - gravityPointX;
		Float dy = particle.getY() - gravityPointY;
		Float dz = particle.getZ() - gravityPointZ;
		Float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
		Float force = -(particle.getMass()) * mass * gravity / (distance * distance);
		Vector3f totalForce = new Vector3f(
			force * (particle.getX() - gravityPointX) / distance,
			force * (particle.getY() - gravityPointY) / distance,
			force * (particle.getZ() - gravityPointZ) / distance
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
