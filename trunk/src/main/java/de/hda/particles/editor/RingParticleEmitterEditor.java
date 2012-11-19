package de.hda.particles.editor;

import de.hda.particles.emitter.RingParticleEmitter;

public class RingParticleEmitterEditor extends AbstractParticleEmitterEditor<RingParticleEmitter> implements Editor {

	private final static String title = "Ring Particle Emitter";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(RingParticleEmitter.class);
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
