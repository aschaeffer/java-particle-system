package de.hda.particles.hud.impl;

import de.hda.particles.hud.HUD;
import org.lwjgl.util.vector.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.impl.configuration.ParticleEmitterConfiguration;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.scene.Scene;

public class EmitterHUD extends AbstractHUD implements HUD {

	public static final Integer DEFAULT_PARTICLE_RENDERER_INDEX = 1;
	public static final Integer DEFAULT_RATE = 10;
	public static final Integer DEFAULT_LIFETIME = 300;
	
	private final Logger logger = LoggerFactory.getLogger(EmitterHUD.class);
	
	public EmitterHUD() {}

	public EmitterHUD(Scene scene) {
		super(scene);
	}

	/**
	 * TODO: use interfaces and generics for factory and initializers
	 */
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
				} catch (Exception e) {
					logger.error("Cound not create emitter configuration using factory! " + e.getMessage(), e);
				}
			}
			Class<? extends ParticleEmitter> clazz = (Class<? extends ParticleEmitter>) command.getPayLoad();
			ParticleEmitter emitter = scene.getParticleSystem().addParticleEmitter(clazz, position, scene.getCameraManager().getDirectionVector(), DEFAULT_PARTICLE_RENDERER_INDEX, DEFAULT_RATE, DEFAULT_LIFETIME, configuration);
			Class initializerClass = (Class) command.getPayLoad3();
			if (initializerClass != null) {
				try {
					initializerClass.getMethod("init", Scene.class, Object.class).invoke(null, scene, emitter);
				} catch (Exception e) {
					logger.error("Cound not initialize emitter using initializer! " + e.getMessage(), e);
				}
			}
			scene.getParticleSystem().endModification();
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Added Emitter: " + clazz.getSimpleName()));
		}
//		if (command.getType() == HUDCommandTypes.REMOVE_EMITTER) {
//			scene.getParticleSystem().re
//		}
	}

	@Override
	public void update() {}

	@Override
	public void setup() {}

}
