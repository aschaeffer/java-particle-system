package de.hda.particles.renderer.impl.particles;

import de.hda.particles.domain.Particle;
import de.hda.particles.renderer.ParticleRenderer;

public class NullParticleRenderer extends AbstractParticleRenderer implements ParticleRenderer {

	public final static String NAME = "NULL";

	public NullParticleRenderer() {}

	@Override
	public void before() {
	}
	
	@Override
	public void after() {
	}

	@Override
	public void render(Particle particle) {
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
