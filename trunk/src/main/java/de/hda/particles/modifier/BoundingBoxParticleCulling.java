package de.hda.particles.modifier;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;

public class BoundingBoxParticleCulling extends AbstractParticleModifier implements ParticleModifier {

	public final static Double DEFAULT_BOUNDING_BOX_MIN_X = 1000.0;
	public final static String BOUNDING_BOX_MIN_X = "minX";
	public final static Double DEFAULT_BOUNDING_BOX_MIN_Y = 1000.0;
	public final static String BOUNDING_BOX_MIN_Y = "minY";
	public final static Double DEFAULT_BOUNDING_BOX_MIN_Z = 1000.0;
	public final static String BOUNDING_BOX_MIN_Z = "minZ";
	public final static Double DEFAULT_BOUNDING_BOX_MAX_X = 1000.0;
	public final static String BOUNDING_BOX_MAX_X = "maxX";
	public final static Double DEFAULT_BOUNDING_BOX_MAX_Y = 1000.0;
	public final static String BOUNDING_BOX_MAX_Y = "maxY";
	public final static Double DEFAULT_BOUNDING_BOX_MAX_Z = 1000.0;
	public final static String BOUNDING_BOX_MAX_Z = "maxZ";
	
	protected Vector3f bbMin = new Vector3f();
	protected Vector3f bbMax = new Vector3f();
	
	public BoundingBoxParticleCulling() {}

	@Override
	public void prepare() {
		Double bbMinX = (Double) configuration.get(BOUNDING_BOX_MIN_X);
		if (bbMinX == null) bbMinX = DEFAULT_BOUNDING_BOX_MIN_X;
		bbMin.setX(bbMinX.floatValue());
		Double bbMinY = (Double) configuration.get(BOUNDING_BOX_MIN_Y);
		if (bbMinY == null) bbMinY = DEFAULT_BOUNDING_BOX_MIN_Y;
		bbMin.setY(bbMinY.floatValue());
		Double bbMinZ = (Double) configuration.get(BOUNDING_BOX_MIN_Z);
		if (bbMinZ == null) bbMinZ = DEFAULT_BOUNDING_BOX_MIN_Z;
		bbMin.setZ(bbMinZ.floatValue());
		Double bbMaxX = (Double) configuration.get(BOUNDING_BOX_MAX_X);
		if (bbMaxX == null) bbMaxX = DEFAULT_BOUNDING_BOX_MAX_X;
		bbMax.setX(bbMaxX.floatValue());
		Double bbMaxY = (Double) configuration.get(BOUNDING_BOX_MAX_Y);
		if (bbMaxY == null) bbMaxY = DEFAULT_BOUNDING_BOX_MAX_Y;
		bbMax.setY(bbMaxY.floatValue());
		Double bbMaxZ = (Double) configuration.get(BOUNDING_BOX_MAX_Z);
		if (bbMaxZ == null) bbMaxZ = DEFAULT_BOUNDING_BOX_MAX_Z;
		bbMax.setZ(bbMaxZ.floatValue());
		
	}
	
	@Override
	public void update(Particle particle) {
		Vector3f position = particle.getPosition();
		if (position.x < bbMin.x || position.y < bbMin.y || position.z < bbMin.z || position.x > bbMax.x || position.y > bbMax.y || position.z > bbMax.z) {
			particle.setRemainingIterations(0);
		}
	}

}
