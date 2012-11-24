package de.hda.particles.modifier;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;

public class GravityPoint extends AbstractParticleModifier implements ParticleModifier {

	public final static String POINT_X = "point_x";
	public final static String POINT_Y = "point_y";
	public final static String POINT_Z = "point_z";
	public final static String MASS = "mass";
	public final static String GRAVITY = "gravity";
	public final static String MAX_FORCE = "maxForce";
	
	public static final Double DEFAULT_GRAVITY = 1.2;
	public static final Double DEFAULT_MASS = 1000.0;
	public final static Double DEFAULT_MAX_FORCE = 10.0;

	public GravityPoint() {}

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
		updateParticleVelocity(particle, new Vector3f(gravityPointX.floatValue(), gravityPointY.floatValue(), gravityPointZ.floatValue()), mass.floatValue(), gravity.floatValue(), maxForce.floatValue());
	}
	
	public void updateParticleVelocity(Particle particle, Vector3f gravityPoint, Float mass, Float gravity, Float maxForce) {
		Float dx = particle.getX() - gravityPoint.x;
		Float dy = particle.getY() - gravityPoint.y;
		Float dz = particle.getZ() - gravityPoint.z;
		Float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
		if (distance == 0.0f) distance = 0.00000000001f;
		Float force = -(particle.getMass()) * mass * gravity / (distance * distance);
		if (Math.abs(force) > maxForce) force = maxForce;
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
