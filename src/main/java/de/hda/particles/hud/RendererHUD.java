package de.hda.particles.hud;

import de.hda.particles.renderer.SkyBoxRenderer;
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
		} else if (command.getType() == HUDCommandTypes.SHOW_RENDERER) {
			scene.getRendererManager().setVisibility((Class) command.getPayLoad(), true);
		} else if (command.getType() == HUDCommandTypes.HIDE_RENDERER) {
			scene.getRendererManager().setVisibility((Class) command.getPayLoad(), false);
		} else if (command.getType() == HUDCommandTypes.SELECT_SKYBOX) {
			SkyBoxRenderer skyBoxRenderer = (SkyBoxRenderer) scene.getRendererManager().getRendererByClass(SkyBoxRenderer.class);
			if (skyBoxRenderer != null) {
				skyBoxRenderer.loadSkybox((String) command.getPayLoad());
			}
		}
	}

}
