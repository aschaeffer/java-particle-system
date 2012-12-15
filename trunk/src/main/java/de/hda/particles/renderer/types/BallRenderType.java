package de.hda.particles.renderer.types;

import org.lwjgl.opengl.GL11;

public class BallRenderType extends AbstractBillboardRenderType implements RenderType {

	public BallRenderType() {
		super("PNG", "images/particles/ball2.png", 1.0f, 150.0f, 150.0f, 75.0f, GL11.GL_ONE);
	}

}
