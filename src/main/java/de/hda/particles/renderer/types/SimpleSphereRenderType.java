package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.glu.Sphere;

import de.hda.particles.domain.Particle;

public class SimpleSphereRenderType extends AbstractRenderType implements RenderType {

	public final static String NAME = "GLU-Sphere";

	public SimpleSphereRenderType() {}

	@Override
	public void before() {
	}
	
	@Override
	public void after() {
	}

	@Override
	public void render(Particle particle) {
		glPushMatrix();
		glEnable(GL_BLEND);
		glColor4f(0.8f, 0.5f, 0.5f, 0.2f);
        glTranslatef(particle.getX(), particle.getY(), particle.getZ());
        Sphere s = new Sphere();
        s.draw(15.0f, 16, 16);
		glPopMatrix();
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
