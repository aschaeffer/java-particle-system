package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.Color;

import de.hda.particles.ParticleSystem;
import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.features.ParticleSize;

public class ExtendedParticleRenderer extends AbstractRenderer implements Renderer {

	private ParticleSystem particleSystem;

	public ExtendedParticleRenderer(ParticleSystem particleSystem) {
		this.particleSystem = particleSystem;
	}

	@Override
	public void update() {
		glPushMatrix();
		glEnable(GL_BLEND);
		glPointSize(5.0f);
		glColor4f(1.0f, 1.0f, 1.0f, 0.3f);
		glBegin(GL_POINTS);
		List<Particle> currentParticles = new ArrayList<Particle>(particleSystem.particles);
		ListIterator<Particle> pIterator = currentParticles.listIterator(0);
		while (pIterator.hasNext()) {
			Particle particle = pIterator.next();
			if (particle != null) {
				glPointSize((Float) particle.get(ParticleSize.CURRENT_SIZE));
				Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
				glColor4f((float) color.getRed() / 255.0f, (float) color.getGreen() / 255.0f, (float) color.getBlue() / 255.0f, (float) color.getAlpha() / 255.0f);
				glVertex3f(particle.getX(), particle.getY(), particle.getZ());
				// System.out.println("particle x:" + particle.getX() + " y:" + particle.getY() + " z:" + particle.getZ() + " r:" + ((float) color.getRed() / 255.0f) + " g:" + color.getGreen() + " b:" + color.getBlue() + " a:" + color.getAlpha());
			}
		}
		glEnd();
		glPopMatrix();
	}

}
