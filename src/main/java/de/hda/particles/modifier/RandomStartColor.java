package de.hda.particles.modifier;

import java.util.Random;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;

public class RandomStartColor extends AbstractParticleModifier implements ParticleModifier {

	private final Random random = new Random();
	
	public RandomStartColor() {}

	@Override
	public void update(Particle particle) {
		if (particle.getPastIterations() == 1)
			particle.put(ParticleColor.CURRENT_COLOR, new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256), 100));
	}

}