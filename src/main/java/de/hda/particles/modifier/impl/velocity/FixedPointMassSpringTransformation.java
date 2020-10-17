package de.hda.particles.modifier.impl.velocity;

import java.util.List;
import java.util.ListIterator;

import org.apache.commons.math3.util.Pair;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.FixedPoint;
import de.hda.particles.domain.Particle;
import de.hda.particles.features.impl.MassSpring;
import de.hda.particles.modifier.impl.AbstractParticleModifier;
import de.hda.particles.modifier.ParticleModifier;

/**
 * This modifier updates particles velocity using mass spring.
 * 
 * TODO: performance & memory usage optimizations
 * 
 * @author aschaeffer
 *
 */
public class FixedPointMassSpringTransformation extends AbstractParticleModifier implements ParticleModifier {

	// memory saving
	private Particle connectedParticle;
	private Float springLength;
	private Float springFriction;
	private Float springConstant;
	private Float currentSpringLength;
	
	public FixedPointMassSpringTransformation() {}

	/**
	 * Update all fixed points once per iteration.
	 */
	@Override
	public void prepare() {
		if (configuration.containsKey(MassSpring.SPRING_FRICTION)) {
			springFriction = ((Double) configuration.get(MassSpring.SPRING_FRICTION)).floatValue();
		}
		if (springFriction == null) {
			springFriction = MassSpring.DEFAULT_SPRING_FRICTION.floatValue();
			configuration.put(MassSpring.SPRING_FRICTION, MassSpring.DEFAULT_SPRING_FRICTION);
		}
		if (configuration.containsKey(MassSpring.SPRING_CONSTANT)) {
			springConstant = ((Double) configuration.get(MassSpring.SPRING_CONSTANT)).floatValue();
		}
		if (springConstant == null) {
			springConstant = MassSpring.DEFAULT_SPRING_CONSTANT.floatValue();
			configuration.put(MassSpring.SPRING_CONSTANT, MassSpring.DEFAULT_SPRING_CONSTANT);
		}
		List<FixedPoint> fixedPoints = particleSystem.getFixedPoints();
		ListIterator<FixedPoint> fixedPointsIterator = fixedPoints.listIterator(0);
		while (fixedPointsIterator.hasNext()) {
			FixedPoint fixedPoint = fixedPointsIterator.next();
			List<Pair<Particle, Float>> connectedParticles = fixedPoint.getSprings();
			ListIterator<Pair<Particle, Float>> springsIterator = connectedParticles.listIterator(0);
			while (springsIterator.hasNext()) {
				Pair<Particle, Float> spring = springsIterator.next();
				connectedParticle = spring.getKey();
				springLength = spring.getValue();
				Vector3f springVector = new Vector3f();
				Vector3f.sub(connectedParticle.getPosition(), fixedPoint.getPosition(), springVector);
				currentSpringLength = springVector.length();
				Vector3f force = new Vector3f(); // force initially has a zero value
				Vector3f temp = new Vector3f();
				if (currentSpringLength != 0) { //to avoid a division by zero check if r is zero
					temp = new Vector3f(springVector.x / currentSpringLength, springVector.y / currentSpringLength, springVector.z / currentSpringLength);
					Float temp1 = (currentSpringLength - springLength) * (-springConstant);
					temp = new Vector3f(temp.x * temp1, temp.y * temp1, temp.z * temp1);
					Vector3f newForce = new Vector3f();
					Vector3f.add(force, temp, newForce);
					force = new Vector3f(newForce);
				}
				temp = new Vector3f(connectedParticle.getVelocity());
				temp.negate();
				temp = new Vector3f(temp.x * springFriction, temp.y * springFriction, temp.z * springFriction);
				Vector3f newForce = new Vector3f();
				Vector3f.add(force, temp, newForce);
				force = new Vector3f(newForce);
				Vector3f newVelocity = new Vector3f();
				Vector3f.add(connectedParticle.getVelocity(), force, newVelocity);
				connectedParticle.setVelocity(newVelocity);
			}
		}
	}

	@Override
	public void update(Particle particle) {
	}

	@Override
	public void addDependencies() {
		particleSystem.addParticleFeature(MassSpring.class);
	}

}
