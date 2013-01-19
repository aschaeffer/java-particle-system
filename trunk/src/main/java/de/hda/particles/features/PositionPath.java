package de.hda.particles.features;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.buffer.CircularFifoBuffer;

import de.hda.particles.domain.Particle;
import de.hda.particles.editor.HUDEditorEntry;
import de.hda.particles.emitter.ParticleEmitter;

public class PositionPath extends AbstractParticleFeature implements ParticleFeature {

	public static final String NUMBER_OF_BUFFERED_POSITIONS = "numberOfBufferedPositions";
	public static final String BUFFERED_POSITIONS = "bufferedPositions";
	
	public final static Integer DEFAULT_NUMBER_OF_BUFFERED_POSITIONS = 10;

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
		entries.add(HUDEditorEntry.create(NUMBER_OF_BUFFERED_POSITIONS, "Number of Buffered Positions"));
		return entries;
	}

	@Override
	public void init(final ParticleEmitter emitter, final Particle particle) {
		Integer numberOfPositions = (Integer) emitter.getConfiguration().get(NUMBER_OF_BUFFERED_POSITIONS);
		if (numberOfPositions == null) numberOfPositions = DEFAULT_NUMBER_OF_BUFFERED_POSITIONS;
		particle.put(NUMBER_OF_BUFFERED_POSITIONS, numberOfPositions);
		particle.put(BUFFERED_POSITIONS, new CircularFifoBuffer(numberOfPositions));
	}

	@Override
	public void decrease(final ParticleEmitter emitter, final String fieldName) {
		if (fieldName.equals(NUMBER_OF_BUFFERED_POSITIONS)) {
			decreaseValue(emitter, fieldName, DEFAULT_NUMBER_OF_BUFFERED_POSITIONS, 0);
		}
	}

	@Override
	public void decreaseMin(final ParticleEmitter emitter, final String fieldName) {
		if (fieldName.equals(NUMBER_OF_BUFFERED_POSITIONS)) {
			increaseValue(emitter, fieldName, 0);
		}
	}

	@Override
	public void increase(final ParticleEmitter emitter, final String fieldName) {
		if (fieldName.equals(NUMBER_OF_BUFFERED_POSITIONS)) {
			increaseValue(emitter, fieldName, DEFAULT_NUMBER_OF_BUFFERED_POSITIONS);
		}
	}

	@Override
	public void increaseMax(final ParticleEmitter emitter, final String fieldName) {
		if (fieldName.equals(NUMBER_OF_BUFFERED_POSITIONS)) {
			increaseValue(emitter, fieldName, 50);
		}
	}
	
	@Override
	public void setDefault(final ParticleEmitter emitter, final String fieldName) {
		if (fieldName.equals(NUMBER_OF_BUFFERED_POSITIONS)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_NUMBER_OF_BUFFERED_POSITIONS);
		}
	}

	@Override
	public String getValue(final ParticleEmitter emitter, final String fieldName) {
		return getIntegerValueAsString(emitter, fieldName);
	}

	@Override
	public Boolean validFieldName(final String fieldName) {
		return (fieldName.equals(NUMBER_OF_BUFFERED_POSITIONS));
	}

}
