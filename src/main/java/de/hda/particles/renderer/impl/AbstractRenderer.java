package de.hda.particles.renderer.impl;

import de.hda.particles.renderer.Renderer;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.UnicodeFont;

import de.hda.particles.scene.Scene;

public abstract class AbstractRenderer implements Renderer {

	protected Scene scene;
	protected Boolean visible = true;
	protected UnicodeFont font;
	
	public AbstractRenderer() {
	}
	
	public AbstractRenderer(Scene scene) {
		this.scene = scene;
	}

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
	public void addDependencies() {
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
	public List<? extends Object> select(Vector4f selectionBox) {
		return null;
	}
	
	@Override
	public void unselect() {
	}
	
	@Override
	public List<? extends Object> getSelected() {
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
