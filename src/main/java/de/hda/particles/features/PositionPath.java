package de.hda.particles.features;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.buffer.CircularFifoBuffer;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.hud.HUDEditorEntry;

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
	public void init(ParticleEmitter emitter, Particle particle) {
		Integer numberOfPositions = (Integer) emitter.getConfiguration().get(NUMBER_OF_BUFFERED_POSITIONS);
		if (numberOfPositions == null) numberOfPositions = DEFAULT_NUMBER_OF_BUFFERED_POSITIONS;
		particle.put(NUMBER_OF_BUFFERED_POSITIONS, numberOfPositions);
		particle.put(BUFFERED_POSITIONS, new CircularFifoBuffer(numberOfPositions));
	}

	@Override
	public void decrease(ParticleEmitter emitter, String fieldName) {
		if (fieldName.equals(NUMBER_OF_BUFFERED_POSITIONS)) {
			decreaseValue(emitter, fieldName, DEFAULT_NUMBER_OF_BUFFERED_POSITIONS, 0);
		}
	}

	@Override
	public void decreaseMin(ParticleEmitter emitter, String fieldName) {
		if (fieldName.equals(NUMBER_OF_BUFFERED_POSITIONS)) {
			increaseValue(emitter, fieldName, 0);
		}
	}

	@Override
	public void increase(ParticleEmitter emitter, String fieldName) {
		if (fieldName.equals(NUMBER_OF_BUFFERED_POSITIONS)) {
			increaseValue(emitter, fieldName, DEFAULT_NUMBER_OF_BUFFERED_POSITIONS);
		}
	}

	@Override
	public void increaseMax(ParticleEmitter emitter, String fieldName) {
		if (fieldName.equals(NUMBER_OF_BUFFERED_POSITIONS)) {
			increaseValue(emitter, fieldName, 50);
		}
	}
	
	@Override
	public void setDefault(ParticleEmitter emitter, String fieldName) {
		if (fieldName.equals(NUMBER_OF_BUFFERED_POSITIONS)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_NUMBER_OF_BUFFERED_POSITIONS);
		}
	}

	@Override
	public String getValue(ParticleEmitter emitter, String fieldName) {
		return getIntegerValueAsString(emitter, fieldName);
	}

	@Override
	public Boolean validFieldName(String fieldName) {
		return (fieldName.equals(NUMBER_OF_BUFFERED_POSITIONS));
	}

}