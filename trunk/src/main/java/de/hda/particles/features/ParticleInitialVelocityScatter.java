package de.hda.particles.features;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.hud.HUDEditorEntry;

/**
 * This particle feature scatters the initial velocity.
 * 
 * @author aschaeffer
 *
 */
public class ParticleInitialVelocityScatter implements ParticleFeature {

	public static final String SCATTER_X = "scatter_x";
	public static final String SCATTER_Y = "scatter_y";
	public static final String SCATTER_Z = "scatter_z";

	private final Random random = new Random();

	@Override
	public void init(ParticleEmitter emitter, Particle particle) {
		if (!emitter.getConfiguration().containsKey(SCATTER_X) || !emitter.getConfiguration().containsKey(SCATTER_Y) || !emitter.getConfiguration().containsKey(SCATTER_Z)) return;
		Double scatterX = (Double) emitter.getConfiguration().get(SCATTER_X);
		Double scatterY = (Double) emitter.getConfiguration().get(SCATTER_Y);
		Double scatterZ = (Double) emitter.getConfiguration().get(SCATTER_Z);
		Vector3f velocity = particle.getVelocity();
		Float vdx = new Double(new Double(velocity.getX()) + scatterX * random.nextDouble() - scatterX / 2.0).floatValue();
		Float vdy = new Double(new Double(velocity.getY()) + scatterY * random.nextDouble() - scatterY / 2.0).floatValue();
		Float vdz = new Double(new Double(velocity.getZ()) + scatterZ * random.nextDouble() - scatterZ / 2.0).floatValue();
		velocity.setX(vdx);
		velocity.setY(vdy);
		velocity.setZ(vdz);
		particle.setVelocity(velocity); // apply velocity changes
	}

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		List<HUDEditorEntry> entries = new ArrayList<HUDEditorEntry>();
		entries.add(HUDEditorEntry.create(SCATTER_X, "Scatter X"));
		entries.add(HUDEditorEntry.create(SCATTER_Y, "Scatter Y"));
		entries.add(HUDEditorEntry.create(SCATTER_Z, "Scatter Z"));
		return entries;
	}

	@Override
	public void decrease(ParticleEmitter emitter, String fieldName) {
		if (!fieldName.equals(SCATTER_X) && !fieldName.equals(SCATTER_Y) && !fieldName.equals(SCATTER_Z))
			return;
		Double value = (Double) emitter.getConfiguration().get(fieldName);
		if (value == null) value = 0.0;
		value -= 0.1;
		emitter.getConfiguration().put(fieldName, value);
	}

	@Override
	public void increase(ParticleEmitter emitter, String fieldName) {
		if (!fieldName.equals(SCATTER_X) && !fieldName.equals(SCATTER_Y) && !fieldName.equals(SCATTER_Z))
			return;
		Double value = (Double) emitter.getConfiguration().get(fieldName);
		if (value == null) value = 0.0;
		value += 0.1;
		emitter.getConfiguration().put(fieldName, value);
	}

	@Override
	public String getValue(ParticleEmitter emitter, String fieldName) {
		if (!fieldName.equals(SCATTER_X) && !fieldName.equals(SCATTER_Y) && !fieldName.equals(SCATTER_Z))
			return null;
		Double value = (Double) emitter.getConfiguration().get(fieldName);
		if (value == null) {
			return null;
		} else {
			return String.format("%.2f", value);
		}
	}

}
