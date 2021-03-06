package de.hda.particles.renderer.impl.particles;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.renderer.ParticleRenderer;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;

public class VelocityIndicatorParticleRenderer extends AbstractParticleRenderer implements ParticleRenderer {

	public final static String NAME = "VelocityIndicator";

	public VelocityIndicatorParticleRenderer() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
		glLineWidth(3.0f);
		glBegin(GL_LINES);
	}
	
	@Override
	public void after() {
		glEnd();
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		Vector3f direction = new Vector3f();
		Vector3f.add(particle.getPosition(), particle.getVelocity(), direction);
		glColor4f(1.0f, 0.0f, 0.0f, 0.8f);
		glVertex3f(particle.getX(), particle.getY(), particle.getZ());
		glColor4f(1.0f, 1.0f, 0.0f, 0.8f);
		glVertex3f(direction.x, direction.y, direction.z);
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
