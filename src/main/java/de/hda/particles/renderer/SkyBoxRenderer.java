package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import de.hda.particles.camera.Camera;

public class SkyBoxRenderer extends AbstractRenderer implements Renderer {

	private static final String SKYBOX_NAME = "sleepyhollow";

	private List<Texture> textures = new ArrayList<Texture>();
	private Camera camera;

	public SkyBoxRenderer(Camera camera) {
		this.camera = camera;
	}
	
	@Override
	public void setup() {
		// load textures
		try {
			textures.add(TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("images/skybox/" + SKYBOX_NAME + "_ft.jpg"), GL_LINEAR));
			textures.add(TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("images/skybox/" + SKYBOX_NAME + "_lf.jpg"), GL_LINEAR));
			textures.add(TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("images/skybox/" + SKYBOX_NAME + "_bk.jpg"), GL_LINEAR));
			textures.add(TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("images/skybox/" + SKYBOX_NAME + "_rt.jpg"), GL_LINEAR));
			textures.add(TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("images/skybox/" + SKYBOX_NAME + "_up.jpg"), GL_LINEAR));
			textures.add(TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("images/skybox/" + SKYBOX_NAME + "_dn.jpg"), GL_LINEAR));
		} catch (IOException e) {
			throw new RuntimeException("skybox loading error");
		}
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
		glRotatef(this.camera.getPitch(), 1.0f, 0.0f, 0.0f);
		// rotate the yaw around the Y axis
		glRotatef(this.camera.getYaw(), 0.0f, 1.0f, 0.0f);
		// rotate the yaw around the Y axis
		glRotatef(this.camera.getRoll(), 0.0f, 0.0f, 1.0f);
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
