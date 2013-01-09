package de.hda.particles.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The default implementation stores 4 particle objects.
 * @author aschaeffer
 *
 */
public abstract class AbstractFace extends ArrayList<Particle> implements Face {

	private static final long serialVersionUID = -5591190730917309984L;

	private int faceRendererIndex = DEFAULT_FACE_RENDERER_INDEX;
	private boolean visibility = true;

	public AbstractFace() {
	}

	public AbstractFace(List<Particle> particles, int faceRendererIndex) {
		this.addAll(particles);
		this.faceRendererIndex = faceRendererIndex;
	}
	
	@Override
	public float[] getVertices() {
		float[] vertices = new float[size()*3];
		for (Integer i = 0; i < size(); i++) {
			vertices[i*3] = get(i).getX();
			vertices[i*3 + 1] = get(i).getY();
			vertices[i*3 + 2] = get(i).getZ();
		}
		return vertices;
	}
	
	@Override
	public int getFaceRendererIndex() {
		return faceRendererIndex;
	}
	
	@Override
	public void setFaceRendererIndex(int faceRendererIndex) {
		this.faceRendererIndex = faceRendererIndex;
	}

	@Override
	public boolean isVisible() {
		return visibility;
	}
	
	@Override
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}
	
	@Override
	public boolean isAlive() {
		for (Integer i = 0; i < size(); i++) {
			if (get(i).getRemainingIterations() <= 0) return false;
		}
		return true;
	}

}
