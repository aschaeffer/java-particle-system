package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.features.ParticleSize;

public class ExtendedParticleRenderer extends AbstractRenderer implements Renderer {

	public ExtendedParticleRenderer() {}

	@Override
	public void update() {
		glPushMatrix();
		glEnable(GL_BLEND);
		// glPointSize(5.0f);
		// glColor4f(1.0f, 1.0f, 1.0f, 0.3f);
		// glBegin(GL_POINTS);
		List<Particle> currentParticles = new ArrayList<Particle>(scene.getParticleSystem().particles);
		ListIterator<Particle> pIterator = currentParticles.listIterator(0);
		while (pIterator.hasNext()) {
			Particle particle = pIterator.next();
			if (particle != null) {
				glEnable(GL_BLEND);
				glPointSize((Float) particle.get(ParticleSize.CURRENT_SIZE));
				glBegin(GL_POINTS);
				Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
				glColor4f((float) color.getRed() / 255.0f, (float) color.getGreen() / 255.0f, (float) color.getBlue() / 255.0f, (float) color.getAlpha() / 255.0f);
				glVertex3f(particle.getX(), particle.getY(), particle.getZ());
				glEnd();
				// System.out.println("particle x:" + particle.getX() + " y:" + particle.getY() + " z:" + particle.getZ() + " r:" + ((float) color.getRed() / 255.0f) + " g:" + color.getGreen() + " b:" + color.getBlue() + " a:" + color.getAlpha());
			}
		}
		// glEnd();
		glPopMatrix();
	}

}
