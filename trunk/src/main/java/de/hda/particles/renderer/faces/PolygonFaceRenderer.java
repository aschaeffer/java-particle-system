package de.hda.particles.renderer.faces;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Face;
import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;

/**
 * Renders polygons of particles connected to the particle.
 * 
 * @author aschaeffer
 *
 */
public class PolygonFaceRenderer extends AbstractFaceRenderer implements FaceRenderer {

	public final static String NAME = "Polygon";

	public PolygonFaceRenderer() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		glColor4f(1.0f, 1.0f, 1.0f, 0.2f);
	}
	
	@Override
	public void after() {
		glPopMatrix();
	}

	@Override
	public void render(Face face) {
		if (face.size() < 3) return;
		glBegin(GL_POLYGON);
		for (Integer i = 0; i < face.size(); i++) {
			Particle p = face.get(i);
			Color color = (Color) p.get(ParticleColor.CURRENT_COLOR);
			if (color != null)
				glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
			glVertex3f(p.getX(), p.getY(), p.getZ());
		}
		glEnd();
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
