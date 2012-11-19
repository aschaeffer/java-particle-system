package de.hda.particles.editor;

import de.hda.particles.emitter.SphereParticleEmitter;

public class SphereParticleEmitterEditor extends AbstractParticleEmitterEditor<SphereParticleEmitter> implements Editor {

	private final static String title = "Sphere Particle Emitter";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(SphereParticleEmitter.class);
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
