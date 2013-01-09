package de.hda.particles.renderer.types;

import org.lwjgl.opengl.GL11;

import de.hda.particles.features.ParticleColor;

public class BallRenderType extends AbstractBillboardRenderType implements RenderType {

	public final static String NAME = "Ball";

	public BallRenderType() {
		super("PNG", "images/particles/ball2.png", 25.0f, 50.0f, 50.0f, 37.5f, GL11.GL_ONE, GL11.GL_POINTS);
	}

	@Override
	public void addDependencies() {
		scene.getParticleSystem().addParticleFeature(ParticleColor.class);
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
