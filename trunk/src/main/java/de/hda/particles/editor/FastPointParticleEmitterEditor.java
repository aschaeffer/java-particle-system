package de.hda.particles.editor;

import de.hda.particles.emitter.FastPointParticleEmitter;

public class FastPointParticleEmitterEditor extends AbstractParticleEmitterEditor<FastPointParticleEmitter> implements Editor {

	private final static String title = "Point Particle Emitter (Fast)";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(FastPointParticleEmitter.class);
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
