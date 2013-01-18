package de.hda.particles.editor;

import java.util.List;

import de.hda.particles.hud.HUDEditorEntry;
import de.hda.particles.modifier.PositionablePlaneModifier;
import de.hda.particles.modifier.PositionablePointModifier;
import de.hda.particles.modifier.collision.CollisionPlane;

public class CollisionPlaneEditor extends AbstractParticleModifierEditor<CollisionPlane> implements Editor {

	private final static String title = "Collision Plane";

	@Override
	public void setup() {
		editorEntries.add(HUDEditorEntry.create(PositionablePointModifier.POSITION_X, "Position X"));
		editorEntries.add(HUDEditorEntry.create(PositionablePointModifier.POSITION_Y, "Position Y"));
		editorEntries.add(HUDEditorEntry.create(PositionablePointModifier.POSITION_Z, "Position Z"));
		editorEntries.add(HUDEditorEntry.create(PositionablePlaneModifier.NORMAL_X, "Normal X"));
		editorEntries.add(HUDEditorEntry.create(PositionablePlaneModifier.NORMAL_Y, "Normal Y"));
		editorEntries.add(HUDEditorEntry.create(PositionablePlaneModifier.NORMAL_Z, "Normal Z"));
	}
	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(CollisionPlane.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return CollisionPlane.class;
	}

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		return editorEntries;
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
		}

	}

	@Override
	public void setMin(String fieldName) {
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
			subject.getConfiguration().put(PositionablePlaneModifier.NORMAL_Z, normalZ + 0.01);
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
		} else {
			return "N/A";
		}
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
