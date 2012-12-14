package de.hda.particles.features;

import java.util.ArrayList;
import java.util.List;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.hud.HUDEditorEntry;

public class ParticleSize extends AbstractParticleFeature implements ParticleFeature {

	public static final String SIZE_BIRTH = "sizeBirth";
	public static final String SIZE_DEATH = "sizeDeath";
	public static final String CURRENT_SIZE = "currentSize";

	public final static Double DEFAULT_SIZE_BIRTH = 5.0;
	public final static Double DEFAULT_SIZE_DEATH = 5.0;

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
		entries.add(HUDEditorEntry.create(SIZE_BIRTH, "Size Birth"));
		entries.add(HUDEditorEntry.create(SIZE_DEATH, "Size Death"));
		return entries;
	}

	@Override
	public void init(final ParticleEmitter emitter, final Particle particle) {
		particle.put(SIZE_BIRTH, emitter.getConfiguration().get(SIZE_BIRTH));
		particle.put(SIZE_DEATH, emitter.getConfiguration().get(SIZE_DEATH));
		particle.put(CURRENT_SIZE, emitter.getConfiguration().get(SIZE_BIRTH));
	}

	@Override
	public void decrease(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		Double value = (Double) emitter.getConfiguration().get(fieldName);
		if (value == null) {
			setDefault(emitter, fieldName);
		} else if (value > 0.0) {
			value -= 0.1;
			emitter.getConfiguration().put(fieldName, value);
		}
	}

	@Override
	public void decreaseMin(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		emitter.getConfiguration().put(fieldName, 0.0);
	}

	@Override
	public void increase(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		Double value = (Double) emitter.getConfiguration().get(fieldName);
		if (value == null) {
			setDefault(emitter, fieldName);
		} else {
			value += 0.1;
			emitter.getConfiguration().put(fieldName, value);
		}
	}

	@Override
	public void increaseMax(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		emitter.getConfiguration().put(fieldName, 100.0);
	}
	
	@Override
	public void setDefault(final ParticleEmitter emitter, final String fieldName) {
		if (fieldName.equals(SIZE_BIRTH)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_SIZE_BIRTH);
		} else if (fieldName.equals(SIZE_DEATH)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_SIZE_DEATH);
		}
	}

	@Override
	public String getValue(final ParticleEmitter emitter, final String fieldName) {
		return getDoubleValueAsString(emitter, fieldName);
	}

	@Override
	public Boolean validFieldName(String fieldName) {
		return (fieldName.equals(SIZE_BIRTH) || fieldName.equals(SIZE_DEATH));
	}

}
