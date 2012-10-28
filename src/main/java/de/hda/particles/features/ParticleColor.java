package de.hda.particles.features;

import java.awt.Color;

import de.hda.particles.domain.Particle;

public class ParticleColor implements ParticleFeature {

	public static final String featureName = "currentColor";

	public Color startColor = new Color(255, 255, 255);
	
	public ParticleColor(Color startColor) {
		this.startColor = startColor;
	}

	public void init(Particle particle) {
		particle.setFeature(featureName, startColor);
	}

}
