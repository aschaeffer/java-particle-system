package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AxisRenderer extends AbstractRenderer implements Renderer {

	private final static float axisLength = 100000.0f;

	private Boolean blockVisiblitySelection = false;
	
	private final Logger logger = LoggerFactory.getLogger(AxisRenderer.class);

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
        glVertex4f (-axisLength, 0.0f, 0.0f, 1.0f);
        glVertex4f (axisLength, 0.0f, 0.0f, 1.0f);
        glColor4f (0.75f, 0.75f, 0.5f, 0.8f);
        glVertex4f (axisLength, 0.0f, 0.0f, 1.0f);
        glVertex4f (-axisLength, 0.0f, 0.0f, 1.0f); // X
        glColor4f (1.0f, 0.5f, 1.0f, 0.8f);
        glVertex4f (0.0f, -axisLength, 0.0f, 1.0f);
        glVertex4f (0.0f, axisLength, 0.0f, 1.0f);
        glColor4f (0.75f, 0.5f, 0.75f, 0.8f);
        glVertex4f (0.0f, axisLength, 0.0f, 1.0f);
        glVertex4f (0.0f, -axisLength, 0.0f, 1.0f); // Y
        glColor4f (0.5f, 1.0f, 1.0f, 0.8f);
        glVertex4f (0.0f, 0.0f, -axisLength, 1.0f);
        glVertex4f (0.0f, 0.0f, axisLength, 1.0f);
        glColor4f (0.5f, 0.75f, 0.75f, 0.8f);
        glVertex4f (0.0f, 0.0f, axisLength, 1.0f);
        glVertex4f (0.0f, 0.0f, -axisLength, 1.0f); // Z
        glEnd ();

		glEnable(GL_DEPTH_TEST);

        glPopMatrix();
        
        renderTitle(new Vector3f(scene.getCameraManager().getPosition().x, 0.0f, 0.0f), "X", 0.0f, false);
        renderTitle(new Vector3f(0.0f, scene.getCameraManager().getPosition().y, 0.0f), "Y", 0.0f, false);
        renderTitle(new Vector3f(0.0f, 0.0f, scene.getCameraManager().getPosition().z), "Z", 0.0f, false);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void setup() {
		super.setup();
        // AWT Font in eine UnicodeFont von slick-util umwandeln
        font = new UnicodeFont(new Font("Arial", Font.BOLD, 24));
        font.getEffects().add(new ColorEffect(new java.awt.Color(0.4f, 0.4f, 0.4f)));
        font.addAsciiGlyphs();
        try {
           font.loadGlyphs();
        } catch (SlickException e) {
        	logger.error("could not load glyphs", e);
        }
	}

}
