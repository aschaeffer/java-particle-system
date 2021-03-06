package de.hda.particles.renderer.impl;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.renderer.Renderer;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.impl.ParticleColor;
import de.hda.particles.features.impl.ParticleSize;

/**
 * Renderer for particles.
 * Does not use render types.
 * Only one type of particles: points
 * Does render points colored and uses size information.
 * Purpose: simple and fast (but slower than FastParticleRenderer)
 * 
 * @author aschaeffer
 *
 */
public class ComplexParticleRenderer extends AbstractRenderer implements Renderer {

	public ComplexParticleRenderer() {}

	@Override
	public void update() {
		glPushMatrix();
		glEnable(GL_BLEND);
		List<Particle> currentParticles = new ArrayList<Particle>(scene.getParticleSystem().getParticles());
		ListIterator<Particle> pIterator = currentParticles.listIterator(0);
		while (pIterator.hasNext()) {
			Particle particle = pIterator.next();
			if (particle != null) {
				glEnable(GL_BLEND);
				glPointSize((Float) particle.get(ParticleSize.CURRENT_SIZE));
				glBegin(GL_POINTS);
				Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
				glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
				glVertex3f(particle.getX(), particle.getY(), particle.getZ());
				glEnd();
			}
		}
		glPopMatrix();
	}

}
