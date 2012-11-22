package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class AxisRenderer extends AbstractRenderer implements Renderer {

	private final static float axisLength = 100000.0f;

	private Boolean blockVisiblitySelection = false;
	
	private UnicodeFont font;

	public AxisRenderer() {}

	@Override
	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
			if (!blockVisiblitySelection) {
				visible = !visible;
				blockVisiblitySelection = true;
			}
		} else {
			blockVisiblitySelection = false;
		}

		if (!visible) return;

		// Axis
		glPushMatrix();
		
		glDisable(GL_DEPTH_TEST);

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

		glEnable(GL_DEPTH_TEST);

        glPopMatrix();

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
