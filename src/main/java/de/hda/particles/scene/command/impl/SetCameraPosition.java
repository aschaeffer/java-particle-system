package de.hda.particles.scene.command.impl;

import de.hda.particles.scene.command.Command;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.camera.Camera;
import de.hda.particles.domain.impl.configuration.CommandConfiguration;
import de.hda.particles.scene.demo.impl.DemoContext;
import de.hda.particles.scene.demo.impl.DemoHandle;

public class SetCameraPosition implements Command {

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		Integer id = (Integer) configuration.get("id");
		List<Camera> cameras = context.getByType(Camera.class);
		ListIterator<Camera> iterator = cameras.listIterator(0);
		while (iterator.hasNext()) {
			Camera camera = iterator.next();
			if (camera.getId().equals(id)) {
				Float x = new Double((Double) configuration.get("x")).floatValue();
				Float y = new Double((Double) configuration.get("y")).floatValue();
				Float z = new Double((Double) configuration.get("z")).floatValue();
				camera.setPosition(new Vector3f(x, y, z));
			}
		}
		return null;
	}
	
}
