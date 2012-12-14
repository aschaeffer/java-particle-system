package de.hda.particles.features;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.hud.HUDEditorEntry;

public class ParticleColor extends AbstractParticleFeature implements ParticleFeature {

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
	
	public final static Integer DEFAULT_COLOR_R = 255;
	public final static Integer DEFAULT_COLOR_G = 255;
	public final static Integer DEFAULT_COLOR_B = 255;
	public final static Integer DEFAULT_COLOR_A = 64;
	public final static Color DEFAULT_COLOR = new Color(DEFAULT_COLOR_R, DEFAULT_COLOR_G, DEFAULT_COLOR_B, DEFAULT_COLOR_A);

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
	public void init(final ParticleEmitter emitter, final Particle particle) {
		Integer startColorR = (Integer) emitter.getConfiguration().get(START_COLOR_R);
		if (startColorR == null) startColorR = DEFAULT_COLOR_R;
		Integer startColorG = (Integer) emitter.getConfiguration().get(START_COLOR_G);
		if (startColorG == null) startColorG = DEFAULT_COLOR_G;
		Integer startColorB = (Integer) emitter.getConfiguration().get(START_COLOR_B);
		if (startColorB == null) startColorB = DEFAULT_COLOR_B;
		Integer startColorA = (Integer) emitter.getConfiguration().get(START_COLOR_A);
		if (startColorA == null) startColorA = DEFAULT_COLOR_A;
		Color startColor = new Color(startColorR, startColorG, startColorB, startColorA);

		Integer endColorR = (Integer) emitter.getConfiguration().get(END_COLOR_R);
		if (endColorR == null) endColorR = DEFAULT_COLOR_R;
		Integer endColorG = (Integer) emitter.getConfiguration().get(END_COLOR_G);
		if (endColorG == null) endColorG = DEFAULT_COLOR_G;
		Integer endColorB = (Integer) emitter.getConfiguration().get(END_COLOR_B);
		if (endColorB == null) endColorB = DEFAULT_COLOR_B;
		Integer endColorA = (Integer) emitter.getConfiguration().get(END_COLOR_A);
		if (endColorA == null) endColorA = DEFAULT_COLOR_A;
		Color endColor = new Color(endColorR, endColorG, endColorB, endColorA);

		particle.put(START_COLOR, startColor);
		particle.put(END_COLOR, endColor);
		particle.put(CURRENT_COLOR, startColor);
	}

	@Override
	public void decrease(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		Integer value = (Integer) emitter.getConfiguration().get(fieldName);
		if (value == null) {
			decreaseMin(emitter, fieldName);
		} else if (value > 0) {
			value--;
			emitter.getConfiguration().put(fieldName, value);
		}
	}

	@Override
	public void decreaseMin(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		emitter.getConfiguration().put(fieldName, 0);
	}

	@Override
	public void increase(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		Integer value = (Integer) emitter.getConfiguration().get(fieldName);
		if (value == null) {
			setDefault(emitter, fieldName);
		} else if (value < 255) {
			value++;
			emitter.getConfiguration().put(fieldName, value);
		}
	}

	@Override
	public void increaseMax(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		emitter.getConfiguration().put(fieldName, 255);
	}
	
	@Override
	public void setDefault(final ParticleEmitter emitter, final String fieldName) {
		if (fieldName.equals(START_COLOR_R)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_COLOR_R);
		} else if (fieldName.equals(START_COLOR_G)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_COLOR_G);
		} else if (fieldName.equals(START_COLOR_B)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_COLOR_B);
		} else if (fieldName.equals(START_COLOR_A)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_COLOR_A);
		} else if (fieldName.equals(END_COLOR_R)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_COLOR_R);
		} else if (fieldName.equals(END_COLOR_G)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_COLOR_G);
		} else if (fieldName.equals(END_COLOR_B)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_COLOR_B);
		} else if (fieldName.equals(END_COLOR_A)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_COLOR_A);
		}
	}

	@Override
	public String getValue(final ParticleEmitter emitter, final String fieldName) {
		return getIntegerValueAsString(emitter, fieldName);
	}

	@Override
	public Boolean validFieldName(final String fieldName) {
		return (fieldName.equals(START_COLOR_R)
			|| fieldName.equals(START_COLOR_G)
			|| fieldName.equals(START_COLOR_B)
			|| fieldName.equals(START_COLOR_A)
			|| fieldName.equals(END_COLOR_R)
			|| fieldName.equals(END_COLOR_G)
			|| fieldName.equals(END_COLOR_B)
			|| fieldName.equals(END_COLOR_A));
	}

}
