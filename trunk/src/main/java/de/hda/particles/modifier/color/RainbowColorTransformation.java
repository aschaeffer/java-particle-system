package de.hda.particles.modifier.color;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.modifier.ParticleModifier;

public class RainbowColorTransformation extends AbstractColorModifier implements ParticleModifier {

	public RainbowColorTransformation() {}

	private Float p = 0.0f;
	private Color c;
	
	@Override
	public void update(Particle particle) {
		p = particle.getLifetimePercent();
		c = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		if (c == null) c = new Color();
		c.fromHSB(p, 1.0f, 0.8f);
		c.setAlpha(100);
		particle.put(ParticleColor.CURRENT_COLOR, c);
	}

}
