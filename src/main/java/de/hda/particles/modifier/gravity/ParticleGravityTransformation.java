package de.hda.particles.modifier.gravity;

import java.util.List;

import de.hda.particles.domain.Particle;
import de.hda.particles.modifier.ParticleModifier;

/**
 * This transformation calculates the gravity forces between each particle.
 * Very expensive O(n^2)
 * 
 * @author aschaeffer
 *
 */
public class ParticleGravityTransformation extends GravityPoint implements ParticleModifier {

	public final static Double DEFAULT_MAX_FORCE = 0.1;

	public ParticleGravityTransformation() {}

	@Override
	public void prepare() {
		List<Particle> particles = particleSystem.getParticles();
		for (Integer target = 0; target < particles.size(); target++) {
			for (Integer source = target + 1; source < particles.size(); source++) {
				Particle sourceParticle = particles.get(source);
				updateParticleVelocity(particles.get(target), sourceParticle.getPosition(), sourceParticle.getMass(), DEFAULT_GRAVITY.floatValue(), DEFAULT_MAX_FORCE.floatValue());
			}
		}
	}

	@Override
	public void update(Particle particle) {
	}

}
