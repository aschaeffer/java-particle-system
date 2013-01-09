package de.hda.particles.hud;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.renderer.SkyBoxRenderer;
import de.hda.particles.scene.Scene;

public class RendererHUD extends AbstractHUD implements HUD {

	private Boolean blockFullscreenSelection = false;
//	private final Boolean blockLoadSceneSelection = false;
//	private final Boolean blockSaveSceneSelection = false;

	private final Logger logger = LoggerFactory.getLogger(RendererHUD.class);

	public RendererHUD() {}

	public RendererHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void input() {
		if (Display.isCloseRequested()) {
			logger.debug("window close requested");
			scene.setRunning(false);
			return;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
			if (!blockFullscreenSelection) {
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.TOGGLE_FULLSCREEN));
				blockFullscreenSelection = true;
			}
		} else {
			blockFullscreenSelection = false;
		}
//		if (Keyboard.isKeyDown(Keyboard.KEY_F1)) {
//		if (!blockSaveSceneSelection) {
//			saveDialog();
//			blockSaveSceneSelection = true;
//		}
//	} else {
//		blockSaveSceneSelection = false;
//	}
//	if (Keyboard.isKeyDown(Keyboard.KEY_F2)) {
//		if (!blockLoadSceneSelection) {
//			loadDialog();
//			blockLoadSceneSelection = true;
//		}
//	} else {
//		blockLoadSceneSelection = false;
//	}
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
				skyBoxRenderer.clearSkybox();
				skyBoxRenderer.loadSkybox((String) command.getPayLoad());
			}
		} else if (command.getType() == HUDCommandTypes.DISPLAY_SIZE) {
			scene.setWidth((Integer) command.getPayLoad());
			scene.setHeight((Integer) command.getPayLoad2());
			scene.applyChanges();
		} else if (command.getType() == HUDCommandTypes.TOGGLE_FULLSCREEN) {
			scene.setFullscreen(!scene.getFullscreen());
			scene.applyChanges();
		}
	}

	@Override
	public void update() {}
	
}
