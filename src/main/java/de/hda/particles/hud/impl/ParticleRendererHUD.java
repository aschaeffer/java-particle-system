package de.hda.particles.hud.impl;

import de.hda.particles.hud.HUD;
import de.hda.particles.renderer.ParticleRenderer;
import de.hda.particles.scene.Scene;

public class ParticleRendererHUD extends AbstractHUD implements HUD {

	public ParticleRendererHUD() {}

	public ParticleRendererHUD(Scene scene) {
		super(scene);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.ADD_PARTICLE_RENDERER) {
			Class<? extends ParticleRenderer> clazz = (Class<? extends ParticleRenderer>) command.getPayLoad();
			scene.getParticleSystem().beginModification();
			scene.getParticleRendererManager().add(clazz);
			scene.getParticleSystem().endModification();
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Added Particle Renderer: " + clazz.getSimpleName()));
		}
		if (command.getType() == HUDCommandTypes.REMOVE_PARTICLE_RENDERER) {
			Integer index = (Integer) command.getPayLoad();
			scene.getParticleSystem().beginModification();
			scene.getParticleRendererManager().remove(index);
			scene.getParticleSystem().endModification();
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Removed Particle Renderer on index: " + index));
		}
	}

	@Override
	public void update() {}

}
