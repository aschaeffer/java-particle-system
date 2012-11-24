package de.hda.particles.features;

import java.util.List;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.hud.HUDEditorEntry;

public interface ParticleFeature {

	public List<HUDEditorEntry> getEditorEntries();
	public void init(ParticleEmitter emitter, Particle particle);
	public void decrease(ParticleEmitter emitter, String fieldName);
	public void decreaseMin(ParticleEmitter emitter, String fieldName);
	public void increase(ParticleEmitter emitter, String fieldName);
	public void increaseMax(ParticleEmitter emitter, String fieldName);
	public void setDefault(ParticleEmitter emitter, String fieldName);
	public String getValue(ParticleEmitter emitter, String fieldName);
	public Boolean validFieldName(String fieldName);

}
