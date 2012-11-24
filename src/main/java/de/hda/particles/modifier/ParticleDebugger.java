package de.hda.particles.modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.Particle;

public class ParticleDebugger extends AbstractParticleModifier implements ParticleModifier {

	private final static Logger logger = LoggerFactory.getLogger(ParticleDebugger.class);

	public ParticleDebugger() {}

	@Override
	public void update(Particle particle) {
		logger.debug(particle.toString());
	}

}
