package de.hda.particles.modifier.impl;

import de.hda.particles.modifier.PositionablePointModifier;
import org.lwjgl.util.vector.Vector3f;

public abstract class AbstractPositionablePointModifier extends AbstractParticleModifier implements
    PositionablePointModifier {

	protected Vector3f position = new Vector3f();

	@Override
	public void prepare() {
		position.setX(((Double) this.configuration.get(POSITION_X)).floatValue());
		position.setY(((Double) this.configuration.get(POSITION_Y)).floatValue());
		position.setZ(((Double) this.configuration.get(POSITION_Z)).floatValue());
	}

	@Override
	public Vector3f getPosition() {
		return new Vector3f(position);
	}

	@Override
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	@Override
	public float getX() {
		return position.x;
	}

	@Override
	public void setX(float x) {
		position.x = x;
	}

	@Override
	public float getY() {
		return position.y;
	}

	@Override
	public void setY(float y) {
		position.y = y;
	}

	@Override
	public float getZ() {
		return position.z;
	}

	@Override
	public void setZ(float z) {
		position.z = z;
	}

	@Override
	public Boolean expectKeys() {
		return (configuration.containsKey(POSITION_X)
			&& configuration.containsKey(POSITION_Y)
			&& configuration.containsKey(POSITION_Z));
	}

}
