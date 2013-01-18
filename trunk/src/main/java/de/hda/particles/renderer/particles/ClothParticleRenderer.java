package de.hda.particles.renderer.particles;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.FixedPosition;
import de.hda.particles.features.MassSpring;
import de.hda.particles.features.ParticleColor;

public class ClothParticleRenderer extends AbstractParticleRenderer implements ParticleRenderer {

	public final static String NAME = "Cloth";

	/**
	 * render additional structural springs
	 */
	private final static Boolean RENDER_STRUCTURED = false;
	
	/**
	 * render fixed particles ?
	 */
	private final static Boolean RENDER_FIXED = true;
	
	/**
	 * rendering using colors
	 */
	private final static Boolean RENDER_COLORED = true;
	
	/**
	 * true: render polygons
	 * false: render mesh
	 */
	private final static Boolean RENDER_POLYGON = true;
	
	public ClothParticleRenderer() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}
	
	@Override
	public void after() {
		// glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {

		@SuppressWarnings("unchecked")
		List<Particle> connectedParticles = (List<Particle>) particle.get(MassSpring.SPRING_CONNECTED_PARTICLES);

		// render fixed particles in red
		if (particle.containsKey(FixedPosition.POSITION_FIXED)) {
			if ((Boolean) particle.get(FixedPosition.POSITION_FIXED)) {
				if (RENDER_FIXED) {
					glPointSize(5.0f);
					glBegin(GL_POINTS);
					glColor4f(1.0f, 0.2f, 0.2f, 0.2f);
					glVertex3f(particle.getX(), particle.getY(), particle.getZ());
					glEnd();
				}
				return;
			}
		}

		if (connectedParticles == null) return;

		Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		if (RENDER_COLORED && color != null) {
			glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
		} else {
			glColor4f(1.0f, 1.0f, 1.0f, 0.2f);
		}

		if (!RENDER_STRUCTURED) {
			// simple, fast, neat, no overlapping
			if (connectedParticles.size() < 4) return; // render only if there are enough points
			if (RENDER_POLYGON) {
				glBegin(GL_POLYGON);
			} else {
				glBegin(GL_LINE_STRIP);
			}
			// order matters!
			glVertex3f(connectedParticles.get(2).getX(), connectedParticles.get(2).getY(), connectedParticles.get(2).getZ());
			glVertex3f(connectedParticles.get(1).getX(), connectedParticles.get(1).getY(), connectedParticles.get(1).getZ());
			glVertex3f(connectedParticles.get(0).getX(), connectedParticles.get(0).getY(), connectedParticles.get(0).getZ());
			glVertex3f(particle.getX(), particle.getY(), particle.getZ());
			glEnd();
		} else {
			// more structured cloth
			if (connectedParticles.size() > 4 || (connectedParticles.size() > 1 && RENDER_FIXED)) {
				if (RENDER_POLYGON) {
					glBegin(GL_POLYGON);
				} else {
					glBegin(GL_LINE_STRIP);
				}
				glVertex3f(particle.getX(), particle.getY(), particle.getZ());
				ListIterator<Particle> iterator = connectedParticles.listIterator();
				while (iterator.hasNext()) {
					Particle springConnectedParticle = iterator.next();
					glVertex3f(springConnectedParticle.getX(), springConnectedParticle.getY(), springConnectedParticle.getZ());
				}
				glVertex3f(particle.getX(), particle.getY(), particle.getZ());
				glEnd();
			} else if (connectedParticles.size() == 1 && !RENDER_POLYGON) {
				glBegin(GL_LINE_STRIP);
				glVertex3f(particle.getX(), particle.getY(), particle.getZ());
				glVertex3f(connectedParticles.get(0).getX(), connectedParticles.get(0).getY(), connectedParticles.get(0).getZ());
				glEnd();
			}
		}
		
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
