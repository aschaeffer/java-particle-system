package de.hda.particles.hud;

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.ADD_RENDERER) {
			scene.getParticleSystem().beginModification();
			scene.getRendererManager().add((Class) command.getPayLoad());
			scene.getParticleSystem().endModification();
		}
	}

}
