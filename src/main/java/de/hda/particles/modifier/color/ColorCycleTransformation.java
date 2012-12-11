package de.hda.particles.modifier.color;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.modifier.AbstractParticleModifier;
import de.hda.particles.modifier.ParticleModifier;

/**
 * Modifier, cycles from start to end color to start
 * 
 * @author aschaeffer
 *
 */
public class ColorCycleTransformation extends AbstractParticleModifier implements ParticleModifier {

	public ColorCycleTransformation() {}

	private Color s;
	private Color e;
	private Float p = 0.0f;
	private Integer r = 0;
	private Integer g = 0;
	private Integer b = 0;
	private Integer a = 0;

	@Override
	public void update(Particle particle) {
		s = (Color) particle.get(ParticleColor.START_COLOR);
		if (s == null) return;
		e = (Color) particle.get(ParticleColor.END_COLOR);
		if (e == null) return;
		p = particle.getLifetimePercent();
		if (p <= 0.5f) {
			r = (int) (p * e.getRed()   + (1.0f - p) * s.getRed());
			g = (int) (p * e.getGreen() + (1.0f - p) * s.getGreen());
			b = (int) (p * e.getBlue()  + (1.0f - p) * s.getBlue());
			a = (int) (p * e.getAlpha() + (1.0f - p) * s.getAlpha());
		} else {
			r = (int) (p * s.getRed()   + (1.0f - p) * e.getRed());
			g = (int) (p * s.getGreen() + (1.0f - p) * e.getGreen());
			b = (int) (p * s.getBlue()  + (1.0f - p) * e.getBlue());
			a = (int) (p * s.getAlpha() + (1.0f - p) * e.getAlpha());
		}
		particle.put(ParticleColor.CURRENT_COLOR, new Color(r, g, b, a));
	}

}
