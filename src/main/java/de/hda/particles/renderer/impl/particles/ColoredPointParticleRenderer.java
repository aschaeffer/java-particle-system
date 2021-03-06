package de.hda.particles.renderer.impl.particles;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.renderer.ParticleRenderer;
import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.impl.ParticleColor;

public class ColoredPointParticleRenderer extends AbstractParticleRenderer implements ParticleRenderer {

	public final static String NAME = "Colored Point";

	public ColoredPointParticleRenderer() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
		glPointSize(5.0f);
		glBegin(GL_POINTS);
	}
	
	@Override
	public void after() {
		glEnd();
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		if (color != null)
			glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
		glVertex3f(particle.getX(), particle.getY(), particle.getZ());
	}

	@Override
	public void addDependencies() {
		scene.getParticleSystem().addParticleFeature(ParticleColor.class);
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
