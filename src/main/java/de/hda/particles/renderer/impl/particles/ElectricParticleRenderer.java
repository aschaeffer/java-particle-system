package de.hda.particles.renderer.impl.particles;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.renderer.ParticleRenderer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.opengl.ARBPointParameters;
import org.lwjgl.opengl.ARBPointSprite;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.ARBPointSprite.*;
import static org.lwjgl.opengl.ARBPointParameters.*;
import de.hda.particles.domain.Particle;

public class ElectricParticleRenderer extends AbstractParticleRenderer implements ParticleRenderer {

	public final static String NAME = "Electric";

	private static Integer SPRITES_PER_ROW = 2;
	private static Integer SPRITES_PER_COLUM = 2;
	private static Integer ITERATIONS_PER_SPRITE = 1;
	private static Float QUAD_WIDTH = 10.0f;

	Random random = new Random();

	public ElectricParticleRenderer() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		scene.getTextureManager().load("PNG", "images/particles/electric.png").bind();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE);
		glEnable(GL_POINT_SPRITE_ARB);
		float quadratic[] = {1.0f, 0.0f, 0.01f, 1};
		ByteBuffer temp = ByteBuffer.allocateDirect(16);
		temp.order(ByteOrder.nativeOrder());
		ARBPointParameters.glPointParameterARB(ARBPointParameters.GL_POINT_DISTANCE_ATTENUATION_ARB, (FloatBuffer) temp.asFloatBuffer().put(quadratic).flip());
		glPointParameterfARB( GL_POINT_FADE_THRESHOLD_SIZE_ARB, 60.0f );
		//Tell it the max and min sizes we can use using our pre-filled array.
		glPointParameterfARB( GL_POINT_SIZE_MIN_ARB, 1.0f );
		glPointParameterfARB( GL_POINT_SIZE_MAX_ARB, 100.0f );
		GL11.glPointSize(100);
        GL11.glEnable(ARBPointSprite.GL_POINT_SPRITE_ARB);
		//Tell OGL to replace the coordinates upon drawing.
		glTexEnvi(GL_POINT_SPRITE_ARB, GL_COORD_REPLACE_ARB, GL_TRUE);
		//Turn off depth masking so particles in front will not occlude particles behind them.
		glDepthMask(false);
		glBegin(GL_QUADS);
		glColor4f(1.0f, 0.5f, 0.5f, 0.2f);
	}
	
	@Override
	public void after() {
		glEnd();
		glDepthMask(true);
		GL11.glDisable(ARBPointSprite.GL_POINT_SPRITE_ARB);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		Integer texNo = particle.getPastIterations() / ITERATIONS_PER_SPRITE;
		Integer row = texNo / SPRITES_PER_ROW;
		Integer col = texNo % SPRITES_PER_COLUM;
		
		float u0 = row / SPRITES_PER_ROW;
		float u1 = (row + 1) / SPRITES_PER_ROW;

		float v0 = col / SPRITES_PER_COLUM;
		float v1 = (col + 1) / SPRITES_PER_COLUM; 

		glTexCoord2f(u1, v0);
		glVertex3f(particle.getX() + QUAD_WIDTH, particle.getY() + QUAD_WIDTH, particle.getZ());
		glTexCoord2f(u0, v0);
		glVertex3f(particle.getX(), particle.getY() + QUAD_WIDTH, particle.getZ());
		glTexCoord2f(u0, v1);
		glVertex3f(particle.getX(), particle.getY(), particle.getZ());
		glTexCoord2f(u1, v1);
		glVertex3f(particle.getX() + QUAD_WIDTH, particle.getY(), particle.getZ());
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
