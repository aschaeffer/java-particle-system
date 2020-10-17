package de.hda.particles.features.impl;

import de.hda.particles.features.ParticleFeature;
import java.util.List;

import de.hda.particles.domain.Particle;
import de.hda.particles.editor.impl.HUDEditorEntry;
import de.hda.particles.emitter.ParticleEmitter;

public class Replication extends AbstractParticleFeature implements ParticleFeature {

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(final ParticleEmitter emitter, final Particle particle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decrease(final ParticleEmitter emitter, final String fieldName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decreaseMin(final ParticleEmitter emitter, final String fieldName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void increase(final ParticleEmitter emitter, final String fieldName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void increaseMax(final ParticleEmitter emitter, final String fieldName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefault(final ParticleEmitter emitter, final String fieldName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getValue(final ParticleEmitter emitter, final String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean validFieldName(final String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

//	public static final String NUMBER_OF_ITERATIONS = "replicationNumberOfIterations";
//	public static final String CHANCE = "replicationChance";
//	public static final String VELOCITY_X = "replicationVelocity_x";
//	public static final String VELOCITY_Y = "replicationVelocity_y";
//	public static final String VELOCITY_Z = "replicationVelocity_z";
//
//	public final static Integer DEFAULT_NUMBER_OF_ITERATIONS = Integer.MAX_VALUE;
//	public final static Double DEFAULT_CHANCE = 0.2;
//
//	public final static Double MIN_DEFAULT_CHANCE = 0.0;
//	public final static Double MAX_DEFAULT_CHANCE = 1.0;
//
//	@Override
//	public List<HUDEditorEntry> getEditorEntries() {
//		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
//		entries.add(HUDEditorEntry.create(NUMBER_OF_ITERATIONS, "Size Birth"));
//		entries.add(HUDEditorEntry.create(CHANCE, "Size Death"));
//		return entries;
//	}
//
//	@Override
//	public void init(ParticleEmitter emitter, Particle particle) {
//		particle.put(NUMBER_OF_ITERATIONS, emitter.getConfiguration().get(NUMBER_OF_ITERATIONS));
//		particle.put(CHANCE, emitter.getConfiguration().get(CHANCE));
//	}
//
//	@Override
//	public void decrease(ParticleEmitter emitter, String fieldName) {
//		if (fieldName.equals(NUMBER_OF_ITERATIONS)) {
//			Integer value = (Integer) emitter.getConfiguration().get(fieldName);
//		}
//		if (!validFieldName(fieldName)) return;
//		Integer value = (Integer) emitter.getConfiguration().get(fieldName);
//		if (value == null) {
//			setDefault(emitter, fieldName);
//		} else if (value > MIN_NUMBER_OF_SEGMENTS) {
//			value--;
//			emitter.getConfiguration().put(fieldName, value);
//		}
//	}
//
//	@Override
//	public void decreaseMin(ParticleEmitter emitter, String fieldName) {
//		if (!validFieldName(fieldName)) return;
//		emitter.getConfiguration().put(fieldName, MIN_NUMBER_OF_SEGMENTS);
//	}
//
//	@Override
//	public void increase(ParticleEmitter emitter, String fieldName) {
//		if (!validFieldName(fieldName)) return;
//		Integer value = (Integer) emitter.getConfiguration().get(fieldName);
//		if (value == null) {
//			setDefault(emitter, fieldName);
//		} else if (value < MAX_NUMBER_OF_SEGMENTS) {
//			value++;
//			emitter.getConfiguration().put(fieldName, value);
//		}
//	}
//
//	@Override
//	public void increaseMax(ParticleEmitter emitter, String fieldName) {
//		if (!validFieldName(fieldName)) return;
//		emitter.getConfiguration().put(fieldName, 100.0);
//	}
//	
//	@Override
//	public void setDefault(ParticleEmitter emitter, String fieldName) {
//		if (fieldName.equals(NUMBER_OF_SEGMENTS)) {
//			emitter.getConfiguration().put(fieldName, DEFAULT_NUMBER_OF_SEGMENTS);
//		} else if (fieldName.equals(SEGMENTS_TO_DRAW)) {
//			emitter.getConfiguration().put(fieldName, DEFAULT_SEGMENTS_TO_DRAW);
//		}
//	}
//
//	@Override
//	public String getValue(ParticleEmitter emitter, String fieldName) {
//		return getIntegerValueAsString(emitter, fieldName);
//	}
//
//	@Override
//	public Boolean validFieldName(String fieldName) {
//		return (fieldName.equals(NUMBER_OF_SEGMENTS) || fieldName.equals(SEGMENTS_TO_DRAW));
//	}

}
