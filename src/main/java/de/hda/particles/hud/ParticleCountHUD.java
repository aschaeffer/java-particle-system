package de.hda.particles.hud;

import de.hda.particles.ParticleSystem;

public class ParticleCountHUD extends AbstractHUD implements HUD {

	ParticleSystem particleSystem;

	public ParticleCountHUD(ParticleSystem particleSystem, Integer width, Integer height) {
		super(width, height);
		this.particleSystem = particleSystem;
	}

	@Override
	public void update() {
		String text = String.format("particles: %d", particleSystem.particles.size());
        font.drawString(width - font.getWidth(text) - 20, height - 20, text);
	}

	@Override
	public void setup() {
		super.setup();
	}

}
