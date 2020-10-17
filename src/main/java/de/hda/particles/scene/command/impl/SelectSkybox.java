package de.hda.particles.scene.command.impl;

import de.hda.particles.scene.command.Command;
import java.util.List;
import java.util.ListIterator;

import de.hda.particles.configuration.impl.CommandConfiguration;
import de.hda.particles.renderer.impl.SkyBoxRenderer;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.impl.DemoContext;
import de.hda.particles.scene.demo.impl.DemoHandle;

public class SelectSkybox implements Command {

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		String name = (String) configuration.get("name");
		List<Scene> scenes = context.getByType(Scene.class);
		ListIterator<Scene> iterator = scenes.listIterator(0);
		while (iterator.hasNext()) {
			Scene scene = iterator.next();
			// scene.beginModification();
			scene.getParticleSystem().beginModification();
			SkyBoxRenderer skyBoxRenderer = (SkyBoxRenderer) scene.getRendererManager().getRendererByClass(SkyBoxRenderer.class);
			if (skyBoxRenderer != null) {
				skyBoxRenderer.clearSkybox();
				skyBoxRenderer.loadSkybox(name);
			}
			scene.getParticleSystem().endModification();
			// scene.endModification();
		}
		return null;
	}
	
}
