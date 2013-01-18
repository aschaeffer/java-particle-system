package de.hda.particles.editor;

import de.hda.particles.emitter.PointParticleEmitter;

public class PointParticleEmitterEditor extends AbstractParticleEmitterEditor<PointParticleEmitter> implements Editor {

	private final static String title = "Point Particle Emitter";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(PointParticleEmitter.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return PointParticleEmitter.class;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
