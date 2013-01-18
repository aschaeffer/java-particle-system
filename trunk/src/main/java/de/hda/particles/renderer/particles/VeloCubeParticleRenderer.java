package de.hda.particles.renderer.particles;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.features.ParticleSize;

public class VeloCubeParticleRenderer extends AbstractParticleRenderer implements ParticleRenderer {

	public final static String NAME = "VeloCube";

	public VeloCubeParticleRenderer() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
		glBegin(GL_QUADS);
	}
	
	@Override
	public void after() {
		glEnd();
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		Double size = (Double) particle.get(ParticleSize.CURRENT_SIZE);
		if (size == null) {
			size = ParticleSize.DEFAULT_SIZE_BIRTH / 2.0;
		} else {
			size = size / 2.0;
		}
		Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		if (color == null) {
			color = ParticleColor.DEFAULT_COLOR;
		}

		Double bbMinX = particle.getX() - (size * particle.getVelocity().x);
		Double bbMinY = particle.getY() - (size * particle.getVelocity().y);
		Double bbMinZ = particle.getZ() - (size * particle.getVelocity().z);
		Double bbMaxX = particle.getX() + (size * particle.getVelocity().x);
		Double bbMaxY = particle.getY() + (size * particle.getVelocity().y);
		Double bbMaxZ = particle.getZ() + (size * particle.getVelocity().z);

		glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);

		// FRONT
	    glVertex3d(bbMinX, bbMinY, bbMinZ);
	    glVertex3d(bbMaxX, bbMinY, bbMinZ);
	    glVertex3d(bbMaxX, bbMaxY, bbMinZ);
	    glVertex3d(bbMinX, bbMaxY, bbMinZ);
	    // BACK
	    glVertex3d(bbMaxX, bbMaxY, bbMaxZ);
	    glVertex3d(bbMinX, bbMaxY, bbMaxZ);
	    glVertex3d(bbMinX, bbMinY, bbMaxZ);
	    glVertex3d(bbMaxX, bbMinY, bbMaxZ);
		// RIGHT
	    glVertex3d(bbMaxX, bbMaxY, bbMaxZ);
	    glVertex3d(bbMaxX, bbMaxY, bbMinZ);
	    glVertex3d(bbMaxX, bbMinY, bbMinZ);
	    glVertex3d(bbMaxX, bbMinY, bbMaxZ);
		// LEFT
	    glVertex3d(bbMinX, bbMinY, bbMinZ);
	    glVertex3d(bbMinX, bbMaxY, bbMinZ);
	    glVertex3d(bbMinX, bbMaxY, bbMaxZ);
	    glVertex3d(bbMinX, bbMinY, bbMaxZ);
		// TOP
	    glVertex3d(bbMaxX, bbMaxY, bbMaxZ);
	    glVertex3d(bbMinX, bbMaxY, bbMaxZ);
	    glVertex3d(bbMinX, bbMaxY, bbMinZ);
	    glVertex3d(bbMaxX, bbMaxY, bbMinZ);
		// BOTTOM
	    glVertex3d(bbMinX, bbMinY, bbMinZ);
	    glVertex3d(bbMinX, bbMinY, bbMaxZ);
	    glVertex3d(bbMaxX, bbMinY, bbMaxZ);
	    glVertex3d(bbMaxX, bbMinY, bbMinZ);

	}

	@Override
	public void addDependencies() {
		scene.getParticleSystem().addParticleFeature(ParticleColor.class);
		scene.getParticleSystem().addParticleFeature(ParticleSize.class);
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
