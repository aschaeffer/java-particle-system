package de.hda.particles.domain;

import org.lwjgl.util.vector.Vector3f;

public class DefaultMassSpringFixedPoint extends AbstractMassSpringFixedPoint implements FixedPoint {

	public DefaultMassSpringFixedPoint() {
		super();
	}

	public DefaultMassSpringFixedPoint(Vector3f position) {
		super(position);
	}

}
