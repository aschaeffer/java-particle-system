package de.hda.particles.scene.command;

import java.util.List;
import java.util.ListIterator;

import de.hda.particles.camera.Camera;
import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public class RotateCamera360 implements Command {

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		Integer id = (Integer) configuration.get("id");
		List<Camera> cameras = context.getByType(Camera.class);
		ListIterator<Camera> iterator = cameras.listIterator(0);
		while (iterator.hasNext()) {
			Camera camera = iterator.next();
			if (camera.getId().equals(id)) {
				Float yaw = camera.getYaw();
				yaw += 360.0f / transitionIterations;
				camera.setYaw(yaw);
			}
		}
		return null;
	}
	
}
