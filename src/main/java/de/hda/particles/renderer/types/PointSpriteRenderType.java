package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.ARBPointParameters;
import org.lwjgl.opengl.ARBPointSprite;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.ARBPointSprite.*;
import static org.lwjgl.opengl.ARBPointParameters.*;
import de.hda.particles.domain.Particle;

public class PointSpriteRenderType extends AbstractRenderType implements RenderType {
	
	public PointSpriteRenderType() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		scene.getTextureManager().load("PNG", "images/particles/ball2.png").bind();
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
		glBegin(GL_POINTS);
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
		 glTexCoord2f(0.5f, 0.5f);
		 glVertex3f(particle.getX(), particle.getY(), particle.getZ());
	}

}


//glTexCoord2f(1, 0);
//glVertex3f(particle.getX(), particle.getY(), particle.getZ() + HEIGHT);
//glTexCoord2f(0, 0);
//glVertex3f(particle.getX(), particle.getY(), particle.getZ() + HEIGHT);
//glTexCoord2f(0, 1);
//glVertex3f(particle.getX() + WIDTH, particle.getY(), particle.getZ());
//glTexCoord2f(1, 1);
//glVertex3f(particle.getX() + WIDTH, particle.getY(), particle.getZ());

// glDisable(GL_POINT_SPRITE_ARB);

//glDisable(GL_VERTEX_PROGRAM_POINT_SIZE_ARB);
//glDisable(GL_POINT_SPRITE_ARB);
//glDisable(GL_TEXTURE);
//glEnable(GL_BLEND);
//glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//glDisable(GL_POINT_SPRITE);
//glDisable(GL_TEXTURE_2D);

// float[] sizes;
// FloatBuffer sizes;
// GL11.glGetFloat(GL12.GL_ALIASED_POINT_SIZE_RANGE, sizes);

//glEnable(GL_BLEND);
//glBlendFunc(GL_SRC_ALPHA, GL_ONE);
//glEnable(GL_POINT_SPRITE_ARB);
//glEnable(GL_VERTEX_PROGRAM_POINT_SIZE_ARB);
// scene.getTextureManager().load("JPG", "images/particles/lightning.jpg").bind();
// scene.getTextureManager().load("PNG", "images/particles/smoke.png").bind();
 
//glEnable(GL_POINT_SPRITE);
//glTexEnvi(GL_POINT_SPRITE, GL_COORD_REPLACE, GL_TRUE);


// glPointSize(5.0f);

//Save the current transform.
// glPushMatrix();
// glDisable(GL_BLEND);
//glPointParameterfARB(GL_POINT_SIZE_MIN_ARB, 1e-9f);
//glPointParameterfARB(GL_POINT_SIZE_MAX_ARB, 1e9f);
//glPointParameterfARB(GL_POINT_DISTANCE_ATTENUATION_ARB, 1);
// glPointSize(100.0f);
// glEnable(GL_POINT_SPRITE_ARB);
// glBegin(GL_POINTS);
// glPointSize(10.0f);
// glBegin(GL_POINTS);

// glTexCoord2f(0.5f, 0.5f);
// GL11.glPointSize(20.0f);
// glColor4f(0.8f, 0.8f, 0.8f, 0.3f);
// glDepthMask(false);
//glBegin(GL_QUADS);

