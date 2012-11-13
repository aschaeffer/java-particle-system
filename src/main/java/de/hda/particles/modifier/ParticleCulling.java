package de.hda.particles.modifier;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;

public class ParticleCulling extends AbstractParticleModifier implements ParticleModifier {

	public final static String POINT1 = "point1";
	public final static String POINT2 = "point2";

	public ParticleCulling() {}

	public void update(Particle particle) {
		Vector3f position = particle.getPosition();
		Vector3f bb1 = (Vector3f) this.configuration.get(POINT1);
		Vector3f bb2 = (Vector3f) this.configuration.get(POINT2);
		if (position.x < bb1.x || position.y < bb1.y || position.z < bb1.z || position.x > bb2.x || position.y > bb2.y || position.z > bb2.z) {
			particle.setRemainingIterations(0);
		}
	}

}
