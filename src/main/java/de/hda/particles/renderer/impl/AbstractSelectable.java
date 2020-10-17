package de.hda.particles.renderer.impl;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public abstract class AbstractSelectable<T> extends AbstractRenderer {

	protected List<T> selected = new ArrayList<T>();

	@Override
	public Boolean isSelectable() {
		return true;
	}

	@Override
	public Boolean select(Vector3f position) {
		selected.clear();
		return false;
	}

	@Override
	public List<? extends Object> select(Vector4f selectionBox) {
		selected.clear();
		return selected;
	}

	@Override
	public void unselect() {
		selected.clear();
	}
	
	@Override
	public List<T> getSelected() {
		return selected;
	}

}
