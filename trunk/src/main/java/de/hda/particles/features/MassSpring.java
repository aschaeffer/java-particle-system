package de.hda.particles.features;

import java.util.*;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.buffer.CircularFifoBuffer;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.hud.HUDEditorEntry;

public class MassSpring implements ParticleFeature {

	public static final String NUMBER_SPRINGS = "numberSprings";   // The number of springs
	public static final String SPRING_LENGTH = "springLength";     // The length that the spring does not exert any force
	public static final String SPRING_FRICTION = "springFriction"; // A  constant to be used for the inner friction of the spring
	public static final String SPRING_CONSTANT = "springConstant"; // A constant to represent the stiffness of the spring

	public static final String CONNECTED_PARTICLES = "connectedParticles";

	public static final Integer DEFAULT_NUMBER_SPRINGS = 2;
	public static final Double DEFAULT_SPRING_LENGTH = 5.0;
	public static final Double DEFAULT_SPRING_CONSTANT = 0.08;
	public static final Double DEFAULT_SPRING_FRICTION = 0.9;

	private final HashMap<ParticleEmitter, Buffer> buffers = new HashMap<ParticleEmitter, Buffer>();

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
		ArrayList<Particle> connectedParticles = new ArrayList<Particle>(buffers.get(emitter));
		particle.put(CONNECTED_PARTICLES, connectedParticles);
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
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
		entries.add(HUDEditorEntry.create(NUMBER_SPRINGS, "Number Of Springs"));
		entries.add(HUDEditorEntry.create(SPRING_LENGTH, "Spring Length"));
		entries.add(HUDEditorEntry.create(SPRING_FRICTION, "Spring Friction"));
		entries.add(HUDEditorEntry.create(SPRING_CONSTANT, "Spring Constant"));
		return entries;
	}

	@Override
	public void decrease(ParticleEmitter emitter, String fieldName) {
		if (fieldName.equals(NUMBER_SPRINGS)) {
			Integer value = (Integer) emitter.getConfiguration().get(fieldName);
			if (value == null) value = DEFAULT_NUMBER_SPRINGS;
			if (value > 0) value -= 1;
			if (value > 0) {
				emitter.getConfiguration().put(NUMBER_SPRINGS, value);
				Buffer buffer = buffers.get(emitter);
				if (buffer != null) buffer.clear();
				buffer = new CircularFifoBuffer(value);
				buffers.put(emitter, buffer);
			}
			emitter.getConfiguration().put(NUMBER_SPRINGS, value);
		} else if (fieldName.equals(SPRING_LENGTH)) {
			Double value = (Double) emitter.getConfiguration().get(fieldName);
			if (value == null) value = DEFAULT_SPRING_LENGTH;
			if (value >= 0.1) value -= 0.1;
			emitter.getConfiguration().put(SPRING_LENGTH, value);
		} else if (fieldName.equals(SPRING_FRICTION)) {
			Double value = (Double) emitter.getConfiguration().get(fieldName);
			if (value == null) value = DEFAULT_SPRING_FRICTION;
			if (value >= 0.01) value -= 0.01;
			emitter.getConfiguration().put(SPRING_FRICTION, value);
		} else if (fieldName.equals(SPRING_CONSTANT)) {
			Double value = (Double) emitter.getConfiguration().get(fieldName);
			if (value == null) value = DEFAULT_SPRING_CONSTANT;
			if (value >= 0.01) value -= 0.01;
			emitter.getConfiguration().put(SPRING_CONSTANT, value);
		}
	}

	@Override
	public void increase(ParticleEmitter emitter, String fieldName) {
		if (fieldName.equals(NUMBER_SPRINGS)) {
			Integer value = (Integer) emitter.getConfiguration().get(fieldName);
			if (value == null) value = DEFAULT_NUMBER_SPRINGS;
			value += 1;
			emitter.getConfiguration().put(NUMBER_SPRINGS, value);
			Buffer buffer = buffers.get(emitter);
			if (buffer != null) buffer.clear();
			buffer = new CircularFifoBuffer(value);
			buffers.put(emitter, buffer);
			emitter.getConfiguration().put(NUMBER_SPRINGS, value);
		} else if (fieldName.equals(SPRING_LENGTH)) {
			Double value = (Double) emitter.getConfiguration().get(fieldName);
			if (value == null) value = DEFAULT_SPRING_LENGTH;
			value += 0.1;
			emitter.getConfiguration().put(SPRING_LENGTH, value);
		} else if (fieldName.equals(SPRING_FRICTION)) {
			Double value = (Double) emitter.getConfiguration().get(fieldName);
			if (value == null) value = DEFAULT_SPRING_FRICTION;
			if (value < 1.0) value += 0.01;
			emitter.getConfiguration().put(SPRING_FRICTION, value);
		} else if (fieldName.equals(SPRING_CONSTANT)) {
			Double value = (Double) emitter.getConfiguration().get(fieldName);
			if (value == null) value = DEFAULT_SPRING_CONSTANT;
			value += 0.01;
			emitter.getConfiguration().put(SPRING_CONSTANT, value);
		}
	}

	@Override
	public String getValue(ParticleEmitter emitter, String fieldName) {
		if (!fieldName.equals(NUMBER_SPRINGS)
			&& !fieldName.equals(SPRING_LENGTH)
			&& !fieldName.equals(SPRING_FRICTION)
			&& !fieldName.equals(SPRING_CONSTANT)
		) return null;
		if (fieldName.equals(NUMBER_SPRINGS)) {
			Integer value = (Integer) emitter.getConfiguration().get(fieldName);
			if (value == null) return "N/A";
			return value.toString();
		} else {
			Double value = (Double) emitter.getConfiguration().get(fieldName);
			if (value == null) return "N/A";
			return String.format("%.2f", value);
		}
	}

}
