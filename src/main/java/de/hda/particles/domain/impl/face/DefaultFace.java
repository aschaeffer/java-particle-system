package de.hda.particles.domain.impl.face;

import de.hda.particles.domain.Face;
import de.hda.particles.domain.Particle;
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
