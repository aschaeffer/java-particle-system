package de.hda.particles.domain;

import org.lwjgl.util.vector.Vector3f;

public class StaticParticle extends AbstractStaticParticle implements Particle {

	public StaticParticle(Vector3f position, Vector3f velocity, Integer renderTypeIndex, Integer lifetime) {
		super(position, velocity, renderTypeIndex, lifetime);
	}

}
