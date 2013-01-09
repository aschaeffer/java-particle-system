package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ListIterator;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.MassSpring;

/**
 * Renderer for springs between two particles.
 * 
 * @author aschaeffer
 *
 */
public class SpringRenderer extends AbstractRenderer implements Renderer {

	/**
	 * If true, springs are rendered in green for pushing springs and red
	 * for pulling springs.
	 */
	private final static Boolean COLORED_SPRING_STRENGTH = true;

	private Particle connectedParticle;
	private Float springLength = 1.0f;
	private Float distance = 1.0f;
	private Float dx = 0.0f;
	private Float dy= 0.0f;
	private Float dz= 0.0f;
	
	public SpringRenderer() {}

	@SuppressWarnings("unchecked")
	@Override
	public void update() {
		try {
			if (!visible) return;
			glPushMatrix();
			glEnable(GL_BLEND);
			glLineWidth(3.0f);
			glBegin(GL_LINES);
			glColor4f(1.0f, 1.0f, 1.0f, 0.1f);
			ListIterator<Particle> iterator = scene.getParticleSystem().getParticles().listIterator(0);
			while (iterator.hasNext()) {
				Particle originParticle = iterator.next();
				if (originParticle != null) {
					List<Particle> connectedParticles = (List<Particle>) originParticle.get(MassSpring.SPRING_CONNECTED_PARTICLES);
					if (connectedParticles == null) continue;
					if (COLORED_SPRING_STRENGTH) {
						springLength = ((Double) originParticle.get(MassSpring.SPRING_LENGTH)).floatValue();
					}
					ListIterator<Particle> connectedParticlesIterator = connectedParticles.listIterator();
					while (connectedParticlesIterator.hasNext()) {
						connectedParticle = connectedParticlesIterator.next();
						if (COLORED_SPRING_STRENGTH) {
							dx = connectedParticle.getX() - originParticle.getX();
							dy = connectedParticle.getY() - originParticle.getY();
							dz = connectedParticle.getZ() - originParticle.getZ();
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
						glVertex3f(originParticle.getX(), originParticle.getY(), originParticle.getZ());
						glVertex3f(connectedParticle.getX(), connectedParticle.getY(), connectedParticle.getZ());
					}
				}
			}
			// TODO: render fixed points springs (maybe)
			glEnd();
	        glPopMatrix();
		} catch (ConcurrentModificationException e) {
			// just catch! (we are just reading)
		}
	}

}
