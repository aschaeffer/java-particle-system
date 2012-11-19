package de.hda.particles.editor;

import de.hda.particles.emitter.PulseRatePointParticleEmitter;

public class PulseRatePointParticleEmitterEditor extends AbstractParticleEmitterEditor<PulseRatePointParticleEmitter> implements Editor {

	private final static String title = "Point Particle Emitter";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(PulseRatePointParticleEmitter.class);
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
