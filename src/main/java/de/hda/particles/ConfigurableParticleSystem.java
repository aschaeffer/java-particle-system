package de.hda.particles;

public class ConfigurableParticleSystem extends AbstractParticleSystem implements ParticleSystem {

	private String name = "Configurable Particle System";
	
	@Override
	public String getSystemName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
