package de.hda.particles.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.features.ParticleFeature;
import de.hda.particles.hud.HUDEditorEntry;

public abstract class AbstractParticleEmitterEditor<T extends ParticleEmitter> implements Editor {

	protected final static String LIFETIME = "lifetime";
	protected final static String RATE = "rate";
	protected final static String RENDER_TYPE_INDEX = "render_type_index";
	protected final static String POSITION_X = "pos_x";
	protected final static String POSITION_Y = "pos_y";
	protected final static String POSITION_Z = "pos_z";
	protected final static String VELOCITY_X = "vel_x";
	protected final static String VELOCITY_Y = "vel_y";
	protected final static String VELOCITY_Z = "vel_z";

	protected final static String title = "Particle Emitter";
	protected ParticleEmitter subject;

	@SuppressWarnings("unchecked")
	@Override
	public void select(Object subject) {
		this.subject = (T) subject;
	}
	
	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
		entries.add(HUDEditorEntry.create(LIFETIME, "Particle Lifetime"));
		entries.add(HUDEditorEntry.create(RATE, "Rate"));
		entries.add(HUDEditorEntry.create(RENDER_TYPE_INDEX, "RenderType"));
		entries.add(HUDEditorEntry.create(POSITION_X, "Position X"));
		entries.add(HUDEditorEntry.create(POSITION_Y, "Position Y"));
		entries.add(HUDEditorEntry.create(POSITION_Z, "Position Z"));
		entries.add(HUDEditorEntry.create(VELOCITY_X, "Velocity X"));
		entries.add(HUDEditorEntry.create(VELOCITY_Y, "Velocity Y"));
		entries.add(HUDEditorEntry.create(VELOCITY_Z, "Velocity Z"));
		ListIterator<ParticleFeature> iterator = subject.getParticleSystem().getParticleFeatures().listIterator(0);
		while (iterator.hasNext()) {
			ParticleFeature feature = iterator.next();
			entries.addAll(feature.getEditorEntries());
		}
		return entries;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public void decrease(String fieldName) {
		if (fieldName.equals(LIFETIME)) {
			Integer value = new Long(subject.getParticleLifetime()).intValue();
			if (value >= 10) {
				value -= 10;
			} else if (value > 0) {
				value -= 1;
			}
			subject.setParticleLifetime(value);
		} else if (fieldName.equals(RATE)) {
			subject.setRate(subject.getRate() - 1);
		} else if (fieldName.equals(RENDER_TYPE_INDEX)) {
			subject.setParticleRenderTypeIndex(subject.getParticleRenderTypeIndex() - 1);
		} else if (fieldName.equals(POSITION_X)) {
			subject.getPosition().x -= 10.0f;
		} else if (fieldName.equals(POSITION_Y)) {
			subject.getPosition().y -= 10.0f;
		} else if (fieldName.equals(POSITION_Z)) {
			subject.getPosition().z -= 10.0f;
		} else if (fieldName.equals(VELOCITY_X)) {
			subject.getParticleDefaultVelocity().x -= 0.1f;
		} else if (fieldName.equals(VELOCITY_Y)) {
			subject.getParticleDefaultVelocity().y -= 0.1f;
		} else if (fieldName.equals(VELOCITY_Z)) {
			subject.getParticleDefaultVelocity().z -= 0.1f;
		} else {
			ListIterator<ParticleFeature> iterator = subject.getParticleSystem().getParticleFeatures().listIterator(0);
			while (iterator.hasNext()) {
				ParticleFeature feature = iterator.next();
				feature.decrease(subject, fieldName);
			}
		}

	}

	@Override
	public void increase(String fieldName) {
		if (fieldName.equals(LIFETIME)) {
			Integer value = new Long(subject.getParticleLifetime()).intValue();
			if (value >= 10) {
				value += 10;
			} else if (value > 0) {
				value += 1;
			}
			subject.setParticleLifetime(value);
		} else if (fieldName.equals(RATE)) {
			subject.setRate(subject.getRate() + 1);
		} else if (fieldName.equals(RENDER_TYPE_INDEX)) {
			subject.setParticleRenderTypeIndex(subject.getParticleRenderTypeIndex() + 1);
		} else if (fieldName.equals(POSITION_X)) {
			subject.getPosition().x += 10.0f;
		} else if (fieldName.equals(POSITION_Y)) {
			subject.getPosition().y += 10.0f;
		} else if (fieldName.equals(POSITION_Z)) {
			subject.getPosition().z += 10.0f;
		} else if (fieldName.equals(VELOCITY_X)) {
			subject.getParticleDefaultVelocity().x += 0.1f;
		} else if (fieldName.equals(VELOCITY_Y)) {
			subject.getParticleDefaultVelocity().y += 0.1f;
		} else if (fieldName.equals(VELOCITY_Z)) {
			subject.getParticleDefaultVelocity().z += 0.1f;
		} else {
			ListIterator<ParticleFeature> iterator = subject.getParticleSystem().getParticleFeatures().listIterator(0);
			while (iterator.hasNext()) {
				ParticleFeature feature = iterator.next();
				feature.increase(subject, fieldName);
			}
		}
	}

	@Override
	public String getValue(String fieldName) {
		if (fieldName.equals(LIFETIME)) {
			return new Long(subject.getParticleLifetime()).toString();
		} else if (fieldName.equals(RATE)) {
			return subject.getRate().toString();
		} else if (fieldName.equals(RENDER_TYPE_INDEX)) {
			return subject.getParticleRenderTypeIndex().toString();
		} else if (fieldName.equals(POSITION_X)) {
			return String.format("%.2f", subject.getPosition().x);
		} else if (fieldName.equals(POSITION_Y)) {
			return String.format("%.2f", subject.getPosition().y);
		} else if (fieldName.equals(POSITION_Z)) {
			return String.format("%.2f", subject.getPosition().z);
		} else if (fieldName.equals(VELOCITY_X)) {
			return String.format("%.2f", subject.getParticleDefaultVelocity().x);
		} else if (fieldName.equals(VELOCITY_Y)) {
			return String.format("%.2f", subject.getParticleDefaultVelocity().y);
		} else if (fieldName.equals(VELOCITY_Z)) {
			return String.format("%.2f", subject.getParticleDefaultVelocity().z);
		} else {
			String value = "N/A";
			ListIterator<ParticleFeature> iterator = subject.getParticleSystem().getParticleFeatures().listIterator(0);
			while (iterator.hasNext()) {
				ParticleFeature feature = iterator.next();
				String value2 = feature.getValue(subject, fieldName);
				if (value2 != null) {
					value = value2;
					break;
				}
			}
			return value;
		}
	}

}
