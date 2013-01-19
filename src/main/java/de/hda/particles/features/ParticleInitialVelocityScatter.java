package de.hda.particles.features;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.editor.HUDEditorEntry;
import de.hda.particles.emitter.ParticleEmitter;

/**
 * This particle feature scatters the initial velocity.
 * 
 * @author aschaeffer
 *
 */
public class ParticleInitialVelocityScatter extends AbstractParticleFeature implements ParticleFeature {

	public static final String SCATTER_X = "scatter_x";
	public static final String SCATTER_Y = "scatter_y";
	public static final String SCATTER_Z = "scatter_z";
	public static final String SCATTER_PULSE = "scatterPulse";
	public static final String SCATTER_PULSE_RESOLUTION = "scatterPulseResolution";

	public static final Double DEFAULT_SCATTER_X = 0.0;
	public static final Double DEFAULT_SCATTER_Y = 0.0;
	public static final Double DEFAULT_SCATTER_Z = 0.0;
	public static final Double DEFAULT_SCATTER_PULSE = 1.0;
	public static final Double DEFAULT_SCATTER_PULSE_RESOLUTION = 0.0;

	private final Random random = new Random();

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
		entries.add(HUDEditorEntry.create(SCATTER_X, "Scatter X"));
		entries.add(HUDEditorEntry.create(SCATTER_Y, "Scatter Y"));
		entries.add(HUDEditorEntry.create(SCATTER_Z, "Scatter Z"));
		entries.add(HUDEditorEntry.create(SCATTER_PULSE, "Scatter Pulse"));
		entries.add(HUDEditorEntry.create(SCATTER_PULSE_RESOLUTION, "Scatter Pulse Resolution"));
		return entries;
	}

	@Override
	public void init(final ParticleEmitter emitter, final Particle particle) {
		if (!emitter.getConfiguration().containsKey(SCATTER_X) || !emitter.getConfiguration().containsKey(SCATTER_Y) || !emitter.getConfiguration().containsKey(SCATTER_Z)) return;
		Double scatterX = (Double) emitter.getConfiguration().get(SCATTER_X);
		Double scatterY = (Double) emitter.getConfiguration().get(SCATTER_Y);
		Double scatterZ = (Double) emitter.getConfiguration().get(SCATTER_Z);
		Double scatterPulse = (Double) emitter.getConfiguration().get(SCATTER_PULSE);
		Double scatterPulseResolution = (Double) emitter.getConfiguration().get(SCATTER_PULSE_RESOLUTION);
		if (scatterPulse == null)
			scatterPulse = DEFAULT_SCATTER_PULSE;
		if (scatterPulseResolution == null)
			scatterPulseResolution = DEFAULT_SCATTER_PULSE_RESOLUTION;
		Double pulsedScatterX = scatterX - Math.sin(emitter.getPastIterations() * scatterPulseResolution) * scatterX * scatterPulse;
		Double pulsedScatterY = scatterY - Math.sin(emitter.getPastIterations() * scatterPulseResolution) * scatterY * scatterPulse;
		Double pulsedScatterZ = scatterZ - Math.sin(emitter.getPastIterations() * scatterPulseResolution) * scatterZ * scatterPulse;
		Vector3f velocity = particle.getVelocity();
		Float vdx = new Double(new Double(velocity.getX()) + pulsedScatterX * random.nextDouble() - pulsedScatterX / 2.0).floatValue();
		Float vdy = new Double(new Double(velocity.getY()) + pulsedScatterY * random.nextDouble() - pulsedScatterY / 2.0).floatValue();
		Float vdz = new Double(new Double(velocity.getZ()) + pulsedScatterZ * random.nextDouble() - pulsedScatterZ / 2.0).floatValue();
		velocity.setX(vdx);
		velocity.setY(vdy);
		velocity.setZ(vdz);
		particle.setVelocity(velocity); // apply velocity changes
	}

	@Override
	public void decrease(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		decreaseDoubleValue(emitter, fieldName);
	}

	@Override
	public void decreaseMin(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		emitter.getConfiguration().put(fieldName, 0.0);
	}

	@Override
	public void increase(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		increaseDoubleValue(emitter, fieldName);
	}

	@Override
	public void increaseMax(final ParticleEmitter emitter, final String fieldName) {
		if (!validFieldName(fieldName)) return;
		emitter.getConfiguration().put(fieldName, 100.0);
	}
	
	@Override
	public void setDefault(final ParticleEmitter emitter, final String fieldName) {
		if (fieldName.equals(SCATTER_X)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_SCATTER_X);
		} else if (fieldName.equals(SCATTER_Y)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_SCATTER_Y);
		} else if (fieldName.equals(SCATTER_Z)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_SCATTER_Z);
		} else if (fieldName.equals(SCATTER_PULSE)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_SCATTER_PULSE);
		} else if (fieldName.equals(SCATTER_PULSE_RESOLUTION)) {
			emitter.getConfiguration().put(fieldName, DEFAULT_SCATTER_PULSE_RESOLUTION);
		}
	}

	@Override
	public String getValue(final ParticleEmitter emitter, final String fieldName) {
		return getDoubleValueAsString(emitter, fieldName);
	}
	
	@Override
	public Boolean validFieldName(final String fieldName) {
		return (fieldName.equals(SCATTER_X)
			|| fieldName.equals(SCATTER_Y)
			|| fieldName.equals(SCATTER_Z)
			|| fieldName.equals(SCATTER_PULSE)
			|| fieldName.equals(SCATTER_PULSE_RESOLUTION));
	}

}
