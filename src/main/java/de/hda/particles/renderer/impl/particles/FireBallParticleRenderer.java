package de.hda.particles.renderer.impl.particles;

//import static org.lwjgl.opengl.GL11.*;
//
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.nio.FloatBuffer;
//
//import org.lwjgl.opengl.ARBPointParameters;
//import org.lwjgl.opengl.ARBPointSprite;
import de.hda.particles.renderer.ParticleRenderer;
import org.lwjgl.opengl.GL11;
//import static org.lwjgl.opengl.ARBPointSprite.*;
//import static org.lwjgl.opengl.ARBPointParameters.*;
//import de.hda.particles.domain.Particle;

public class FireBallParticleRenderer extends AbstractBillboardParticleRenderer implements ParticleRenderer {

	public final static String NAME = "FireBall";

	public FireBallParticleRenderer() {
		super("PNG", "images/particles/ball1.png", 20.0f, 50.0f, 50.0f, 35.0f, GL11.GL_ONE, GL11.GL_POINTS, 0.8f, 0.3f, 0.3f, 0.02f);
	}

	/*
	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		scene.getTextureManager().load("PNG", "images/particles/ball1.png").bind();
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
		glBegin(GL_POINTS);
		glColor4f(1.0f, 0.5f, 0.5f, 0.05f);
	}
	
	@Override
	public void after() {
		glEnd();
		glDepthMask(true);
		GL11.glDisable(ARBPointSprite.GL_POINT_SPRITE_ARB);
		glPointSize(5.0f);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		 glTexCoord2f(0.5f, 0.5f);
		 glVertex3f(particle.getX(), particle.getY(), particle.getZ());
	}
	*/
	
	@Override
	public String getName() {
		return NAME;
	}

}
