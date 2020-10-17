package de.hda.particles.editor.impl.emitter;

import de.hda.particles.editor.Editor;
import de.hda.particles.emitter.impl.PooledFieldParticleEmitter;

public class PooledFieldParticleEmitterEditor extends AbstractParticleEmitterEditor<PooledFieldParticleEmitter> implements
		Editor {

	private final static String title = "Field Particle Emitter";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(PooledFieldParticleEmitter.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return PooledFieldParticleEmitter.class;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
