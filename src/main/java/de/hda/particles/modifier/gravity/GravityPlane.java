package de.hda.particles.modifier.gravity;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.modifier.ParticleModifier;

public class GravityPlane extends GravityPoint implements ParticleModifier {

	public final static String NORMAL_X = "normal_x";
	public final static String NORMAL_Y = "normal_y";
	public final static String NORMAL_Z = "normal_z";

	public final static Double DEFAULT_GRAVITY = 1.8;
	public final static Double DEFAULT_MASS = 2000.0;
	public final static Double DEFAULT_MAX_FORCE = 1.0;

	public Vector3f normal = new Vector3f(0.0f, 1.0f, 0.0f);
	public Vector3f normalizedNormal = new Vector3f(0.0f, 1.0f, 0.0f);
	// public Vector3f inverseNormalizedNormal = new Vector3f(0.0f, -1.0f, 0.0f);

	public GravityPlane() {}

	@Override
	public void prepare() {
		if (!configuration.containsKey(POSITION_X) || !configuration.containsKey(POSITION_Y) || !configuration.containsKey(POSITION_Z)) return;
		position.setX(((Double) this.configuration.get(POSITION_X)).floatValue());
		position.setY(((Double) this.configuration.get(POSITION_Y)).floatValue());
		position.setZ(((Double) this.configuration.get(POSITION_Z)).floatValue());
		if (!configuration.containsKey(NORMAL_X) || !configuration.containsKey(NORMAL_Y) || !configuration.containsKey(NORMAL_Z)) return;
		normal.setX(((Double) this.configuration.get(NORMAL_X)).floatValue());
		normal.setY(((Double) this.configuration.get(NORMAL_Y)).floatValue());
		normal.setZ(((Double) this.configuration.get(NORMAL_Z)).floatValue());
		normal.normalise(normalizedNormal);
		// normalizedNormal.negate(inverseNormalizedNormal);
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

	/**
	 * Apply plane gravity to particle:
	 * 1. normalize plane normal (done only once in prepare)
	 * 2. inverse normalized plane normal (done only once in prepare)
	 * 3. scale inverse normalized plane normal (per particle)
	 * 4. add scale inverse normalized plane normal to particle velocity (per particle)
	 */
	@Override
	public void update(Particle particle) {
		// particle to
		// q (plane point) to p (particle point)
		Vector3f planePointToParticle = new Vector3f();
		Vector3f.sub(particle.getPosition(), position, planePointToParticle);
		// distance p to nearest point on the plane
		Float distance = Vector3f.dot(planePointToParticle, normalizedNormal);
		Float force = -(particle.getMass()) * mass * gravity / (distance * distance);
		// velocity change vector
		Vector3f n = new Vector3f(
			(force * normalizedNormal.x) / (distance * particle.getMass()),
			(force * normalizedNormal.y) / (distance * particle.getMass()),
			(force * normalizedNormal.z) / (distance * particle.getMass())
		);
		// new velocity
		Vector3f newVelocity = new Vector3f();
		Vector3f.add(particle.getVelocity(), n, newVelocity);
		particle.setVelocity(newVelocity);
	}

}
