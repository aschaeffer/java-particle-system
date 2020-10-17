package de.hda.particles.renderer.impl;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.renderer.Renderer;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import de.hda.particles.domain.Particle;

/**
 * Renderer for particles.
 * Does not use render types.
 * Only one type of particles: points
 * Purpose: simple and fast
 * 
 * @author aschaeffer
 *
 */
public class FastParticleRenderer extends AbstractRenderer implements Renderer {

	public FastParticleRenderer() {}

	@Override
	public void update() {
		glPushMatrix();
		glEnable(GL_BLEND);
		glPointSize(5.0f);
		glColor4f(0.8f, 0.8f, 0.8f, 0.3f);

		glBegin(GL_POINTS);
		List<Particle> currentParticles = new ArrayList<Particle>(scene.getParticleSystem().getParticles());
		ListIterator<Particle> pIterator = currentParticles.listIterator(0);
		while (pIterator.hasNext()) {
			Particle particle = pIterator.next();
			if (particle != null) {
				glVertex3f(particle.getX(), particle.getY(), particle.getZ());
			}
		}
		glEnd();
		glPopMatrix();
	}

}
