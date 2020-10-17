package de.hda.particles.editor.impl.modifier;

import de.hda.particles.editor.Editor;
import de.hda.particles.editor.impl.HUDEditorEntry;
import java.util.List;

import de.hda.particles.hud.impl.HUDCommandTypes;
import de.hda.particles.hud.impl.TopMenuHUD;
import de.hda.particles.listener.ModifierLifetimeListener;
import de.hda.particles.modifier.impl.gravity.GravityBase;
import de.hda.particles.modifier.impl.gravity.ParticleGravityTransformation;
import de.hda.particles.modifier.ParticleModifier;

public class ParticleGravityTransformationEditor extends AbstractParticleModifierEditor<ParticleGravityTransformation> implements
		Editor, ModifierLifetimeListener {

	private final static String title = "Particle Gravity Transformation";

	private Boolean active = false;

	@Override
	public void setup() {
		editorEntries.add(HUDEditorEntry.create(GravityBase.GRAVITY, "Gravity"));
		editorEntries.add(HUDEditorEntry.create(GravityBase.MAX_FORCE, "Max Force"));
		scene.getParticleSystem().addModifierListener(this);
	}

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(ParticleGravityTransformation.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return ParticleGravityTransformation.class;
	}

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		return editorEntries;
	}
	
	@Override
	public void decrease(String fieldName) {
		if (fieldName.equals(GravityBase.GRAVITY)) {
			Double value = (Double) subject.getConfiguration().get(GravityBase.GRAVITY);
			if (value == null) value = ParticleGravityTransformation.DEFAULT_GRAVITY;
			subject.getConfiguration().put(GravityBase.GRAVITY, value - 0.1);
		} else if (fieldName.equals(GravityBase.MAX_FORCE)) {
			Double value = (Double) subject.getConfiguration().get(GravityBase.MAX_FORCE);
			if (value == null) value = ParticleGravityTransformation.DEFAULT_MAX_FORCE;
			if (value >= 1.0) {
				subject.getConfiguration().put(GravityBase.MAX_FORCE, value - 1.0);
			} else if (value >= 0.1) {
				subject.getConfiguration().put(GravityBase.MAX_FORCE, value - 0.1);
			} else if (value > 0.0) {
				subject.getConfiguration().put(GravityBase.MAX_FORCE, value - 0.01);
			}
		}

	}

	@Override
	public void setMin(String fieldName) {
		if (fieldName.equals(GravityBase.GRAVITY)) {
			subject.getConfiguration().put(GravityBase.GRAVITY, 0.1);
		} else if (fieldName.equals(GravityBase.MAX_FORCE)) {
			subject.getConfiguration().put(GravityBase.MAX_FORCE, 0.01);
		}
	}

	@Override
	public void increase(String fieldName) {
		if (fieldName.equals(GravityBase.GRAVITY)) {
			Double value = (Double) subject.getConfiguration().get(GravityBase.GRAVITY);
			if (value == null) value = ParticleGravityTransformation.DEFAULT_GRAVITY;
			subject.getConfiguration().put(GravityBase.GRAVITY, value + 0.1);
		} else if (fieldName.equals(GravityBase.MAX_FORCE)) {
			Double value = (Double) subject.getConfiguration().get(GravityBase.MAX_FORCE);
			if (value == null) value = ParticleGravityTransformation.DEFAULT_MAX_FORCE;
			if (value >= 1.0) {
				subject.getConfiguration().put(GravityBase.MAX_FORCE, value + 1.0);
			} else if (value >= 0.1) {
				subject.getConfiguration().put(GravityBase.MAX_FORCE, value + 0.1);
			} else {
				subject.getConfiguration().put(GravityBase.MAX_FORCE, value + 0.01);
			}
		}
	}

	@Override
	public void setMax(String fieldName) {
		if (fieldName.equals(GravityBase.GRAVITY)) {
			subject.getConfiguration().put(GravityBase.GRAVITY, 100.0);
		} else if (fieldName.equals(GravityBase.MAX_FORCE)) {
			subject.getConfiguration().put(GravityBase.MAX_FORCE, 100.0);
		}
	}

	@Override
	public String getValue(String fieldName) {
		String superValue = super.getValue(fieldName);
		if (! "N/A".equals(superValue)) {
			return superValue;
		} else if (fieldName.equals(GravityBase.GRAVITY)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityBase.GRAVITY));
		} else if (fieldName.equals(GravityBase.MAX_FORCE)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityBase.MAX_FORCE));
		} else {
			return "N/A";
		}
	}

	@Override
	public String getTitle() {
		return title;
	}
	
	/**
	 * Let's add this editor to the top menu when ParticleGravityTransformation modifier
	 * is created.
	 */
	@Override
	public void onModifierCreation(ParticleModifier modifier) {
		if (!active && modifier.getClass().equals(ParticleGravityTransformation.class)) {
			scene.getMenuManager().addChildMenuEntryToEntry(TopMenuHUD.TOP_MENU, "ParticleGravityTransformation", "ParticleGravityTransformation", "faenza/dark/mail-send-receive", HUDCommandTypes.EDIT, this, ParticleGravityTransformation.class);
			active = true;
		}
	}

	@Override
	public void onModifierDeath(ParticleModifier modifier) {}

}
