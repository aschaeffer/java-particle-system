package de.hda.particles.hud;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.camera.Camera;
import de.hda.particles.scene.Scene;

public class CameraHUD extends AbstractHUD implements HUD {

	public CameraHUD() {}

	public CameraHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void update() {
		Camera camera = scene.getCameraManager();
	    Vector3f position = camera.getPosition();
        font.drawString(10, 10, String.format("%s x:%.2f y:%.2f z:%.2f yaw:%.2f pitch:%.2f roll:%.2f fov:%.2f", camera.getName(), position.x, position.y, position.z, camera.getYaw(), camera.getPitch(), camera.getRoll(), camera.getFov()));
	}

	@Override
	public void setup() {
		super.setup();
	}
}
