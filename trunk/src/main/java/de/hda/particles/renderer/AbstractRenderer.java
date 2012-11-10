package de.hda.particles.renderer;

public abstract class AbstractRenderer implements Renderer {

	@Override
	public void setup() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public Boolean isFinished() {
		return false;
	}

}
