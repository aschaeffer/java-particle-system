package de.hda.particles;

public class DefaultParticleSystem extends AbstractParticleSystem implements ParticleSystem {

	public final static String SYSTEM_NAME = "particle physics";
    
	@Override
	public String getSystemName() {
		return SYSTEM_NAME;
	}
	
}
