package de.hda.particles.editor.impl.emitter;

import de.hda.particles.editor.Editor;
import de.hda.particles.emitter.impl.SphereParticleEmitter;

public class SphereParticleEmitterEditor extends AbstractParticleEmitterEditor<SphereParticleEmitter> implements
		Editor {

	private final static String title = "Sphere Particle Emitter";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(SphereParticleEmitter.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return SphereParticleEmitter.class;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
