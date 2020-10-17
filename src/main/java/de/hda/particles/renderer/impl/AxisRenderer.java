package de.hda.particles.renderer.impl;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.renderer.Renderer;
import org.lwjgl.input.Keyboard;

public class AxisRenderer extends AbstractRenderer implements Renderer {

	private final static float axisLength = 100000.0f;

	private Boolean blockVisiblitySelection = false;
	
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
	}

}
