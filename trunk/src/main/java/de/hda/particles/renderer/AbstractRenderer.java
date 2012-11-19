package de.hda.particles.renderer;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.scene.Scene;

public abstract class AbstractRenderer implements Renderer {

	protected Scene scene;

	@Override
	public void setup() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public Boolean isFinished() {
		return false;
	}
	
	@Override
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}
	
	@Override
	public void select(Vector3f position) {
	}
	
	@Override
	public Object getSelected() {
		return null;
	}
	
	@Override
	public void move(Vector3f position) {
	}

	@Override
	public void remove(Vector3f position) {
	}

}
