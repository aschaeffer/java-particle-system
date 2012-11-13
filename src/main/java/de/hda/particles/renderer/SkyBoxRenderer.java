package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;

import java.util.List;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

public class SkyBoxRenderer extends AbstractRenderer implements Renderer {

	private static final String SKYBOX_NAME = "sleepyhollow";

	private List<Texture> textures = new ArrayList<Texture>();

	public SkyBoxRenderer() {}

	@Override
	public void setup() {
		textures.add(scene.getTextureManager().load("JPG", "images/skybox/" + SKYBOX_NAME + "_ft.jpg"));
		textures.add(scene.getTextureManager().load("JPG", "images/skybox/" + SKYBOX_NAME + "_lf.jpg"));
		textures.add(scene.getTextureManager().load("JPG", "images/skybox/" + SKYBOX_NAME + "_bk.jpg"));
		textures.add(scene.getTextureManager().load("JPG", "images/skybox/" + SKYBOX_NAME + "_rt.jpg"));
		textures.add(scene.getTextureManager().load("JPG", "images/skybox/" + SKYBOX_NAME + "_up.jpg"));
		textures.add(scene.getTextureManager().load("JPG", "images/skybox/" + SKYBOX_NAME + "_dn.jpg"));
	}

	@Override
	public void update() {
		
		glPushMatrix();

		glPushAttrib(GL_ENABLE_BIT);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_DEPTH_TEST);
        glDisable(GL_LIGHTING);
        glDisable(GL_BLEND);
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        glLoadIdentity();
		// rotate the pitch around the X axis
		glRotatef(scene.getCameraManager().getPitch(), 1.0f, 0.0f, 0.0f);
		// rotate the yaw around the Y axis
		glRotatef(scene.getCameraManager().getYaw(), 0.0f, 1.0f, 0.0f);
		// rotate the yaw around the Y axis
		glRotatef(scene.getCameraManager().getRoll(), 0.0f, 0.0f, 1.0f);
		// translate to the position vector's location
		// glTranslatef(this.camera.getPosition().x, this.camera.getPosition().y, this.camera.getPosition().z);

        // glRotatef(angle, this.camera.getPosition().x, this.camera.getPosition().y, this.camera.getPosition().z);
        // glTranslatef(this.camera.getPosition().x, this.camera.getPosition().y, this.camera.getPosition().z);
        // this.camera.lookThrough();

		glActiveTexture(GL_TEXTURE0);

		// Render the front quad
		// clampToEdge();
		textures.get(0).bind();
		glBegin(GL_QUADS);
		glTexCoord2f(1.0f, 1.0f);
		glVertex3f(0.5f, -0.5f, -0.5f);
		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(-0.5f, -0.5f, -0.5f);
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(-0.5f, 0.5f, -0.5f);
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(0.5f, 0.5f, -0.5f);
		glEnd();

		// Render the left quad
		clampToEdge();
		textures.get(1).bind();
		glBegin(GL_QUADS);
		glTexCoord2f(1.0f, 1.0f);
		glVertex3f(0.5f, -0.5f, 0.5f);
		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(0.5f, -0.5f, -0.5f);
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(0.5f, 0.5f, -0.5f);
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(0.5f, 0.5f, 0.5f);
		glEnd();

		// Render the back quad
		clampToEdge();
		textures.get(2).bind();
		glBegin(GL_QUADS);
		glTexCoord2f(1.0f, 1.0f);
		glVertex3f(-0.5f, -0.5f, 0.5f);
		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(0.5f, -0.5f, 0.5f);
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(0.5f, 0.5f, 0.5f);
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(-0.5f, 0.5f, 0.5f);
		glEnd();

		// Render the right quad
		clampToEdge();
		textures.get(3).bind();
		glBegin(GL_QUADS);
		glTexCoord2f(1.0f, 1.0f);
		glVertex3f(-0.5f, -0.5f, -0.5f);
		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(-0.5f, -0.5f, 0.5f);
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(-0.5f, 0.5f, 0.5f);
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(-0.5f, 0.5f, -0.5f);
		glEnd();

		// Render the top quad
		clampToEdge();
		textures.get(4).bind();
		glBegin(GL_QUADS);
		glTexCoord2f(1.0f, 1.0f);
		glVertex3f(-0.5f, 0.5f, -0.5f);
		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(-0.5f, 0.5f, 0.5f);
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(0.5f, 0.5f, 0.5f);
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(0.5f, 0.5f, -0.5f);
		glEnd();

		// Render the bottom quad
		clampToEdge();
		textures.get(5).bind();
		glBegin(GL_QUADS);
		glTexCoord2f(1.0f, 1.0f);
		glVertex3f(-0.5f, -0.5f, -0.5f);
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(-0.5f, -0.5f, 0.5f);
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(0.5f, -0.5f, 0.5f);
		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(0.5f, -0.5f, -0.5f);
		glEnd();

		// Restore enable bits and matrix
		glPopAttrib();
		glClear(GL_DEPTH_BUFFER_BIT);

		glPopMatrix();

	}

	// clamp textures, that edges get dont create a line in between
	private void clampToEdge() {
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	}

}
