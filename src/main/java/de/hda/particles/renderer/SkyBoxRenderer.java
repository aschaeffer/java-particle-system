package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ENABLE_BIT;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.opengl.Texture;

public class SkyBoxRenderer extends AbstractRenderer implements Renderer {

	private static final String DEFAULT_SKYBOX_NAME = "sleepyhollow";

	private final List<Texture> textures = new ArrayList<Texture>();
	
	private Boolean ready = false;

	public SkyBoxRenderer() {}

	@Override
	public void setup() {
		loadSkybox(DEFAULT_SKYBOX_NAME);
	}

	@Override
	public void update() {
		
		if (!ready || !visible) return;

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
		glEnable(GL_DEPTH_TEST);
		glPopAttrib();
		glClear(GL_DEPTH_BUFFER_BIT);

		glPopMatrix();

	}

	// clamp textures, that edges get dont create a line in between
	private void clampToEdge() {
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	}
	
	public void loadSkybox(String name) {
		ready = false;
		textures.clear();
		textures.add(scene.getTextureManager().load("JPG", "images/skybox/" + name + "_ft.jpg"));
		textures.add(scene.getTextureManager().load("JPG", "images/skybox/" + name + "_lf.jpg"));
		textures.add(scene.getTextureManager().load("JPG", "images/skybox/" + name + "_bk.jpg"));
		textures.add(scene.getTextureManager().load("JPG", "images/skybox/" + name + "_rt.jpg"));
		textures.add(scene.getTextureManager().load("JPG", "images/skybox/" + name + "_up.jpg"));
		textures.add(scene.getTextureManager().load("JPG", "images/skybox/" + name + "_dn.jpg"));
		ready = true;
	}

}
