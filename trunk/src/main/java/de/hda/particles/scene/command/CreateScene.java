package de.hda.particles.scene.command;

import java.util.List;

import de.hda.particles.ParticleSystem;
import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.scene.ConfigurableScene;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;
import de.hda.particles.scene.demo.DemoManager;
import de.hda.particles.timing.FpsInformation;

public class CreateScene implements Command {

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		List<ParticleSystem> particleSystems = context.getByType(ParticleSystem.class);
		if (particleSystems.size() > 0) {
			ConfigurableScene scene = new ConfigurableScene(particleSystems.get(0));
			if (configuration.containsKey(Scene.NAME)) {
				scene.setName((String) configuration.get(Scene.NAME));
			} else {
				scene.setName(Scene.DEFAULT_NAME);
			}
			if (configuration.containsKey(Scene.WIDTH)) {
				scene.setWidth((Integer) configuration.get(Scene.WIDTH));
			} else {
				scene.setWidth(Scene.DEFAULT_WIDTH);
			}
			if (configuration.containsKey(Scene.HEIGHT)) {
				scene.setHeight((Integer) configuration.get(Scene.HEIGHT));
			} else {
				scene.setHeight(Scene.DEFAULT_HEIGHT);
			}
			if (configuration.containsKey(Scene.FULLSCREEN)) {
				scene.setFullscreen((Boolean) configuration.get(Scene.FULLSCREEN));
			} else {
				scene.setFullscreen(Scene.DEFAULT_FULLSCREEN);
			}
			if (configuration.containsKey(Scene.VSYNC)) {
				scene.setVSync((Boolean) configuration.get(Scene.VSYNC));
			} else {
				scene.setVSync(Scene.DEFAULT_VSYNC);
			}
			if (configuration.containsKey("farPlane")) {
				scene.setFarPlane((Float) configuration.get("farPlane"));
			} else {
				scene.setFarPlane(Scene.DEFAULT_FAR_PLANE);
			}
			if (configuration.containsKey("nearPlane")) {
				scene.setNearPlane((Float) configuration.get("nearPlane"));
			} else {
				scene.setNearPlane(Scene.DEFAULT_NEAR_PLANE);
			}
			if (configuration.containsKey(FpsInformation.MAX_FPS)) {
				scene.setMaxFps((Integer) configuration.get(FpsInformation.MAX_FPS));
			} else {
				scene.setMaxFps(FpsInformation.DEFAULT_MAX_FPS);
			}
	        // init fps information instances
			scene.addFpsInformationInstance(scene);
			scene.addFpsInformationInstance(scene.getParticleSystem());
			scene.addFpsInformationInstance(context.getByType(DemoManager.class).get(0));
			// inject scene into managers
			scene.getCameraManager().setScene(scene);
			scene.getHudManager().setScene(scene);
			scene.getRenderTypeManager().setScene(scene);
			scene.getTextOverlayManager().setScene(scene);
			scene.getRendererManager().setScene(scene);
			// important: the first renderers have to be ordered
			scene.getRendererManager().insertAt(scene.getCameraManager(), 0);
			scene.getRendererManager().insertAt(scene.getTextOverlayManager(), 1);
			scene.getRendererManager().insertAt(scene.getRenderTypeManager(), 2);
			scene.getRendererManager().insertAt(scene.getFaceRendererManager(), 3);
			scene.getRendererManager().insertAt(scene.getHudManager(), 4);
			// scene.getRendererManager().setup(); // finally setup managers recursively
			return context.add(scene);
		}
		return null;
	}
	
}
