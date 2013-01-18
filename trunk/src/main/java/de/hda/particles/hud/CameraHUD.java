package de.hda.particles.hud;

import org.lwjgl.input.Keyboard;

import de.hda.particles.camera.Camera;
import de.hda.particles.camera.FirstPersonCamera;
import de.hda.particles.scene.Scene;

public class CameraHUD extends AbstractHUD implements HUD {

	private final static Integer MARGIN = 10;
	private Boolean blockCameraSelection = false;
	private Boolean blockCameraFlySelection = false;
	private Boolean blockCameraResetSelection = false;
	
	private Camera camera;

	public CameraHUD() {}

	public CameraHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void update() {
		camera = scene.getCameraManager();
		String text = String.format("%s x:%.1f y:%.1f z:%.1f yaw:%.1f pitch:%.1f roll:%.1f fov:%.1f", camera.getName(), camera.getX(), camera.getY(), camera.getZ(), camera.getYaw(), camera.getPitch(), camera.getRoll(), camera.getFov());
        font.drawString(scene.getWidth() - font.getWidth(text) - MARGIN, MARGIN, text);
	}
	
	@Override
	public void input() {
		// camera selection
		if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
			if (!blockCameraSelection) {
				scene.getCameraManager().selectNextCamera();
				addNotice("Selected Camera \"" + scene.getCameraManager().getName() + "\"");
				blockCameraSelection = true;
			}
		} else {
			blockCameraSelection = false;
		}
		// camera mode selection
		if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
			if (!blockCameraFlySelection) {
				scene.getCameraManager().nextMode();
				addNotice("Selected Camera Mode \"" + scene.getCameraManager().getModeName() + "\"");
				blockCameraFlySelection = true;
			}
		} else {
			blockCameraFlySelection = false;
		}
		// set camera position to origin
		if (Keyboard.isKeyDown(Keyboard.KEY_0)) {
			if (!blockCameraResetSelection) {
				scene.getCameraManager().reset();
				addNotice("Reset Camera Position");
				blockCameraResetSelection = true;
			}
		} else {
			blockCameraResetSelection = false;
		}
	}

	@Override
	public void setup() {
		super.setup();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.ADD_CAMERA) {
			if (command.getPayLoad() == null) {
				scene.getCameraManager().add(new FirstPersonCamera("new cam", scene, scene.getCameraManager().getPosition()));
			} else {
				scene.getCameraManager().add((Class<? extends Camera>) command.getPayLoad());
			}
		}
		if (command.getType() == HUDCommandTypes.REMOVE_CAMERA) {
			scene.getCameraManager().removeSelectedCamera();
		}
	}

}
