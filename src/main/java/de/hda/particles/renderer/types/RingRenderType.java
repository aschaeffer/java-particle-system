package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.ARBPointParameters.*;
import static org.lwjgl.opengl.ARBPointSprite.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.ARBPointParameters;
import org.lwjgl.opengl.ARBPointSprite;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.features.ParticleSize;
import de.hda.particles.features.TubeSegments;

public class RingRenderType extends AbstractRenderType implements RenderType {

	private final Vector3f upUnitVector = new Vector3f(0.0f, 1.0f, 0.0f);
	private Vector3f unitTangent = new Vector3f();
	private Vector3f unitNormal = new Vector3f();
	private Vector3f unitBinormal = new Vector3f();
	private Vector3f point = new Vector3f();

	public RingRenderType() {}

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
		GL11.glPointSize(150);
        GL11.glEnable(ARBPointSprite.GL_POINT_SPRITE_ARB);
		//Tell OGL to replace the coordinates upon drawing.
		glTexEnvi(GL_POINT_SPRITE_ARB, GL_COORD_REPLACE_ARB, GL_TRUE);
		//Turn off depth masking so particles in front will not occlude particles behind them.
		glDepthMask(false);
	}
	
	@Override
	public void after() {
		glDepthMask(true);
		GL11.glDisable(ARBPointSprite.GL_POINT_SPRITE_ARB);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		unitTangent = new Vector3f();
		unitNormal = new Vector3f();
		unitBinormal = new Vector3f();
		particle.getVelocity().normalise(unitTangent);
		Vector3f.cross(unitTangent, upUnitVector, unitNormal);
		Vector3f.cross(unitTangent, unitNormal, unitBinormal);

		Double radius = (Double) particle.get(ParticleSize.CURRENT_SIZE);
		if (radius == null) {
			radius = ParticleSize.DEFAULT_SIZE_BIRTH;
		}
		Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		if (color == null) {
			color = ParticleColor.DEFAULT_COLOR;
		}
		Integer numberOfSegments = (Integer) particle.get(TubeSegments.NUMBER_OF_SEGMENTS);
		if (numberOfSegments == null) {
			numberOfSegments = TubeSegments.DEFAULT_NUMBER_OF_SEGMENTS;
		}
		Integer segmentsToDraw = (Integer) particle.get(TubeSegments.SEGMENTS_TO_DRAW);
		if (segmentsToDraw == null) {
			segmentsToDraw = TubeSegments.DEFAULT_SEGMENTS_TO_DRAW;
		}
		// start drawing ring (line loops are ideal for this task)
		glLineWidth(radius.floatValue());
		if (numberOfSegments == segmentsToDraw) {
			glBegin(GL_LINE_LOOP);
		} else {
			glBegin(GL_LINE_STRIP);
		}
		glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
		for(int j=0; j < segmentsToDraw; j++) {
			double x = radius * Math.cos(2 * Math.PI / numberOfSegments * j);
			double y = radius * Math.sin(2 * Math.PI / numberOfSegments * j);
			// Affine Transformation
			point = particle.getPosition();
			point.x += x * unitNormal.x + y * unitBinormal.x;
			point.y += x * unitNormal.y + y * unitBinormal.y;
			point.z += x * unitNormal.z + y * unitBinormal.z;
			glVertex3f(point.x, point.y, point.z);
		}
		glEnd();

	}

}
