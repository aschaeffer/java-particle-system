package de.hda.particles.hud;

import de.hda.particles.ParticleSystem;
import de.hda.particles.scene.ParticleSystemScene;

public class FpsHUD extends AbstractHUD implements HUD {

	ParticleSystemScene renderer;
	ParticleSystem particleSystem;

	public FpsHUD(ParticleSystemScene renderer, ParticleSystem particleSystem, Integer width, Integer height) {
		super(width, height);
		this.renderer = renderer;
		this.particleSystem = particleSystem;
	}

	@Override
	public void update() {
        font.drawString(10, height - 20, String.format("fps: %.0f physics: %.0f", renderer.fps, particleSystem.fps));
	}

	@Override
	public void setup() {
		super.setup();
	}

}
