package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.features.ParticleSize;

public class ComplexPointRenderType extends AbstractRenderType implements RenderType {

	public ComplexPointRenderType() {}

	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
	}
	
	public void after() {
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		glPointSize((Float) particle.get(ParticleSize.CURRENT_SIZE));
		glBegin(GL_POINTS);
		Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		// glColor4b(color.getRedByte(), color.getGreenByte(), color.getBlueByte(), color.getAlphaByte());
		glColor4f((float) color.getRed() / 255.0f, (float) color.getGreen() / 255.0f, (float) color.getBlue() / 255.0f, (float) color.getAlpha() / 255.0f);
		glVertex3f(particle.getX(), particle.getY(), particle.getZ());
		glEnd();
	}

}
