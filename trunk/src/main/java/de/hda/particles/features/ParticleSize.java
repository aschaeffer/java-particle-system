package de.hda.particles.features;

import java.util.ArrayList;
import java.util.List;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.hud.HUDEditorEntry;

public class ParticleSize implements ParticleFeature {

	public static final String SIZE_BIRTH = "sizeBirth";
	public static final String SIZE_DEATH = "sizeDeath";
	public static final String CURRENT_SIZE = "currentSize";

	@Override
	public void init(ParticleEmitter emitter, Particle particle) {
		particle.put(SIZE_BIRTH, emitter.getConfiguration().get(SIZE_BIRTH));
		particle.put(SIZE_DEATH, emitter.getConfiguration().get(SIZE_DEATH));
		particle.put(CURRENT_SIZE, emitter.getConfiguration().get(SIZE_BIRTH));
	}

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
		entries.add(HUDEditorEntry.create(SIZE_BIRTH, "Size Birth"));
		entries.add(HUDEditorEntry.create(SIZE_DEATH, "Size Death"));
		return entries;
	}

	@Override
	public void decrease(ParticleEmitter emitter, String fieldName) {
		if (!fieldName.equals(SIZE_BIRTH) && !fieldName.equals(SIZE_DEATH))
			return;
		Double value = (Double) emitter.getConfiguration().get(fieldName);
		if (value == null) value = 0.0;
		if (value > 0.0) value -= 0.1;
		emitter.getConfiguration().put(fieldName, value);
	}

	@Override
	public void increase(ParticleEmitter emitter, String fieldName) {
		if (!fieldName.equals(SIZE_BIRTH) && !fieldName.equals(SIZE_DEATH))
			return;
		Double value = (Double) emitter.getConfiguration().get(fieldName);
		if (value == null) value = 0.0;
		value += 0.1;
		emitter.getConfiguration().put(fieldName, value);
	}

	@Override
	public String getValue(ParticleEmitter emitter, String fieldName) {
		if (!fieldName.equals(SIZE_BIRTH) && !fieldName.equals(SIZE_DEATH)) return null;
		Double value = (Double) emitter.getConfiguration().get(fieldName);
		if (value == null) {
			return null;
		} else {
			return String.format("%.2f", value);
		}
	}

}
