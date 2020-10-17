package de.hda.particles.domain.impl.particle;

import de.hda.particles.domain.Particle;
import org.lwjgl.util.vector.Vector3f;

public class DefaultHashMapParticle extends AbstractHashMapParticle implements Particle {

	private static final long serialVersionUID = 3985708698631506038L;

	public DefaultHashMapParticle() {
		super();
	}

	public DefaultHashMapParticle(Vector3f position, Vector3f velocity, Integer particleRendererIndex, Integer lifetime) {
		super(position, velocity, particleRendererIndex, lifetime);
	}

}
