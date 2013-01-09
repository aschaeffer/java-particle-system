package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;
import java.util.ListIterator;

import de.hda.particles.domain.FixedPoint;

/**
 * Renderer for fixed points.
 * 
 * @author aschaeffer
 *
 */
public class FixedPointRenderer extends AbstractRenderer implements Renderer {

	private List<FixedPoint> fixedPoints;
	private FixedPoint fixedPoint;

	public FixedPointRenderer() {}

	@Override
	public void update() {
		if (!visible) return;
		fixedPoints = scene.getParticleSystem().getFixedPoints();
		if (!fixedPoints.isEmpty()) {
			ListIterator<FixedPoint> fIterator = fixedPoints.listIterator(0);
			glPushMatrix();
			glPointSize(1.0f);
			glBegin(GL_POINTS);
			glColor4f(1.0f, 0.2f, 0.2f, 0.2f);
			while (fIterator.hasNext()) {
				fixedPoint = fIterator.next();
				if (fixedPoint != null) {
					glVertex3f(fixedPoint.getX(), fixedPoint.getY(), fixedPoint.getZ());
				}
			}
			glEnd();
	        glPopMatrix();
		}
	}

}
