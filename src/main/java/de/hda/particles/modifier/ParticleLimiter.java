package de.hda.particles.modifier;

import de.hda.particles.domain.Particle;

public class ParticleLimiter extends AbstractParticleModifier implements ParticleModifier {

	public final static Integer DEFAULT_MAX_PARTICLES = 200000;

	public final static String MAX_PARTICLES = "maxParticles";

	private Integer maxParticles = DEFAULT_MAX_PARTICLES;
	private Boolean deactivatedState = false;

	public ParticleLimiter() {}

	@Override
	public void prepare() {
		maxParticles = (Integer) configuration.get(MAX_PARTICLES);
		if (maxParticles == null) maxParticles = DEFAULT_MAX_PARTICLES;
		if (particleSystem.getParticles().size() > maxParticles) {
			if (!deactivatedState) {
				if (!particleSystem.areEmittersStopped()) {
					deactivatedState = true;
					particleSystem.toggleEmitters();
				}
			} else {
				if (!particleSystem.areEmittersStopped()) {
					deactivatedState = false;
				}
			}
		} else {
			if (deactivatedState) {
				if (particleSystem.areEmittersStopped()) {
					deactivatedState = false;
					particleSystem.toggleEmitters();
				}
			}
		}
	}

	@Override
	public void update(Particle particle) {}

}
