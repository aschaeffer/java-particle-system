package de.hda.particles.renderer.particles;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.domain.Particle;

public class SimpleQuadsParticleRenderer extends AbstractParticleRenderer implements ParticleRenderer {

	public final static String NAME = "Quads";

	public SimpleQuadsParticleRenderer() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
		glPointSize(5.0f);
		glColor4f(0.8f, 0.8f, 0.8f, 0.3f);
		glBegin(GL_QUADS);
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
