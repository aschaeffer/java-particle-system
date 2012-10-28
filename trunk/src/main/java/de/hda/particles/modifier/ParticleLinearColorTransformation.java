package de.hda.particles.modifier;

import java.awt.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.domain.features.ParticleColor;

public class ParticleLinearColorTransformation extends AbstractParticleModifier implements ParticleModifier {

	public final static String START_COLOR = "startColor";
	public final static String END_COLOR = "endColor";
	
	public void update(Particle particle) {
		Color startColor = (Color) this.configuration.get(START_COLOR);
		Color endColor = (Color) this.configuration.get(END_COLOR);
		Integer r = startColor.getRed() + Math.round(new Float(endColor.getRed() - startColor.getRed()) * particle.getLifetimePercent());
		Integer g = startColor.getGreen() + Math.round(new Float(endColor.getGreen() - startColor.getGreen()) * particle.getLifetimePercent());
		Integer b = startColor.getBlue() + Math.round(new Float(endColor.getBlue() - startColor.getBlue()) * particle.getLifetimePercent());
		Color mixColor = new Color(r,g,b);
		particle.setFeature(ParticleColor.featureName, mixColor);
	}

}
