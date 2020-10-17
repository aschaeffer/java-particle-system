package de.hda.particles.renderer.impl.particles;

import static org.lwjgl.opengl.ARBPointParameters.GL_POINT_FADE_THRESHOLD_SIZE_ARB;
import static org.lwjgl.opengl.ARBPointParameters.GL_POINT_SIZE_MAX_ARB;
import static org.lwjgl.opengl.ARBPointParameters.GL_POINT_SIZE_MIN_ARB;
import static org.lwjgl.opengl.ARBPointParameters.glPointParameterfARB;
import static org.lwjgl.opengl.ARBPointSprite.GL_COORD_REPLACE_ARB;
import static org.lwjgl.opengl.ARBPointSprite.GL_POINT_SPRITE_ARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import de.hda.particles.renderer.ParticleRenderer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.ARBPointParameters;
import org.lwjgl.opengl.ARBPointSprite;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.newdawn.slick.opengl.Texture;

import de.hda.particles.domain.Particle;
import de.hda.particles.domain.Shader;
import de.hda.particles.features.impl.ParticleColor;

public class TestShaderParticleRenderer extends AbstractParticleRenderer implements ParticleRenderer {

	public final static String NAME = "TestShader";
	
	public Shader shader;

	private static float quadratic[] = {1.0f, 0.0f, 0.01f, 1};
	private final FloatBuffer bbBuffer = ByteBuffer.allocateDirect(16).order(ByteOrder.nativeOrder()).asFloatBuffer();
	protected final FloatBuffer flippedBbBuffer;
	

	protected Float minSize = 1.0f;
	protected Float maxSize = 2.0f;
	protected Float pointSize = 1.5f;
	protected Float fadeThresholdSize = 1.5f;

	protected String textureFormat = "PNG";
	protected String textureFilename = "images/particles/ball2.png";
	protected Texture texture;

	public TestShaderParticleRenderer() {
		// super("PNG", "images/particles/ball2.png", 25.0f, 50.0f, 50.0f, 37.5f, GL_ONE, GL_POINTS, 0.5f, 0.5f, 1.0f, 0.1f);
		super();
		flippedBbBuffer = (FloatBuffer) bbBuffer.put(quadratic).flip();
	}
	
	@Override
	public void before() {

		if (shader == null) shader = scene.getShaderManager().load("sizeroll");
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		if (texture == null) texture = scene.getTextureManager().load(textureFormat, textureFilename);
		texture.bind();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE);
		glUseProgram(shader.getShaderProgram());
		Integer timeUniform = glGetUniformLocation(shader.getShaderProgram(), "time");
		glUniform1f(timeUniform, (float) Math.random());
		// glUniformMatrix4(location, transpose, matrices)4f(glGetUniformLocation(shader.getShaderProgram(), "projection"), scene.getRendererManager().getProjectionMatrix());
		// glUniform4f(glGetUniformLocation(shader.getShaderProgram(), "color"), 1, 0, 0, 1); // red
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
		glDepthMask(false);
		glBegin(GL_POINTS);
	}
	
	@Override
	public void after() {
		// super.after();
		glEnd();
		glDepthMask(true);
		GL11.glDisable(ARBPointSprite.GL_POINT_SPRITE_ARB);
		glUseProgram(0);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		if (color != null)
			glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
		glTexCoord2f(0.5f, 0.5f);
		glVertex3f(particle.getX(), particle.getY(), particle.getZ());
		glNormal3f(particle.getVelX(), particle.getVelY(), particle.getVelZ());
	}

	@Override
	public void addDependencies() {
		scene.getParticleSystem().addParticleFeature(ParticleColor.class);
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
