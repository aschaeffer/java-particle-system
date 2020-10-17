package de.hda.particles.scene.command.impl;

import de.hda.particles.scene.command.Command;
import java.util.List;
import java.util.ListIterator;

import de.hda.particles.camera.Camera;
import de.hda.particles.configuration.impl.CommandConfiguration;
import de.hda.particles.scene.demo.impl.DemoContext;
import de.hda.particles.scene.demo.impl.DemoHandle;

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
