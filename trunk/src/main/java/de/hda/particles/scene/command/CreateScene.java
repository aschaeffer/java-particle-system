package de.hda.particles.scene.command;

import java.util.List;

import de.hda.particles.ParticleSystem;
import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.scene.ConfigurableScene;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public class CreateScene implements Command {

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration) {
		List<ParticleSystem> particleSystems = context.getByType(ParticleSystem.class);
		if (particleSystems.size() > 0) {
			ConfigurableScene scene = new ConfigurableScene(particleSystems.get(0));
			scene.setName((String) configuration.get("name"));
			scene.setWidth((Integer) configuration.get("width"));
			scene.setHeight((Integer) configuration.get("height"));
			scene.setFullscreen((Boolean) configuration.get("fullscreen"));
	        // init fps information instances
			scene.addFpsInformationInstance(scene);
			scene.addFpsInformationInstance(particleSystems.get(0));
			// inject scene into managers
			scene.getCameraManager().setScene(scene);
			scene.getHudManager().setScene(scene);
			scene.getRenderTypeManager().setScene(scene);
			scene.getRendererManager().setScene(scene);
			scene.getRendererManager().insertAt(scene.getCameraManager(), 0);
			scene.getRendererManager().insertAt(scene.getRenderTypeManager(), 1);
			scene.getRendererManager().insertAt(scene.getHudManager(), 2);
			scene.getRendererManager().setup();
			return context.add(scene);
		}
		return null;
	}
	
}
