package de.hda.particles.editor;

import de.hda.particles.emitter.PooledPulseRatePointParticleEmitter;

public class PulseRatePointParticleEmitterEditor extends AbstractParticleEmitterEditor<PooledPulseRatePointParticleEmitter> implements Editor {

	private final static String title = "Point Particle Emitter";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(PooledPulseRatePointParticleEmitter.class);
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
