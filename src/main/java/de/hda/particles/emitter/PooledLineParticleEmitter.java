package de.hda.particles.emitter;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleFeature;

/**
 * Line Particle Emitter (Pooled)
 * two points / one point + normal + distance
 * damit sowie mit velocity scatter/pulse hätte man (fast) den "wave" emitter
 * 
 * @author aschaeffer
 *
 */
public class PooledLineParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {
	
	// TODO: How to place the second point???
	public final static String POSITION2 = "position2";

	private Vector3f position2 = new Vector3f();
	private Vector3f lineVector = new Vector3f();
	private float px;
	private float py;
	private float pz;
	private float particleDist;

	private final Random random = new Random();

	public PooledLineParticleEmitter() {}

	/**
	 * Fetches a particle (new or recylced) from the particle pool,
	 * set default values and constructs particle features.
	 */
	@Override
	public void update() {
		pastIterations++;
		position2 = (Vector3f) this.configuration.get(POSITION2);
		lineVector = Vector3f.sub(position2, position, lineVector); // from position to position2
		for (Integer i = 0; i < rate; i++) {
			// calc position (choose random length between 0 and distance of both positions)
			particleDist = random.nextFloat() * lineVector.length();
			px = position.x + (position2.x - position.x) * particleDist;
			py = position.y + (position2.y - position.y) * particleDist;
			pz = position.z + (position2.z - position.z) * particleDist;
			// create particle
			Particle particle = particleSystem.getParticlePool().next();
			particle.setPastIterations(0);
			particle.setMass(Particle.DEFAULT_MASS);
			particle.setVisibility(true);
			particle.setIndex(0);
			particle.clear();
			particle.setPosition(new Vector3f(px, py, pz));
			particle.setVelocity(particleDefaultVelocity);
			particle.setRenderTypeIndex(particleRenderTypeIndex);
			particle.setRemainingIterations(particleLifetime);
			for (ParticleFeature particleFeature: particleSystem.getParticleFeatures()) {
				particleFeature.init(this, particle);
			}
			particleSystem.addParticle(particle);
		}
	}

	@Override
	public void setup() {
	}

	@Override
	public void destroy() {
	}

}
