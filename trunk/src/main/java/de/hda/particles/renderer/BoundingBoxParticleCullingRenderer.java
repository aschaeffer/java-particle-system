package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;
import java.util.ListIterator;

import org.lwjgl.input.Keyboard;

import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.modifier.BoundingBoxParticleCulling;
import de.hda.particles.modifier.ParticleModifier;

public class BoundingBoxParticleCullingRenderer extends AbstractSelectable<BoundingBoxParticleCulling> implements Renderer {

	private Boolean blockVisibilitySelection = false;
	
	public BoundingBoxParticleCullingRenderer() {}

	@Override
	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
			if (!blockVisibilitySelection) {
				visible = !visible;
				blockVisibilitySelection = true;
			}
		} else {
			blockVisibilitySelection = false;
		}

		if (!visible) return;

		List<ParticleModifier> currentModifiers = scene.getParticleSystem().getParticleModifiers();
		ListIterator<ParticleModifier> pIterator = currentModifiers.listIterator(0);
		while (pIterator.hasNext()) {
			ParticleModifier modifier = pIterator.next();
			if (modifier != null) {
				if (modifier.getClass().equals(BoundingBoxParticleCulling.class)) {
					ParticleModifierConfiguration configuration = modifier.getConfiguration();
					Double bbMinX = (Double) configuration.get(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_X);
					Double bbMinY = (Double) configuration.get(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_Y);
					Double bbMinZ = (Double) configuration.get(BoundingBoxParticleCulling.BOUNDING_BOX_MIN_Z);
					Double bbMaxX = (Double) configuration.get(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_X);
					Double bbMaxY = (Double) configuration.get(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_Y);
					Double bbMaxZ = (Double) configuration.get(BoundingBoxParticleCulling.BOUNDING_BOX_MAX_Z);
					glPushMatrix();
					glColor4f(1.0f, 0.0f, 0.0f, 0.25f);
					glBegin(GL_LINE_LOOP);
					// FRONT
				    glVertex3d(bbMinX, bbMinY, bbMinZ);
				    glVertex3d(bbMaxX, bbMinY, bbMinZ);
				    glVertex3d(bbMaxX, bbMaxY, bbMinZ);
				    glVertex3d(bbMinX, bbMaxY, bbMinZ);
				    // BACK
				    glVertex3d(bbMaxX, bbMaxY, bbMaxZ);
				    glVertex3d(bbMinX, bbMaxY, bbMaxZ);
				    glVertex3d(bbMinX, bbMinY, bbMaxZ);
				    glVertex3d(bbMaxX, bbMinY, bbMaxZ);
					// RIGHT
				    glVertex3d(bbMaxX, bbMaxY, bbMaxZ);
				    glVertex3d(bbMaxX, bbMaxY, bbMinZ);
				    glVertex3d(bbMaxX, bbMinY, bbMinZ);
				    glVertex3d(bbMaxX, bbMinY, bbMaxZ);
					// LEFT
				    glVertex3d(bbMinX, bbMinY, bbMinZ);
				    glVertex3d(bbMinX, bbMaxY, bbMinZ);
				    glVertex3d(bbMinX, bbMaxY, bbMaxZ);
				    glVertex3d(bbMinX, bbMinY, bbMaxZ);
					// TOP
				    glVertex3d(bbMaxX, bbMaxY, bbMaxZ);
				    glVertex3d(bbMinX, bbMaxY, bbMaxZ);
				    glVertex3d(bbMinX, bbMaxY, bbMinZ);
				    glVertex3d(bbMaxX, bbMaxY, bbMinZ);
					// BOTTOM
				    glVertex3d(bbMinX, bbMinY, bbMinZ);
				    glVertex3d(bbMinX, bbMinY, bbMaxZ);
				    glVertex3d(bbMaxX, bbMinY, bbMaxZ);
				    glVertex3d(bbMaxX, bbMinY, bbMinZ);
				    glEnd();
				    glPopMatrix();
				}
			}
		}
	}

}
