package de.hda.particles.scene.command;

import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.vector.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.camera.Camera;
import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public class CreateCamera implements Command {

	private final Logger logger = LoggerFactory.getLogger(CreateCamera.class);

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		String type = (String) configuration.get("class");
		try {
			Class<?> clazz = Class.forName(type);
			List<Scene> scenes = context.getByType(Scene.class);
			ListIterator<Scene> iterator = scenes.listIterator(0);
			while (iterator.hasNext()) {
				Camera camera = (Camera) clazz.newInstance();
				camera.setName((String) configuration.get("name"));
				camera.setId((Integer) configuration.get("id"));
				camera.setPosition(new Vector3f(new Double((Double) configuration.get("x")).floatValue(), new Double((Double) configuration.get("y")).floatValue(), new Double((Double) configuration.get("z")).floatValue()));
				camera.setYaw(new Double((Double) configuration.get("yaw")).floatValue());
				camera.setPitch(new Double((Double) configuration.get("pitch")).floatValue());
				camera.setRoll(new Double((Double) configuration.get("roll")).floatValue());
				camera.setFov(new Double((Double) configuration.get("fov")).floatValue());
				Scene scene = iterator.next();
				scene.getCameraManager().add(camera);
				camera.setScene(scene);
				context.add(camera);
				logger.info("created camera " + camera.getName());
			}
		} catch (Exception e) {
			logger.error("could not create new camera of type " + type, e);
		}
		return null;
	}
	
}
