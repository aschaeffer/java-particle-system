package de.hda.particles.modifier.impl.color;

import java.util.Random;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.impl.ParticleColor;
import de.hda.particles.modifier.ParticleModifier;

public class RandomStartColor extends AbstractColorModifier implements ParticleModifier {

	private final static Integer DEFAULT_ALPHA = 100;

	private final Random random = new Random();
	
	public RandomStartColor() {}

	@Override
	public void update(Particle particle) {
		if (particle.getPastIterations() == 1)
			particle.put(ParticleColor.CURRENT_COLOR, new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256), DEFAULT_ALPHA));
	}

}
