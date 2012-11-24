package de.hda.particles.renderer;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.scene.Scene;

public abstract class AbstractRenderer implements Renderer {

	protected Scene scene;
	protected Boolean visible = true;

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
	public Boolean select(Vector3f position) {
		return false;
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
	
	@Override
	public void setVisible(Boolean visibility) {
		this.visible = visibility;
	}

	@Override
	public Boolean isVisible() {
		return visible;
	}

}
