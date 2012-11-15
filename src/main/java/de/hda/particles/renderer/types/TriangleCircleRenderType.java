package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;

public class TriangleCircleRenderType extends AbstractRenderType implements RenderType {

	private static Color innerColor = new Color(255, 255, 0, 64);
	private static Color outerColor = new Color(255, 0, 0, 0);
	private static Integer slices = 15;
	private static Float radius = 100.0f;

	public TriangleCircleRenderType() {
	}

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
		glVertex3f(particle.getX(), particle.getY(), particle.getZ());
		float incr = (float) (2 * Math.PI / slices);
		glBegin(GL_TRIANGLE_FAN);
		glColor4f(innerColor.getRed() / 255.0f, innerColor.getGreen() / 255.0f, innerColor.getBlue() / 255.0f, innerColor.getAlpha() / 255.0f);
		glVertex3f(particle.getX(), particle.getY(), particle.getZ());
		glColor4f(outerColor.getRed() / 255.0f, outerColor.getGreen() / 255.0f, outerColor.getBlue() / 255.0f, outerColor.getAlpha() / 255.0f);

		for (int i = 0; i < slices; i++) {
			float angle = incr * i;
			float x = (float) Math.cos(angle) * radius;
			float y = (float) Math.sin(angle) * radius;
			glVertex3f(particle.getX() + x, particle.getY() + 0, particle.getZ() + y);
		}
		glVertex3f(particle.getX() + radius, particle.getY(), particle.getZ());
		glEnd();
	}

}
