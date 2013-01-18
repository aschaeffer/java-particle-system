package de.hda.particles.editor;

import de.hda.particles.emitter.PooledSphereParticleEmitter;

public class PooledSphereParticleEmitterEditor extends AbstractParticleEmitterEditor<PooledSphereParticleEmitter> implements Editor {

	private final static String title = "Ring Particle Emitter";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(PooledSphereParticleEmitter.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return PooledSphereParticleEmitter.class;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
