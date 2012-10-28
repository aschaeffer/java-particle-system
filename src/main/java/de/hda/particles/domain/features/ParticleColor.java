package de.hda.particles.domain.features;

import java.awt.Color;

import de.hda.particles.domain.Particle;

public class ParticleColor implements ParticleFeature {

	public static final String featureName = "currentColor";

	public Object defaultValue = new Color(255, 255, 255);

	public void init(Particle particle) {
		particle.setFeature(featureName, defaultValue);
	}

}
