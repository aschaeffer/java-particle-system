package de.hda.particles.hud;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.camera.Camera;
import de.hda.particles.camera.FirstPersonCamera;
import de.hda.particles.scene.Scene;

public class CameraHUD extends AbstractHUD implements HUD {

	private Boolean blockCameraSelection = false;
	private Boolean blockCameraFlySelection = false;
	private Boolean blockCameraResetSelection = false;

	public CameraHUD() {}

	public CameraHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void update() {
		// camera selection
		// Keyboard.next();
		if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
			if (!blockCameraSelection) {
				scene.getCameraManager().selectNextCamera();
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Selected Camera \"" + scene.getCameraManager().getName() + "\""));
				blockCameraSelection = true;
			}
		} else {
			blockCameraSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
			if (!blockCameraFlySelection) {
				scene.getCameraManager().nextMode();
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Selected Camera Mode \"" + scene.getCameraManager().getModeName() + "\""));
				blockCameraFlySelection = true;
			}
		} else {
			blockCameraFlySelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_0)) {
			if (!blockCameraResetSelection) {
				scene.getCameraManager().reset();
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Reset Camera Position"));
				blockCameraResetSelection = true;
			}
		} else {
			blockCameraResetSelection = false;
		}
		
		Camera camera = scene.getCameraManager();
	    Vector3f position = camera.getPosition();
        font.drawString(10, 10, String.format("%s x:%.2f y:%.2f z:%.2f yaw:%.2f pitch:%.2f roll:%.2f fov:%.2f", camera.getName(), position.x, position.y, position.z, camera.getYaw(), camera.getPitch(), camera.getRoll(), camera.getFov()));
	}

	@Override
	public void setup() {
		super.setup();
	}

	@Override
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.ADD_CAMERA) {
			Camera camera = new FirstPersonCamera("new cam", scene, scene.getCameraManager().getPosition());
			scene.getCameraManager().add(camera);
		}
		if (command.getType() == HUDCommandTypes.REMOVE_CAMERA) {
			scene.getCameraManager().removeSelectedCamera();
		}
	}

}
