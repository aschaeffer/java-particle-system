package de.hda.particles.features;

import java.util.List;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.hud.HUDEditorEntry;

public interface ParticleFeature {

	public void init(ParticleEmitter emitter, Particle particle);
	public List<HUDEditorEntry> getEditorEntries();
	public void decrease(ParticleEmitter emitter, String fieldName);
	public void increase(ParticleEmitter emitter, String fieldName);
	public String getValue(ParticleEmitter emitter, String fieldName);

}
