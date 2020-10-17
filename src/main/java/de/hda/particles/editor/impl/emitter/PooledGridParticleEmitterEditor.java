package de.hda.particles.editor.impl.emitter;

import de.hda.particles.editor.Editor;
import de.hda.particles.emitter.impl.PooledGridParticleEmitter;

public class PooledGridParticleEmitterEditor extends AbstractParticleEmitterEditor<PooledGridParticleEmitter> implements
		Editor {

	private final static String title = "Grid Particle Emitter";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(PooledGridParticleEmitter.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return PooledGridParticleEmitter.class;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
