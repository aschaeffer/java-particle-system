package de.hda.particles.renderer.impl.particles;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.domain.Particle;
import de.hda.particles.renderer.ParticleRenderer;

public class SimpleTriangleParticleRenderer extends AbstractParticleRenderer implements ParticleRenderer {

	public final static String NAME = "Triangle";

	public SimpleTriangleParticleRenderer() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
		glPointSize(5.0f);
		glColor4f(0.8f, 0.8f, 0.8f, 0.3f);
		glBegin(GL_TRIANGLES);
	}
	
	@Override
	public void after() {
		glEnd();
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		glVertex3f(particle.getX(), particle.getY() + 10.0f, particle.getZ());
		glVertex3f(particle.getX() + 10.0f, particle.getY(), particle.getZ());
		glVertex3f(particle.getX(), particle.getY(), particle.getZ() + 10.0f);
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
