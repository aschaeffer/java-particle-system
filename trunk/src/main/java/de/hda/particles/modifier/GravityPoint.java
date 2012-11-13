package de.hda.particles.modifier;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;

public class GravityPoint extends AbstractParticleModifier implements ParticleModifier {

	public final static String POINT = "point";
	public final static String MASS = "mass";
	public final static String GRAVITY = "gravity";
	public final static String MAX_DISTANCE = "maxDistance";
	
	public final float maxDistance = 100.0f;

	public GravityPoint() {}

	public void update(Particle particle) {
		Vector3f gravityPoint = (Vector3f) this.configuration.get(POINT);
		Float mass = (Float) this.configuration.get(MASS);
		Float gravity = (Float) this.configuration.get(GRAVITY);
		
		Float dx = particle.getX() - gravityPoint.x;
		Float dy = particle.getY() - gravityPoint.y;
		Float dz = particle.getZ() - gravityPoint.z;
		Float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
		// System.out.println("distance: " + distance);
		
		Float force = -(particle.getMass()) * mass * gravity / (distance * distance);
		// System.out.println("force: " + force);
		Vector3f totalForce = new Vector3f(
			force * (particle.getX() - gravityPoint.x) / distance,
			force * (particle.getY() - gravityPoint.y) / distance,
			force * (particle.getZ() - gravityPoint.z) / distance
		);
		// System.out.println("tforce: " + totalForce.x + ":" + totalForce.y + ":" + totalForce.z);
		
		Vector3f accelleration = new Vector3f(
			totalForce.x / particle.getMass(),
			totalForce.y / particle.getMass(),
			totalForce.z / particle.getMass()
		);
		// System.out.println("acc: " + accelleration.x + ":" + accelleration.y + ":" + accelleration.z);
		Vector3f newVelocity = new Vector3f();
		Vector3f.add(particle.getVelocity(), accelleration, newVelocity);
		// System.out.println("nvel: " + newVelocity.x + ":" + newVelocity.y + ":" + newVelocity.z);
		particle.setVelocity(newVelocity);


//		Vector3f impulse1 = new Vector3f();
//		Vector3f.add(particle.getPosition(), particle.getVelocity(), impulse1);
//		impulse1.scale(particle.getMass());
//
//		Vector3f impulse2 = new Vector3f();
//		Vector3f.add(particle.getPosition(), gravityPoint, impulse2);
//		impulse2.scale(particle.getMass() / gravityMass);
//		particle.setVelocity(impulse2);
//
////		Vector3f targetVelocity = new Vector3f();
////		Vector3f.add(impulse1, impulse2, targetVelocity);
////		particle.setVelocity(targetVelocity);

		
//		Vector3f targetPosition = new Vector3f();
//		Vector3f.add(particle.getPosition(), particle.getVelocity(), targetPosition);
//		Vector3f gravityVelocity = new Vector3f();
//		Vector3f.sub(gravityPoint, targetPosition, gravityVelocity);
//		Vector3f targetVelocity = new Vector3f();
//		Vector3f.add(particle.getVelocity(), gravityVelocity, targetVelocity);
//		particle.setVelocity(targetVelocity);

//		Vector3f targetPosition = new Vector3f();
//		Vector3f.add(particle.getPosition(), particle.getVelocity(), targetPosition);
//		Vector3f impulse = new Vector3f();
//		Vector3f.add(targetPosition, gravityPoint, impulse);
//		impulse.scale(particle.getMass() / gravityMass);
//		particle.setVelocity(impulse);
//		
		

//		Vector3f vDistance = new Vector3f();
//		Vector3f.sub(gravityPoint, particle.getPosition(), vDistance);

		
//		Vector3f.add(particle.getPosition(), particle.getVelocity(), targetPosition);

//		Vector3f targetPosition = new Vector3f();
//		Vector3f.cross(particle.getPosition(), particle.getVelocity(), targetPosition);
//		Vector3f impulse = new Vector3f();
//		Vector3f.add(targetPosition, gravityPoint, impulse);
//		impulse.scale(gravity);
//		particle.setVelocity(impulse);
		
//		Float xN = particle.getX() + ((point.getX() - particle.getX()) * gravity);
//		Float yN = particle.getY() + ((point.getY() - particle.getY()) * gravity);
//		Float zN = particle.getZ() + ((point.getZ() - particle.getZ()) * gravity);
//		
//		particle.setPosition(new Vector3f(xN, yN, zN));

		
//		Vector3f res = new Vector3f();
//		Vector3f.sub(point, particle.getPosition(), res);
//		float distance = (float)sqrt( (res.x * res.x) + (res.y * res.y) + (res.z * res.z) );
//
//		if (distance < maxDistance) {
//			Vector3f newVelocity = new Vector3f();
//			Vector3f.add(res, particle.getVelocity(), newVelocity);
//			particle.setVelocity(newVelocity);
//		}
		
		
		// Vector3f newVelocity = particle.getVelocity().
		
		// othogonaler vektor auf fläche berechnen (skalarprodukt oder kreuzprodukt?)
		// partikel entlang des vektors 

	}

}
