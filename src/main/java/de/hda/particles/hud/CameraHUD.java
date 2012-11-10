package de.hda.particles.hud;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.camera.Camera;

public class CameraHUD extends AbstractHUD implements HUD {

	Camera camera;

	public CameraHUD(Camera camera, Integer width, Integer height) {
		super(width, height);
		this.camera = camera;
	}

	@Override
	public void update() {
	    Vector3f position = camera.getPosition();
        font.drawString(10, 10, String.format("%s x:%.2f y:%.2f z:%.2f yaw:%.2f pitch:%.2f roll:%.2f", camera.getName(), position.x, position.y, position.z, camera.getYaw(), camera.getPitch(), camera.getRoll()));
	}

	@Override
	public void setup() {
		super.setup();
	}
}
