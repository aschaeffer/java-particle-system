package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.ListIterator;

import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.features.ParticleSize;
import de.hda.particles.features.PositionPath;
import de.hda.particles.features.TubeSegments;

public class TubeRenderType extends AbstractRenderType implements RenderType {

	private final Vector3f upUnitVector = new Vector3f(0.0f, 1.0f, 0.0f);
	private Vector3f lastPosition = null;
	private Vector3f lastUnitTangent = null;
	private Vector3f lastUnitNormal = null;
	private Vector3f lastUnitBinormal = null;
	private Vector3f nextPosition = null;
	private final Vector3f nextTangent = new Vector3f();
	private Vector3f nextUnitTangent = new Vector3f();
	private Vector3f nextUnitNormal = new Vector3f();
	private Vector3f nextUnitBinormal = new Vector3f();
	private Vector3f point;

	public TubeRenderType() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_FRONT_AND_BACK);
	}
	
	@Override
	public void after() {
		glDisable(GL_CULL_FACE);
		glPopMatrix();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void render(Particle particle) {
		CircularFifoBuffer positionPathBuffer = (CircularFifoBuffer) particle.get(PositionPath.BUFFERED_POSITIONS);
		if (positionPathBuffer == null) return;
		ArrayList<Vector3f> positionPath = new ArrayList<Vector3f>(positionPathBuffer);

		ListIterator<Vector3f> iterator = positionPath.listIterator();
		lastPosition = null;
		nextPosition = null;
		lastUnitTangent = null;
		lastUnitNormal = null;
		lastUnitBinormal = null;
		nextUnitTangent = null;
		nextUnitNormal = null;
		nextUnitBinormal = null;
		while (iterator.hasNext()) {
			lastPosition = nextPosition;
			nextPosition = iterator.next();
			if (lastPosition == null || nextPosition == null) continue;
			lastUnitTangent = nextUnitTangent;
			lastUnitNormal = nextUnitNormal;
			lastUnitBinormal = nextUnitBinormal;
			nextUnitTangent = new Vector3f();
			nextUnitNormal = new Vector3f();
			nextUnitBinormal = new Vector3f();
			Vector3f.sub(nextPosition, lastPosition, nextTangent);
			nextTangent.normalise(nextUnitTangent);
			Vector3f.cross(nextUnitTangent, upUnitVector, nextUnitNormal);
			Vector3f.cross(nextUnitTangent, nextUnitNormal, nextUnitBinormal);

			if (lastUnitTangent == null || lastUnitNormal == null || lastUnitBinormal == null) continue;

			Double radius = (Double) particle.get(ParticleSize.CURRENT_SIZE);
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
			// start drawing (quad strips are ideal for this task)
			glBegin(GL_QUAD_STRIP);
			glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
			for(int j=0; j <= segmentsToDraw; j++) { // quad strip: draw one segment more for full tube
				double x = radius * Math.cos(2 * Math.PI / numberOfSegments * j);
				double y = radius * Math.sin(2 * Math.PI / numberOfSegments * j);
				// Affine Transformation
				point = new Vector3f(nextPosition);
				point.x += x * nextUnitNormal.x + y * nextUnitBinormal.x;
				point.y += x * nextUnitNormal.y + y * nextUnitBinormal.y;
				point.z += x * nextUnitNormal.z + y * nextUnitBinormal.z;
				glVertex3f(point.x, point.y, point.z);
				point = new Vector3f(lastPosition);
				point.x += x * lastUnitNormal.x + y * lastUnitBinormal.x;
				point.y += x * lastUnitNormal.y + y * lastUnitBinormal.y;
				point.z += x * lastUnitNormal.z + y * lastUnitBinormal.z;
				glVertex3f(point.x, point.y, point.z);
			}
			glEnd();
		}
	}

}
