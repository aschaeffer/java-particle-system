package de.hda.particles.features;

import java.util.List;

import de.hda.particles.domain.Particle;
import de.hda.particles.editor.HUDEditorEntry;
import de.hda.particles.emitter.ParticleEmitter;

public interface ParticleFeature {

	public List<HUDEditorEntry> getEditorEntries();
	public void init(final ParticleEmitter emitter, final Particle particle);
	public void decrease(final ParticleEmitter emitter, final String fieldName);
	public void decreaseMin(final ParticleEmitter emitter, final String fieldName);
	public void increase(final ParticleEmitter emitter, final String fieldName);
	public void increaseMax(final ParticleEmitter emitter, final String fieldName);
	public void setDefault(final ParticleEmitter emitter, final String fieldName);
	public String getValue(final ParticleEmitter emitter, final String fieldName);
	public Object getObject(final ParticleEmitter emitter, final String fieldName);
	public Boolean validFieldName(final String fieldName);

}
