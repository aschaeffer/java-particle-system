package de.hda.particles.features;

import java.util.ArrayList;
import java.util.List;

import de.hda.particles.domain.Particle;
import de.hda.particles.editor.HUDEditorEntry;
import de.hda.particles.emitter.ParticleEmitter;

public class FixedPosition extends AbstractParticleFeature implements ParticleFeature {

	public static final String POSITION_FIXED = "positionFixed";

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
		return entries;
	}

	@Override
	public void init(final ParticleEmitter emitter, final Particle particle) {
		particle.put(POSITION_FIXED, false);
	}

	@Override
	public void decrease(final ParticleEmitter emitter, final String fieldName) {
	}

	@Override
	public void decreaseMin(final ParticleEmitter emitter, final String fieldName) {
	}

	@Override
	public void increase(final ParticleEmitter emitter, final String fieldName) {
	}

	@Override
	public void increaseMax(final ParticleEmitter emitter, final String fieldName) {
	}
	
	@Override
	public void setDefault(final ParticleEmitter emitter, final String fieldName) {
		if (fieldName.equals(POSITION_FIXED)) {
			emitter.getConfiguration().put(fieldName, false);
		}
	}

	@Override
	public String getValue(final ParticleEmitter emitter, final String fieldName) {
		return null;
	}

	@Override
	public Boolean validFieldName(String fieldName) {
		return false;
	}

}
