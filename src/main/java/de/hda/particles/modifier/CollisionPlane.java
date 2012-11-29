package de.hda.particles.modifier;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;

/**
 * Calculates if a particle position is in front or behind of the plane.
 * The plane is defined using a point and the plane normal.
 * 
 * @author aschaeffer
 *
 */
public class CollisionPlane extends AbstractParticleModifier implements ParticleModifier {

	public final static String POSITION_X = "position_x";
	public final static String POSITION_Y = "position_y";
	public final static String POSITION_Z = "position_z";

	public final static String NORMAL_X = "normal_x";
	public final static String NORMAL_Y = "normal_y";
	public final static String NORMAL_Z = "normal_z";

	public Vector3f position = new Vector3f();
	public Vector3f normal = new Vector3f(0.0f, 1.0f, 0.0f);

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
	}

	@Override
	public void update(Particle particle) {
		Vector3f velocity = particle.getVelocity();
		Vector3f particleNextPosition = new Vector3f();
		Vector3f.add(particle.getPosition(), velocity, particleNextPosition);
		Vector3f planePositionToParticle = new Vector3f();
		Vector3f.sub(particleNextPosition, position, planePositionToParticle);
		Float x = Vector3f.dot(planePositionToParticle, normal);
		if (x < 0) {
			// e = particle velocity
			// r = e - 2*p (new particle velocity)
			// p = ((e * n) / n^2 ) * n
			// p2 = 2*p = (en / nq) * n * 2
			Float nq = normal.x * normal.x + normal.y * normal.y + normal.z * normal.z;
			if (nq == 0.0f) return;
			Float en = Vector3f.dot(velocity, normal);
			Vector3f p2 = new Vector3f(normal);
			p2.scale((en / nq) * 2);
			Vector3f r = new Vector3f();
			Vector3f.sub(velocity, p2, r);
			particle.setVelocity(r);
		}
	}

}
