package de.hda.particles.modifier;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;

public class ParticleLinearColorTransformation extends AbstractParticleModifier implements ParticleModifier {

	public ParticleLinearColorTransformation() {}

	public void update(Particle particle) {
		Color s = (Color) particle.get(ParticleColor.START_COLOR);
		if (s == null) return;
		Color e = (Color) particle.get(ParticleColor.END_COLOR);
		if (e == null) return;
		Float p = particle.getLifetimePercent();
		Integer r = (int) (e.getRed() * p + s.getRed() * (1.0f - p));
		Integer g = (int) (e.getGreen() * p + s.getGreen() * (1.0f - p));
		Integer b = (int) (e.getBlue() * p + s.getBlue() * (1.0f - p));
		Integer a = (int) (e.getAlpha() * p + s.getAlpha() * (1.0f - p));
		particle.put(ParticleColor.CURRENT_COLOR, new Color(r, g, b, a));
	}

}
