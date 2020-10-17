package de.hda.particles.modifier.impl.gravity;

import de.hda.particles.domain.Particle;
import de.hda.particles.modifier.ParticleModifier;

public class GravityPulsar extends GravityPoint implements ParticleModifier {

	public final static String MIN_GRAVITY = "gravity_min";
	public final static String MAX_GRAVITY = "gravity_max";
	public final static String MIN_MASS = "mass_min";
	public final static String MAX_MASS = "mass_max";

	public final static Double DEFAULT_MIN_GRAVITY = 0.2;
	public final static Double DEFAULT_MAX_GRAVITY = 10.0;
	public final static Double DEFAULT_MIN_MASS = 500.0;
	public final static Double DEFAULT_MAX_MASS = 5000.0;
	public final static Double DEFAULT_MAX_FORCE = 0.7;
	
	private float minGravity = DEFAULT_MIN_GRAVITY.floatValue();
	private float maxGravity = DEFAULT_MAX_GRAVITY.floatValue();
	private float minMass = DEFAULT_MIN_MASS.floatValue();
	private float maxMass = DEFAULT_MAX_MASS.floatValue();
	
	private Float currentGravity = DEFAULT_GRAVITY.floatValue();
	private Float currentMass = DEFAULT_MASS.floatValue();
	
	public GravityPulsar() {}

	@Override
	public void prepare() {
		if (!expectKeys()) return;
		super.prepare();
		minGravity = ((Double) this.configuration.get(MIN_GRAVITY)).floatValue();
		maxGravity = ((Double) this.configuration.get(MAX_GRAVITY)).floatValue();
		minMass = ((Double) this.configuration.get(MIN_MASS)).floatValue();
		maxMass = ((Double) this.configuration.get(MAX_MASS)).floatValue();
		currentGravity = new Double(Math.sin(particleSystem.getPastIterations() / (maxGravity - minGravity))).floatValue() + minGravity;
		currentMass = new Double(Math.sin(particleSystem.getPastIterations() / (maxMass - minMass))).floatValue() + minMass;
	}

	@Override
	public void update(Particle particle) {
		if (!expectKeys()) return;
		updateParticleVelocity(particle, position, currentMass, currentGravity, maxForce);
	}

}
