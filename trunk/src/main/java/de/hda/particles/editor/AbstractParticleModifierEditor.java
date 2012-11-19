package de.hda.particles.editor;

import java.util.ArrayList;
import java.util.List;

import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.hud.HUDEditorEntry;

public abstract class AbstractParticleModifierEditor<T extends ParticleModifier> implements Editor {

	protected final static String title = "Particle Modifier";
	protected ParticleModifier subject;

	@SuppressWarnings("unchecked")
	@Override
	public void select(Object subject) {
		this.subject = (T) subject;
	}
	
	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
		return entries;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public void decrease(String fieldName) {
	}

	@Override
	public void increase(String fieldName) {
	}

	@Override
	public String getValue(String fieldName) {
		return "N/A";
	}

}
