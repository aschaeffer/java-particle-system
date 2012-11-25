package de.hda.particles.modifier;

import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.MassSpring;

public class MassSpringTransformation extends AbstractParticleModifier implements ParticleModifier {

	public MassSpringTransformation() {}

	@Override
	public void update(Particle particle) {
		Double springLength = (Double) particle.get(MassSpring.SPRING_LENGTH);
		if (springLength == null) return;
		Float springLengthF = springLength.floatValue();
		Double springFriction = (Double) particle.get(MassSpring.SPRING_FRICTION);
		if (springFriction == null) return;
		Float springFrictionF = springFriction.floatValue();
		Double springConstant = (Double) particle.get(MassSpring.SPRING_CONSTANT);
		if (springConstant == null) return;
		Float springConstantF = springConstant.floatValue();
		@SuppressWarnings("unchecked")
		List<Particle> connectedParticles = (List<Particle>) particle.get(MassSpring.SPRING_CONNECTED_PARTICLES);
		if (connectedParticles == null) return;
		ListIterator<Particle> iterator = connectedParticles.listIterator();
		while (iterator.hasNext()) {
			Particle c = iterator.next();
			Vector3f springVector = new Vector3f();
			Vector3f.sub(particle.getPosition(), c.getPosition(), springVector);
			Float r = springVector.length();
			Vector3f force = new Vector3f(); // force initially has a zero value
			Vector3f temp = new Vector3f();
			if (r != 0) { //to avoid a division by zero check if r is zero
				temp = new Vector3f(springVector.x / r, springVector.y / r, springVector.z / r);
				Float temp1 = (r - springLengthF) * (-springConstantF);
				temp = new Vector3f(temp.x * temp1, temp.y * temp1, temp.z * temp1);
				Vector3f newForce = new Vector3f();
				Vector3f.add(force, temp, newForce);
				force = new Vector3f(newForce);
			}
			temp = new Vector3f();
			Vector3f.sub(particle.getVelocity(), c.getVelocity(), temp);
			temp.negate();
			temp = new Vector3f(temp.x * springFrictionF, temp.y * springFrictionF, temp.z * springFrictionF);
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

}
