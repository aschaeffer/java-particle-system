package de.hda.particles.editor.impl.modifier;

import de.hda.particles.editor.Editor;
import de.hda.particles.editor.impl.HUDEditorEntry;
import java.util.ArrayList;
import java.util.List;

import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.scene.Scene;

public abstract class AbstractParticleModifierEditor<T extends ParticleModifier> implements Editor {

	protected final static String title = "Particle Modifier";
	protected Scene scene;
	protected ParticleModifier subject;
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
	public String getTitle() {
		return title;
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
	public Object getObject(String fieldName) {
		return subject.getConfiguration().get(fieldName);
	}

}
