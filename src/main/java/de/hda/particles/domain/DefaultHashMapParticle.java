package de.hda.particles.domain;

import org.lwjgl.util.vector.Vector3f;

public class DefaultHashMapParticle extends AbstractHashMapParticle implements Particle {

	private static final long serialVersionUID = -7235333253264010644L;

	public DefaultHashMapParticle() {
		super();
	}

	public DefaultHashMapParticle(Vector3f position, Vector3f velocity, Integer renderTypeIndex, Integer lifetime) {
		super(position, velocity, renderTypeIndex, lifetime);
	}

}
