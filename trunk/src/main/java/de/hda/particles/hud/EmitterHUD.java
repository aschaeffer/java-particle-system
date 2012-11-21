package de.hda.particles.hud;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.ParticleEmitterConfiguration;
import de.hda.particles.scene.Scene;

public class EmitterHUD extends AbstractHUD implements HUD {

	public EmitterHUD() {}

	public EmitterHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void update() {
	}

	@Override
	public void setup() {
		super.setup();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.ADD_EMITTER) {
			scene.getParticleSystem().beginModification();
			Vector3f position = new Vector3f(scene.getCameraManager().getPosition());
			scene.getParticleSystem().addParticleEmitter((Class) command.getPayLoad(), position, scene.getCameraManager().getDirectionVector(), 1, 10, 300, ParticleEmitterConfiguration.EMPTY);
			scene.getParticleSystem().endModification();
		}
//		if (command.getType() == HUDCommandTypes.REMOVE_EMITTER) {
//			scene.getParticleSystem().re
//		}
	}

}
