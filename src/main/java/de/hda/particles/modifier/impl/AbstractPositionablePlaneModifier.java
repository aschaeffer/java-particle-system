package de.hda.particles.modifier.impl;

import de.hda.particles.modifier.PositionablePlaneModifier;
import org.lwjgl.util.vector.Vector3f;

public abstract class AbstractPositionablePlaneModifier extends AbstractPositionablePointModifier implements
    PositionablePlaneModifier {

	public Vector3f normal = new Vector3f(0.0f, 1.0f, 0.0f);
	public Vector3f normalizedNormal = new Vector3f(0.0f, 1.0f, 0.0f);

	@Override
	public void prepare() {
		super.prepare();
		normal.setX(((Double) this.configuration.get(NORMAL_X)).floatValue());
		normal.setY(((Double) this.configuration.get(NORMAL_Y)).floatValue());
		normal.setZ(((Double) this.configuration.get(NORMAL_Z)).floatValue());
	}

	@Override
	public void setNormal(Vector3f normal) {
		this.normal = normal;
	}

	@Override
	public Boolean expectKeys() {
		return (configuration.containsKey(NORMAL_X)
			&& configuration.containsKey(NORMAL_Y)
			&& configuration.containsKey(NORMAL_Z));
	}

}
