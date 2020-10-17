package de.hda.particles.editor.impl.modifier;

import de.hda.particles.editor.Editor;
import de.hda.particles.editor.impl.HUDEditorEntry;
import java.util.List;

import de.hda.particles.modifier.PositionablePointModifier;
import de.hda.particles.modifier.impl.gravity.GravityPulsar;
import de.hda.particles.modifier.impl.gravity.GravityBase;

public class GravityPulsarEditor  extends AbstractParticleModifierEditor<GravityPulsar> implements Editor {

	private final static String title = "Gravity Pulsar";

	@Override
	public void setup() {
		editorEntries.add(HUDEditorEntry.create(PositionablePointModifier.POSITION_X, "Position X"));
		editorEntries.add(HUDEditorEntry.create(PositionablePointModifier.POSITION_Y, "Position Y"));
		editorEntries.add(HUDEditorEntry.create(PositionablePointModifier.POSITION_Z, "Position Z"));
		editorEntries.add(HUDEditorEntry.create(GravityPulsar.MIN_GRAVITY, "Min Gravity"));
		editorEntries.add(HUDEditorEntry.create(GravityPulsar.MAX_GRAVITY, "Max Gravity"));
		editorEntries.add(HUDEditorEntry.create(GravityPulsar.MIN_MASS, "Min Mass"));
		editorEntries.add(HUDEditorEntry.create(GravityPulsar.MAX_MASS, "Max Mass"));
		editorEntries.add(HUDEditorEntry.create(GravityBase.MAX_FORCE, "Max Force"));
	}

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(GravityPulsar.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return GravityPulsar.class;
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
		} else if (fieldName.equals(GravityPulsar.MIN_GRAVITY)) {
			Double minGravity = (Double) subject.getConfiguration().get(GravityPulsar.MIN_GRAVITY);
			if (minGravity == null) minGravity = GravityPulsar.DEFAULT_MIN_GRAVITY;
			subject.getConfiguration().put(GravityPulsar.MIN_GRAVITY, minGravity - 0.1);
		} else if (fieldName.equals(GravityPulsar.MAX_GRAVITY)) {
			Double maxGravity = (Double) subject.getConfiguration().get(GravityPulsar.MAX_GRAVITY);
			if (maxGravity == null) maxGravity = GravityPulsar.DEFAULT_MAX_GRAVITY;
			subject.getConfiguration().put(GravityPulsar.MAX_GRAVITY, maxGravity - 0.1);
		} else if (fieldName.equals(GravityPulsar.MIN_MASS)) {
			Double minMass = (Double) subject.getConfiguration().get(GravityPulsar.MIN_MASS);
			if (minMass == null) minMass = GravityPulsar.DEFAULT_MIN_MASS;
			subject.getConfiguration().put(GravityPulsar.MIN_MASS, minMass - 50.0);
		} else if (fieldName.equals(GravityPulsar.MAX_MASS)) {
			Double maxMass = (Double) subject.getConfiguration().get(GravityPulsar.MAX_MASS);
			if (maxMass == null) maxMass = GravityPulsar.DEFAULT_MAX_MASS;
			subject.getConfiguration().put(GravityPulsar.MAX_MASS, maxMass - 50.0);
		} else if (fieldName.equals(GravityBase.MAX_FORCE)) {
			Double maxForce = (Double) subject.getConfiguration().get(GravityBase.MAX_FORCE);
			if (maxForce == null) maxForce = GravityPulsar.DEFAULT_MAX_FORCE;
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
		if (fieldName.equals(GravityPulsar.MIN_GRAVITY)) {
			subject.getConfiguration().put(GravityPulsar.MIN_GRAVITY, 0.1);
		} else if (fieldName.equals(GravityPulsar.MAX_GRAVITY)) {
			subject.getConfiguration().put(GravityPulsar.MAX_GRAVITY, 1000.0);
		} else if (fieldName.equals(GravityPulsar.MIN_MASS)) {
			subject.getConfiguration().put(GravityPulsar.MIN_MASS, 0.0);
		} else if (fieldName.equals(GravityPulsar.MAX_MASS)) {
			subject.getConfiguration().put(GravityPulsar.MAX_MASS, 50000.0);
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
		} else if (fieldName.equals(GravityPulsar.MIN_GRAVITY)) {
			Double minGravity = (Double) subject.getConfiguration().get(GravityPulsar.MIN_GRAVITY);
			if (minGravity == null) minGravity = GravityPulsar.DEFAULT_MIN_GRAVITY;
			subject.getConfiguration().put(GravityPulsar.MIN_GRAVITY, minGravity + 0.1);
		} else if (fieldName.equals(GravityPulsar.MAX_GRAVITY)) {
			Double maxGravity = (Double) subject.getConfiguration().get(GravityPulsar.MAX_GRAVITY);
			if (maxGravity == null) maxGravity = GravityPulsar.DEFAULT_MAX_GRAVITY;
			subject.getConfiguration().put(GravityPulsar.MAX_GRAVITY, maxGravity + 0.1);
		} else if (fieldName.equals(GravityPulsar.MIN_MASS)) {
			Double minMass = (Double) subject.getConfiguration().get(GravityPulsar.MIN_MASS);
			if (minMass == null) minMass = GravityPulsar.DEFAULT_MIN_MASS;
			subject.getConfiguration().put(GravityPulsar.MIN_MASS, minMass + 50.0);
		} else if (fieldName.equals(GravityPulsar.MAX_MASS)) {
			Double maxMass = (Double) subject.getConfiguration().get(GravityPulsar.MAX_MASS);
			if (maxMass == null) maxMass = GravityPulsar.DEFAULT_MAX_MASS;
			subject.getConfiguration().put(GravityPulsar.MAX_MASS, maxMass + 50.0);
		} else if (fieldName.equals(GravityBase.MAX_FORCE)) {
			Double maxForce = (Double) subject.getConfiguration().get(GravityBase.MAX_FORCE);
			if (maxForce == null) maxForce = GravityPulsar.DEFAULT_MAX_FORCE;
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
		if (fieldName.equals(GravityPulsar.MIN_GRAVITY)) {
			subject.getConfiguration().put(GravityPulsar.MIN_GRAVITY, 1000.0);
		} else if (fieldName.equals(GravityPulsar.MAX_GRAVITY)) {
			subject.getConfiguration().put(GravityPulsar.MAX_GRAVITY, 10000.0);
		} else if (fieldName.equals(GravityPulsar.MIN_MASS)) {
			subject.getConfiguration().put(GravityPulsar.MIN_MASS, 10000.0);
		} else if (fieldName.equals(GravityPulsar.MAX_MASS)) {
			subject.getConfiguration().put(GravityPulsar.MAX_MASS, 50000.0);
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
		} else if (fieldName.equals(GravityPulsar.MIN_GRAVITY)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityPulsar.MIN_GRAVITY));
		} else if (fieldName.equals(GravityPulsar.MAX_GRAVITY)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityPulsar.MAX_GRAVITY));
		} else if (fieldName.equals(GravityPulsar.MIN_MASS)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityPulsar.MIN_MASS));
		} else if (fieldName.equals(GravityPulsar.MAX_MASS)) {
			return String.format("%.2f", (Double) subject.getConfiguration().get(GravityPulsar.MAX_MASS));
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
