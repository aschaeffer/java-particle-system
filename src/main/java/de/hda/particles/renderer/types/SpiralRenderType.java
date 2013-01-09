package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.ListIterator;

import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.Particle;
import de.hda.particles.domain.factory.PositionPathBufferingConfigurationFactory;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.features.ParticleSize;
import de.hda.particles.features.PositionPath;
import de.hda.particles.features.TubeSegments;
import de.hda.particles.modifier.PositionPathBuffering;

public class SpiralRenderType extends AbstractRenderType implements RenderType {

	public final static String NAME = "Sprial";

	private final Vector3f upUnitVector = new Vector3f(0.0f, 1.0f, 0.0f);
	private Vector3f lastPosition = null;
	private Vector3f lastUnitTangent = null;
	private Vector3f lastUnitNormal = null;
	private Vector3f lastUnitBinormal = null;
	private Vector3f nextPosition = null;
	private final Vector3f nextTangent = new Vector3f();
	private final Vector3f nextUnitTangent = new Vector3f();
	private final Vector3f nextUnitNormal = new Vector3f();
	private final Vector3f nextUnitBinormal = new Vector3f();
	private float px;
	private float py;
	private float pz;
	private float length;
	private Double radius;
	private int twist;
	private int twist2;
	private float affineX;
	private float affineY;

	private final Logger logger = LoggerFactory.getLogger(SpiralRenderType.class);

	public SpiralRenderType() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
//		glEnable(GL_CULL_FACE);
//		glCullFace(GL_FRONT_AND_BACK);
	}
	
	@Override
	public void after() {
//		glDisable(GL_CULL_FACE);
		glPopMatrix();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void render(Particle particle) {
		CircularFifoBuffer positionPathBuffer = (CircularFifoBuffer) particle.get(PositionPath.BUFFERED_POSITIONS);
		if (positionPathBuffer == null || positionPathBuffer.size() < 5) return;
		ArrayList<Vector3f> positionPath = new ArrayList<Vector3f>(positionPathBuffer);
		ListIterator<Vector3f> iterator = positionPath.listIterator();
		nextPosition = null;
		while (iterator.hasNext()) {
			lastPosition = nextPosition;
			nextPosition = iterator.next();

			if (lastPosition == null || nextPosition == null) continue;
			if (lastPosition.x == nextPosition.x || lastPosition.y == nextPosition.y || lastPosition.z == nextPosition.z) continue;

			lastUnitTangent = nextUnitTangent;
			lastUnitNormal = nextUnitNormal;
			lastUnitBinormal = nextUnitBinormal;

			nextTangent.x = nextPosition.x - lastPosition.x;
			nextTangent.y = nextPosition.y - lastPosition.y;
			nextTangent.z = nextPosition.z - lastPosition.z;

			length = nextTangent.length();
			if (length == 0.0f) length = 1.0f;
			nextUnitTangent.x = nextTangent.x / length;
			nextUnitTangent.y = nextTangent.y / length;
			nextUnitTangent.z = nextTangent.z / length;

			nextUnitNormal.x = nextUnitTangent.y * upUnitVector.z - nextUnitTangent.z * upUnitVector.y;
			nextUnitNormal.y = upUnitVector.x * nextUnitTangent.z - upUnitVector.z * nextUnitTangent.x;
			nextUnitNormal.z = nextUnitTangent.x * upUnitVector.y - nextUnitTangent.y * upUnitVector.x;

			nextUnitBinormal.x = nextUnitTangent.y * nextUnitNormal.z - nextUnitTangent.z * nextUnitNormal.y;
			nextUnitBinormal.y = nextUnitNormal.x * nextUnitTangent.z - nextUnitNormal.z * nextUnitTangent.x;
			nextUnitBinormal.z = nextUnitTangent.x * nextUnitNormal.y - nextUnitTangent.y * nextUnitNormal.x;

			if (lastUnitTangent == null || lastUnitNormal == null || lastUnitBinormal == null) continue;

			radius = (Double) particle.get(ParticleSize.CURRENT_SIZE);
			if (radius == null) {
				radius = ParticleSize.DEFAULT_SIZE_BIRTH;
			}
			Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
			if (color == null) {
				color = ParticleColor.DEFAULT_COLOR;
			}
			Integer numberOfSegments = (Integer) particle.get(TubeSegments.NUMBER_OF_SEGMENTS);
			if (numberOfSegments == null) {
				numberOfSegments = TubeSegments.DEFAULT_NUMBER_OF_SEGMENTS;
			}
			Integer segmentsToDraw = (Integer) particle.get(TubeSegments.SEGMENTS_TO_DRAW);
			if (segmentsToDraw == null) {
				segmentsToDraw = TubeSegments.DEFAULT_SEGMENTS_TO_DRAW;
			}
			Integer currentStartSegment = (Integer) particle.get(TubeSegments.CURRENT_START_SEGMENT);
			if (currentStartSegment == null) {
				currentStartSegment = TubeSegments.DEFAULT_CURRENT_START_SEGMENT;
			}
			Integer segmentTwist = (Integer) particle.get(TubeSegments.SEGMENT_TWIST);
			if (segmentTwist == null) {
				segmentTwist = TubeSegments.DEFAULT_SEGMENT_TWIST;
			}
			twist = iterator.nextIndex() * segmentTwist;
			twist2 = (iterator.nextIndex()+1) * segmentTwist;

			glBegin(GL_QUAD_STRIP); // start drawing (quad strips are ideal for this task)
			glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
			for(int j=currentStartSegment; j <= currentStartSegment+segmentsToDraw; j++) { // quad strip: draw one segment more for full tube
				affineX = new Double(radius * Math.cos(2 * Math.PI / numberOfSegments * (j+twist))).floatValue();
				affineY = new Double(radius * Math.sin(2 * Math.PI / numberOfSegments * (j+twist))).floatValue();
				// Affine Transformation
				px = nextPosition.x + (affineX * nextUnitNormal.x + affineY * nextUnitBinormal.x);
				py = nextPosition.y + (affineX * nextUnitNormal.y + affineY * nextUnitBinormal.y);
				pz = nextPosition.z + (affineX * nextUnitNormal.z + affineY * nextUnitBinormal.z);
				glVertex3f(px, py, pz);
				affineX = new Double(radius * Math.cos(2 * Math.PI / numberOfSegments * (j+twist2))).floatValue();
				affineY = new Double(radius * Math.sin(2 * Math.PI / numberOfSegments * (j+twist2))).floatValue();
				px = lastPosition.x + (affineX * lastUnitNormal.x + affineY * lastUnitBinormal.x);
				py = lastPosition.y + (affineX * lastUnitNormal.y + affineY * lastUnitBinormal.y);
				pz = lastPosition.z + (affineX * lastUnitNormal.z + affineY * lastUnitBinormal.z);
				glVertex3f(px, py, pz);
			}
			glEnd();
		}
	}

	@Override
	public void addDependencies() {
		scene.getParticleSystem().addParticleFeature(ParticleColor.class);
		scene.getParticleSystem().addParticleFeature(ParticleSize.class);
		scene.getParticleSystem().addParticleFeature(PositionPath.class);
		scene.getParticleSystem().addParticleFeature(TubeSegments.class);
		if (!scene.getParticleSystem().hasModifier(PositionPathBuffering.class)) {
			scene.getParticleSystem().addParticleModifier(PositionPathBuffering.class, PositionPathBufferingConfigurationFactory.create(scene));
		}
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
