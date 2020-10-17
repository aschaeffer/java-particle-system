package de.hda.particles.modifier.impl.velocity;

import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.vector.Vector3f;

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
public class MassSpringTransformation extends AbstractParticleModifier implements ParticleModifier {

	// memory saving
	private Float springLength;
	private Float springFriction;
	private Float springConstant;
	private Float currentSpringLength;
	
	public MassSpringTransformation() {}

	@Override
	public void update(Particle particle) {
		if (!particle.containsKey(MassSpring.SPRING_LENGTH) || !particle.containsKey(MassSpring.SPRING_FRICTION) || !particle.containsKey(MassSpring.SPRING_CONSTANT) || !particle.containsKey(MassSpring.SPRING_CONNECTED_PARTICLES)) return;
		springLength = ((Double) particle.get(MassSpring.SPRING_LENGTH)).floatValue();
		springFriction = ((Double) particle.get(MassSpring.SPRING_FRICTION)).floatValue();
		springConstant = ((Double) particle.get(MassSpring.SPRING_CONSTANT)).floatValue();
		@SuppressWarnings("unchecked")
		ListIterator<Particle> iterator = ((List<Particle>) particle.get(MassSpring.SPRING_CONNECTED_PARTICLES)).listIterator();
		while (iterator.hasNext()) {
			Particle c = iterator.next();
			Vector3f springVector = new Vector3f();
			Vector3f.sub(particle.getPosition(), c.getPosition(), springVector);
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
			temp = new Vector3f();
			Vector3f.sub(particle.getVelocity(), c.getVelocity(), temp);
			temp.negate();
			temp = new Vector3f(temp.x * springFriction, temp.y * springFriction, temp.z * springFriction);
			Vector3f newForce = new Vector3f();
			Vector3f.add(force, temp, newForce);
			force = new Vector3f(newForce);
			Vector3f newVelocity = new Vector3f();
			Vector3f.add(particle.getVelocity(), force, newVelocity);
			particle.setVelocity(newVelocity);
			force.negate();
			newVelocity = new Vector3f();
			Vector3f.add(c.getVelocity(), force, newVelocity);
			c.setVelocity(newVelocity);
		}
	}

	@Override
	public void addDependencies() {
		particleSystem.addParticleFeature(MassSpring.class);
	}

}
