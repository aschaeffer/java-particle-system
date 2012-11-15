package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.glu.Sphere;

import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.modifier.GravityPoint;
import de.hda.particles.modifier.ParticleModifier;

public class GravityPointRenderer extends AbstractRenderer implements Renderer {

	public GravityPointRenderer() {}

	@Override
	public void update() {
		List<ParticleModifier> currentModifiers = new ArrayList<ParticleModifier>(scene.getParticleSystem().getParticleModifiers());
		ListIterator<ParticleModifier> pIterator = currentModifiers.listIterator(0);
		while (pIterator.hasNext()) {
			ParticleModifier modifier = pIterator.next();
			if (modifier != null) {
				if (modifier.getClass().equals(GravityPoint.class)) {
					ParticleModifierConfiguration configuration = modifier.getConfiguration();
					Float gravityPointX = new Float((Double) configuration.get(GravityPoint.POINT_X));
					Float gravityPointY = new Float((Double) configuration.get(GravityPoint.POINT_Y));
					Float gravityPointZ = new Float((Double) configuration.get(GravityPoint.POINT_Z));
					glPushMatrix();
					glColor4f(2.0f, 0.3f, 9.3f, 0.8f);
			        glTranslatef(gravityPointX, gravityPointY, gravityPointZ);
			        Sphere s = new Sphere();
			        s.draw(10.0f, 16, 16);
					glPopMatrix();
				}
			}
		}
	}

}
