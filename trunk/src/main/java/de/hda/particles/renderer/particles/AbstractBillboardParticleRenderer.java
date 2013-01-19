package de.hda.particles.renderer.particles;

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
import org.newdawn.slick.opengl.Texture;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;

public abstract class AbstractBillboardParticleRenderer extends AbstractParticleRenderer {

	public final static String TEXTURE_FORMAT = "textureFormat";
	public final static String TEXTURE_FILENAME = "textureFilename";
	public final static String MIN_SIZE = "minSize";
	public final static String MAX_SIZE = "maxSize";
	public final static String POINT_SIZE = "pointSize";
	public final static String FADE_THRESHOLD_SIZE = "fadeThresholdSize";
	public final static String BLEND_FUNCTION = "blendFunction";
	public final static String RENDER_FUNCTION = "renderFunction";
	
	public final static Float MIN_MIN_SIZE = 0.0f;
	
	private static float quadratic[] = {1.0f, 0.0f, 0.01f, 1};
	private final FloatBuffer bbBuffer = ByteBuffer.allocateDirect(16).order(ByteOrder.nativeOrder()).asFloatBuffer();
	protected final FloatBuffer flippedBbBuffer;
	
	protected String textureFormat = "PNG";
	protected String textureFilename = "images/particles/ball2.png";
	protected Float minSize = 1.0f;
	protected Float maxSize = 150.0f;
	protected Float pointSize = 150.0f;
	protected Float fadeThresholdSize = 75.0f;
	protected Integer blendFunction = GL11.GL_ONE;
	protected Integer renderFunction = GL11.GL_POINTS;
	
	protected Texture texture;

	public AbstractBillboardParticleRenderer() {
		super();
		flippedBbBuffer = (FloatBuffer) bbBuffer.put(quadratic).flip();
	}

	public AbstractBillboardParticleRenderer(String textureFormat, String textureFilename, Float minSize, Float maxSize, Float pointSize, Float fadeThresholdSize, Integer blendFunction, Integer renderFunction) {
		super();
		flippedBbBuffer = (FloatBuffer) bbBuffer.put(quadratic).flip();
		this.textureFormat = textureFormat;
		this.textureFilename = textureFilename;
		this.minSize = minSize;
		this.maxSize = maxSize;
		this.pointSize = pointSize;
		this.fadeThresholdSize = fadeThresholdSize;
		this.blendFunction = blendFunction;
		this.renderFunction = renderFunction;
	}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		if (texture == null) texture = scene.getTextureManager().load(textureFormat, textureFilename);
		// texture = scene.getTextureManager().load(textureFormat, textureFilename);
		texture.bind();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, blendFunction);
		glEnable(GL_POINT_SPRITE_ARB);
		ARBPointParameters.glPointParameterARB(ARBPointParameters.GL_POINT_DISTANCE_ATTENUATION_ARB, flippedBbBuffer);
		glPointParameterfARB(GL_POINT_FADE_THRESHOLD_SIZE_ARB, fadeThresholdSize);
		//Tell it the max and min sizes we can use using our pre-filled array.
		glPointParameterfARB(GL_POINT_SIZE_MIN_ARB, minSize);
		glPointParameterfARB(GL_POINT_SIZE_MAX_ARB, maxSize);
		GL11.glPointSize(pointSize);
        GL11.glEnable(ARBPointSprite.GL_POINT_SPRITE_ARB);
		//Tell OGL to replace the coordinates upon drawing.
		glTexEnvi(GL_POINT_SPRITE_ARB, GL_COORD_REPLACE_ARB, GL_TRUE);
		//Turn off depth masking so particles in front will not occlude particles behind them.
		glDepthMask(false);
		if (renderFunction > -1) glBegin(renderFunction);
		glColor4f(1.0f, 1.0f, 1.0f, 0.2f);
	}

	@Override
	public void render(Particle particle) {
		Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		if (color != null)
			glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
		glTexCoord2f(0.5f, 0.5f);
		glVertex3f(particle.getX(), particle.getY(), particle.getZ());
	}

	@Override
	public void after() {
		if (renderFunction > -1) glEnd();
		glDepthMask(true);
		GL11.glDisable(ARBPointSprite.GL_POINT_SPRITE_ARB);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	public String getTextureFormat() {
		return textureFormat;
	}

	public void setTextureFormat(String textureFormat) {
		this.textureFormat = textureFormat;
	}

	public String getTextureFilename() {
		return textureFilename;
	}

	public void setTextureFilename(String textureFilename) {
		this.textureFilename = textureFilename;
	}

	public Float getMinSize() {
		return minSize;
	}

	public void setMinSize(Float minSize) {
		this.minSize = minSize;
	}

	public Float getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Float maxSize) {
		this.maxSize = maxSize;
	}

	public Float getPointSize() {
		return pointSize;
	}

	public void setPointSize(Float pointSize) {
		this.pointSize = pointSize;
	}

	public Float getFadeThresholdSize() {
		return fadeThresholdSize;
	}

	public void setFadeThresholdSize(Float fadeThresholdSize) {
		this.fadeThresholdSize = fadeThresholdSize;
	}

	public Integer getBlendFunction() {
		return blendFunction;
	}

	public void setBlendFunction(Integer blendFunction) {
		this.blendFunction = blendFunction;
	}

	public Integer getRenderFunction() {
		return renderFunction;
	}

	public void setRenderFunction(Integer renderFunction) {
		this.renderFunction = renderFunction;
	}

}
