package de.hda.particles.editor;

import java.util.ArrayList;
import java.util.List;

import de.hda.particles.hud.HUDEditorEntry;
import de.hda.particles.modifier.gravity.GravityPoint;

public class GravityPointEditor extends AbstractParticleModifierEditor<GravityPoint> implements Editor {

	private final static String title = "Gravity Point";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(GravityPoint.class);
	}

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
		entries.add(HUDEditorEntry.create(GravityPoint.POSITION_X, "Position X"));
		entries.add(HUDEditorEntry.create(GravityPoint.POSITION_Y, "Position Y"));
		entries.add(HUDEditorEntry.create(GravityPoint.POSITION_Z, "Position Z"));
		entries.add(HUDEditorEntry.create(GravityPoint.GRAVITY, "Gravity"));
		entries.add(HUDEditorEntry.create(GravityPoint.MASS, "Mass"));
		entries.add(HUDEditorEntry.create(GravityPoint.MAX_FORCE, "Max Force"));
		entries.addAll(super.getEditorEntries());
		return entries;
	}
	
	@Override
	public void decrease(String fieldName) {
		super.decrease(fieldName);
		if (fieldName.equals(GravityPoint.POSITION_X)) {
			Double positionX = (Double) subject.getConfiguration().get(GravityPoint.POSITION_X);
			if (positionX == null) positionX = 0.0;
			subject.getConfiguration().put(GravityPoint.POSITION_X, positionX - 10.0);
		} else if (fieldName.equals(GravityPoint.POSITION_Y)) {
			Double positionY = (Double) subject.getConfiguration().get(GravityPoint.POSITION_Y);
			if (positionY == null) positionY = 0.0;
			subject.getConfiguration().put(GravityPoint.POSITION_Y, positionY - 10.0);
		} else if (fieldName.equals(GravityPoint.POSITION_Z)) {
			Double positionZ = (Double) subject.getConfiguration().get(GravityPoint.POSITION_Z);
			if (positionZ == null) positionZ = 0.0;
			subject.getConfiguration().put(GravityPoint.POSITION_Z, positionZ - 10.0);
		} else if (fieldName.equals(GravityPoint.GRAVITY)) {
			Double gravity = (Double) subject.getConfiguration().get(GravityPoint.GRAVITY);
			if (gravity == null) gravity = GravityPoint.DEFAULT_GRAVITY;
			subject.getConfiguration().put(GravityPoint.GRAVITY, gravity - 0.1);
		} else if (fieldName.equals(GravityPoint.MASS)) {
			Double mass = (Double) subject.getConfiguration().get(GravityPoint.MASS);
			if (mass == null) mass = GravityPoint.DEFAULT_MASS;
			subject.getConfiguration().put(GravityPoint.MASS, mass - 50.0);
		} else if (fieldName.equals(GravityPoint.MAX_FORCE)) {
			Double maxForce = (Double) subject.getConfiguration().get(GravityPoint.MAX_FORCE);
			if (maxForce == null) maxForce = GravityPoint.DEFAULT_MAX_FORCE;
			if (maxForce >= 1.0) {
				subject.getConfiguration().put(GravityPoint.MAX_FORCE, maxForce - 1.0);
			} else if (maxForce >= 0.1) {
				subject.getConfiguration().put(GravityPoint.MAX_FORCE, maxForce - 0.1);
			} else if (maxForce >= 0.0) {
				subject.getConfiguration().put(GravityPoint.MAX_FORCE, maxForce - 0.01);
			}
		}
	}

	@Override
	public void setMin(String fieldName) {
		if (fieldName.equals(GravityPoint.GRAVITY)) {
			subject.getConfiguration().put(GravityPoint.GRAVITY, 0.1);
		} else if (fieldName.equals(GravityPoint.MASS)) {
			subject.getConfiguration().put(GravityPoint.MASS, 0);
		} else if (fieldName.equals(GravityPoint.MAX_FORCE)) {
			subject.getConfiguration().put(GravityPoint.MAX_FORCE, 0.01);
		}
	}

	@Override
	public void increase(String fieldName) {
		super.decrease(fieldName);
		if (fieldName.equals(GravityPoint.POSITION_X)) {
			Double positionX = (Double) subject.getConfiguration().get(GravityPoint.POSITION_X);
			if (positionX == null) positionX = 0.0;
			subject.getConfiguration().put(GravityPoint.POSITION_X, positionX + 10.0);
		} else if (fieldName.equals(GravityPoint.POSITION_Y)) {
			Double positionY = (Double) subject.getConfiguration().get(GravityPoint.POSITION_Y);
			if (positionY == null) positionY = 0.0;
			subject.getConfiguration().put(GravityPoint.POSITION_Y, positionY + 10.0);
		} else if (fieldName.equals(GravityPoint.POSITION_Z)) {
			Double positionZ = (Double) subject.getConfiguration().get(GravityPoint.POSITION_Z);
			if (positionZ == null) positionZ = 0.0;
			subject.getConfiguration().put(GravityPoint.POSITION_Z, positionZ + 10.0);
		} else if (fieldName.equals(GravityPoint.GRAVITY)) {
			Double gravity = (Double) subject.getConfiguration().get(GravityPoint.GRAVITY);
			if (gravity == null) gravity = GravityPoint.DEFAULT_GRAVITY;
			subject.getConfiguration().put(GravityPoint.GRAVITY, gravity + 0.1);
		} else if (fieldName.equals(GravityPoint.MASS)) {
			Double mass = (Double) subject.getConfiguration().get(GravityPoint.MASS);
			if (mass == null) mass = GravityPoint.DEFAULT_MASS;
			subject.getConfiguration().put(GravityPoint.MASS, mass + 50.0);
		} else if (fieldName.equals(GravityPoint.MAX_FORCE)) {
			Double maxForce = (Double) subject.getConfiguration().get(GravityPoint.MAX_FORCE);
			if (maxForce == null) maxForce = GravityPoint.DEFAULT_MAX_FORCE;
			if (maxForce >= 1.0) {
				subject.getConfiguration().put(GravityPoint.MAX_FORCE, maxForce + 1.0);
			} else if (maxForce >= 0.1) {
				subject.getConfiguration().put(GravityPoint.MAX_FORCE, maxForce + 0.1);
			} else {
				subject.getConfiguration().put(GravityPoint.MAX_FORCE, maxForce + 0.01);
			}
		}
	}

	@Override
	public void setMax(String fieldName) {
		if (fieldName.equals(GravityPoint.GRAVITY)) {
			subject.getConfiguration().put(GravityPoint.GRAVITY, 100.0);
		} else if (fieldName.equals(GravityPoint.MASS)) {
			subject.getConfiguration().put(GravityPoint.MASS, 10000.0);
		} else if (fieldName.equals(GravityPoint.MAX_FORCE)) {
			subject.getConfiguration().put(GravityPoint.MAX_FORCE, 100.0);
		}
	}

	@Override
	public String getValue(String fieldName) {
		String superValue = super.getValue(fieldName);
		if (! "N/A".equals(superValue)) {
			return superValue;
		} else if (fieldName.equals(GravityPoint.POSITION_X)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityPoint.POSITION_X));
		} else if (fieldName.equals(GravityPoint.POSITION_Y)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityPoint.POSITION_Y));
		} else if (fieldName.equals(GravityPoint.POSITION_Z)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityPoint.POSITION_Z));
		} else if (fieldName.equals(GravityPoint.GRAVITY)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityPoint.GRAVITY));
		} else if (fieldName.equals(GravityPoint.MASS)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityPoint.MASS));
		} else if (fieldName.equals(GravityPoint.MAX_FORCE)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityPoint.MAX_FORCE));
		} else {
			return "N/A";
		}
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
