package de.hda.particles.hud;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.ParticleEmitterConfiguration;
import de.hda.particles.emitter.ParticleEmitter;
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
			ParticleEmitterConfiguration configuration = new ParticleEmitterConfiguration();
			Class configurationFactoryClass = (Class) command.getPayLoad2();
			if (configurationFactoryClass != null) {
				try {
					configuration = (ParticleEmitterConfiguration) configurationFactoryClass.getMethod("create", Scene.class).invoke(null, scene);
				} catch (Exception e) {}
			}
			Class<? extends ParticleEmitter> clazz = (Class<? extends ParticleEmitter>) command.getPayLoad();
			scene.getParticleSystem().addParticleEmitter(clazz, position, scene.getCameraManager().getDirectionVector(), 1, 10, 300, configuration);
			scene.getParticleSystem().endModification();
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Added Emitter: " + clazz.getSimpleName()));
		}
//		if (command.getType() == HUDCommandTypes.REMOVE_EMITTER) {
//			scene.getParticleSystem().re
//		}
	}

}
