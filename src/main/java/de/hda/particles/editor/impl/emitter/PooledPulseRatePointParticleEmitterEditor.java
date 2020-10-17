package de.hda.particles.editor.impl.emitter;

import de.hda.particles.editor.Editor;
import de.hda.particles.emitter.impl.PooledPulseRatePointParticleEmitter;

public class PooledPulseRatePointParticleEmitterEditor extends AbstractParticleEmitterEditor<PooledPulseRatePointParticleEmitter> implements
		Editor {

	private final static String title = "Point Particle Emitter";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(PooledPulseRatePointParticleEmitter.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return PooledPulseRatePointParticleEmitter.class;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
