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
		if (!configuration.containsKey(POINT_X) || !configuration.containsKey(POINT_Y) || !configuration.containsKey(POINT_Z) || !configuration.containsKey(GRAVITY) || !configuration.containsKey(MASS) || !configuration.containsKey(MAX_FORCE)) return;
		Double gravityPointXd = (Double) this.configuration.get(POINT_X);
		Double gravityPointYd = (Double) this.configuration.get(POINT_Y);
		Double gravityPointZd = (Double) this.configuration.get(POINT_Z);
		Double massd = (Double) this.configuration.get(MASS);
		Double gravityd = (Double) this.configuration.get(GRAVITY);
		Double maxForce = (Double) this.configuration.get(MAX_FORCE);
		Float gravityPointX = gravityPointXd.floatValue();
		Float gravityPointY = gravityPointYd.floatValue();
		Float gravityPointZ = gravityPointZd.floatValue();
		Float mass = massd.floatValue();
		Float gravity = gravityd.floatValue();
		Float dx = particle.getX() - gravityPointX;
		Float dy = particle.getY() - gravityPointY;
		Float dz = particle.getZ() - gravityPointZ;
		Float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
		Float force = -(particle.getMass()) * mass * gravity / (distance * distance);
		if (Math.abs(force) > maxForce) force = maxForce.floatValue();
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
