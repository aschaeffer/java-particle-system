package de.hda.particles.renderer;

import org.lwjgl.util.vector.Vector3f;

public abstract class AbstractSelectable<T> extends AbstractRenderer {

	protected T selected;

	@Override
	public Boolean isSelectable() {
		return true;
	}

	@Override
	public void select(Vector3f position) {
		selected = null;
	}

	@Override
	public T getSelected() {
		return selected;
	}

}
