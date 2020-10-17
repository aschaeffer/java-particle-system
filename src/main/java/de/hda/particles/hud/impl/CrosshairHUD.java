package de.hda.particles.hud.impl;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import de.hda.particles.hud.HUD;
import de.hda.particles.scene.Scene;

public class CrosshairHUD extends AbstractHUD implements HUD {

	private final static Float SIZE = 12.0f;
	private final static Float WIDTH = 2.0f;
	
	public CrosshairHUD() {}

	public CrosshairHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void update() {
	    glTranslatef(0.375f, 0.375f, 0.0f);
	    glPushMatrix();
		glEnable(GL_BLEND);
		glLineWidth(WIDTH*2);
        glBegin(GL_LINES);
        glColor4f(0.0f, 0.0f, 0.0f, 0.75f);
	    glVertex2f(scene.getWidth() / 2.0f - SIZE - 1, scene.getHeight() / 2.0f);
	    glVertex2f(scene.getWidth() / 2.0f + SIZE + 1, scene.getHeight() / 2.0f);
	    glVertex2f(scene.getWidth() / 2.0f, scene.getHeight() / 2.0f - SIZE - 1);
	    glVertex2f(scene.getWidth() / 2.0f, scene.getHeight() / 2.0f + SIZE + 1);
	    glEnd();
		glLineWidth(WIDTH);
        glBegin(GL_LINES);
        glColor4f(1.0f, 1.0f, 1.0f, 0.75f);
	    glVertex2f(scene.getWidth() / 2.0f - SIZE, scene.getHeight() / 2.0f);
	    glVertex2f(scene.getWidth() / 2.0f + SIZE, scene.getHeight() / 2.0f);
	    glVertex2f(scene.getWidth() / 2.0f, scene.getHeight() / 2.0f - SIZE);
	    glVertex2f(scene.getWidth() / 2.0f, scene.getHeight() / 2.0f + SIZE);
	    glEnd();
	    glPopMatrix();
	}

	@Override
	public void setup() {
		super.setup();
	}

}
