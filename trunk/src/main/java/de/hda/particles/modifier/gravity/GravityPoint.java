package de.hda.particles.modifier.gravity;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.modifier.AbstractParticleModifier;
import de.hda.particles.modifier.ParticleModifier;

public class GravityPoint extends AbstractParticleModifier implements ParticleModifier {

	public final static String POSITION_X = "position_x";
	public final static String POSITION_Y = "position_y";
	public final static String POSITION_Z = "position_z";

	public final static String MASS = "mass";
	public final static String GRAVITY = "gravity";
	public final static String MAX_FORCE = "maxForce";
	
	public final static Double DEFAULT_GRAVITY = 0.8;
	public final static Double DEFAULT_MASS = 1000.0;
	public final static Double DEFAULT_MAX_FORCE = 3.0;
	
	protected Vector3f position = new Vector3f();
	protected Float gravity = 0.0f;
	protected Float mass = 0.0f;
	protected Float maxForce = 0.0f;

	public GravityPoint() {}

	@Override
	public void prepare() {
		if (!configuration.containsKey(POSITION_X) || !configuration.containsKey(POSITION_Y) || !configuration.containsKey(POSITION_Z)) return;
		position.setX(((Double) this.configuration.get(POSITION_X)).floatValue());
		position.setY(((Double) this.configuration.get(POSITION_Y)).floatValue());
		position.setZ(((Double) this.configuration.get(POSITION_Z)).floatValue());
		Double g = (Double) this.configuration.get(GRAVITY);
		if (g == null) g = DEFAULT_GRAVITY;
		gravity = g.floatValue();
		Double m = (Double) this.configuration.get(MASS);
		if (m == null) m = DEFAULT_MASS;
		mass = m.floatValue();
		Double mf = (Double) this.configuration.get(MAX_FORCE);
		if (mf == null) mf = DEFAULT_MAX_FORCE;
		maxForce = mf.floatValue();
	}

	@Override
	public void update(Particle particle) {
		if (!configuration.containsKey(POSITION_X) || !configuration.containsKey(POSITION_Y) || !configuration.containsKey(POSITION_Z)) return;
		updateParticleVelocity(particle, position, mass, gravity, maxForce);
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
