package de.hda.particles.modifier.impl.gravity;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.modifier.impl.AbstractPositionablePlaneModifier;
import de.hda.particles.modifier.PositionablePlaneModifier;

public class GravityPlane extends AbstractPositionablePlaneModifier implements PositionablePlaneModifier {

	public final static Double DEFAULT_GRAVITY = 1.8;
	public final static Double DEFAULT_MASS = 2000.0;
	public final static Double DEFAULT_MAX_FORCE = 1.0;
	
	protected Float gravity = 0.0f;
	protected Float mass = 0.0f;
	protected Float maxForce = 0.0f;

	public GravityPlane() {}

	@Override
	public void prepare() {
		if (!expectKeys()) return;
		super.prepare();
		normal.normalise(normalizedNormal);
		Double g = (Double) this.configuration.get(GravityBase.GRAVITY);
		if (g == null) g = DEFAULT_GRAVITY;
		gravity = g.floatValue();
		Double m = (Double) this.configuration.get(GravityBase.MASS);
		if (m == null) m = DEFAULT_MASS;
		mass = m.floatValue();
		Double mf = (Double) this.configuration.get(GravityBase.MAX_FORCE);
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
