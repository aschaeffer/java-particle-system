package de.hda.particles.editor.impl.renderer;

import de.hda.particles.editor.Editor;
import de.hda.particles.editor.impl.HUDEditorEntry;
import java.util.ArrayList;
import java.util.List;

import de.hda.particles.renderer.ParticleRenderer;
import de.hda.particles.scene.Scene;

public abstract class AbstractParticleRendererEditor<T extends ParticleRenderer> implements Editor {

	protected Scene scene;
	protected T subject;
	protected List<HUDEditorEntry> editorEntries = new ArrayList<HUDEditorEntry>();;

	@Override
	public void setup() {
	}
	
	@Override
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void select(Object subject) {
		this.subject = (T) subject;
	}
	
	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		return editorEntries;
	}

	@Override
	public void decrease(String fieldName) {
	}

	@Override
	public void setMin(String fieldName) {
	}

	@Override
	public void increase(String fieldName) {
	}

	@Override
	public void setMax(String fieldName) {
	}

	@Override
	public String getValue(String fieldName) {
		return "N/A";
	}

	@Override
	public String getTitle() {
		if (subject == null) return "Unknown Particle Renderer";
		return subject.getName() + " Particle Renderer";
	}

}
