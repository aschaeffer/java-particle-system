package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.ARBPointParameters.*;
import static org.lwjgl.opengl.ARBPointSprite.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.lwjgl.opengl.ARBPointParameters;
import org.lwjgl.opengl.ARBPointSprite;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.MassSpring;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.features.PositionTrace;

public class HairRenderType extends AbstractRenderType implements RenderType {

	public HairRenderType() {}

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
		glPointParameterfARB( GL_POINT_FADE_THRESHOLD_SIZE_ARB, 75.0f );
		//Tell it the max and min sizes we can use using our pre-filled array.
		glPointParameterfARB( GL_POINT_SIZE_MIN_ARB, 1.0f );
		glPointParameterfARB( GL_POINT_SIZE_MAX_ARB, 150.0f );
		glPointSize(150);
        glEnable(ARBPointSprite.GL_POINT_SPRITE_ARB);
		//Tell OGL to replace the coordinates upon drawing.
		glTexEnvi(GL_POINT_SPRITE_ARB, GL_COORD_REPLACE_ARB, GL_TRUE);
		//Turn off depth masking so particles in front will not occlude particles behind them.
		glDepthMask(false);
		glLineWidth(3.0f);
		glBegin(GL_LINES);
		glColor4f(1.0f, 1.0f, 1.0f, 0.2f);
	}
	
	@Override
	public void after() {
		glEnd();
		glDepthMask(true);
		glDisable(ARBPointSprite.GL_POINT_SPRITE_ARB);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		CircularFifoBuffer tracedParticlesBuffer = (CircularFifoBuffer) particle.get(PositionTrace.POSITIONS);
		if (tracedParticlesBuffer == null) return;
		ArrayList<Particle> tracedParticles = new ArrayList<Particle>(tracedParticlesBuffer);

		Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		if (color != null)
			glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);

		
		@SuppressWarnings("unchecked")
		ListIterator<Particle> iterator = tracedParticles.listIterator();
		Particle lastParticle = null;
		Particle nextParticle = null;
		if (iterator.hasNext()) nextParticle = iterator.next();
		while (iterator.hasNext()) {
			lastParticle = nextParticle;
			nextParticle = iterator.next();
			Vector3f direction = new Vector3f();
			Vector3f.sub(nextParticle.getPosition(), particle.getPosition(), direction);
			glTexCoord2f(0.5f, 0.5f);
			glVertex3f(particle.getX(), particle.getY(), particle.getZ());
			glVertex3f(particle.getX() + direction.x, particle.getY() + direction.y, particle.getZ() + direction.z);
		}
	}

}
