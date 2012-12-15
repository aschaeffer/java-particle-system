package de.hda.particles.modifier.velocity;

import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.MassSpring;
import de.hda.particles.modifier.AbstractParticleModifier;
import de.hda.particles.modifier.ParticleModifier;

public class MassSpringTransformation extends AbstractParticleModifier implements ParticleModifier {

	private Float springLength;
	private Float springFriction;
	private Float springConstant;
	private Float r;
	
	public MassSpringTransformation() {}

	@Override
	public void update(Particle particle) {
		if (!expectKeys()) return;
		springLength = ((Double) particle.get(MassSpring.SPRING_LENGTH)).floatValue();
		springFriction = ((Double) particle.get(MassSpring.SPRING_FRICTION)).floatValue();
		springConstant = ((Double) particle.get(MassSpring.SPRING_CONSTANT)).floatValue();
		@SuppressWarnings("unchecked")
		ListIterator<Particle> iterator = ((List<Particle>) particle.get(MassSpring.SPRING_CONNECTED_PARTICLES)).listIterator();
		while (iterator.hasNext()) {
			Particle c = iterator.next();
			Vector3f springVector = new Vector3f();
			Vector3f.sub(particle.getPosition(), c.getPosition(), springVector);
			r = springVector.length();
			Vector3f force = new Vector3f(); // force initially has a zero value
			Vector3f temp = new Vector3f();
			if (r != 0) { //to avoid a division by zero check if r is zero
				temp = new Vector3f(springVector.x / r, springVector.y / r, springVector.z / r);
				Float temp1 = (r - springLength) * (-springConstant);
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
	public Boolean expectKeys() {
		return (configuration.containsKey(MassSpring.SPRING_LENGTH)
			&& configuration.containsKey(MassSpring.SPRING_FRICTION)
			&& configuration.containsKey(MassSpring.SPRING_CONSTANT)
			&& configuration.containsKey(MassSpring.SPRING_CONNECTED_PARTICLES));
	}

}
