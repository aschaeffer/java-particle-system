package de.hda.particles.editor.impl.emitter;

import de.hda.particles.editor.Editor;
import de.hda.particles.emitter.impl.StaticPointParticleEmitter;

public class StaticPointParticleEmitterEditor extends AbstractParticleEmitterEditor<StaticPointParticleEmitter> implements
		Editor {

	private final static String title = "Point Particle Emitter (Fast)";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(StaticPointParticleEmitter.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return StaticPointParticleEmitter.class;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
