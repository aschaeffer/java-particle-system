package de.hda.particles.modifier.impl;

import de.hda.particles.modifier.ParticleModifier;
import org.apache.commons.collections.buffer.CircularFifoBuffer;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.impl.PositionPath;

public class PositionPathBuffering extends AbstractParticleModifier implements ParticleModifier {

	private CircularFifoBuffer positionPathBuffer;

	public PositionPathBuffering() {}

	@Override
	public void update(Particle particle) {
		positionPathBuffer = (CircularFifoBuffer) particle.get(PositionPath.BUFFERED_POSITIONS);
		if (positionPathBuffer == null) return;
		positionPathBuffer.add(particle.getPosition());
	}

	@Override
	public void addDependencies() {
		particleSystem.addParticleFeature(PositionPath.class);
	}

}
