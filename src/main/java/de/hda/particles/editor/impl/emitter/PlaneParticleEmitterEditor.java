package de.hda.particles.editor.impl.emitter;

import de.hda.particles.editor.Editor;
import de.hda.particles.emitter.impl.PlaneParticleEmitter;

public class PlaneParticleEmitterEditor extends AbstractParticleEmitterEditor<PlaneParticleEmitter> implements Editor {

	private final static String title = "Plane Particle Emitter";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(PlaneParticleEmitter.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return PlaneParticleEmitter.class;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
