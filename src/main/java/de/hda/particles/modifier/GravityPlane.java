package de.hda.particles.modifier;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;

public class GravityPlane extends AbstractParticleModifier implements ParticleModifier {

	public final static String POINT = "point";
	public final static String VECTOR_1 = "vector1";
	public final static String VECTOR_2 = "vector2";
	public final static String MASS = "mass";
	public final static String GRAVITY = "gravity";

//	public static final Double DEFAULT_GRAVITY = 1.2;
//	public static final Double DEFAULT_MASS = 1000.0;
//
//	public enum DIRECTION {
//		NEGATIVE_X,
//		NEGATIVE_Y,
//		NEGATIVE_Z,
//		POSITIVE_X,
//		POSITIVE_Y,
//		POSITIVE_Z
//	}

	public GravityPlane() {}

	/**
	 * Orthogonalprojektion des Partikels auf die Ebene, aufgespannt von
	 * Punkt 1 und Punkt 2.
	 */
	@Override
	public void update(Particle particle) {
		Vector3f position = (Vector3f) this.configuration.get(POINT);
		Vector3f vector1 = (Vector3f) this.configuration.get(VECTOR_1);
		Vector3f vector2 = (Vector3f) this.configuration.get(VECTOR_2);
		Float mass = (Float) this.configuration.get(MASS);
		Float gravity = (Float) this.configuration.get(GRAVITY);

		// Normalenvektor auf Ebene
		Vector3f normal = new Vector3f();
		Vector3f.cross(vector1, vector2, normal);
		normal.normalise();
		
		// Abstand des Partikels zur Ebene
		Vector3f diff = new Vector3f();
		diff.x = particle.getX() - position.x;
		diff.y = particle.getY() - position.y;
		diff.z = particle.getZ() - position.z;
		Float distance = Vector3f.dot(diff, normal);
		
		Float force = -(particle.getMass()) * mass * gravity / (distance * distance);
		Vector3f totalForce = new Vector3f(
			force * (particle.getX() - position.x) / distance, // nicht 100% richtig (statt position muesste
			force * (particle.getY() - position.y) / distance, // eigentlich der partikel auf die ebene pro-
			force * (particle.getZ() - position.z) / distance  // jiziert werden)
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
