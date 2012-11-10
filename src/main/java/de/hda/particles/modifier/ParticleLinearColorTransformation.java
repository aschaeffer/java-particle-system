package de.hda.particles.modifier;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;

public class ParticleLinearColorTransformation extends AbstractParticleModifier implements ParticleModifier {

	public void update(Particle particle) {
		Color s = (Color) particle.get(ParticleColor.START_COLOR);
		Color e = (Color) particle.get(ParticleColor.END_COLOR);
		Float p = particle.getLifetimePercent();
		Integer r = (int) (s.getRed() * p + e.getRed() * (1.0f - p));
		Integer g = (int) (s.getGreen() * p + e.getGreen() * (1.0f - p));
		Integer b = (int) (s.getBlue() * p + e.getBlue() * (1.0f - p));
		Integer a = (int) (s.getAlpha() * p + e.getAlpha() * (1.0f - p));
		particle.put(ParticleColor.CURRENT_COLOR, new Color(r, g, b, a));
		// System.out.println("p:"+p+" r:"+r+" b:"+b+" g:"+g+" a:"+a);
	}

}
