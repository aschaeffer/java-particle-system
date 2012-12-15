package de.hda.particles.modifier;

import org.lwjgl.util.vector.Vector3f;

public abstract class AbstractPositionableModifier extends AbstractParticleModifier {

	public final static String POSITION_X = "position_x";
	public final static String POSITION_Y = "position_y";
	public final static String POSITION_Z = "position_z";

	protected Vector3f position = new Vector3f();

	@Override
	public void prepare() {
		position.setX(((Double) this.configuration.get(POSITION_X)).floatValue());
		position.setY(((Double) this.configuration.get(POSITION_Y)).floatValue());
		position.setZ(((Double) this.configuration.get(POSITION_Z)).floatValue());
	}

	@Override
	public Boolean expectKeys() {
		return (configuration.containsKey(POSITION_X)
			&& configuration.containsKey(POSITION_Y)
			&& configuration.containsKey(POSITION_Z));
	}

}
