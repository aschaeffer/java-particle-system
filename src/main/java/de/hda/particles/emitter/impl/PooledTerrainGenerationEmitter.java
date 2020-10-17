package de.hda.particles.emitter.impl;

import de.hda.particles.emitter.ParticleEmitter;
import java.util.ArrayList;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.FixedPoint;
import de.hda.particles.domain.Particle;
import de.hda.particles.features.impl.MassSpring;
import de.hda.particles.features.ParticleFeature;
import de.hda.particles.modifier.impl.velocity.FixedPointMassSpringTransformation;
import de.hda.particles.modifier.impl.velocity.MassSpringTransformation;

/**
 * soft body object emitter
 * 
 * ein 3d objekt wird geladen
 * aus den vertices werden fixed points erzeugt
 * zu jedem fixed point wird ein partikel erzeugt
 * verbundene vertices des objekts -> verbinden der partikel mit federn
 * problem: die springs haben eine feste länge!
 * 
 * @author aschaeffer
 *
 */
public class PooledTerrainGenerationEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	private final static Float SIZE = 5000.0f;

	private final ArrayList<Particle> terrainParticles = new ArrayList<Particle>();
	private final ArrayList<FixedPoint> terrainFixedPoints = new ArrayList<FixedPoint>();

	private Double springLength;
	private Double springFriction;
	private Double springConstant;

	private final Random random = new Random();
	private final Logger logger = LoggerFactory.getLogger(PooledTerrainGenerationEmitter.class);

	public PooledTerrainGenerationEmitter() {}

	@Override
	@SuppressWarnings("unchecked")
	public void update() {
		pastIterations++;
		prepare();
		if (pastIterations == 1) {
			// generate first 4 particles
			// o1 .. o4
			Particle o1 = generateParticle(position.x - SIZE, position.y, position.z - SIZE);
			Particle o2 = generateParticle(position.x + SIZE, position.y, position.z - SIZE);
			Particle o3 = generateParticle(position.x + SIZE, position.y, position.z + SIZE);
			Particle o4 = generateParticle(position.x - SIZE, position.y, position.z + SIZE);
			ArrayList<Particle> springs = (ArrayList<Particle>) o1.get(MassSpring.SPRING_CONNECTED_PARTICLES);
        	springs.add(o2);
        	springs.add(o4);
        	springs = (ArrayList<Particle>) o2.get(MassSpring.SPRING_CONNECTED_PARTICLES);
        	springs.add(o3);
        	springs = (ArrayList<Particle>) o4.get(MassSpring.SPRING_CONNECTED_PARTICLES);
        	springs.add(o3);
			return;
		}
		Particle a1 = terrainParticles.get(random.nextInt(terrainParticles.size()));
		ArrayList<Particle> springs = (ArrayList<Particle>) a1.get(MassSpring.SPRING_CONNECTED_PARTICLES);
		if (springs.size() < 2) return;
		Particle a2 = springs.get(0);
		Particle a4 = springs.get(1);
		springs = (ArrayList<Particle>) a2.get(MassSpring.SPRING_CONNECTED_PARTICLES);
		Particle a3 = springs.get(0);
		// generate 
		Particle a12 = insertParticle(a1, a2);
		Particle a14 = insertParticle(a1, a4);
		Particle a23 = insertParticle(a2, a3);
		Particle a43 = insertParticle(a4, a3);
		// generate center particle
		Particle a1234 = insertCenterParticle(a12, a14, a23, a43);
		// apply delta
		float delta = random.nextFloat() * 10.0f;
		a1234.setZ(a1234.getZ() + delta);
		// reduce delta
		
//		Particle a12 = generateParticle((a1.getX() + a2.getX()) / 2.0f, (a1.getY() + a2.getY()) / 2.0f, (a1.getZ() + a2.getZ()) / 2.0f);
//		Particle a14 = generateParticle((a1.getX() + a4.getX()) / 2.0f, (a1.getY() + a4.getY()) / 2.0f, (a1.getZ() + a4.getZ()) / 2.0f);
//		// Particle a1234 = generateParticle((a1.getX() + a2.getX()) / 2.0f, (a1.getY() + a4.getY()) / 2.0f, (a1.getZ() + a4.getZ()) / 2.0f);
//		springs.clear();
//		springs.add(a12);
//		springs.add(a14);
//		springs = (ArrayList<Particle>) a12.get(MassSpring.SPRING_CONNECTED_PARTICLES);
//		springs.add(a2);
//		springs = (ArrayList<Particle>) a14.get(MassSpring.SPRING_CONNECTED_PARTICLES);
//		springs.add(a4);
//		
//		// calc heights
//		TerrainIteration i = new TerrainIteration(n1, n3, n7, n9);
//		// create particles
	}

	@Override
	public void setup() {
	}
	
	@Override
	public void destroy() {
	}
	
	public void prepare() {
		springLength = (Double) configuration.get(MassSpring.SPRING_LENGTH);
		if (springLength == null) {
			springLength = MassSpring.DEFAULT_SPRING_LENGTH;
			configuration.put(MassSpring.SPRING_LENGTH, MassSpring.DEFAULT_SPRING_LENGTH);
		}
		springFriction = (Double) configuration.get(MassSpring.SPRING_FRICTION);
		if (springFriction == null) {
			springFriction = MassSpring.DEFAULT_SPRING_FRICTION;
			configuration.put(MassSpring.SPRING_FRICTION, MassSpring.DEFAULT_SPRING_FRICTION);
		}
		springConstant = (Double) configuration.get(MassSpring.SPRING_CONSTANT);
		if (springConstant == null) {
			springConstant = MassSpring.DEFAULT_SPRING_CONSTANT;
			configuration.put(MassSpring.SPRING_CONSTANT, MassSpring.DEFAULT_SPRING_CONSTANT);
		}
	}
	
	private Particle insertParticle(Particle p1, Particle p2) {
		Particle p12 = generateParticle((p1.getX() + p2.getX()) / 2.0f, (p1.getY() + p2.getY()) / 2.0f, (p1.getZ() + p2.getZ()) / 2.0f);
		ArrayList<Particle> springs = (ArrayList<Particle>) p1.get(MassSpring.SPRING_CONNECTED_PARTICLES);
		int index = springs.indexOf(p2);
		springs.set(index, p12);
		springs = (ArrayList<Particle>) p12.get(MassSpring.SPRING_CONNECTED_PARTICLES);
		springs.add(p2);
		return p12;
	}
	
	private Particle insertCenterParticle(Particle p12, Particle p14, Particle p23, Particle p43) {
		// Particle a1234 = generateParticle((a1.getX() + a2.getX()) / 2.0f, (a1.getY() + a4.getY()) / 2.0f, (a1.getZ() + a4.getZ()) / 2.0f);
		return null;
	}
	

	private Particle generateParticle(float x, float y, float z) {
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
		particle.put(MassSpring.SPRING_LENGTH, springLength);
		particle.put(MassSpring.SPRING_FRICTION, springFriction);
		particle.put(MassSpring.SPRING_CONSTANT, springConstant);
		particle.put(MassSpring.SPRING_CONNECTED_PARTICLES, new ArrayList<Particle>());
    	terrainParticles.add(particle);
    	particleSystem.addParticle(particle);
		return particle;
	}
//	
//	private FixedPoint generateFixedPoint(float x, float y, float z) {
//		FixedPoint fixedPoint = new DefaultMassSpringFixedPoint();
//		fixedPoint.setMass(FixedPoint.DEFAULT_MASS);
//		fixedPoint.setX(position.x + x * SCALE);
//		fixedPoint.setY(position.y + y * SCALE);
//		fixedPoint.setZ(position.z + z * SCALE);
//		fixedPoint.setSpringFriction(springFriction.floatValue());
//		fixedPoint.setSpringConstant(springConstant.floatValue());
//		return fixedPoint;
//	}

	private class TerrainIteration {
		public float n1;
		public float n2;
		public float n3;
		public float n4;
		public float n5;
		public float n6;
		public float n7;
		public float n8;
		public float n9;
		
		public TerrainIteration(float n1, float n3, float n7, float n9) {
			n2 = (n1 + n3) / 2.0f;
			n4 = (n1 + n7) / 2.0f;
			n6 = (n3 + n9) / 2.0f;
			n8 = (n7 + n9) / 2.0f;
			n5 = (n1 + n3 + n7 + n9) / 4.0f + random.nextFloat();
		}
	}
	@Override
	public void addDependencies() {
		particleSystem.addParticleFeature(MassSpring.class);
		particleSystem.addParticleModifier(MassSpringTransformation.class);
		particleSystem.addParticleModifier(FixedPointMassSpringTransformation.class);
	}
    
}
