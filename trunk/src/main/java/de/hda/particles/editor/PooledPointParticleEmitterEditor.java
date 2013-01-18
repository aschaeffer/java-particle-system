package de.hda.particles.editor;

import de.hda.particles.emitter.PooledPointParticleEmitter;

public class PooledPointParticleEmitterEditor extends AbstractParticleEmitterEditor<PooledPointParticleEmitter> implements Editor {

	private final static String title = "Point Particle Emitter (Pooled)";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(PooledPointParticleEmitter.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return PooledPointParticleEmitter.class;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
