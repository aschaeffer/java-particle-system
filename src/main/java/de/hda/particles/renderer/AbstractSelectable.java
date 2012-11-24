package de.hda.particles.renderer;

import org.lwjgl.util.vector.Vector3f;

public abstract class AbstractSelectable<T> extends AbstractRenderer {

	protected T selected;

	@Override
	public Boolean isSelectable() {
		return true;
	}

	@Override
	public Boolean select(Vector3f position) {
		selected = null;
		return false;
	}

	@Override
	public T getSelected() {
		return selected;
	}

}
