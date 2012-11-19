package de.hda.particles.hud;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.ParticleEmitterConfiguration;
import de.hda.particles.emitter.*;
import de.hda.particles.camera.Camera;
import de.hda.particles.camera.FirstPersonCamera;
import de.hda.particles.scene.Scene;

public class RendererHUD extends AbstractHUD implements HUD {

	public RendererHUD() {}

	public RendererHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void update() {
	}

	@Override
	public void setup() {
		super.setup();
	}

	@Override
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.ADD_RENDERER) {
			scene.getParticleSystem().beginModification();
			scene.getRendererManager().add((Class) command.getPayLoad());
			scene.getParticleSystem().endModification();
		}
	}

}
