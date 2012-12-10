package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.features.ParticleSize;

public class ComplexPointRenderType extends AbstractRenderType implements RenderType {

	public ComplexPointRenderType() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
	}
	
	@Override
	public void after() {
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		Double size = (Double) particle.get(ParticleSize.CURRENT_SIZE);
		if (size != null)
			glPointSize(size.floatValue());
		glBegin(GL_POINTS);
		Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		if (color != null)
			glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
		glVertex3f(particle.getX(), particle.getY(), particle.getZ());
		glEnd();
	}

}