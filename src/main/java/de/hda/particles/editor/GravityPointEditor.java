package de.hda.particles.editor;

import java.util.List;

import de.hda.particles.hud.HUDEditorEntry;
import de.hda.particles.modifier.GravityPoint;

public class GravityPointEditor  extends AbstractParticleModifierEditor<GravityPoint> implements Editor {

	private final static String title = "Gravity Point";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(GravityPoint.class);
	}

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = super.getEditorEntries();
		entries.add(HUDEditorEntry.create(GravityPoint.POINT_X, "Position X"));
		entries.add(HUDEditorEntry.create(GravityPoint.POINT_Y, "Position Y"));
		entries.add(HUDEditorEntry.create(GravityPoint.POINT_Z, "Position Z"));
		entries.add(HUDEditorEntry.create(GravityPoint.GRAVITY, "Gravity"));
		entries.add(HUDEditorEntry.create(GravityPoint.MASS, "Mass"));
		return entries;
	}
	
	@Override
	public void decrease(String fieldName) {
		super.decrease(fieldName);
		if (fieldName.equals(GravityPoint.POINT_X)) {
			Double positionX = (Double) subject.getConfiguration().get(GravityPoint.POINT_X);
			if (positionX == null) positionX = 0.0;
			subject.getConfiguration().put(GravityPoint.POINT_X, positionX - 10.0);
		} else if (fieldName.equals(GravityPoint.POINT_Y)) {
			Double positionY = (Double) subject.getConfiguration().get(GravityPoint.POINT_Y);
			if (positionY == null) positionY = 0.0;
			subject.getConfiguration().put(GravityPoint.POINT_Y, positionY - 10.0);
		} else if (fieldName.equals(GravityPoint.POINT_Z)) {
			Double positionZ = (Double) subject.getConfiguration().get(GravityPoint.POINT_Z);
			if (positionZ == null) positionZ = 0.0;
			subject.getConfiguration().put(GravityPoint.POINT_Z, positionZ - 10.0);
		} else if (fieldName.equals(GravityPoint.GRAVITY)) {
			Double gravity = (Double) subject.getConfiguration().get(GravityPoint.GRAVITY);
			if (gravity == null) gravity = 0.0;
			subject.getConfiguration().put(GravityPoint.GRAVITY, gravity - 0.1);
		} else if (fieldName.equals(GravityPoint.MASS)) {
			Double mass = (Double) subject.getConfiguration().get(GravityPoint.MASS);
			if (mass == null) mass = 0.0;
			subject.getConfiguration().put(GravityPoint.MASS, mass - 50.0);
		}
	}

	@Override
	public void increase(String fieldName) {
		super.decrease(fieldName);
		if (fieldName.equals(GravityPoint.POINT_X)) {
			Double positionX = (Double) subject.getConfiguration().get(GravityPoint.POINT_X);
			if (positionX == null) positionX = 0.0;
			subject.getConfiguration().put(GravityPoint.POINT_X, positionX + 10.0);
		} else if (fieldName.equals(GravityPoint.POINT_Y)) {
			Double positionY = (Double) subject.getConfiguration().get(GravityPoint.POINT_Y);
			if (positionY == null) positionY = 0.0;
			subject.getConfiguration().put(GravityPoint.POINT_Y, positionY + 10.0);
		} else if (fieldName.equals(GravityPoint.POINT_Z)) {
			Double positionZ = (Double) subject.getConfiguration().get(GravityPoint.POINT_Z);
			if (positionZ == null) positionZ = 0.0;
			subject.getConfiguration().put(GravityPoint.POINT_Z, positionZ + 10.0);
		} else if (fieldName.equals(GravityPoint.GRAVITY)) {
			Double gravity = (Double) subject.getConfiguration().get(GravityPoint.GRAVITY);
			if (gravity == null) gravity = 0.0;
			subject.getConfiguration().put(GravityPoint.GRAVITY, gravity + 0.1);
		} else if (fieldName.equals(GravityPoint.MASS)) {
			Double mass = (Double) subject.getConfiguration().get(GravityPoint.MASS);
			if (mass == null) mass = 0.0;
			subject.getConfiguration().put(GravityPoint.MASS, mass + 50.0);
		}
	}

	@Override
	public String getValue(String fieldName) {
		String superValue = super.getValue(fieldName);
		if (! "N/A".equals(superValue)) {
			return superValue;
		} else if (fieldName.equals(GravityPoint.POINT_X)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityPoint.POINT_X));
		} else if (fieldName.equals(GravityPoint.POINT_Y)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityPoint.POINT_Y));
		} else if (fieldName.equals(GravityPoint.POINT_Z)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityPoint.POINT_Z));
		} else if (fieldName.equals(GravityPoint.GRAVITY)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityPoint.GRAVITY));
		} else if (fieldName.equals(GravityPoint.MASS)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityPoint.MASS));
		} else {
			return "N/A";
		}
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
