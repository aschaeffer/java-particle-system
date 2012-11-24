package de.hda.particles.features;

import java.util.*;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.buffer.CircularFifoBuffer;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.hud.HUDEditorEntry;

public class MassSpring extends AbstractParticleFeature implements ParticleFeature {

	public static final String NUMBER_SPRINGS = "numberSprings";   // The number of springs
	public static final String SPRING_LENGTH = "springLength";     // The length that the spring does not exert any force
	public static final String SPRING_FRICTION = "springFriction"; // A  constant to be used for the inner friction of the spring
	public static final String SPRING_CONSTANT = "springConstant"; // A constant to represent the stiffness of the spring
	public static final String SPRING_CONSTRUCTION_RULE = "springConstructionRule"; // Rule describes how to create springs

	public static final String SPRING_CONNECTED_PARTICLES = "springConnectedParticles";

	public static final Integer DEFAULT_NUMBER_SPRINGS = 2;
	public static final Double DEFAULT_SPRING_LENGTH = 5.0;
	public static final Double DEFAULT_SPRING_FRICTION = 0.9;
	public static final Double DEFAULT_SPRING_CONSTANT = 0.08;
	public static final Integer DEFAULT_SPRING_CONSTRUCTION_RULE = 0;

	private final HashMap<ParticleEmitter, Buffer> buffers = new HashMap<ParticleEmitter, Buffer>();

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
		entries.add(HUDEditorEntry.create(SPRING_CONSTRUCTION_RULE, "Spring Construction Rule"));
		entries.add(HUDEditorEntry.create(NUMBER_SPRINGS, "Number Of Springs"));
		entries.add(HUDEditorEntry.create(SPRING_LENGTH, "Spring Length"));
		entries.add(HUDEditorEntry.create(SPRING_FRICTION, "Spring Friction"));
		entries.add(HUDEditorEntry.create(SPRING_CONSTANT, "Spring Constant"));
		return entries;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(ParticleEmitter emitter, Particle particle) {
		if (!buffers.containsKey(emitter)) {
			Integer numberSprings = (Integer) emitter.getConfiguration().get(NUMBER_SPRINGS);
			if (numberSprings == null) {
				numberSprings = DEFAULT_NUMBER_SPRINGS;
				emitter.getConfiguration().put(NUMBER_SPRINGS, DEFAULT_NUMBER_SPRINGS);
			}
			buffers.put(emitter, new CircularFifoBuffer(numberSprings));
		}
		Integer springContructionRule = (Integer) emitter.getConfiguration().get(SPRING_CONSTRUCTION_RULE);
		if (springContructionRule == null) {
			springContructionRule = DEFAULT_SPRING_CONSTRUCTION_RULE;
			emitter.getConfiguration().put(SPRING_CONSTRUCTION_RULE, DEFAULT_SPRING_CONSTRUCTION_RULE);
		}
		constructSpringConnections(emitter, particle, springContructionRule);
		Double springLength = (Double) emitter.getConfiguration().get(SPRING_LENGTH);
		if (springLength == null) {
			springLength = DEFAULT_SPRING_LENGTH;
			emitter.getConfiguration().put(SPRING_LENGTH, DEFAULT_SPRING_LENGTH);
		}
		particle.put(SPRING_LENGTH, springLength);
		Double springFriction = (Double) emitter.getConfiguration().get(SPRING_FRICTION);
		if (springFriction == null) {
			springFriction = DEFAULT_SPRING_FRICTION;
			emitter.getConfiguration().put(SPRING_FRICTION, DEFAULT_SPRING_FRICTION);
		}
		particle.put(SPRING_FRICTION, springFriction);
		Double springConstant = (Double) emitter.getConfiguration().get(SPRING_CONSTANT);
		if (springConstant == null) {
			springConstant = DEFAULT_SPRING_CONSTANT;
			emitter.getConfiguration().put(SPRING_CONSTANT, DEFAULT_SPRING_CONSTANT);
		}
		particle.put(SPRING_CONSTANT, springConstant);
		buffers.get(emitter).add(particle);
	}

	@Override
	public void decrease(ParticleEmitter emitter, String fieldName) {
		if (fieldName.equals(SPRING_CONSTRUCTION_RULE)) {
			Integer value = decreaseValue(emitter, SPRING_CONSTRUCTION_RULE, DEFAULT_SPRING_CONSTRUCTION_RULE, 0);
			if (value == 3) {
				setNumberOfSprings(emitter, 3);
			} else if (value == 4) {
				setNumberOfSprings(emitter, 5);
			}
		} else if (fieldName.equals(NUMBER_SPRINGS)) {
			setNumberOfSprings(emitter, decreaseValue(emitter, NUMBER_SPRINGS, DEFAULT_NUMBER_SPRINGS, 0));
		} else if (fieldName.equals(SPRING_LENGTH)) {
			decreaseValue(emitter, SPRING_LENGTH, DEFAULT_SPRING_LENGTH, 0.01);
		} else if (fieldName.equals(SPRING_FRICTION)) {
			decreaseValue(emitter, SPRING_FRICTION, DEFAULT_SPRING_FRICTION, 0.01);
		} else if (fieldName.equals(SPRING_CONSTANT)) {
			decreaseValue(emitter, SPRING_CONSTANT, DEFAULT_SPRING_CONSTANT, 0.01);
		}
	}

	@Override
	public void decreaseMin(ParticleEmitter emitter, String fieldName) {
		if (fieldName.equals(SPRING_CONSTRUCTION_RULE)) {
			emitter.getConfiguration().put(SPRING_CONSTRUCTION_RULE, 0);
		} else if (fieldName.equals(NUMBER_SPRINGS)) {
			emitter.getConfiguration().put(NUMBER_SPRINGS, 0);
		} else if (fieldName.equals(SPRING_LENGTH)) {
			emitter.getConfiguration().put(SPRING_LENGTH, 0.01);
		} else if (fieldName.equals(SPRING_FRICTION)) {
			emitter.getConfiguration().put(SPRING_FRICTION, 0.01);
		} else if (fieldName.equals(SPRING_CONSTANT)) {
			emitter.getConfiguration().put(SPRING_CONSTANT, 0.01);
		}
	}

	@Override
	public void increase(ParticleEmitter emitter, String fieldName) {
		if (fieldName.equals(SPRING_CONSTRUCTION_RULE)) {
			Integer value = increaseValue(emitter, SPRING_CONSTRUCTION_RULE, DEFAULT_SPRING_CONSTRUCTION_RULE);
			if (value == 3) {
				setNumberOfSprings(emitter, 3);
			} else if (value == 4) {
				setNumberOfSprings(emitter, 5);
			}
		} else if (fieldName.equals(NUMBER_SPRINGS)) {
			setNumberOfSprings(emitter, increaseValue(emitter, NUMBER_SPRINGS, DEFAULT_NUMBER_SPRINGS));
		} else if (fieldName.equals(SPRING_LENGTH)) {
			increaseValue(emitter, SPRING_LENGTH, DEFAULT_SPRING_LENGTH);
		} else if (fieldName.equals(SPRING_FRICTION)) {
			increaseValue(emitter, SPRING_FRICTION, DEFAULT_SPRING_FRICTION, 1.0);
		} else if (fieldName.equals(SPRING_CONSTANT)) {
			increaseValue(emitter, SPRING_CONSTANT, DEFAULT_SPRING_CONSTANT);
		}
	}

	@Override
	public void increaseMax(ParticleEmitter emitter, String fieldName) {
		if (fieldName.equals(SPRING_CONSTRUCTION_RULE)) {
			emitter.getConfiguration().put(SPRING_CONSTRUCTION_RULE, 4);
		} else if (fieldName.equals(NUMBER_SPRINGS)) {
			emitter.getConfiguration().put(NUMBER_SPRINGS, 100);
		} else if (fieldName.equals(SPRING_LENGTH)) {
			emitter.getConfiguration().put(SPRING_LENGTH, 1000.0);
		} else if (fieldName.equals(SPRING_FRICTION)) {
			emitter.getConfiguration().put(SPRING_FRICTION, 1.00);
		} else if (fieldName.equals(SPRING_CONSTANT)) {
			emitter.getConfiguration().put(SPRING_CONSTANT, 1.00);
		}
	}
	
	@Override
	public void setDefault(ParticleEmitter emitter, String fieldName) {
		if (fieldName.equals(SPRING_CONSTRUCTION_RULE)) {
			emitter.getConfiguration().put(SPRING_CONSTRUCTION_RULE, DEFAULT_SPRING_CONSTRUCTION_RULE);
		} else if (fieldName.equals(NUMBER_SPRINGS)) {
			emitter.getConfiguration().put(NUMBER_SPRINGS, DEFAULT_NUMBER_SPRINGS);
		} else if (fieldName.equals(SPRING_LENGTH)) {
			emitter.getConfiguration().put(SPRING_LENGTH, DEFAULT_SPRING_LENGTH);
		} else if (fieldName.equals(SPRING_FRICTION)) {
			emitter.getConfiguration().put(SPRING_FRICTION, DEFAULT_SPRING_FRICTION);
		} else if (fieldName.equals(SPRING_CONSTANT)) {
			emitter.getConfiguration().put(SPRING_CONSTANT, DEFAULT_SPRING_CONSTANT);
		}
	}

	@Override
	public String getValue(ParticleEmitter emitter, String fieldName) {
		if (!validFieldName(fieldName)) return null;
		if (fieldName.equals(SPRING_CONSTRUCTION_RULE) || fieldName.equals(NUMBER_SPRINGS)) {
			return getIntegerValueAsString(emitter, fieldName);
//			Integer value = (Integer) emitter.getConfiguration().get(fieldName);
//			if (value == null) return "N/A";
//			return value.toString();
		} else {
			return getDoubleValueAsString(emitter, fieldName);
//			Double value = (Double) emitter.getConfiguration().get(fieldName);
//			if (value == null) return "N/A";
//			return String.format("%.2f", value);
		}
	}
	
	private void setNumberOfSprings(ParticleEmitter emitter, Integer numberOfSprings) {
		if (numberOfSprings > 0) {
			emitter.getConfiguration().put(NUMBER_SPRINGS, numberOfSprings);
			Buffer buffer = buffers.get(emitter);
			if (buffer != null) buffer.clear();
			buffer = new CircularFifoBuffer(numberOfSprings);
			buffers.put(emitter, buffer);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void constructSpringConnections(ParticleEmitter emitter, Particle particle, Integer springContructionRule) {
		ArrayList<Particle> springConnectedParticles = new ArrayList<Particle>();;
		if (springContructionRule == 0) {
			// connect the new particle with it's n predessors
			springConnectedParticles.addAll(buffers.get(emitter));
		} else if (springContructionRule == 1) {
			/**
			 * connect with the 2*next particles
			 * 
			 *    *---*---*---*---*
			 *      *---*---*---*---*
			 */
			ArrayList<Particle> candidates = new ArrayList<Particle>(buffers.get(emitter));
			for (Integer i = 0; i < candidates.size(); i = i + 2) {
				springConnectedParticles.add(candidates.get(i));
			}
		} else if (springContructionRule == 2) {
			/**
			 * connect with the 3*next particles
			 * 
			 *    *-----*-----*-----*-----*
			 *      *-----*-----*-----*-----*
			 *        *-----*-----*-----*-----*
			 */
			ArrayList<Particle> candidates = new ArrayList<Particle>(buffers.get(emitter));
			for (Integer i = 0; i < candidates.size(); i = i + 3) {
				springConnectedParticles.add(candidates.get(i));
			}
		} else if (springContructionRule == 3) {
			/**
			 *    *---*---*---*---*---
			 *    |   |   |   |   |      (...)
			 *    *---*---*---*---*---
			 */
			ArrayList<Particle> previousParticles = new ArrayList<Particle>(buffers.get(emitter));
			Integer lastIndex = previousParticles.size() - 1;
			if (previousParticles.size() == 3) {
				Particle previousParticle = previousParticles.get(lastIndex);
				if (previousParticle.containsKey("SPRING_MOD_2")) {
					springConnectedParticles.add(previousParticles.get(lastIndex - 1));
				} else {
					springConnectedParticles.add(previousParticles.get(lastIndex));
					springConnectedParticles.add(previousParticles.get(lastIndex - 1));
					particle.put("SPRING_MOD_2", true);
				}
			}
		} else if (springContructionRule == 4) {
			/**
			 *      *---*---*---*---*---
			 *     /|  /|  /|  /|  /|  
			 *    *-+-*-+-*-+-*-+-*-+-
			 *    |/  |/  |/  |/  |/     (...)
			 *    *---*---*---*---*---
			 */
			ArrayList<Particle> previousParticles = new ArrayList<Particle>(buffers.get(emitter));
			Integer lastIndex = previousParticles.size() - 1;
			if (previousParticles.size() == 5) {
				Particle previousParticle = previousParticles.get(lastIndex);
				if (previousParticle.containsKey("SPRING_MOD_4")) { // step 1
					springConnectedParticles.add(previousParticles.get(lastIndex));
					springConnectedParticles.add(previousParticles.get(lastIndex - 1));
					springConnectedParticles.add(previousParticles.get(lastIndex - 4));
				} else if (previousParticle.containsKey("SPRING_MOD_3")) { // step 2
					springConnectedParticles.add(previousParticles.get(lastIndex - 1));
					springConnectedParticles.add(previousParticles.get(lastIndex - 4));
					particle.put("SPRING_MOD_4", true);
				} else if (previousParticle.containsKey("SPRING_MOD_2")) { // step 3
					springConnectedParticles.add(previousParticles.get(lastIndex));
					springConnectedParticles.add(previousParticles.get(lastIndex - 4));
					particle.put("SPRING_MOD_3", true);
				} else { // step 4
					springConnectedParticles.add(previousParticles.get(lastIndex - 4));
					particle.put("SPRING_MOD_2", true);
				}
			}
		}
		particle.put(SPRING_CONNECTED_PARTICLES, springConnectedParticles);
	}
	
	@Override
	public Boolean validFieldName(String fieldName) {
		return (fieldName.equals(SPRING_CONSTRUCTION_RULE)
			|| fieldName.equals(NUMBER_SPRINGS)
			|| fieldName.equals(SPRING_LENGTH)
			|| fieldName.equals(SPRING_FRICTION)
			|| fieldName.equals(SPRING_CONSTANT));
	}

}
