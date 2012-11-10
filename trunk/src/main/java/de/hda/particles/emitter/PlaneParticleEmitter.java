package de.hda.particles.emitter;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleFeature;

public class PlaneParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	public final static String POSITION2 = "position2";
	public final static String VELOCITY = "startVelocity";
	
	private Vector3f position2;
	private Integer rate = 3;

	private Random random = new Random();

	public void update() {
		pastIterations++;
		position2 = (Vector3f) this.configuration.get(POSITION2);
		Float dx = Math.abs(position.x - position2.x);
		Float dy = Math.abs(position.y - position2.y);
		Float dz = Math.abs(position.z - position2.z);
		// create new particles (emit)
		for (Integer i = 0; i < rate; i++) {
			Float rx = random.nextFloat() * dx;
			Float ry = random.nextFloat() * dy;
			Float rz = random.nextFloat() * dz;
			Vector3f particleStartPosition = new Vector3f(rx, ry, rz);
			// Vector3f particleStartVelocity = new Vector3f();
			// Vector3f.cross(position, position2, particleStartVelocity);
			// particleStartVelocity.scale((Float) this.configuration.get(VELOCITY));
			
			Particle particle = new Particle(particleStartPosition, particleDefaultVelocity, particleLifetime);
			for (ParticleFeature particleFeature: particleSystem.particleFeatures) {
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
		// TODO Auto-generated method stub
		
	}

}
