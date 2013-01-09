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
import de.hda.particles.features.PositionPath;

public class HairRenderType extends AbstractRenderType implements RenderType {

	public final static String NAME = "Hair";

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
//		glPointSize(150);
        glEnable(ARBPointSprite.GL_POINT_SPRITE_ARB);
		//Tell OGL to replace the coordinates upon drawing.
		glTexEnvi(GL_POINT_SPRITE_ARB, GL_COORD_REPLACE_ARB, GL_TRUE);
		//Turn off depth masking so particles in front will not occlude particles behind them.
		glDepthMask(false);
		glLineWidth(30.0f);
		glBegin(GL_LINE_STRIP);
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
		CircularFifoBuffer positionPathBuffer = (CircularFifoBuffer) particle.get(PositionPath.BUFFERED_POSITIONS);
		if (positionPathBuffer == null) return;
		ArrayList<Vector3f> positionPath = new ArrayList<Vector3f>(positionPathBuffer);

		Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		if (color != null)
			glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);
		
		ListIterator<Vector3f> iterator = positionPath.listIterator();
		Vector3f lastPosition = null;
		Vector3f nextPosition = null;
		if (iterator.hasNext()) nextPosition = iterator.next();
		while (iterator.hasNext()) {
			// lastPosition = nextPosition;
			nextPosition = iterator.next();
			// Vector3f direction = new Vector3f();
			// if (nextPosition == null || lastPosition == null) continue;
			// Vector3f.sub(nextPosition, lastPosition, direction);
			// glTexCoord2f(0.5f, 0.5f);
			glVertex3f(nextPosition.x, nextPosition.y, nextPosition.z);
			// glVertex3f(lastPosition.x + direction.x, lastPosition.y + direction.y, lastPosition.z + direction.z);
		}
	}

	@Override
	public void addDependencies() {
		scene.getParticleSystem().addParticleFeature(ParticleColor.class);
		scene.getParticleSystem().addParticleFeature(PositionPath.class);
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
