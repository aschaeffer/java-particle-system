package de.hda.particles.modifier;

import org.apache.commons.collections.buffer.CircularFifoBuffer;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.PositionPath;

public class PositionPathBuffering extends AbstractParticleModifier implements ParticleModifier {

	public PositionPathBuffering() {}

	@Override
	public void update(Particle particle) {
		CircularFifoBuffer tracedParticlesBuffer = (CircularFifoBuffer) particle.get(PositionPath.BUFFERED_POSITIONS);
		if (tracedParticlesBuffer == null) return;
		tracedParticlesBuffer.add(particle.getPosition());
	}

}
