package de.hda.particles.editor.impl.emitter;

import de.hda.particles.editor.Editor;
import de.hda.particles.emitter.impl.PooledTetrahedronParticleEmitter;

public class PooledTetrahedronParticleEmitterEditor extends AbstractParticleEmitterEditor<PooledTetrahedronParticleEmitter> implements
		Editor {

	private final static String title = "Tetrahedron Particle Emitter (Pooled)";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(PooledTetrahedronParticleEmitter.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return PooledTetrahedronParticleEmitter.class;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
