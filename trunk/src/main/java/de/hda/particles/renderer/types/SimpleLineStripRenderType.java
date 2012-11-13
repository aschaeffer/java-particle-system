package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.domain.Particle;

public class SimpleLineStripRenderType extends AbstractRenderType implements RenderType {

	public SimpleLineStripRenderType() {}

	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
		glLineWidth(2.0f);
		glColor4f(0.8f, 0.8f, 0.8f, 0.3f);
		glBegin(GL_LINE_STRIP);
	}
	
	public void after() {
		glEnd();
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		glVertex3f(particle.getX(), particle.getY(), particle.getZ());
	}

}
