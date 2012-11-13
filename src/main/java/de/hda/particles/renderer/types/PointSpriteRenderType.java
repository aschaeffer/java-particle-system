package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.domain.Particle;

public class PointSpriteRenderType extends AbstractRenderType implements RenderType {
	
	private static final Float WIDTH = 25.6f;
	private static final Float HEIGHT = 6.4f;

	public PointSpriteRenderType() {}

	public void before() {
		glPushMatrix();
		glEnable(GL_TEXTURE);
		glDisable(GL_BLEND);
//		glPointParameterfARB(GL_POINT_SIZE_MIN_ARB, 1e-9f);
//      glPointParameterfARB(GL_POINT_SIZE_MAX_ARB, 1e9f);
//      glPointParameterfARB(GL_POINT_DISTANCE_ATTENUATION_ARB, 1);
        // glPointSize(100.0f);
        // glEnable(GL_POINT_SPRITE_ARB);
		scene.getTextureManager().load("JPG", "images/particles/lightning.jpg").bind();
        glDepthMask(false);
        // glBegin(GL_POINTS);
		glBegin(GL_QUADS);
        // glPointSize(10.0f);
		glBegin(GL_POINTS);

        // glTexCoord2f(0.5f, 0.5f);
	}
	
	public void after() {
		glEnd();
		glDepthMask(true);
        // glDisable(GL_POINT_SPRITE_ARB);
        glDisable(GL_TEXTURE);
        glEnable(GL_BLEND);
		glPopMatrix();
	}

	public void render(Particle particle) {
		glTexCoord2f(1, 0);
		glVertex3f(particle.getX(), particle.getY(), particle.getZ() + HEIGHT);
		glTexCoord2f(0, 0);
		glVertex3f(particle.getX(), particle.getY(), particle.getZ() + HEIGHT);
		glTexCoord2f(0, 1);
		glVertex3f(particle.getX() + WIDTH, particle.getY(), particle.getZ());
		glTexCoord2f(1, 1);
		glVertex3f(particle.getX() + WIDTH, particle.getY(), particle.getZ());
	}

}
