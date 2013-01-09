package de.hda.particles.camera;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.glu.GLU;

import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.modifier.PositionablePointModifier;

public class ThirdPersonSpectatorCamera extends FirstPersonCamera implements Camera {

	private PositionablePointModifier currentModifier;
	private Integer currentModifierIndex = 0;

	public ThirdPersonSpectatorCamera() {}

	@Override
	public void update() {
		time = Sys.getTime();
		deltaTime = (time - lastTime) / 1000.0f;
		lastTime = time;

		if (currentModifier == null) {
			getNextModifier();
			return;
		} else if (currentModifierIndex >= scene.getParticleSystem().getParticleModifiers().size()) {
			getNextModifier();
		}

		// when passing in the distance to move
		// we times the movementSpeed with dt this is a time scale
		// so if its a slow frame u move more then a fast frame
		// so on a slow computer you move just as fast as on a fast computer
		movementModifier = 1.0f;

		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			movementModifier = movementModifier * 10.0f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			movementModifier = movementModifier * 10.0f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			walkForward(movementModifier * movementSpeed * deltaTime);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			walkBackwards(movementModifier * movementSpeed * deltaTime);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			strafeLeft(movementModifier * movementSpeed * deltaTime);
			roll(-1.0f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			strafeRight(movementModifier * movementSpeed * deltaTime);
			roll(1.0f);
		}

		deltaWheel = Mouse.getDWheel();
		if (deltaWheel < 0) {
			zoom(movementModifier * -deltaWheel / 60.0f);
		} else if (deltaWheel > 0) {
			zoom(movementModifier * -deltaWheel / 60.0f);
		}

		// look through the camera before you draw anything
		lookThrough();

		// reduce roll
		reduceRoll();
	}

	@Override
	public void setup() {
	    Mouse.setGrabbed(true);
	}

	@Override
	public void destroy() {
	    Mouse.setGrabbed(false);
	}
	
	@Override
	public void nextMode() {
		getNextModifier();
	}

	@Override
	public String getModeName() {
		if (currentModifier == null) return "Not following anything";
		return "Following " + currentModifier.getClass().getSimpleName();
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
