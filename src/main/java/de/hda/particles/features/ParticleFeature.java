package de.hda.particles.features;

import java.util.List;

import de.hda.particles.domain.Particle;
import de.hda.particles.editor.impl.HUDEditorEntry;
import de.hda.particles.emitter.ParticleEmitter;

public interface ParticleFeature {

	List<HUDEditorEntry> getEditorEntries();
	void init(final ParticleEmitter emitter, final Particle particle);
	void decrease(final ParticleEmitter emitter, final String fieldName);
	void decreaseMin(final ParticleEmitter emitter, final String fieldName);
	void increase(final ParticleEmitter emitter, final String fieldName);
	void increaseMax(final ParticleEmitter emitter, final String fieldName);
	void setDefault(final ParticleEmitter emitter, final String fieldName);
	String getValue(final ParticleEmitter emitter, final String fieldName);
	Object getObject(final ParticleEmitter emitter, final String fieldName);
	Boolean validFieldName(final String fieldName);

}
