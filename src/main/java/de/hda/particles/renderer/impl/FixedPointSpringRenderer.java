package de.hda.particles.renderer.impl;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.renderer.Renderer;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.math3.util.Pair;

import de.hda.particles.domain.FixedPoint;
import de.hda.particles.domain.Particle;

/**
 * Renderer for springs from fixed points to particles.
 * 
 * @author aschaeffer
 *
 */
public class FixedPointSpringRenderer extends AbstractRenderer implements Renderer {

	/**
	 * If true, springs are rendered in green for pushing springs and red
	 * for pulling springs.
	 */
	private final static Boolean COLORED_SPRING_STRENGTH = true;

	private List<FixedPoint> fixedPoints;
	private FixedPoint fixedPoint;
	private List<Pair<Particle, Float>> connectedParticles;
	private Particle connectedParticle;

	private Float springLength = 1.0f;
	private Float distance = 1.0f;
	private Float dx = 0.0f;
	private Float dy= 0.0f;
	private Float dz= 0.0f;
	
	public FixedPointSpringRenderer() {}

	@Override
	public void update() {
		try {
			if (!visible) return;
			glPushMatrix();
			glEnable(GL_BLEND);
			glLineWidth(3.0f);
			glBegin(GL_LINES);
			glColor4f(1.0f, 1.0f, 1.0f, 0.1f);
			fixedPoints = scene.getParticleSystem().getFixedPoints();
			if (!fixedPoints.isEmpty()) {
				ListIterator<FixedPoint> fIterator = fixedPoints.listIterator(0);
				while (fIterator.hasNext()) {
					fixedPoint = fIterator.next();
					if (fixedPoint != null) {
						connectedParticles = fixedPoint.getSprings();
						ListIterator<Pair<Particle, Float>> springsIterator = connectedParticles.listIterator(0);
						while (springsIterator.hasNext()) {
							Pair<Particle, Float> spring = springsIterator.next();
							connectedParticle = spring.getKey();
							springLength = spring.getValue();
							if (COLORED_SPRING_STRENGTH) {
								dx = connectedParticle.getX() - fixedPoint.getX();
								dy = connectedParticle.getY() - fixedPoint.getY();
								dz = connectedParticle.getZ() - fixedPoint.getZ();
								distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
								if (Math.abs(springLength - distance) < 1.0) {
									glColor4f(1.0f, 1.0f, 1.0f, 0.1f);
								} else if (distance < springLength) {
									// shorter than normal: green
									glColor4f(0.0f, 1.0f - (distance / springLength), 0.0f, 0.2f);
								} else {
									// longer than normal: red
									glColor4f(1.0f - (springLength / distance), 0.0f, 0.0f, 0.2f);
								}
							}
							glVertex3f(fixedPoint.getX(), fixedPoint.getY(), fixedPoint.getZ());
							glVertex3f(connectedParticle.getX(), connectedParticle.getY(), connectedParticle.getZ());
						}
					}
				}
			}
			glEnd();
	        glPopMatrix();
		} catch (ConcurrentModificationException e) {
			// just catch! (we are just reading)
		}
	}

}
