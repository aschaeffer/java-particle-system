package de.hda.particles.modifier;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;

public class RainbowColorTransformation extends AbstractParticleModifier implements ParticleModifier {

	public RainbowColorTransformation() {}

	public void update(Particle particle) {
		Float p = particle.getLifetimePercent();
		Color c = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		if (c == null) c = new Color();
		c.fromHSB(1.0f - (p / 100.0f), 1.0f, 0.8f);
		particle.put(ParticleColor.CURRENT_COLOR, c);
	}

}
