package de.hda.particles.scene.command.impl;

import de.hda.particles.scene.command.Command;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.impl.configuration.CommandConfiguration;
import de.hda.particles.domain.impl.configuration.ParticleModifierConfiguration;
import de.hda.particles.hud.impl.HUDCommand;
import de.hda.particles.hud.impl.HUDCommandTypes;
import de.hda.particles.modifier.PositionablePointModifier;
import de.hda.particles.modifier.impl.gravity.GravityBase;
import de.hda.particles.modifier.impl.gravity.GravityPoint;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.impl.DemoContext;
import de.hda.particles.scene.demo.impl.DemoHandle;

public class AddGravityPoint implements Command {

	private final Logger logger = LoggerFactory.getLogger(AddGravityPoint.class);

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		List<Scene> scenes = context.getByType(Scene.class);
		ListIterator<Scene> iterator = scenes.listIterator(0);
		while (iterator.hasNext()) {
			Scene scene = iterator.next();
			// scene.beginModification();
			scene.getParticleSystem().beginModification();
			ParticleModifierConfiguration modifierConfiguration = new ParticleModifierConfiguration();
			modifierConfiguration.put(PositionablePointModifier.POSITION_X, configuration.get(PositionablePointModifier.POSITION_X));
			modifierConfiguration.put(PositionablePointModifier.POSITION_Y, configuration.get(PositionablePointModifier.POSITION_Y));
			modifierConfiguration.put(PositionablePointModifier.POSITION_Z, configuration.get(PositionablePointModifier.POSITION_Z));
			if (configuration.containsKey(GravityBase.GRAVITY)) {
				modifierConfiguration.put(GravityBase.GRAVITY, configuration.get(GravityBase.GRAVITY));
			} else {
				modifierConfiguration.put(GravityBase.GRAVITY, GravityPoint.DEFAULT_GRAVITY);
			}
			if (configuration.containsKey(GravityBase.MASS)) {
				modifierConfiguration.put(GravityBase.MASS, configuration.get(GravityBase.MASS));
			} else {
				modifierConfiguration.put(GravityBase.MASS, GravityPoint.DEFAULT_MASS);
			}
			if (configuration.containsKey(GravityBase.MAX_FORCE)) {
				modifierConfiguration.put(GravityBase.MAX_FORCE, configuration.get(GravityBase.MAX_FORCE));
			} else {
				modifierConfiguration.put(GravityBase.MAX_FORCE, GravityPoint.DEFAULT_MAX_FORCE);
			}
			GravityPoint gravityPoint = new GravityPoint();
			// gravityPoint.setId((Integer) configuration.get("id"));
			scene.getParticleSystem().addParticleModifier(gravityPoint, modifierConfiguration);
			scene.getParticleSystem().endModification();
			// scene.endModification();
			context.add(gravityPoint);
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Added Gravity Point"));
			logger.info("created gravityPoint");
		}
		return null;
	}
	
}
