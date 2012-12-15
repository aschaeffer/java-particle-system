package de.hda.particles.editor;

import java.util.ArrayList;
import java.util.List;

import de.hda.particles.hud.HUDEditorEntry;
import de.hda.particles.modifier.PositionablePlaneModifier;
import de.hda.particles.modifier.PositionablePointModifier;
import de.hda.particles.modifier.gravity.GravityBase;
import de.hda.particles.modifier.gravity.GravityPlane;

public class GravityPlaneEditor extends AbstractParticleModifierEditor<GravityPlane> implements Editor {

	private final static String title = "Gravity Plane";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(GravityPlane.class);
	}

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
		entries.add(HUDEditorEntry.create(PositionablePointModifier.POSITION_X, "Position X"));
		entries.add(HUDEditorEntry.create(PositionablePointModifier.POSITION_Y, "Position Y"));
		entries.add(HUDEditorEntry.create(PositionablePointModifier.POSITION_Z, "Position Z"));
		entries.add(HUDEditorEntry.create(PositionablePlaneModifier.NORMAL_X, "Normal X"));
		entries.add(HUDEditorEntry.create(PositionablePlaneModifier.NORMAL_Y, "Normal Y"));
		entries.add(HUDEditorEntry.create(PositionablePlaneModifier.NORMAL_Z, "Normal Z"));
		entries.add(HUDEditorEntry.create(GravityBase.GRAVITY, "Gravity"));
		entries.add(HUDEditorEntry.create(GravityBase.MASS, "Mass"));
		entries.add(HUDEditorEntry.create(GravityBase.MAX_FORCE, "Max Force"));
		entries.addAll(super.getEditorEntries());
		return entries;
	}
	
	@Override
	public void decrease(String fieldName) {
		super.decrease(fieldName);
		if (fieldName.equals(PositionablePointModifier.POSITION_X)) {
			Double positionX = (Double) subject.getConfiguration().get(PositionablePointModifier.POSITION_X);
			if (positionX == null) positionX = 0.0;
			subject.getConfiguration().put(PositionablePointModifier.POSITION_X, positionX - 10.0);
		} else if (fieldName.equals(PositionablePointModifier.POSITION_Y)) {
			Double positionY = (Double) subject.getConfiguration().get(PositionablePointModifier.POSITION_Y);
			if (positionY == null) positionY = 0.0;
			subject.getConfiguration().put(PositionablePointModifier.POSITION_Y, positionY - 10.0);
		} else if (fieldName.equals(PositionablePointModifier.POSITION_Z)) {
			Double positionZ = (Double) subject.getConfiguration().get(PositionablePointModifier.POSITION_Z);
			if (positionZ == null) positionZ = 0.0;
			subject.getConfiguration().put(PositionablePointModifier.POSITION_Z, positionZ - 10.0);
		} else if (fieldName.equals(PositionablePlaneModifier.NORMAL_X)) {
			Double normalX = (Double) subject.getConfiguration().get(PositionablePlaneModifier.NORMAL_X);
			if (normalX == null) normalX = 0.0;
			subject.getConfiguration().put(PositionablePlaneModifier.NORMAL_X, normalX - 0.01);
		} else if (fieldName.equals(PositionablePlaneModifier.NORMAL_Y)) {
			Double normalY = (Double) subject.getConfiguration().get(PositionablePlaneModifier.NORMAL_Y);
			if (normalY == null) normalY = 0.0;
			subject.getConfiguration().put(PositionablePlaneModifier.NORMAL_Y, normalY - 0.01);
		} else if (fieldName.equals(PositionablePlaneModifier.NORMAL_Z)) {
			Double normalZ = (Double) subject.getConfiguration().get(PositionablePlaneModifier.NORMAL_Z);
			if (normalZ == null) normalZ = 0.0;
			subject.getConfiguration().put(PositionablePlaneModifier.NORMAL_Z, normalZ - 0.01);
		} else if (fieldName.equals(GravityBase.GRAVITY)) {
			Double gravity = (Double) subject.getConfiguration().get(GravityBase.GRAVITY);
			if (gravity == null) gravity = GravityPlane.DEFAULT_GRAVITY;
			subject.getConfiguration().put(GravityBase.GRAVITY, gravity - 0.1);
		} else if (fieldName.equals(GravityBase.MASS)) {
			Double mass = (Double) subject.getConfiguration().get(GravityBase.MASS);
			if (mass == null) mass = GravityPlane.DEFAULT_MASS;
			subject.getConfiguration().put(GravityBase.MASS, mass - 50.0);
		} else if (fieldName.equals(GravityBase.MAX_FORCE)) {
			Double maxForce = (Double) subject.getConfiguration().get(GravityBase.MAX_FORCE);
			if (maxForce == null) maxForce = GravityPlane.DEFAULT_MAX_FORCE;
			if (maxForce >= 1.0) {
				subject.getConfiguration().put(GravityBase.MAX_FORCE, maxForce - 1.0);
			} else if (maxForce >= 0.1) {
				subject.getConfiguration().put(GravityBase.MAX_FORCE, maxForce - 0.1);
			} else if (maxForce >= 0.0) {
				subject.getConfiguration().put(GravityBase.MAX_FORCE, maxForce - 0.01);
			}
		}

	}

	@Override
	public void setMin(String fieldName) {
		if (fieldName.equals(GravityBase.GRAVITY)) {
			subject.getConfiguration().put(GravityBase.GRAVITY, 0.1);
		} else if (fieldName.equals(GravityBase.MASS)) {
			subject.getConfiguration().put(GravityBase.MASS, 0);
		} else if (fieldName.equals(GravityBase.MAX_FORCE)) {
			subject.getConfiguration().put(GravityBase.MAX_FORCE, 0.01);
		}
	}

	@Override
	public void increase(String fieldName) {
		super.decrease(fieldName);
		if (fieldName.equals(PositionablePointModifier.POSITION_X)) {
			Double positionX = (Double) subject.getConfiguration().get(PositionablePointModifier.POSITION_X);
			if (positionX == null) positionX = 0.0;
			subject.getConfiguration().put(PositionablePointModifier.POSITION_X, positionX + 10.0);
		} else if (fieldName.equals(PositionablePointModifier.POSITION_Y)) {
			Double positionY = (Double) subject.getConfiguration().get(PositionablePointModifier.POSITION_Y);
			if (positionY == null) positionY = 0.0;
			subject.getConfiguration().put(PositionablePointModifier.POSITION_Y, positionY + 10.0);
		} else if (fieldName.equals(PositionablePointModifier.POSITION_Z)) {
			Double positionZ = (Double) subject.getConfiguration().get(PositionablePointModifier.POSITION_Z);
			if (positionZ == null) positionZ = 0.0;
			subject.getConfiguration().put(PositionablePointModifier.POSITION_Z, positionZ + 10.0);
		} else if (fieldName.equals(PositionablePlaneModifier.NORMAL_X)) {
			Double normalX = (Double) subject.getConfiguration().get(PositionablePlaneModifier.NORMAL_X);
			if (normalX == null) normalX = 0.0;
			subject.getConfiguration().put(PositionablePlaneModifier.NORMAL_X, normalX + 0.01);
		} else if (fieldName.equals(PositionablePlaneModifier.NORMAL_Y)) {
			Double normalY = (Double) subject.getConfiguration().get(PositionablePlaneModifier.NORMAL_Y);
			if (normalY == null) normalY = 0.0;
			subject.getConfiguration().put(PositionablePlaneModifier.NORMAL_Y, normalY + 0.01);
		} else if (fieldName.equals(PositionablePlaneModifier.NORMAL_Z)) {
			Double normalZ = (Double) subject.getConfiguration().get(PositionablePlaneModifier.NORMAL_Z);
			if (normalZ == null) normalZ = 0.0;
			subject.getConfiguration().put(GravityPlane.NORMAL_Z, normalZ + 0.01);
		} else if (fieldName.equals(GravityBase.GRAVITY)) {
			Double gravity = (Double) subject.getConfiguration().get(GravityBase.GRAVITY);
			if (gravity == null) gravity = GravityPlane.DEFAULT_GRAVITY;
			subject.getConfiguration().put(GravityBase.GRAVITY, gravity + 0.1);
		} else if (fieldName.equals(GravityBase.MASS)) {
			Double mass = (Double) subject.getConfiguration().get(GravityBase.MASS);
			if (mass == null) mass = GravityPlane.DEFAULT_MASS;
			subject.getConfiguration().put(GravityBase.MASS, mass + 50.0);
		} else if (fieldName.equals(GravityBase.MAX_FORCE)) {
			Double maxForce = (Double) subject.getConfiguration().get(GravityBase.MAX_FORCE);
			if (maxForce == null) maxForce = GravityPlane.DEFAULT_MAX_FORCE;
			if (maxForce >= 1.0) {
				subject.getConfiguration().put(GravityBase.MAX_FORCE, maxForce + 1.0);
			} else if (maxForce >= 0.1) {
				subject.getConfiguration().put(GravityBase.MAX_FORCE, maxForce + 0.1);
			} else {
				subject.getConfiguration().put(GravityBase.MAX_FORCE, maxForce + 0.01);
			}
		}
	}

	@Override
	public void setMax(String fieldName) {
		if (fieldName.equals(GravityBase.GRAVITY)) {
			subject.getConfiguration().put(GravityBase.GRAVITY, 100.0);
		} else if (fieldName.equals(GravityBase.MASS)) {
			subject.getConfiguration().put(GravityBase.MASS, 10000.0);
		} else if (fieldName.equals(GravityBase.MAX_FORCE)) {
			subject.getConfiguration().put(GravityBase.MAX_FORCE, 100.0);
		}
	}

	@Override
	public String getValue(String fieldName) {
		String superValue = super.getValue(fieldName);
		if (! "N/A".equals(superValue)) {
			return superValue;
		} else if (fieldName.equals(PositionablePointModifier.POSITION_X)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(PositionablePointModifier.POSITION_X));
		} else if (fieldName.equals(PositionablePointModifier.POSITION_Y)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(PositionablePointModifier.POSITION_Y));
		} else if (fieldName.equals(PositionablePointModifier.POSITION_Z)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(PositionablePointModifier.POSITION_Z));
		} else if (fieldName.equals(PositionablePlaneModifier.NORMAL_X)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(PositionablePlaneModifier.NORMAL_X));
		} else if (fieldName.equals(PositionablePlaneModifier.NORMAL_Y)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(PositionablePlaneModifier.NORMAL_Y));
		} else if (fieldName.equals(PositionablePlaneModifier.NORMAL_Z)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(PositionablePlaneModifier.NORMAL_Z));
		} else if (fieldName.equals(GravityBase.GRAVITY)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityBase.GRAVITY));
		} else if (fieldName.equals(GravityBase.MASS)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityBase.MASS));
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
	
}
