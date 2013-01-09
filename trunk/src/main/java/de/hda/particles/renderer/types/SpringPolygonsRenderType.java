package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.MassSpring;
import de.hda.particles.features.ParticleColor;

/**
 * Renders polygons of particles connected to the particle.
 * 
 * The resulting polygon isn't very useful though.
 * 
 * @author aschaeffer
 *
 */
public class SpringPolygonsRenderType extends AbstractRenderType implements RenderType {

	public final static String NAME = "SpringPolygons";

	public SpringPolygonsRenderType() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}
	
	@Override
	public void after() {
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		if (color != null)
			glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);

		@SuppressWarnings("unchecked")
		List<Particle> connectedParticles = (List<Particle>) particle.get(MassSpring.SPRING_CONNECTED_PARTICLES);
		if (connectedParticles == null) return;
		ListIterator<Particle> iterator = connectedParticles.listIterator();
		glBegin(GL_POLYGON);
		glVertex3f(particle.getX(), particle.getY(), particle.getZ());
		while (iterator.hasNext()) {
			Particle springConnectedParticle = iterator.next();
			glVertex3f(springConnectedParticle.getX(), springConnectedParticle.getY(), springConnectedParticle.getZ());
		}
		// glVertex3f(particle.getX(), particle.getY(), particle.getZ());
		glEnd();
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
