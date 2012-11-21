package de.hda.particles.modifier;

import static org.lwjgl.opengl.GL11.*;

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
		List<Particle> connectedParticles = (List<Particle>) particle.get(MassSpring.CONNECTED_PARTICLES);
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
				// force += (springVector / r) * (r - springLength) * (-springConstant); //the spring force is added to the force

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

//			//force += -(mass1->vel - mass2->vel) * frictionConstant;                                               //the friction force is added to the force
//                                                //with this addition we obtain the net force of the spring
//               
//                temp = VectorMath.subtract(mass1.getVelocity(), mass2.getVelocity());
//                temp.negate();
//                temp = VectorMath.multiply(temp, frictionConstant);
//                force = VectorMath.add(force, temp);
//
//        mass1.applyForce(force);                                                                                                        //force is applied to mass1
//        force.negate();
//        mass2.applyForce(force);                                                                                                        //the opposite of force is applied to mass2
//			
//			
//			
//			
//			
//			
			
//			Vector3f direction = new Vector3f();
//			Vector3f.sub(c.getPosition(), particle.getPosition(), direction);
////			glVertex3f(particle.getX(), particle.getY(), particle.getZ());
////			glVertex3f(particle.getX() + direction.x, particle.getY() + direction.y, particle.getZ() + direction.z);
//			
//			/**
//			 * 1. Aktuelle Länge zwischen den beiden Partikeln berechnen
//			 * 1.1 distanz sqrt quadrat
//			 * 2. kraft = normale länge der feder - tatsächliche länge
//			 */
//			Float distance = (float) Math.sqrt(direction.x * direction.x + direction.y * direction.y + direction.z * direction.z);
//			
//			Float force = -(particle.getMass()) * mass * gravity / (distance * distance);
//			Vector3f totalForce = new Vector3f(
//				force * (particle.getX() - gravityPointX) / distance,
//				force * (particle.getY() - gravityPointY) / distance,
//				force * (particle.getZ() - gravityPointZ) / distance
//			);
//			Vector3f accelleration = new Vector3f(
//				totalForce.x / particle.getMass(),
//				totalForce.y / particle.getMass(),
//				totalForce.z / particle.getMass()
//			);
//			Vector3f newVelocity = new Vector3f();
//			Vector3f.add(particle.getVelocity(), accelleration, newVelocity);
//			particle.setVelocity(newVelocity);

		}

		
		
//		Vector3f velocity = particle.getVelocity();
//		Vector3f position = particle.getPosition();
//		Vector3f newPosition = new Vector3f();
//		Vector3f.add(particle.getPosition(), particle.getVelocity(), newPosition);
//		particle.setPosition(newPosition);
//		position.x = position.x + velocity.x;
//		position.y = position.y + velocity.y;
//		position.z = position.z + velocity.z;
//		particle.setPosition(position);
	}

}
