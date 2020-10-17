package de.hda.particles.camera.impl;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.camera.Camera;
import java.util.List;

import org.lwjgl.util.glu.GLU;

import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.modifier.PositionablePointModifier;

/**
 * The ThirdPersonSpectatorCamera looks at a modifier like a gravity point.
 */
public class ThirdPersonSpectatorCamera extends FirstPersonCamera implements Camera {

	/**
	 * The modifier to look at.
	 */
	private PositionablePointModifier currentModifier;
	private Integer currentModifierIndex = 0;

	public ThirdPersonSpectatorCamera() {}

	@Override
	public void update() {
    updateTime();

		updateCurrentModifier();

		updateMovementModifier();

		move();

		zoom();

		// look through the camera before you draw anything
		lookThrough();

		// reduce roll
		reduceRoll();
	}

	// Rotates through the available modifiers.
	@Override
	public void nextMode() {
		getNextModifier();
	}

	@Override
	public String getModeName() {
		if (currentModifier == null) return "Not following anything";
		return "Following " + currentModifier.getClass().getSimpleName();
	}

	public void updateCurrentModifier() {
		if (currentModifier == null) {
			getNextModifier();
			return;
		} else if (currentModifierIndex >= scene.getParticleSystem().getParticleModifiers().size()) {
			getNextModifier();
		}
	}

	public void getNextModifier() {
		currentModifier = null;
		currentModifierIndex++;
		List<ParticleModifier> modifiers = scene.getParticleSystem().getParticleModifiers();
		if (currentModifierIndex >= modifiers.size()) {
			currentModifierIndex = 0;
		}
		if (modifiers.get(currentModifierIndex) instanceof PositionablePointModifier) {
			currentModifier = (PositionablePointModifier) modifiers.get(currentModifierIndex);
		}
	}

	@Override
	public void lookThrough() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(fov, (float) scene.getWidth() / (float) scene.getHeight(), scene.getNearPlane(), scene.getFarPlane());
		// set the modelview matrix back to the identity
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		GLU.gluLookAt(
			position.x, position.y, position.z,
			currentModifier.getX()-position.x, currentModifier.getY()-position.y, currentModifier.getZ()-position.z,
			0.0f, -1.0f, 0.0f
		);
	}

}
