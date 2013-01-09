package de.hda.particles.renderer.faces;

import de.hda.particles.domain.Face;

/**
 * Renders polygons of particles connected to the particle.
 * 
 * @author aschaeffer
 *
 */
public class NullFaceRenderer extends AbstractFaceRenderer implements FaceRenderer {

	public final static String NAME = "Polygon";

	public NullFaceRenderer() {}

	@Override
	public void before() {
	}
	
	@Override
	public void after() {
	}

	@Override
	public void render(Face face) {
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
