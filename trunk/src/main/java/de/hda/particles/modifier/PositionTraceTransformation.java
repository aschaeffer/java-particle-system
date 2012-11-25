package de.hda.particles.modifier;

import org.apache.commons.collections.buffer.CircularFifoBuffer;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.PositionTrace;

public class PositionTraceTransformation extends AbstractParticleModifier implements ParticleModifier {

	public PositionTraceTransformation() {}

	@Override
	public void update(Particle particle) {
		CircularFifoBuffer tracedParticlesBuffer = (CircularFifoBuffer) particle.get(PositionTrace.POSITIONS);
		if (tracedParticlesBuffer == null) return;
		tracedParticlesBuffer.add(particle.getPosition());
	}

}
