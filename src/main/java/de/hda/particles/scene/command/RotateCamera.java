package de.hda.particles.scene.command;

import java.util.List;
import java.util.ListIterator;

import de.hda.particles.camera.Camera;
import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public class RotateCamera implements Command {

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		Integer id = (Integer) configuration.get("id");
		Float rotateYaw = ((Double) configuration.get("yaw")).floatValue();
		Float rotatePitch = ((Double) configuration.get("pitch")).floatValue();
		List<Camera> cameras = context.getByType(Camera.class);
		ListIterator<Camera> iterator = cameras.listIterator(0);
		while (iterator.hasNext()) {
			Camera camera = iterator.next();
			if (camera.getId().equals(id)) {
				// Yaw
				Float yaw = camera.getYaw();
				yaw += rotateYaw / transitionIterations;
				camera.setYaw(yaw);
				// Pitch
				Float pitch = camera.getPitch();
				pitch += rotatePitch / transitionIterations;
				camera.setPitch(pitch);
			}
		}
		return null;
	}
	
}
