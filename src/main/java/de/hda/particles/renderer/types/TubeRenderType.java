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

public class TubeRenderType extends AbstractRenderType implements RenderType {

	private final static Integer SEGMENTS = 16;

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
		glLineWidth(3.0f);
		glBegin(GL_LINES);
	}
	
	@Override
	public void after() {
		glEnd();
		glPopMatrix();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void render(Particle particle) {
		CircularFifoBuffer positionPathBuffer = (CircularFifoBuffer) particle.get(PositionPath.BUFFERED_POSITIONS);
		if (positionPathBuffer == null) return;
		ArrayList<Vector3f> positionPath = new ArrayList<Vector3f>(positionPathBuffer);

//		Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
//		if (color != null)
//			glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
		
		ListIterator<Vector3f> iterator = positionPath.listIterator();
		lastPosition = null;
		nextPosition = null;
		lastUnitTangent = null;
		lastUnitNormal = null;
		lastUnitBinormal = null;
		nextUnitTangent = null;
		nextUnitNormal = null;
		nextUnitBinormal = null;
		// if (iterator.hasNext()) nextPosition = iterator.next();
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

			glBegin(GL_QUAD_STRIP);
			glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
			for(int j=0; j <= SEGMENTS; j++) {
				double x = radius * Math.cos(2 * Math.PI / SEGMENTS * j);
				double y = radius * Math.sin(2 * Math.PI / SEGMENTS * j);
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

//			glBegin(GL_LINE_LOOP);
//			for(int j=0; j < segments; j++) {
//				double x = radius * Math.cos(2 * Math.PI / segments * j);
//				double y = radius * Math.sin(2 * Math.PI / segments * j);
//				// Affine Transformation
//				// point = normal(t)*x + binormal(t)*y + curve(t);
//				point = new Vector3f(lastPosition);
//				point.x += x * nextUnitNormal.x + y * nextUnitBinormal.x;
//				point.y += x * nextUnitNormal.y + y * nextUnitBinormal.y;
//				point.z += x * nextUnitNormal.z + y * nextUnitBinormal.z;
//				glVertex3f(point.x, point.y, point.z);
//			}
//			glEnd();
//
//			if (lastUnitTangent == null || lastUnitNormal == null || lastUnitBinormal == null) continue;
//			
//			glBegin(GL_LINES);
//			for(int j=0; j < segments; j++) {
//				double x = radius * Math.cos(2 * Math.PI / segments * j);
//				double y = radius * Math.sin(2 * Math.PI / segments * j);
//				// Affine Transformation
//				point = new Vector3f(nextPosition);
//				point.x += x * nextUnitNormal.x + y * nextUnitBinormal.x;
//				point.y += x * nextUnitNormal.y + y * nextUnitBinormal.y;
//				point.z += x * nextUnitNormal.z + y * nextUnitBinormal.z;
//				glVertex3f(point.x, point.y, point.z);
//				point = new Vector3f(lastPosition);
//				point.x += x * lastUnitNormal.x + y * lastUnitBinormal.x;
//				point.y += x * lastUnitNormal.y + y * lastUnitBinormal.y;
//				point.z += x * lastUnitNormal.z + y * lastUnitBinormal.z;
//				glVertex3f(point.x, point.y, point.z);
//			}
//			glEnd();
			
			
			// point1 := lastPosition
			// point2 := nextPosition
//			Vector3f.sub(nextPosition, lastPosition, nextNormal);
//			nextNormal.normalise(nextUnitTangent);
//			Vector3f.cross(nextUnitTangent, upUnitVector, nextUnitNormal);
//			Vector3f.cross(nextUnitTangent, nextUnitNormal, nextUnitBinormal);
			
//			point1 := curve(t);
//			point2 := curve(t + delta);
//			unitTangent := normalize(point2 - point1);
//			unitNormal := crossProduct(unitTangent, vec3(0, 1, 0));
//			unitBinormal := crossProduct(unitTangent, unitNormal);
			
			
			// frenet frame: an jedem punkt der kurve eine orthonormalbasis
			// Idee: Entlang der Raumkurve Ringe in der von der Einheitsnormale
			// und Einheitsbinormale aufgespannten Ebene zeichnen
			
			// http://de.wikipedia.org/wiki/Frenetsche_Formeln
			// 
			// Für einen Kurvenpunkt \vec{r}(s) erhält man durch Ableiten nach s den
			// Tangenteneinheitsvektor, der die momentane Richtung der Kurve, also die
			// Änderung der Position bei einer Änderung der Bogenlänge, angibt
			// haben wir schon, ist "lastNormal"
			// T := TangentenEinheitsVektor (lastNormal)
			// T_e = d_r / d_s
			//
			// N := HauptNormalenEinheitsVektor ()
			// N_e = d_T_e / d_s
			// 
			// B := BinormalenEinheitsVektor
			// B_e = T_e x N_e
			
			
//			Vector3f tangentUnitVector = new Vector3f(lastNormal); // T
//			Vector3f normalUnitVector = new Vector3f(); // N
//			Vector3f.cross(tangentUnitVector, upUnitVector, normalUnitVector);
//			Vector3f binormalUnitVector = new Vector3f(); // B
//			Vector3f.cross(tangentUnitVector, normalUnitVector, binormalUnitVector);
//			
//			
//			Vector
//			point1 := curve(t - delta/2);
//			point2 := curve(t + delta/2);
//			unitTangent := normalize(point2 - point1);
//			unitNormal
//			:= crossProduct(unitTangent, vec3(0, 1, 0));
//			unitBinormal := crossProduct(unitTangent, unitNormal);
			
			// https://github.com/mrdoob/three.js/blob/master/src/extras/geometries/TubeGeometry.js
			
			
			
			
			// central curve of tube C(v)
			// closed planar curve Y(u) = (y_1(u), y_2(u))
			// coordinate direction T(v) = C'(v) / |C'(v)|
			// other coordinate direction N(v) and B(v)
			// right handed orthonormal set: T(v), N(v), B(v)
			// N := curve normal
			// B := curve binormal: B = T x N
			// X(u,v) = C(v) + y_1(u) * N(v) + y_2 * B(v)
			// Y(u) = r(cos u, sin u)
			// r := radius
			
			// Float angle = Vector3f.angle(lastNormal, nextNormal);
			// translate to next
			// rotate by angle
			// draw circle
//			glPushMatrix();
//			glTranslated(lastPosition.x, lastPosition.y, lastPosition.z);
//			float radius = 30.0f;
//			for (int i=0; i <= 36; i++) {
//				float degInRad = i * 10 * DEG2RAD;
//				glVertex3f(new Double(Math.cos(degInRad) * radius).floatValue(), new Double(Math.sin(degInRad) * radius).floatValue(), 0.0f);
//			}
//			glPopMatrix();
			
			// punkt und normale: alte position und direction
			// punkt plus  
			// otho
			
//			for (int i=0; i <= 360; i++) {
//				float degInRad = i * DEG2RAD;
//				glVertex2f(Math.cos(degInRad) * radius, Math.sin(degInRad) * radius);
//			}
//			
//			
//			glVertex3f(particle.getX(), particle.getY() + 10.0f, particle.getZ());
//			glVertex3f(particle.getX() + 10.0f, particle.getY(), particle.getZ());
//			glVertex3f(particle.getX(), particle.getY(), particle.getZ() + 10.0f);
//
//			glTexCoord2f(0.5f, 0.5f);
//			glVertex3f(lastPosition.x, lastPosition.y, lastPosition.z);
//			glVertex3f(lastPosition.x + direction.x, lastPosition.y + direction.y, lastPosition.z + direction.z);
		}
	}

}
