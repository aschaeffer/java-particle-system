package de.hda.particles.features;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;

public class ParticleColor implements ParticleFeature {

	public static final String START_COLOR_R = "startColor_r";
	public static final String START_COLOR_G = "startColor_g";
	public static final String START_COLOR_B = "startColor_b";
	public static final String START_COLOR_A = "startColor_a";
	public static final String END_COLOR_R = "endColor_r";
	public static final String END_COLOR_G = "endColor_g";
	public static final String END_COLOR_B = "endColor_b";
	public static final String END_COLOR_A = "endColor_a";

	public static final String START_COLOR = "startColor";
	public static final String END_COLOR = "endColor";
	public static final String CURRENT_COLOR = "currentColor";

	public void init(ParticleEmitter emitter, Particle particle) {
		Integer startColorR = (Integer) emitter.getConfiguration().get(START_COLOR_R);
		Integer startColorG = (Integer) emitter.getConfiguration().get(START_COLOR_G);
		Integer startColorB = (Integer) emitter.getConfiguration().get(START_COLOR_B);
		Integer startColorA = (Integer) emitter.getConfiguration().get(START_COLOR_A);
		Color startColor = new Color(startColorR, startColorG, startColorB, startColorA);

		Integer endColorR = (Integer) emitter.getConfiguration().get(END_COLOR_R);
		Integer endColorG = (Integer) emitter.getConfiguration().get(END_COLOR_G);
		Integer endColorB = (Integer) emitter.getConfiguration().get(END_COLOR_B);
		Integer endColorA = (Integer) emitter.getConfiguration().get(END_COLOR_A);
		Color endColor = new Color(endColorR, endColorG, endColorB, endColorA);

		particle.put(START_COLOR, startColor);
		particle.put(END_COLOR, endColor);
		particle.put(CURRENT_COLOR, startColor);
	}

}
