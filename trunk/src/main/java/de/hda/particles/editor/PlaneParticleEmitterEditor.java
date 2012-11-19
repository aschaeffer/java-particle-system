package de.hda.particles.editor;

import de.hda.particles.emitter.PlaneParticleEmitter;

public class PlaneParticleEmitterEditor extends AbstractParticleEmitterEditor<PlaneParticleEmitter> implements Editor {

	private final static String title = "Plane Particle Emitter";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(PlaneParticleEmitter.class);
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
