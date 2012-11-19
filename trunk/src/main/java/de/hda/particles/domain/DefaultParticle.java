package de.hda.particles.domain;

import org.lwjgl.util.vector.Vector3f;

public class DefaultParticle extends AbstractParticle implements Particle {

	public DefaultParticle(Vector3f position, Vector3f velocity, Integer renderTypeIndex, Integer lifetime) {
		super(position, velocity, renderTypeIndex, lifetime);
	}

}
