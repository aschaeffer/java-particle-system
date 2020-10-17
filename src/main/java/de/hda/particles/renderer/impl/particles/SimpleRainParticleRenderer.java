package de.hda.particles.renderer.impl.particles;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.domain.Particle;
import de.hda.particles.renderer.ParticleRenderer;

public class SimpleRainParticleRenderer extends AbstractParticleRenderer implements ParticleRenderer {

	public final static String NAME = "Rain";

	public SimpleRainParticleRenderer() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
		glLineWidth(3.0f);
		glColor4f(0.3f, 0.5f, 0.8f, 0.4f);
		glBegin(GL_LINES);
	}
	
	@Override
	public void after() {
		glEnd();
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		glColor4f(0.3f, 0.5f, 0.8f, 0.0f);
		glVertex3f(particle.getX(), particle.getY(), particle.getZ());
		glColor4f(0.7f, 0.7f, 0.9f, 0.3f);
		glVertex3f(particle.getX(), particle.getY() - 80.0f, particle.getZ());
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
