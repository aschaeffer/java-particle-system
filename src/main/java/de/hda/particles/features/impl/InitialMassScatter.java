package de.hda.particles.features.impl;

import de.hda.particles.features.ParticleFeature;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hda.particles.domain.Particle;
import de.hda.particles.editor.impl.HUDEditorEntry;
import de.hda.particles.emitter.ParticleEmitter;

/**
 * This particle feature scatters the initial velocity.
 * 
 * @author aschaeffer
 *
 */
public class InitialMassScatter extends AbstractParticleFeature implements ParticleFeature {

	public static final String SCATTER_MIN = "mass_scatter_min";
	public static final String SCATTER_MAX = "mass_scatter_max";

	public static final Double DEFAULT_SCATTER_MIN = 0.1;
	public static final Double DEFAULT_SCATTER_MAX = 0.9;
	
	public static final Double MIN_SCATTER = 0.01;
	public static final Double MAX_SCATTER = 10000.0;

	private final Random random = new Random();

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
		entries.add(HUDEditorEntry.create(SCATTER_MIN, "Mass Scatter Min"));
		entries.add(HUDEditorEntry.create(SCATTER_MAX, "Mass Scatter Max"));
		return entries;
	}

	@Override
	public void init(final ParticleEmitter emitter, final Particle particle) {
		if (!emitter.getConfiguration().containsKey(SCATTER_MIN) || !emitter.getConfiguration().containsKey(SCATTER_MAX)) return;
		Double scatterMin = (Double) emitter.getConfiguration().get(SCATTER_MIN);
		Double scatterMax = (Double) emitter.getConfiguration().get(SCATTER_MAX);
		Double scatterDiff = scatterMax - scatterMin;
		// apply mass changes
		particle.setMass(random.nextFloat() * scatterDiff.floatValue() + scatterMin.floatValue());
	}

	@Override
	public void decrease(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		decreaseValue(emitter, fieldName, DEFAULT_SCATTER_MIN, MIN_SCATTER);
	}

	@Override
	public void decreaseMin(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		emitter.getConfiguration().put(fieldName, MIN_SCATTER);
	}

	@Override
	public void increase(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		increaseValue(emitter, fieldName, DEFAULT_SCATTER_MAX, MAX_SCATTER);
	}

	@Override
	public void increaseMax(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		emitter.getConfiguration().put(fieldName, MAX_SCATTER);
	}
	
	@Override
	public void setDefault(final ParticleEmitter emitter, final String fieldName) {
		if (fieldName.equals(SCATTER_MIN)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_SCATTER_MIN);
		} else if (fieldName.equals(SCATTER_MAX)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_SCATTER_MAX);
		}
	}

	@Override
	public String getValue(final ParticleEmitter emitter, final String fieldName) {
		return getDoubleValueAsString(emitter, fieldName);
	}
	
	@Override
	public Boolean validFieldName(final String fieldName) {
		return (fieldName.equals(SCATTER_MIN) || fieldName.equals(SCATTER_MAX));
	}

}
