package de.hda.particles.scene;

import de.hda.particles.camera.CameraManager;
import de.hda.particles.hud.HUDManager;
import de.hda.particles.renderer.RendererManager;
import de.hda.particles.timing.FpsLimiter;

public abstract class AbstractScene extends FpsLimiter implements Scene {

	protected HUDManager hudManager;
	protected CameraManager cameraManager;
	protected RendererManager rendererManager;

	protected Integer width = 800;
	protected Integer height = 600;
	protected Float fov = 90.0f;
	protected Boolean fullscreen = false;
	protected Boolean running = true;

	public AbstractScene(Integer width, Integer height) {
		this.width = width;
		this.height = height;
	}
	
	public void update() {
		calcFps();
		limitFps();
	}
	
	public void setFov(Float fov) {
		if (fov > 150.0f || fov < 15.0f) return;
		this.fov = fov;
	}

}
