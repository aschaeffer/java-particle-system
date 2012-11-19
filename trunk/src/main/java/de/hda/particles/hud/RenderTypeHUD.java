package de.hda.particles.hud;

import de.hda.particles.scene.Scene;

public class RenderTypeHUD extends AbstractHUD implements HUD {

	public RenderTypeHUD() {}

	public RenderTypeHUD(Scene scene) {
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
		if (command.getType() == HUDCommandTypes.ADD_RENDER_TYPE) {
			scene.getParticleSystem().beginModification();
			scene.getRenderTypeManager().add((Class) command.getPayLoad());
			scene.getParticleSystem().endModification();
		}
	}

}
