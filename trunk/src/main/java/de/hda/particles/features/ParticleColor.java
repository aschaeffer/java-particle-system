package de.hda.particles.features;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.hud.HUDEditorEntry;

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

	@Override
	public void init(ParticleEmitter emitter, Particle particle) {
		Integer startColorR = (Integer) emitter.getConfiguration().get(START_COLOR_R);
		if (startColorR == null) startColorR = 255;
		Integer startColorG = (Integer) emitter.getConfiguration().get(START_COLOR_G);
		if (startColorG == null) startColorG = 255;
		Integer startColorB = (Integer) emitter.getConfiguration().get(START_COLOR_B);
		if (startColorB == null) startColorB = 255;
		Integer startColorA = (Integer) emitter.getConfiguration().get(START_COLOR_A);
		if (startColorA == null) startColorA = 48;
		Color startColor = new Color(startColorR, startColorG, startColorB, startColorA);

		Integer endColorR = (Integer) emitter.getConfiguration().get(END_COLOR_R);
		if (endColorR == null) endColorR = 255;
		Integer endColorG = (Integer) emitter.getConfiguration().get(END_COLOR_G);
		if (endColorG == null) endColorG = 255;
		Integer endColorB = (Integer) emitter.getConfiguration().get(END_COLOR_B);
		if (endColorB == null) endColorB = 255;
		Integer endColorA = (Integer) emitter.getConfiguration().get(END_COLOR_A);
		if (endColorA == null) endColorA = 48;
		Color endColor = new Color(endColorR, endColorG, endColorB, endColorA);

		particle.put(START_COLOR, startColor);
		particle.put(END_COLOR, endColor);
		particle.put(CURRENT_COLOR, startColor);
	}

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
		entries.add(HUDEditorEntry.create(START_COLOR_R, "Start Color R"));
		entries.add(HUDEditorEntry.create(START_COLOR_G, "Start Color G"));
		entries.add(HUDEditorEntry.create(START_COLOR_B, "Start Color B"));
		entries.add(HUDEditorEntry.create(START_COLOR_A, "Start Color A"));
		entries.add(HUDEditorEntry.create(END_COLOR_R, "End Color R"));
		entries.add(HUDEditorEntry.create(END_COLOR_G, "End Color G"));
		entries.add(HUDEditorEntry.create(END_COLOR_B, "End Color B"));
		entries.add(HUDEditorEntry.create(END_COLOR_A, "End Color A"));
		return entries;
	}

	@Override
	public void decrease(ParticleEmitter emitter, String fieldName) {
		if (!fieldName.equals(START_COLOR_R)
			&& !fieldName.equals(START_COLOR_G)
			&& !fieldName.equals(START_COLOR_B)
			&& !fieldName.equals(START_COLOR_A)
			&& !fieldName.equals(END_COLOR_R)
			&& !fieldName.equals(END_COLOR_G)
			&& !fieldName.equals(END_COLOR_B)
			&& !fieldName.equals(END_COLOR_A)
		) return;
		Integer value = (Integer) emitter.getConfiguration().get(fieldName);
		if (value == null) value = 0;
		if (value > 0) value--;
		emitter.getConfiguration().put(fieldName, value);
	}

	@Override
	public void increase(ParticleEmitter emitter, String fieldName) {
		if (!fieldName.equals(START_COLOR_R)
			&& !fieldName.equals(START_COLOR_G)
			&& !fieldName.equals(START_COLOR_B)
			&& !fieldName.equals(START_COLOR_A)
			&& !fieldName.equals(END_COLOR_R)
			&& !fieldName.equals(END_COLOR_G)
			&& !fieldName.equals(END_COLOR_B)
			&& !fieldName.equals(END_COLOR_A)
		) return;
		Integer value = (Integer) emitter.getConfiguration().get(fieldName);
		if (value == null) value = 255;
		if (value < 255) value++;
		emitter.getConfiguration().put(fieldName, value);
	}

	@Override
	public String getValue(ParticleEmitter emitter, String fieldName) {
		if (!fieldName.equals(START_COLOR_R)
			&& !fieldName.equals(START_COLOR_G)
			&& !fieldName.equals(START_COLOR_B)
			&& !fieldName.equals(START_COLOR_A)
			&& !fieldName.equals(END_COLOR_R)
			&& !fieldName.equals(END_COLOR_G)
			&& !fieldName.equals(END_COLOR_B)
			&& !fieldName.equals(END_COLOR_A)
		) return null;
		Integer value = (Integer) emitter.getConfiguration().get(fieldName);
		if (value == null) {
			return null;
		} else {
			return value.toString();
		}
	}

}
