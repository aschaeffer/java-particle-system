package de.hda.particles.features;

import java.util.ArrayList;
import java.util.List;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.hud.HUDEditorEntry;

public class TubeSegments extends AbstractParticleFeature implements ParticleFeature {

	public static final String NUMBER_OF_SEGMENTS = "numberOfSegments";
	public static final String SEGMENTS_TO_DRAW = "segmentsToDraw";

	public final static Integer DEFAULT_NUMBER_OF_SEGMENTS = 16;
	public final static Integer DEFAULT_SEGMENTS_TO_DRAW = 16;

	public final static Integer MIN_NUMBER_OF_SEGMENTS = 3;
	public final static Integer MAX_NUMBER_OF_SEGMENTS = 64;

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
		entries.add(HUDEditorEntry.create(NUMBER_OF_SEGMENTS, "Number of Tube Segments"));
		entries.add(HUDEditorEntry.create(SEGMENTS_TO_DRAW, "Number of Segments to Draw"));
		return entries;
	}

	@Override
	public void init(ParticleEmitter emitter, Particle particle) {
		particle.put(NUMBER_OF_SEGMENTS, emitter.getConfiguration().get(NUMBER_OF_SEGMENTS));
		particle.put(SEGMENTS_TO_DRAW, emitter.getConfiguration().get(SEGMENTS_TO_DRAW));
	}

	@Override
	public void decrease(ParticleEmitter emitter, String fieldName) {
		if (!validFieldName(fieldName)) return;
		Integer value = (Integer) emitter.getConfiguration().get(fieldName);
		if (value == null) {
			setDefault(emitter, fieldName);
		} else if (value > MIN_NUMBER_OF_SEGMENTS) {
			value--;
			emitter.getConfiguration().put(fieldName, value);
		}
	}

	@Override
	public void decreaseMin(ParticleEmitter emitter, String fieldName) {
		if (!validFieldName(fieldName)) return;
		emitter.getConfiguration().put(fieldName, MIN_NUMBER_OF_SEGMENTS);
	}

	@Override
	public void increase(ParticleEmitter emitter, String fieldName) {
		if (!validFieldName(fieldName)) return;
		Integer value = (Integer) emitter.getConfiguration().get(fieldName);
		if (value == null) {
			setDefault(emitter, fieldName);
		} else if (value < MAX_NUMBER_OF_SEGMENTS) {
			value++;
			emitter.getConfiguration().put(fieldName, value);
		}
	}

	@Override
	public void increaseMax(ParticleEmitter emitter, String fieldName) {
		if (!validFieldName(fieldName)) return;
		emitter.getConfiguration().put(fieldName, 100.0);
	}
	
	@Override
	public void setDefault(ParticleEmitter emitter, String fieldName) {
		if (fieldName.equals(NUMBER_OF_SEGMENTS)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_NUMBER_OF_SEGMENTS);
		} else if (fieldName.equals(SEGMENTS_TO_DRAW)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_SEGMENTS_TO_DRAW);
		}
	}

	@Override
	public String getValue(ParticleEmitter emitter, String fieldName) {
		return getIntegerValueAsString(emitter, fieldName);
	}

	@Override
	public Boolean validFieldName(String fieldName) {
		return (fieldName.equals(NUMBER_OF_SEGMENTS) || fieldName.equals(SEGMENTS_TO_DRAW));
	}

}
