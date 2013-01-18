package de.hda.particles.hud;

import de.hda.particles.renderer.faces.FaceRenderer;
import de.hda.particles.scene.Scene;

public class FaceRendererHUD extends AbstractHUD implements HUD {

	public FaceRendererHUD() {}

	public FaceRendererHUD(Scene scene) {
		super(scene);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.ADD_FACE_RENDERER) {
			Class<? extends FaceRenderer> clazz = (Class<? extends FaceRenderer>) command.getPayLoad(); 
			scene.getParticleSystem().beginModification();
			scene.getFaceRendererManager().add(clazz);
			scene.getParticleSystem().endModification();
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Added Face Renderer: " + clazz.getSimpleName()));
		}
		if (command.getType() == HUDCommandTypes.REMOVE_FACE_RENDERER) {
			Integer index = (Integer) command.getPayLoad();
			scene.getParticleSystem().beginModification();
			scene.getFaceRendererManager().remove(index);
			scene.getParticleSystem().endModification();
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Removed face renderer on index: " + index));
		}
	}

	@Override
	public void update() {}

	@Override
	public void setup() {}

}
