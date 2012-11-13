package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import de.hda.particles.domain.Particle;

public class SimpleParticleRenderer extends AbstractRenderer implements Renderer {

	public SimpleParticleRenderer() {}

	@Override
	public void update() {
		glPushMatrix();
		// glEnable(GL_POINT_SMOOTH);
		glEnable(GL_BLEND);
		glPointSize(5.0f);
		glColor4f(0.8f, 0.8f, 0.8f, 0.3f);

		glBegin(GL_POINTS);
		List<Particle> currentParticles = new ArrayList<Particle>(scene.getParticleSystem().particles);
		ListIterator<Particle> pIterator = currentParticles.listIterator(0);
		while (pIterator.hasNext()) {
			Particle particle = pIterator.next();
			if (particle != null) {
				glVertex3f(particle.getX(), particle.getY(), particle.getZ());
				// System.out.println("particle x:" + particle.getX() + " y:" + particle.getY() + " z:" + particle.getZ());
			}
		}
		glEnd();
		glPopMatrix();
	}

}
