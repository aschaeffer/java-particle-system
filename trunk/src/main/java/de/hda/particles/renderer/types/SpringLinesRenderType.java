package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.MassSpring;

public class SpringLinesRenderType extends AbstractRenderType implements RenderType {

	public final static String NAME = "SpringLines";

	public SpringLinesRenderType() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
		glLineWidth(3.0f);
		glBegin(GL_LINES);
		glColor4f(0.8f, 0.8f, 0.8f, 0.5f);
	}
	
	@Override
	public void after() {
		glEnd();
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		@SuppressWarnings("unchecked")
		List<Particle> connectedParticles = (List<Particle>) particle.get(MassSpring.SPRING_CONNECTED_PARTICLES);
		if (connectedParticles == null) return;
		ListIterator<Particle> iterator = connectedParticles.listIterator();
		while (iterator.hasNext()) {
			Particle c = iterator.next();
			Vector3f direction = new Vector3f();
			Vector3f.sub(c.getPosition(), particle.getPosition(), direction);
			glVertex3f(particle.getX(), particle.getY(), particle.getZ());
			glVertex3f(particle.getX() + direction.x, particle.getY() + direction.y, particle.getZ() + direction.z);
		}
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
