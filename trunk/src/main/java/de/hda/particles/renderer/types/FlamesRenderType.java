package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.ARBPointParameters;
import org.lwjgl.opengl.ARBPointSprite;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.ARBPointSprite.*;
import static org.lwjgl.opengl.ARBPointParameters.*;
import de.hda.particles.domain.Particle;

public class FlamesRenderType extends AbstractRenderType implements RenderType {

	private static Integer SPRITES_PER_ROW = 2;
	private static Integer SPRITES_PER_COLUM = 2;
	private static Integer ITERATIONS_PER_SPRITE = 1;
	private static Float QUAD_WIDTH = 10.0f;
	
	private Texture texture;

	public FlamesRenderType() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		texture = scene.getTextureManager().load("PNG", "images/particles/flames.png");
		texture.bind();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE);
		glEnable(GL_POINT_SPRITE_ARB);
		float quadratic[] = {1.0f, 0.0f, 0.01f, 1};
		ByteBuffer temp = ByteBuffer.allocateDirect(16);
		temp.order(ByteOrder.nativeOrder());
		ARBPointParameters.glPointParameterARB(ARBPointParameters.GL_POINT_DISTANCE_ATTENUATION_ARB, (FloatBuffer) temp.asFloatBuffer().put(quadratic).flip());
		glPointParameterfARB( GL_POINT_FADE_THRESHOLD_SIZE_ARB, 40.0f );
		//Tell it the max and min sizes we can use using our pre-filled array.
		glPointParameterfARB( GL_POINT_SIZE_MIN_ARB, 30.0f );
		glPointParameterfARB( GL_POINT_SIZE_MAX_ARB, 50.0f );
		GL11.glPointSize(600);
        GL11.glEnable(ARBPointSprite.GL_POINT_SPRITE_ARB);
		//Tell OGL to replace the coordinates upon drawing.
		glTexEnvi(GL_POINT_SPRITE_ARB, GL_COORD_REPLACE_ARB, GL_TRUE);
		//Turn off depth masking so particles in front will not occlude particles behind them.
		glDepthMask(false);
		glBegin(GL_QUADS);
		glColor4f(1.0f, 0.5f, 0.5f, 0.05f);
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

//		glMatrixMode(GL_TEXTURE);
//        glLoadIdentity();
//        glScalef(row/2f, col/2f, 1f);
//		glVertex3f(particle.getX(), particle.getY(), particle.getZ());
        
//		glTexCoord2f(0.5f, 0.5f);
//		glVertex3f(particle.getX(), particle.getY(), );
	}

}
