package de.hda.particles.editor;

import java.util.ArrayList;
import java.util.List;

import de.hda.particles.hud.HUDEditorEntry;
import de.hda.particles.modifier.collision.CollisionPlane;

public class CollisionPlaneEditor  extends AbstractParticleModifierEditor<CollisionPlane> implements Editor {

	private final static String title = "Collision Plane";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(CollisionPlane.class);
	}

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
		entries.add(HUDEditorEntry.create(CollisionPlane.POSITION_X, "Position X"));
		entries.add(HUDEditorEntry.create(CollisionPlane.POSITION_Y, "Position Y"));
		entries.add(HUDEditorEntry.create(CollisionPlane.POSITION_Z, "Position Z"));
		entries.add(HUDEditorEntry.create(CollisionPlane.NORMAL_X, "Normal X"));
		entries.add(HUDEditorEntry.create(CollisionPlane.NORMAL_Y, "Normal Y"));
		entries.add(HUDEditorEntry.create(CollisionPlane.NORMAL_Z, "Normal Z"));
		entries.addAll(super.getEditorEntries());
		return entries;
	}
	
	@Override
	public void decrease(String fieldName) {
		super.decrease(fieldName);
		if (fieldName.equals(CollisionPlane.POSITION_X)) {
			Double positionX = (Double) subject.getConfiguration().get(CollisionPlane.POSITION_X);
			if (positionX == null) positionX = 0.0;
			subject.getConfiguration().put(CollisionPlane.POSITION_X, positionX - 10.0);
		} else if (fieldName.equals(CollisionPlane.POSITION_Y)) {
			Double positionY = (Double) subject.getConfiguration().get(CollisionPlane.POSITION_Y);
			if (positionY == null) positionY = 0.0;
			subject.getConfiguration().put(CollisionPlane.POSITION_Y, positionY - 10.0);
		} else if (fieldName.equals(CollisionPlane.POSITION_Z)) {
			Double positionZ = (Double) subject.getConfiguration().get(CollisionPlane.POSITION_Z);
			if (positionZ == null) positionZ = 0.0;
			subject.getConfiguration().put(CollisionPlane.POSITION_Z, positionZ - 10.0);
		} else if (fieldName.equals(CollisionPlane.NORMAL_X)) {
			Double normalX = (Double) subject.getConfiguration().get(CollisionPlane.NORMAL_X);
			if (normalX == null) normalX = 0.0;
			subject.getConfiguration().put(CollisionPlane.NORMAL_X, normalX - 0.01);
		} else if (fieldName.equals(CollisionPlane.NORMAL_Y)) {
			Double normalY = (Double) subject.getConfiguration().get(CollisionPlane.NORMAL_Y);
			if (normalY == null) normalY = 0.0;
			subject.getConfiguration().put(CollisionPlane.NORMAL_Y, normalY - 0.01);
		} else if (fieldName.equals(CollisionPlane.NORMAL_Z)) {
			Double normalZ = (Double) subject.getConfiguration().get(CollisionPlane.NORMAL_Z);
			if (normalZ == null) normalZ = 0.0;
			subject.getConfiguration().put(CollisionPlane.NORMAL_Z, normalZ - 0.01);
		}

	}

	@Override
	public void setMin(String fieldName) {
	}

	@Override
	public void increase(String fieldName) {
		super.decrease(fieldName);
		if (fieldName.equals(CollisionPlane.POSITION_X)) {
			Double positionX = (Double) subject.getConfiguration().get(CollisionPlane.POSITION_X);
			if (positionX == null) positionX = 0.0;
			subject.getConfiguration().put(CollisionPlane.POSITION_X, positionX + 10.0);
		} else if (fieldName.equals(CollisionPlane.POSITION_Y)) {
			Double positionY = (Double) subject.getConfiguration().get(CollisionPlane.POSITION_Y);
			if (positionY == null) positionY = 0.0;
			subject.getConfiguration().put(CollisionPlane.POSITION_Y, positionY + 10.0);
		} else if (fieldName.equals(CollisionPlane.POSITION_Z)) {
			Double positionZ = (Double) subject.getConfiguration().get(CollisionPlane.POSITION_Z);
			if (positionZ == null) positionZ = 0.0;
			subject.getConfiguration().put(CollisionPlane.POSITION_Z, positionZ + 10.0);
		} else if (fieldName.equals(CollisionPlane.NORMAL_X)) {
			Double normalX = (Double) subject.getConfiguration().get(CollisionPlane.NORMAL_X);
			if (normalX == null) normalX = 0.0;
			subject.getConfiguration().put(CollisionPlane.NORMAL_X, normalX + 0.01);
		} else if (fieldName.equals(CollisionPlane.NORMAL_Y)) {
			Double normalY = (Double) subject.getConfiguration().get(CollisionPlane.NORMAL_Y);
			if (normalY == null) normalY = 0.0;
			subject.getConfiguration().put(CollisionPlane.NORMAL_Y, normalY + 0.01);
		} else if (fieldName.equals(CollisionPlane.NORMAL_Z)) {
			Double normalZ = (Double) subject.getConfiguration().get(CollisionPlane.NORMAL_Z);
			if (normalZ == null) normalZ = 0.0;
			subject.getConfiguration().put(CollisionPlane.NORMAL_Z, normalZ + 0.01);
		}
	}

	@Override
	public void setMax(String fieldName) {
	}

	@Override
	public String getValue(String fieldName) {
		String superValue = super.getValue(fieldName);
		if (! "N/A".equals(superValue)) {
			return superValue;
		} else if (fieldName.equals(CollisionPlane.POSITION_X)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(CollisionPlane.POSITION_X));
		} else if (fieldName.equals(CollisionPlane.POSITION_Y)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(CollisionPlane.POSITION_Y));
		} else if (fieldName.equals(CollisionPlane.POSITION_Z)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(CollisionPlane.POSITION_Z));
		} else if (fieldName.equals(CollisionPlane.NORMAL_X)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(CollisionPlane.NORMAL_X));
		} else if (fieldName.equals(CollisionPlane.NORMAL_Y)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(CollisionPlane.NORMAL_Y));
		} else if (fieldName.equals(CollisionPlane.NORMAL_Z)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(CollisionPlane.NORMAL_Z));
		} else {
			return "N/A";
		}
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}