package de.hda.particles.emitter;

import java.util.ArrayList;

import de.hda.particles.domain.Face;
import de.hda.particles.domain.Particle;
import de.hda.particles.features.MassSpring;
import de.hda.particles.features.ParticleFeature;
import de.hda.particles.features.ParticleSize;
import de.hda.particles.modifier.velocity.MassSpringTransformation;

/**
 * This emitter is like the point particle emitter, but it uses
 * the particle pool for emittation of particles. This leads to
 * less memory consumption, less garbage collection and more
 * performance.
 * 
 * @author aschaeffer
 *
 */
public class PooledTetrahedronParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	private final static float TH = new Double(Math.sqrt(2/3.0)).floatValue();
	private final static float TM = new Double(Math.sqrt(1/24.0)).floatValue();
	private final static float EM = new Double(Math.sqrt(1/3.0)).floatValue();
	private final static float SM = new Double(Math.sqrt(1/12.0)).floatValue();

	private float size = ParticleSize.DEFAULT_SIZE_BIRTH.floatValue();
	private float th;
	private float tm;
	private float em;
	private float sm;

	public PooledTetrahedronParticleEmitter() {}
	
	/**
	 * Fetches a particle (new or recylced) from the particle pool,
	 * set default values and constructs particle features.
	 */
	@Override
	public void update() {
		prepare();
		pastIterations++;
		for (Integer i = 0; i < rate; i++) {
			// generate particles
			Particle p1 = generateParticle(
				position.x - (size / 2.0f),
				position.y - tm,
				position.z + sm
			);
			Particle p2 = generateParticle(
				position.x,
				position.y - tm,
				position.z - em
			);
			Particle p3 = generateParticle(
				position.x + (size / 2.0f),
				position.y - tm,
				position.z + sm
			);
			Particle p4 = generateParticle(
				position.x,
				position.y + th - tm,
				position.z
			);
			// connect springs
			connectWithSprings(p1, p2, p3, p4);
			connectWithSprings(p2, p3, p4);
			connectWithSprings(p3, p4);
			// generate a faces
			generateFace(p1, p2, p3);
			generateFace(p1, p2, p4);
			generateFace(p1, p3, p4);
			generateFace(p2, p3, p4);
		}
	}
	
	private void prepare() {
		if (configuration.containsKey(MassSpring.SPRING_LENGTH)) {
			size = ((Double) configuration.get(MassSpring.SPRING_LENGTH)).floatValue();
		} else {
			size = MassSpring.DEFAULT_SPRING_LENGTH.floatValue();
		}
		// Tetraeder-Höhe
        th = TH * size;

        // Höhe des Tetraeder-Mittelpunkts
        tm = TM * size;

        // Strecke Ecke-Mittelpunkt eines Dreiecks
        em = EM * size;

        // Strecke Seite-Mittelpunkt eines Dreiecks
        sm = SM * size;
	}
	
	public Particle generateParticle(float x, float y, float z) {
		Particle particle = particleSystem.getParticlePool().next();
		particle.setPastIterations(0);
		particle.setMass(Particle.DEFAULT_MASS);
		particle.setVisibility(true);
		particle.setIndex(0);
		particle.clear();
		particle.setX(x);
		particle.setY(y);
		particle.setZ(z);
		particle.setVelocity(particleDefaultVelocity);
		particle.setParticleRendererIndex(particleRendererIndex);
		particle.setRemainingIterations(particleLifetime);
		for (ParticleFeature particleFeature: particleSystem.getParticleFeatures()) {
			particleFeature.init(this, particle);
		}
		particleSystem.addParticle(particle);
		return particle;
	}
	
	private void connectWithSprings(Particle p1, Particle p2) {
		ArrayList<Particle> springConnectedParticles = new ArrayList<Particle>();
		springConnectedParticles.add(p2);
		p1.put(MassSpring.SPRING_CONNECTED_PARTICLES, springConnectedParticles);
	}

	private void connectWithSprings(Particle p1, Particle p2, Particle p3) {
		ArrayList<Particle> springConnectedParticles = new ArrayList<Particle>();
		springConnectedParticles.add(p2);
		springConnectedParticles.add(p3);
		p1.put(MassSpring.SPRING_CONNECTED_PARTICLES, springConnectedParticles);
	}

	private void connectWithSprings(Particle p1, Particle p2, Particle p3, Particle p4) {
		ArrayList<Particle> springConnectedParticles = new ArrayList<Particle>();
		springConnectedParticles.add(p2);
		springConnectedParticles.add(p3);
		springConnectedParticles.add(p4);
		p1.put(MassSpring.SPRING_CONNECTED_PARTICLES, springConnectedParticles);
	}

	private Face generateFace(Particle p1, Particle p2, Particle p3) {
		Face face = particleSystem.getFacePool().next();
		face.setFaceRendererIndex(faceRendererIndex);
		face.clear();
		face.add(p1);
		face.add(p2);
		face.add(p3);
		particleSystem.addFace(face);
		return face;
	}

	@Override
	public void addDependencies() {
		particleSystem.addParticleFeature(ParticleSize.class);
		particleSystem.addParticleFeature(MassSpring.class);
		particleSystem.addParticleModifier(MassSpringTransformation.class);
	}

	@Override
	public void setup() {}

	@Override
	public void destroy() {}

}
