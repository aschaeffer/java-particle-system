package de.hda.particles.modifier.color;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.modifier.AbstractParticleModifier;
import de.hda.particles.modifier.ParticleModifier;

public class RainbowColorTransformation extends AbstractParticleModifier implements ParticleModifier {

	public RainbowColorTransformation() {}

	@Override
	public void update(Particle particle) {
		Float p = particle.getLifetimePercent();
		Color c = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		if (c == null) c = new Color();
		c.fromHSB(p, 1.0f, 0.8f);
		c.setAlpha(100);
		particle.put(ParticleColor.CURRENT_COLOR, c);
	}

}
