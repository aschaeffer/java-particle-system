package de.hda.particles.modifier;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;

public class ParticleVelocityTransformation extends AbstractParticleModifier implements ParticleModifier {

	public void update(Particle particle) {
		Vector3f velocity = particle.getVelocity();
		Vector3f position = particle.getPosition();
		position.x = position.x + velocity.x;
		position.y = position.y + velocity.y;
		position.z = position.z + velocity.z;
		particle.setPosition(position);
	}

}
