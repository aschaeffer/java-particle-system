package de.hda.particles.emitter.impl;

import de.hda.particles.emitter.ParticleEmitter;
import org.lwjgl.Sys;

/**
 * This particle emitter is  
 * @author aschaeffer
 *
 */
public class PooledPulseRatePointParticleEmitter extends PooledPointParticleEmitter implements ParticleEmitter {

	private final static Double RATE_STRETCH_FACTOR = 1000.0;
	private final static Double RATE_SCALE_FACTOR = 5.0;
	private final static Double MIN_RATE= 0.0;
	
	private long lastFrameTimeStamp = 0; // when the last frame was

	public PooledPulseRatePointParticleEmitter() {}

	/**
	 * Creates new particles and adds them to the particle system
	 */
	@Override
	public void update() {
		long frameTimeStamp = Sys.getTime();
		long timeDelta = frameTimeStamp - lastFrameTimeStamp;
		lastFrameTimeStamp = frameTimeStamp;
		rate = new Double((Math.sin(new Double(timeDelta * RATE_STRETCH_FACTOR)) * RATE_SCALE_FACTOR) + MIN_RATE).intValue();
		super.update();
	}

}
