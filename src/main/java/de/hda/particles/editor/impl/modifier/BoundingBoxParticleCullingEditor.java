package de.hda.particles.editor.impl.modifier;

import de.hda.particles.editor.Editor;
import de.hda.particles.editor.impl.HUDEditorEntry;
import java.util.List;

import de.hda.particles.hud.impl.HUDCommandTypes;
import de.hda.particles.hud.impl.TopMenuHUD;
import de.hda.particles.listener.ModifierLifetimeListener;
import de.hda.particles.modifier.impl.BoundingBoxParticleCulling;
import de.hda.particles.modifier.ParticleModifier;

public class BoundingBoxParticleCullingEditor extends AbstractParticleModifierEditor<BoundingBoxParticleCulling> implements
		Editor, ModifierLifetimeListener {

	private final static String title = "Particle Culling (Bounding Box)";

	private Boolean active = false;

	@Override
	public void setup() {
		editorEntries.add(HUDEditorEntry.create(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_X, "Min X"));
		editorEntries.add(HUDEditorEntry.create(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_Y, "Min Y"));
		editorEntries.add(HUDEditorEntry.create(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_Z, "Min Z"));
		editorEntries.add(HUDEditorEntry.create(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_X, "Max X"));
		editorEntries.add(HUDEditorEntry.create(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_Y, "Max Y"));
		editorEntries.add(HUDEditorEntry.create(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_Z, "Max Z"));
		scene.getParticleSystem().addModifierListener(this);
	}

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(BoundingBoxParticleCulling.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return BoundingBoxParticleCulling.class;
	}

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		return editorEntries;
	}
	
	@Override
	public void decrease(String fieldName) {
		if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_X) ||
			fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_Y) ||
			fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_Z) ||
			fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_X) ||
			fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_Y) ||
			fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_Z)
		) {
			Double value = (Double) subject.getConfiguration().get(fieldName);
			if (value == null) value = getDefault(fieldName);
			value -= 500.0;
			subject.getConfiguration().put(fieldName, value);
		}
	}

	@Override
	public void setMin(String fieldName) {
		Double value = 0.0;
		if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_X)) {
			value = BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MIN_X / 10.0;
		} else if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_Y)) {
			value = BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MIN_Y / 10.0;
		} else if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_Z)) {
			value = BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MIN_Z / 10.0;
		} else if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_X)) {
			value = BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MAX_X / 10.0;
		} else if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_Y)) {
			value = BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MAX_Y / 10.0;
		} else if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_Z)) {
			value = BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MAX_Z / 10.0;
		}
		subject.getConfiguration().put(fieldName, value);
	}

	@Override
	public void increase(String fieldName) {
		if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_X) ||
			fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_Y) ||
			fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_Z) ||
			fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_X) ||
			fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_Y) ||
			fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_Z)
		) {
			Double value = (Double) subject.getConfiguration().get(fieldName);
			if (value == null) value = getDefault(fieldName);
			value += 500.0;
			subject.getConfiguration().put(fieldName, value);
		}
	}

	@Override
	public void setMax(String fieldName) {
		Double value = 0.0;
		if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_X)) {
			value = BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MIN_X * 10.0;
		} else if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_Y)) {
			value = BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MIN_Y * 10.0;
		} else if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_Z)) {
			value = BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MIN_Z * 10.0;
		} else if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_X)) {
			value = BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MAX_X * 10.0;
		} else if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_Y)) {
			value = BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MAX_Y * 10.0;
		} else if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_Z)) {
			value = BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MAX_Z * 10.0;
		}
		subject.getConfiguration().put(fieldName, value);
	}

	public Double getDefault(final String fieldName) {
		if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_X)) {
			return BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MIN_X;
		} else if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_Y)) {
			return BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MIN_Y;
		} else if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_Z)) {
			return BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MIN_Z;
		} else if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_X)) {
			return BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MAX_X;
		} else if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_Y)) {
			return BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MAX_Y;
		} else if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_Z)) {
			return BoundingBoxParticleCulling.DEFAULT_BOUNDING_BOX_MAX_Z;
		}
		return 10000.0;
	}

	@Override
	public String getValue(String fieldName) {
		if (fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_X) ||
			fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_Y) ||
			fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_Z) ||
			fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_X) ||
			fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_Y) ||
			fieldName.equals(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_Z)
		) {
			Double value = (Double) subject.getConfiguration().get(fieldName);
			if (value == null) value = getDefault(fieldName);
			return String.format("%.2f", value);
		} else {
			return "N/A";
		}
	}

	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * Let's add this editor to the top menu when BoundingBoxParticleCulling modifier
	 * is created.
	 */
	@Override
	public void onModifierCreation(ParticleModifier modifier) {
		if (!active && modifier.getClass().equals(BoundingBoxParticleCulling.class)) {
			scene.getMenuManager().addChildMenuEntryToEntry(TopMenuHUD.TOP_MENU, "BoundingBoxParticleCulling", "BoundingBoxParticleCulling", "faenza/dark/view-fullscreen", HUDCommandTypes.EDIT, this, BoundingBoxParticleCulling.class);
			active = true;
		}
	}

	@Override
	public void onModifierDeath(ParticleModifier modifier) {}

}
