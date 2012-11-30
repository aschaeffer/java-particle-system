package de.hda.particles.hud;

import de.hda.particles.renderer.types.RenderType;
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

	@SuppressWarnings("unchecked")
	@Override
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.ADD_RENDER_TYPE) {
			Class<? extends RenderType> clazz = (Class<? extends RenderType>) command.getPayLoad(); 
			scene.getParticleSystem().beginModification();
			scene.getRenderTypeManager().add(clazz);
			scene.getParticleSystem().endModification();
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Added Render Type: " + clazz.getSimpleName()));
		}
	}

}
