package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.MassSpring;
import de.hda.particles.features.ParticleColor;

public class TexturedClothRenderType extends AbstractBillboardRenderType implements RenderType {

	public final static String NAME = "Cloth (Textured)";

	private final static String FORMAT = "JPG";
	private final static String FILENAME = "images/cloth/black-watch-tartan.jpg";
//	private final static String FORMAT = "JPG";
//	private final static String FILENAME = "images/cloth/kilt-2.jpg";
//	private final static String FORMAT = "PNG";
//	private final static String FILENAME = "images/particles/smoke.png";
//	private final static String FORMAT = "PNG";
//	private final static String FILENAME = "images/particles/ball2.png";
	
	public TexturedClothRenderType() {
		super(FORMAT, FILENAME, 10.0f, 20.0f, 20.0f, 15.0f, GL11.GL_ONE_MINUS_SRC_ALPHA, -1);
	}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		if (texture == null) texture = scene.getTextureManager().load(textureFormat, textureFilename);
		texture.bind();
//		glEnable(GL_BLEND);
//		glBlendFunc(GL_SRC_ALPHA, blendFunction);
		
//		glEnable(GL_POINT_SPRITE_ARB);
//		ARBPointParameters.glPointParameterARB(ARBPointParameters.GL_POINT_DISTANCE_ATTENUATION_ARB, flippedBbBuffer);
//		glPointParameterfARB( GL_POINT_FADE_THRESHOLD_SIZE_ARB, fadeThresholdSize);
//		//Tell it the max and min sizes we can use using our pre-filled array.
//		glPointParameterfARB( GL_POINT_SIZE_MIN_ARB, minSize);
//		glPointParameterfARB( GL_POINT_SIZE_MAX_ARB, maxSize);
//		GL11.glPointSize(pointSize);
//      GL11.glEnable(ARBPointSprite.GL_POINT_SPRITE_ARB);
		//Tell OGL to replace the coordinates upon drawing.
//		glTexEnvi(GL_POINT_SPRITE_ARB, GL_COORD_REPLACE_ARB, GL_TRUE);
		//Turn off depth masking so particles in front will not occlude particles behind them.
//		glDepthMask(false);
//		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		glBegin(GL_QUADS);
	}

	@Override
	public void after() {
		glEnd();
//		glDepthMask(true);
//		GL11.glDisable(ARBPointSprite.GL_POINT_SPRITE_ARB);
//		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		if (color != null) {
			glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f); // color.getAlpha() / 255.0f
		} else {
			glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		}

		@SuppressWarnings("unchecked")
		List<Particle> connectedParticles = (List<Particle>) particle.get(MassSpring.SPRING_CONNECTED_PARTICLES);
		if (connectedParticles == null || connectedParticles.size() < 4) return; // render only if there are enough points
		// order matters!
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(connectedParticles.get(2).getX(), connectedParticles.get(2).getY(), connectedParticles.get(2).getZ());
		glTexCoord2f(texture.getWidth(), 0.0f);
		glVertex3f(connectedParticles.get(1).getX(), connectedParticles.get(1).getY(), connectedParticles.get(1).getZ());
		glTexCoord2f(texture.getWidth(), texture.getHeight());
		glVertex3f(connectedParticles.get(0).getX(), connectedParticles.get(0).getY(), connectedParticles.get(0).getZ());
		glTexCoord2f(0.0f, texture.getHeight());
		glVertex3f(particle.getX(), particle.getY(), particle.getZ());
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
