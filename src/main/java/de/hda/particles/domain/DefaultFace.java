package de.hda.particles.domain;

import java.util.List;

public class DefaultFace extends AbstractFace implements Face {

	private static final long serialVersionUID = 2761783268491853735L;

	public DefaultFace() {
		super();
	}

	public DefaultFace(List<Particle> particles, int faceRendererIndex) {
		super(particles, faceRendererIndex);
	}

}
