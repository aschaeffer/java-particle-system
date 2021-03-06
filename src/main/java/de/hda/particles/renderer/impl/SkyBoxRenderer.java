package de.hda.particles.renderer.impl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import de.hda.particles.renderer.Renderer;
import org.newdawn.slick.opengl.Texture;

import de.hda.particles.hud.impl.HUDCommand;
import de.hda.particles.hud.impl.HUDCommandTypes;
import de.hda.particles.textures.DeferredTextureLoaderCallback;

public class SkyBoxRenderer extends AbstractRenderer implements Renderer, DeferredTextureLoaderCallback {

	private static final String DEFAULT_SKYBOX_NAME = "sleepyhollow";
//	private static final String DEFAULT_SKYBOX_FORMAT = "JPG";

	private final Texture[] textures = new Texture[6]; // = new ArrayList<Texture>();
	private Boolean deferedLoading = true;
	private String name = DEFAULT_SKYBOX_NAME;
//	private final String format = DEFAULT_SKYBOX_FORMAT;
	
	private Boolean ready = false;

	public SkyBoxRenderer() {
		for (Integer i=0; i<6; i++)
			textures[i] = null;
	}

	@Override
	public void setup() {
		loadSkybox(DEFAULT_SKYBOX_NAME);
	}

	@Override
	public void update() {
		
		if (!ready) checkReady();
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

		glActiveTexture(GL_TEXTURE0);

		// Render the front quad
		// clampToEdge();
		textures[0].bind();
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
		textures[1].bind();
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
		textures[2].bind();
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
		textures[3].bind();
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
		textures[4].bind();
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
		textures[5].bind();
		glBegin(GL_QUADS);
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(-0.5f, -0.5f, -0.5f);
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(-0.5f, -0.5f, 0.5f);
		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(0.5f, -0.5f, 0.5f);
		glTexCoord2f(1.0f, 1.0f);
		glVertex3f(0.5f, -0.5f, -0.5f);
		glEnd();

		// Restore enable bits and matrix
		glEnable(GL_DEPTH_TEST);
		glPopAttrib();

		glPopMatrix();
	}

	// clamp textures, that edges get dont create a line in between
	private void clampToEdge() {
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	}
	
	public void loadSkybox(String name) {
		this.name = name;
		if (deferedLoading) {
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Loading SkyBox"));
			scene.getTextureManager().loadDeferred("ft", name, this, "JPG", "images/skybox/" + name + "_ft.jpg");
			scene.getTextureManager().loadDeferred("lf", name, this, "JPG", "images/skybox/" + name + "_lf.jpg");
			scene.getTextureManager().loadDeferred("bk", name, this, "JPG", "images/skybox/" + name + "_bk.jpg");
			scene.getTextureManager().loadDeferred("rt", name, this, "JPG", "images/skybox/" + name + "_rt.jpg");
			scene.getTextureManager().loadDeferred("up", name, this, "JPG", "images/skybox/" + name + "_up.jpg");
			scene.getTextureManager().loadDeferred("dn", name, this, "JPG", "images/skybox/" + name + "_dn.jpg");
		} else {
			textures[0] = scene.getTextureManager().load("JPG", "images/skybox/" + name + "_ft.jpg");
			textures[1] = scene.getTextureManager().load("JPG", "images/skybox/" + name + "_lf.jpg");
			textures[2] = scene.getTextureManager().load("JPG", "images/skybox/" + name + "_bk.jpg");
			textures[3] = scene.getTextureManager().load("JPG", "images/skybox/" + name + "_rt.jpg");
			textures[4] = scene.getTextureManager().load("JPG", "images/skybox/" + name + "_up.jpg");
			textures[5] = scene.getTextureManager().load("JPG", "images/skybox/" + name + "_dn.jpg");
		}
	}
	
	public void clearSkybox() {
		ready = false;
		for (Integer i=0; i<6; i++)
			textures[i] = null;
	}
	
	private void checkReady() {
		if (deferedLoading) {
			ready = true;
			for (Integer i=0; i<6; i++)
				if (textures[i] == null) ready = false;
			if (ready)
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "SkyBox Loaded"));
		} else {
			loadSkybox(name);
			ready = true;
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "SkyBox Loaded"));
		}
	}

	@Override
	public void setTexture(String key, Texture object) {
		if ("ft".equals(key)) {
			textures[0] = object;
		} else if ("lf".equals(key)) {
			textures[1] = object;
		} else if ("bk".equals(key)) {
			textures[2] = object;
		} else if ("rt".equals(key)) {
			textures[3] = object;
		} else if ("up".equals(key)) {
			textures[4] = object;
		} else if ("dn".equals(key)) {
			textures[5] = object;
		}
	}
	
	@Override
	public void reportTextureLoadingError(String name) {
		deferedLoading = false;
		this.name = name;
	}

}
