package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.modifier.GravityPoint;
import de.hda.particles.modifier.ParticleModifier;

public class GravityPointRenderer extends AbstractRenderer implements Renderer {

	public GravityPointRenderer() {}

	@Override
	public void update() {
		List<ParticleModifier> currentModifiers = new ArrayList<ParticleModifier>(scene.getParticleSystem().modifiers);
		ListIterator<ParticleModifier> pIterator = currentModifiers.listIterator(0);
		while (pIterator.hasNext()) {
			ParticleModifier modifier = pIterator.next();
			if (modifier != null) {
				if (modifier.getClass().equals(GravityPoint.class)) {
					ParticleModifierConfiguration configuration = modifier.getConfiguration();
					Vector3f point = (Vector3f) configuration.get(GravityPoint.POINT);
					glPushMatrix();
					glColor4f(2.0f, 0.3f, 9.3f, 0.8f);
			        glTranslatef(point.x, point.y, point.z);
			        Sphere s = new Sphere();
			        s.draw(10.0f, 16, 16);
					glPopMatrix();
				}
			}
		}
	}

}
