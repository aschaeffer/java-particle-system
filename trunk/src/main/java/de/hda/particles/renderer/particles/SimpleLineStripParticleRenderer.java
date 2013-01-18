package de.hda.particles.renderer.particles;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.domain.Particle;

public class SimpleLineStripParticleRenderer extends AbstractParticleRenderer implements ParticleRenderer {

	public final static String NAME = "LineStrip";

	public SimpleLineStripParticleRenderer() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
		glLineWidth(2.0f);
		glColor4f(0.8f, 0.8f, 0.8f, 0.3f);
		glBegin(GL_LINE_STRIP);
	}
	
	@Override
	public void after() {
		glEnd();
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		glVertex3f(particle.getX(), particle.getY(), particle.getZ());
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
