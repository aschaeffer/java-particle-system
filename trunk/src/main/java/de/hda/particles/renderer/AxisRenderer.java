package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class AxisRenderer extends AbstractRenderer implements Renderer {

	private final static float axisLength = 1000.0f;
	
	private Boolean activated = true;
	
	private UnicodeFont font;

	public AxisRenderer() {}

	@Override
	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_X)) activated = !activated;

		if (!activated) return;

		// Axis
		glPushMatrix();
		
		glPointSize(10.0f);
        glBegin(GL_POINTS);
        glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        glVertex3f(0.0f, 0.0f, 0.0f);
        glEnd();

		glLineWidth(3.0f);
        glBegin (GL_LINES);
        glColor4f (1.0f, 1.0f, 0.5f, 0.8f);
        glVertex3f (-axisLength, 0.0f, 0.0f);
        glVertex3f (axisLength, 0.0f, 0.0f);
        glColor4f (0.75f, 0.75f, 0.5f, 0.8f);
        glVertex3f (axisLength, 0.0f, 0.0f);
        glVertex3f (-axisLength, 0.0f, 0.0f); // X
        glColor4f (1.0f, 0.5f, 1.0f, 0.8f);
        glVertex3f (0.0f, -axisLength, 0.0f);
        glVertex3f (0.0f, axisLength, 0.0f);
        glColor4f (0.75f, 0.5f, 0.75f, 0.8f);
        glVertex3f (0.0f, axisLength, 0.0f);
        glVertex3f (0.0f, -axisLength, 0.0f); // Y
        glColor4f (0.5f, 1.0f, 1.0f, 0.8f);
        glVertex3f (0.0f, 0.0f, -axisLength);
        glVertex3f (0.0f, 0.0f, axisLength);
        glColor4f (0.5f, 0.75f, 0.75f, 0.8f);
        glVertex3f (0.0f, 0.0f, axisLength);
        glVertex3f (0.0f, 0.0f, -axisLength); // Z
        glEnd ();

//        glLoadIdentity();
//        glMatrixMode(GL_PROJECTION); 

        // glTranslatef(100.0f, 0, 0);
        // font.drawString(0.0f, 0.0f, "X");
        glPopMatrix();
        
        // Text Axis

//      glRasterPos3f (axisLength, 0.0f, 0.0f);
//      font.drawString(0.0f, 0.0f, "X");
//      glRasterPos3f (-1000.0f, 0.0f, 0.0f);
//      font.drawString(0.0f, 0.0f, "-X");
//      glRasterPos3f (0.0f, 1000.0f, 0.0f);
//      font.drawString(0.0f, 0.0f, "Y");
//      glRasterPos3f (0.0f, -1000.0f, 0.0f);
//      font.drawString(0.0f, 0.0f, "-Y");
//      glRasterPos3f (0.0f, 0.0f, 1000.0f);
//      font.drawString(0.0f, 0.0f, "Z");
//      glRasterPos3f (0.0f, 0.0f, -1000.0f);
//      font.drawString(0.0f, 0.0f, "-Z");

	}

	@SuppressWarnings("unchecked")
	@Override
	public void setup() {
        // AWT Font in eine UnicodeFont von slick-util umwandeln
        font = new UnicodeFont(new Font("Arial", Font.BOLD, 12));
        font.getEffects().add(new ColorEffect(new java.awt.Color(0.8f, 0.2f, 0.8f)));
        font.addAsciiGlyphs();
        try {
           font.loadGlyphs();
        } catch (SlickException e) {
           e.printStackTrace();
        }
	}

}
