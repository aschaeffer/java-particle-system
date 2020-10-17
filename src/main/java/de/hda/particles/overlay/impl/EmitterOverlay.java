package de.hda.particles.overlay.impl;

import de.hda.particles.overlay.TextOverlay;
import java.util.ListIterator;

import de.hda.particles.emitter.ParticleEmitter;

public class EmitterOverlay extends AbstractTextOverlay implements TextOverlay {

	@Override
	public void update() {
		if (!visible) return;
		ListIterator<ParticleEmitter> pIterator = scene.getParticleSystem().getParticleEmitters().listIterator(0);
		while (pIterator.hasNext()) {
			ParticleEmitter emitter = pIterator.next();
			if (emitter != null) {
		        render(emitter.getPosition(), "Emitter\nLifetime: " + emitter.getParticleLifetime() + "\nRate: " + emitter.getRate() + "\nParticleRenderer: " + emitter.getParticleRendererIndex(), 300.0f, false);
			}
		}
	}

}
