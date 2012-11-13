package de.hda.particles.modifier;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;

public class GravityPlane extends AbstractParticleModifier implements ParticleModifier {

	public final static String POINT_1 = "point1";
	public final static String POINT_2 = "point2";
	public final static String GRAVITY = "gravity";

	public GravityPlane() {}

	public void update(Particle particle) {
		Vector3f point1 = (Vector3f) this.configuration.get(POINT_1);
		Vector3f point2 = (Vector3f) this.configuration.get(POINT_2);
		Float gravity = (Float) this.configuration.get(GRAVITY);
		
		// othogonaler vektor auf fläche berechnen (skalarprodukt oder kreuzprodukt?)
		// partikel entlang des vektors 

	}

}
