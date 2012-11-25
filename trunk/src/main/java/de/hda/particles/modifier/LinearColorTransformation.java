package de.hda.particles.modifier;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;

public class LinearColorTransformation extends AbstractParticleModifier implements ParticleModifier {

	public LinearColorTransformation() {}

	@Override
	public void update(Particle particle) {
		Color s = (Color) particle.get(ParticleColor.START_COLOR);
		if (s == null) return;
		Color e = (Color) particle.get(ParticleColor.END_COLOR);
		if (e == null) return;
		Float p = particle.getLifetimePercent();
		Integer r = (int) (p * e.getRed()   + (1.0f - p) * s.getRed());
		Integer g = (int) (p * e.getGreen() + (1.0f - p) * s.getGreen());
		Integer b = (int) (p * e.getBlue()  + (1.0f - p) * s.getBlue());
		Integer a = (int) (p * e.getAlpha() + (1.0f - p) * s.getAlpha());
		particle.put(ParticleColor.CURRENT_COLOR, new Color(r, g, b, a));
	}

}
