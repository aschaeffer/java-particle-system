package de.hda.particles.features.impl;

import de.hda.particles.features.ParticleFeature;
import java.util.ArrayList;
import java.util.List;

import de.hda.particles.domain.Particle;
import de.hda.particles.editor.impl.HUDEditorEntry;
import de.hda.particles.emitter.ParticleEmitter;

/**
 * Tube segments is a particle feature which describes a volumeous
 * particle object like rings, tubes, spirals
 * 
 * @author aschaeffer
 *
 */
public class TubeSegments extends AbstractParticleFeature implements ParticleFeature {

	public static final String NUMBER_OF_SEGMENTS = "numberOfSegments";
	public static final String SEGMENTS_TO_DRAW = "segmentsToDraw";
	public static final String CURRENT_START_SEGMENT = "currentStartSegment";
	public static final String SEGMENT_TWIST = "segmentTwist";

	public final static Integer DEFAULT_NUMBER_OF_SEGMENTS = 16;
	public final static Integer DEFAULT_SEGMENTS_TO_DRAW = 16;
	public final static Integer DEFAULT_CURRENT_START_SEGMENT = 0;
	public final static Integer DEFAULT_SEGMENT_TWIST = 2;

	public final static Integer MIN_NUMBER_OF_SEGMENTS = 3;
	public final static Integer MAX_NUMBER_OF_SEGMENTS = 64;
	public final static Integer MIN_SEGMENT_TWIST = 0;

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
		entries.add(HUDEditorEntry.create(NUMBER_OF_SEGMENTS, "Number of Tube Segments"));
		entries.add(HUDEditorEntry.create(SEGMENTS_TO_DRAW, "Number of Segments to Draw"));
		entries.add(HUDEditorEntry.create(SEGMENT_TWIST, "Segment Twist"));
		return entries;
	}

	@Override
	public void init(final ParticleEmitter emitter, final Particle particle) {
		particle.put(NUMBER_OF_SEGMENTS, emitter.getConfiguration().get(NUMBER_OF_SEGMENTS));
		particle.put(SEGMENTS_TO_DRAW, emitter.getConfiguration().get(SEGMENTS_TO_DRAW));
		particle.put(CURRENT_START_SEGMENT, emitter.getConfiguration().get(CURRENT_START_SEGMENT));
		particle.put(SEGMENT_TWIST, emitter.getConfiguration().get(SEGMENT_TWIST));
	}

	@Override
	public void decrease(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		Integer value = (Integer) emitter.getConfiguration().get(fieldName);
		if (value == null) {
			setDefault(emitter, fieldName);
		} else {
			if (fieldName.equals(SEGMENT_TWIST)) {
				if (value > MIN_SEGMENT_TWIST) {
					emitter.getConfiguration().put(fieldName, value-1);
				}
			} else if (value > MIN_NUMBER_OF_SEGMENTS) {
				emitter.getConfiguration().put(fieldName, value-1);
			}
		}
	}

	@Override
	public void decreaseMin(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		if (fieldName.equals(SEGMENT_TWIST)) {
			emitter.getConfiguration().put(fieldName, MIN_SEGMENT_TWIST);
		} else {
			emitter.getConfiguration().put(fieldName, MIN_NUMBER_OF_SEGMENTS);
		}
	}

	@Override
	public void increase(final ParticleEmitter emitter, final String fieldName) {
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
	public void increaseMax(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		emitter.getConfiguration().put(fieldName, MAX_NUMBER_OF_SEGMENTS); // twist is limited too
	}
	
	@Override
	public void setDefault(final ParticleEmitter emitter, final String fieldName) {
		if (fieldName.equals(NUMBER_OF_SEGMENTS)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_NUMBER_OF_SEGMENTS);
		} else if (fieldName.equals(SEGMENTS_TO_DRAW)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_SEGMENTS_TO_DRAW);
		} else if (fieldName.equals(SEGMENT_TWIST)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_SEGMENT_TWIST);
		}
	}

	@Override
	public String getValue(final ParticleEmitter emitter, final String fieldName) {
		return getIntegerValueAsString(emitter, fieldName);
	}

	@Override
	public Boolean validFieldName(final String fieldName) {
		return (fieldName.equals(NUMBER_OF_SEGMENTS) || fieldName.equals(SEGMENTS_TO_DRAW) || fieldName.equals(SEGMENT_TWIST));
	}

}
