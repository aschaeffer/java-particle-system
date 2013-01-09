package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.ListIterator;

import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.domain.factory.PositionPathBufferingConfigurationFactory;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.features.ParticleSize;
import de.hda.particles.features.PositionPath;
import de.hda.particles.features.TubeSegments;
import de.hda.particles.modifier.PositionPathBuffering;
import de.hda.particles.modifier.size.LinearSizeTransformation;
import de.hda.particles.modifier.size.PulseSizeTransformation;

public class TubeRenderType extends AbstractRenderType implements RenderType {

	public final static String NAME = "Tube";

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
	private float length;
	private Double radius;
	private Color color;
	private Integer numberOfSegments;
	private Integer segmentsToDraw;

	public TubeRenderType() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
	}
	
	@Override
	public void after() {
		glPopMatrix();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void render(Particle particle) {
		CircularFifoBuffer positionPathBuffer = (CircularFifoBuffer) particle.get(PositionPath.BUFFERED_POSITIONS);
		if (positionPathBuffer == null || positionPathBuffer.size() < 5) return;

		radius = (Double) particle.get(ParticleSize.CURRENT_SIZE);
		if (radius == null) {
			radius = ParticleSize.DEFAULT_SIZE_BIRTH;
		}
		color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		if (color == null) {
			color = ParticleColor.DEFAULT_COLOR;
		}
		numberOfSegments = (Integer) particle.get(TubeSegments.NUMBER_OF_SEGMENTS);
		if (numberOfSegments == null) {
			numberOfSegments = TubeSegments.DEFAULT_NUMBER_OF_SEGMENTS;
		}
		segmentsToDraw = (Integer) particle.get(TubeSegments.SEGMENTS_TO_DRAW);
		if (segmentsToDraw == null) {
			segmentsToDraw = TubeSegments.DEFAULT_SEGMENTS_TO_DRAW;
		}
		
		ArrayList<Vector3f> positionPath = new ArrayList<Vector3f>(positionPathBuffer);
		ListIterator<Vector3f> iterator = positionPath.listIterator();
		nextPosition = null;
		while (iterator.hasNext()) {
			lastPosition = nextPosition;
			nextPosition = iterator.next();

			if (lastPosition == null) continue; //  || nextPosition == null
			// if (lastPosition.x == nextPosition.x && lastPosition.y == nextPosition.y && lastPosition.z == nextPosition.z) continue;
			// if (lastPosition.x == nextPosition.x || lastPosition.y == nextPosition.y || lastPosition.z == nextPosition.z) continue;

			lastUnitTangent = nextUnitTangent;
			lastUnitNormal = nextUnitNormal;
			lastUnitBinormal = nextUnitBinormal;

			nextTangent.x = nextPosition.x - lastPosition.x;
			nextTangent.y = nextPosition.y - lastPosition.y;
			nextTangent.z = nextPosition.z - lastPosition.z;
//			nextTangent.x = lastPosition.x - nextPosition.x;
//			nextTangent.y = lastPosition.y - nextPosition.y;
//			nextTangent.z = lastPosition.z - nextPosition.z;

			length = (float) Math.sqrt(nextTangent.x*nextTangent.x +nextTangent.y*nextTangent.y + nextTangent.z*nextTangent.z);
			// length = nextTangent.length();
			if (length == 0.0f) length = 0.0001f;
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

			// glBegin(GL_TRIANGLE_STRIP); // triangle strip is faster than quad strip
			glBegin(GL_QUAD_STRIP); // start drawing (quad strips are ideal for this task)
			glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
			for(int j=0; j <= segmentsToDraw; j++) { // quad strip: draw one segment more for full tube
				px = new Double(radius * Math.cos(2 * Math.PI / numberOfSegments * j)).floatValue();
				py = new Double(radius * Math.sin(2 * Math.PI / numberOfSegments * j)).floatValue();
				// Affine Transformation
				glVertex3f(
					nextPosition.x + px * nextUnitNormal.x + py * nextUnitBinormal.x,
					nextPosition.y + px * nextUnitNormal.y + py * nextUnitBinormal.y,
					nextPosition.z + px * nextUnitNormal.z + py * nextUnitBinormal.z
				);
				glVertex3f(
					lastPosition.x + px * lastUnitNormal.x + py * lastUnitBinormal.x,
					lastPosition.y + px * lastUnitNormal.y + py * lastUnitBinormal.y,
					lastPosition.z + px * lastUnitNormal.z + py * lastUnitBinormal.z
				);
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
		if (!scene.getParticleSystem().hasModifier(LinearSizeTransformation.class) && !scene.getParticleSystem().hasModifier(PulseSizeTransformation.class)) {
			scene.getParticleSystem().addParticleModifier(LinearSizeTransformation.class, new ParticleModifierConfiguration());
		}
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
