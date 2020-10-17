package de.hda.particles.renderer.impl;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.renderer.Renderer;
import java.util.ListIterator;

import org.lwjgl.util.glu.Sphere;

import de.hda.particles.domain.impl.configuration.ParticleModifierConfiguration;
import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.modifier.PositionablePointModifier;
import de.hda.particles.modifier.impl.gravity.BlackHole;
import de.hda.particles.modifier.impl.gravity.GravityBase;

public class BlackHoleRenderer extends AbstractSelectable<BlackHole> implements Renderer {

	public BlackHoleRenderer() {}

	@Override
	public void update() {
		if (!visible) return;
		ListIterator<ParticleModifier> pIterator = scene.getParticleSystem().getParticleModifiers().listIterator(0);
		while (pIterator.hasNext()) {
			ParticleModifier modifier = pIterator.next();
			if (modifier != null) {
				if (modifier.getClass().equals(BlackHole.class)) {
					ParticleModifierConfiguration configuration = modifier.getConfiguration();
					Float positionX = new Float((Double) configuration.get(PositionablePointModifier.POSITION_X));
					Float positionY = new Float((Double) configuration.get(PositionablePointModifier.POSITION_Y));
					Float positionZ = new Float((Double) configuration.get(PositionablePointModifier.POSITION_Z));
					Float gravity = new Float((Double) configuration.get(GravityBase.GRAVITY));
					Float mass = new Float((Double) configuration.get(GravityBase.MASS));
					Float eventHorizon = gravity * mass / BlackHole.EVENT_HORIZON_FACTOR.floatValue();
					glPushMatrix();
					glColor4f(1.0f, 0.6f, 0.0f, 0.3f);
			        glTranslatef(positionX, positionY, positionZ);
			        Sphere s = new Sphere();
			        s.draw(eventHorizon, 16, 16);
					glPopMatrix();
				}
			}
		}
	}

}
