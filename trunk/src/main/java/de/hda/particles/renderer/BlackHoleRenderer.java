package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.modifier.BlackHole;
import de.hda.particles.modifier.ParticleModifier;

public class BlackHoleRenderer extends AbstractRenderer implements Renderer {

	public BlackHoleRenderer() {}

	@Override
	public void update() {
		List<ParticleModifier> currentModifiers = new ArrayList<ParticleModifier>(scene.getParticleSystem().getParticleModifiers());
		ListIterator<ParticleModifier> pIterator = currentModifiers.listIterator(0);
		while (pIterator.hasNext()) {
			ParticleModifier modifier = pIterator.next();
			if (modifier != null) {
				if (modifier.getClass().equals(BlackHole.class)) {
					ParticleModifierConfiguration configuration = modifier.getConfiguration();
					Vector3f point = (Vector3f) configuration.get(BlackHole.POINT);
					Float gravity = (Float) configuration.get(BlackHole.GRAVITY);
					Float mass = (Float) configuration.get(BlackHole.MASS);
					Float eventHorizon = gravity * mass / BlackHole.EVENT_HORIZON_FACTOR;
					glPushMatrix();
					glColor4f(1.0f, 0.6f, 0.0f, 0.3f);
			        glTranslatef(point.x, point.y, point.z);
			        Sphere s = new Sphere();
			        s.draw(eventHorizon, 16, 16);
					glPopMatrix();
				}
			}
		}
	}

}
