package de.hda.particles.editor;

import java.util.ArrayList;
import java.util.List;

import de.hda.particles.hud.HUDEditorEntry;
import de.hda.particles.modifier.gravity.BlackHole;

public class BlackHoleEditor  extends AbstractParticleModifierEditor<BlackHole> implements Editor {

	private final static String title = "Black Hole";
	
	private final List<HUDEditorEntry> editorEntries = new ArrayList<HUDEditorEntry>();

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(BlackHole.class);
	}

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		editorEntries.clear();
		editorEntries.add(HUDEditorEntry.create(BlackHole.POSITION_X, "Position X"));
		editorEntries.add(HUDEditorEntry.create(BlackHole.POSITION_Y, "Position Y"));
		editorEntries.add(HUDEditorEntry.create(BlackHole.POSITION_Z, "Position Z"));
		editorEntries.add(HUDEditorEntry.create(BlackHole.GRAVITY, "Gravity"));
		editorEntries.add(HUDEditorEntry.create(BlackHole.MASS, "Mass"));
		editorEntries.add(HUDEditorEntry.create(BlackHole.MAX_FORCE, "Max Force"));
		editorEntries.addAll(super.getEditorEntries());
		return editorEntries;
	}
	
	@Override
	public void decrease(String fieldName) {
		super.decrease(fieldName);
		if (fieldName.equals(BlackHole.POSITION_X)) {
			Double positionX = (Double) subject.getConfiguration().get(BlackHole.POSITION_X);
			if (positionX == null) positionX = 0.0;
			subject.getConfiguration().put(BlackHole.POSITION_X, positionX - 10.0);
		} else if (fieldName.equals(BlackHole.POSITION_Y)) {
			Double positionY = (Double) subject.getConfiguration().get(BlackHole.POSITION_Y);
			if (positionY == null) positionY = 0.0;
			subject.getConfiguration().put(BlackHole.POSITION_Y, positionY - 10.0);
		} else if (fieldName.equals(BlackHole.POSITION_Z)) {
			Double positionZ = (Double) subject.getConfiguration().get(BlackHole.POSITION_Z);
			if (positionZ == null) positionZ = 0.0;
			subject.getConfiguration().put(BlackHole.POSITION_Z, positionZ - 10.0);
		} else if (fieldName.equals(BlackHole.GRAVITY)) {
			Double gravity = (Double) subject.getConfiguration().get(BlackHole.GRAVITY);
			if (gravity == null) gravity = BlackHole.DEFAULT_GRAVITY;
			subject.getConfiguration().put(BlackHole.GRAVITY, gravity - 0.1);
		} else if (fieldName.equals(BlackHole.MASS)) {
			Double mass = (Double) subject.getConfiguration().get(BlackHole.MASS);
			if (mass == null) mass = BlackHole.DEFAULT_MASS;
			subject.getConfiguration().put(BlackHole.MASS, mass - 50.0);
		} else if (fieldName.equals(BlackHole.MAX_FORCE)) {
			Double maxForce = (Double) subject.getConfiguration().get(BlackHole.MAX_FORCE);
			if (maxForce == null) maxForce = BlackHole.DEFAULT_MAX_FORCE;
			if (maxForce >= 1.0) {
				subject.getConfiguration().put(BlackHole.MAX_FORCE, maxForce - 1.0);
			} else if (maxForce >= 0.1) {
				subject.getConfiguration().put(BlackHole.MAX_FORCE, maxForce - 0.1);
			} else if (maxForce >= 0.0) {
				subject.getConfiguration().put(BlackHole.MAX_FORCE, maxForce - 0.01);
			}
		}
	}

	@Override
	public void setMin(String fieldName) {
		if (fieldName.equals(BlackHole.GRAVITY)) {
			subject.getConfiguration().put(BlackHole.GRAVITY, 0.1);
		} else if (fieldName.equals(BlackHole.MASS)) {
			subject.getConfiguration().put(BlackHole.MASS, 0);
		} else if (fieldName.equals(BlackHole.MAX_FORCE)) {
			subject.getConfiguration().put(BlackHole.MAX_FORCE, 0.01);
		}
	}

	@Override
	public void increase(String fieldName) {
		super.decrease(fieldName);
		if (fieldName.equals(BlackHole.POSITION_X)) {
			Double positionX = (Double) subject.getConfiguration().get(BlackHole.POSITION_X);
			if (positionX == null) positionX = 0.0;
			subject.getConfiguration().put(BlackHole.POSITION_X, positionX + 10.0);
		} else if (fieldName.equals(BlackHole.POSITION_Y)) {
			Double positionY = (Double) subject.getConfiguration().get(BlackHole.POSITION_Y);
			if (positionY == null) positionY = 0.0;
			subject.getConfiguration().put(BlackHole.POSITION_Y, positionY + 10.0);
		} else if (fieldName.equals(BlackHole.POSITION_Z)) {
			Double positionZ = (Double) subject.getConfiguration().get(BlackHole.POSITION_Z);
			if (positionZ == null) positionZ = 0.0;
			subject.getConfiguration().put(BlackHole.POSITION_Z, positionZ + 10.0);
		} else if (fieldName.equals(BlackHole.GRAVITY)) {
			Double gravity = (Double) subject.getConfiguration().get(BlackHole.GRAVITY);
			if (gravity == null) gravity = BlackHole.DEFAULT_GRAVITY;
			subject.getConfiguration().put(BlackHole.GRAVITY, gravity + 0.1);
		} else if (fieldName.equals(BlackHole.MASS)) {
			Double mass = (Double) subject.getConfiguration().get(BlackHole.MASS);
			if (mass == null) mass = BlackHole.DEFAULT_MASS;
			subject.getConfiguration().put(BlackHole.MASS, mass + 50.0);
		} else if (fieldName.equals(BlackHole.MAX_FORCE)) {
			Double maxForce = (Double) subject.getConfiguration().get(BlackHole.MAX_FORCE);
			if (maxForce == null) maxForce = BlackHole.DEFAULT_MAX_FORCE;
			if (maxForce >= 1.0) {
				subject.getConfiguration().put(BlackHole.MAX_FORCE, maxForce + 1.0);
			} else if (maxForce >= 0.1) {
				subject.getConfiguration().put(BlackHole.MAX_FORCE, maxForce + 0.1);
			} else {
				subject.getConfiguration().put(BlackHole.MAX_FORCE, maxForce + 0.01);
			}
		}
	}

	@Override
	public void setMax(String fieldName) {
		if (fieldName.equals(BlackHole.GRAVITY)) {
			subject.getConfiguration().put(BlackHole.GRAVITY, 100.0);
		} else if (fieldName.equals(BlackHole.MASS)) {
			subject.getConfiguration().put(BlackHole.MASS, 10000.0);
		} else if (fieldName.equals(BlackHole.MAX_FORCE)) {
			subject.getConfiguration().put(BlackHole.MAX_FORCE, 100.0);
		}
	}

	@Override
	public String getValue(String fieldName) {
		String superValue = super.getValue(fieldName);
		if (! "N/A".equals(superValue)) {
			return superValue;
		} else if (fieldName.equals(BlackHole.POSITION_X)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(BlackHole.POSITION_X));
		} else if (fieldName.equals(BlackHole.POSITION_Y)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(BlackHole.POSITION_Y));
		} else if (fieldName.equals(BlackHole.POSITION_Z)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(BlackHole.POSITION_Z));
		} else if (fieldName.equals(BlackHole.GRAVITY)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(BlackHole.GRAVITY));
		} else if (fieldName.equals(BlackHole.MASS)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(BlackHole.MASS));
		} else if (fieldName.equals(BlackHole.MAX_FORCE)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(BlackHole.MAX_FORCE));
		} else {
			return "N/A";
		}
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
