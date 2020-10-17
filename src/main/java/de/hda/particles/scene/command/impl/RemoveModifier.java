package de.hda.particles.scene.command.impl;

import de.hda.particles.scene.command.Command;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.ParticleSystem;
import de.hda.particles.domain.impl.configuration.CommandConfiguration;
import de.hda.particles.listener.ModifierLifetimeListener;
import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.impl.DemoContext;
import de.hda.particles.scene.demo.impl.DemoHandle;

public class RemoveModifier implements Command, ModifierLifetimeListener {
	
	private DemoContext context;

	private final Logger logger = LoggerFactory.getLogger(RemoveModifier.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		this.context = context;
		String type = (String) configuration.get("class");
		try {
			Class<? extends ParticleModifier> clazz = (Class<? extends ParticleModifier>) Class.forName(type);
			List<Scene> scenes = context.getByType(Scene.class);
			ListIterator<Scene> iterator = scenes.listIterator(0);
			while (iterator.hasNext()) {
				Scene scene = iterator.next();
				ParticleSystem particleSystem = scene.getParticleSystem();
				particleSystem.beginModification();
				particleSystem.addModifierListener(this);
				particleSystem.removeParticleModifier(clazz);
				particleSystem.removeModifierListener(this);
				particleSystem.endModification();
				logger.info("created new modifier of type " + type);
			}
		} catch (Exception e) {
			logger.error("could not create new modifier of type " + type);
		}
		return null;
	}

	@Override
	public void onModifierCreation(ParticleModifier modifier) {}

	@Override
	public void onModifierDeath(ParticleModifier modifier) {
		context.remove(modifier);
	}
	
}
