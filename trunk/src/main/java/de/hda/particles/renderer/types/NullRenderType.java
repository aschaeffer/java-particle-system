package de.hda.particles.renderer.types;

import de.hda.particles.domain.Particle;

public class NullRenderType extends AbstractRenderType implements RenderType {

	public final static String NAME = "NULL";

	public NullRenderType() {}

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
