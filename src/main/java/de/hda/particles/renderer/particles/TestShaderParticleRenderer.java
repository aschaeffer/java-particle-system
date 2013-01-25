package de.hda.particles.renderer.particles;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.domain.Shader;
import de.hda.particles.features.ParticleColor;

public class TestShaderParticleRenderer extends AbstractParticleRenderer implements ParticleRenderer {

	public final static String NAME = "TestShader";
	
	public Shader shader;

	public TestShaderParticleRenderer() {
		// super("PNG", "images/particles/ball2.png", 25.0f, 50.0f, 50.0f, 37.5f, GL_ONE, GL_POINTS, 0.5f, 0.5f, 1.0f, 0.1f);
	}
	
	@Override
	public void before() {
		if (shader == null) shader = scene.getShaderManager().load("sizeroll");
		// super.before();
		glUseProgram(shader.getShaderProgram());
		// glUniform4f(glGetUniformLocation(shader.getShaderProgram(), "color"), 1, 0, 0, 1); // red
		glBegin(GL_POINTS);
	}
	
	@Override
	public void after() {
		// super.after();
		glEnd();
		glUseProgram(0);
	}

	@Override
	public void render(Particle particle) {
		Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		if (color != null)
			glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
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
