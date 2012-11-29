package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.ARBPointParameters.*;
import static org.lwjgl.opengl.ARBPointSprite.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.lwjgl.opengl.*;
import org.lwjgl.util.Color;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.MassSpring;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.features.PositionPath;

public class TubeRenderType extends AbstractRenderType implements RenderType {

	private static final Float DEG2RAD = 3.14159f / 180.0f;
	
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

	@Override
	public void render(Particle particle) {
		CircularFifoBuffer positionPathBuffer = (CircularFifoBuffer) particle.get(PositionPath.BUFFERED_POSITIONS);
		if (positionPathBuffer == null) return;
		ArrayList<Vector3f> positionPath = new ArrayList<Vector3f>(positionPathBuffer);

		Color color = (Color) particle.get(ParticleColor.CURRENT_COLOR);
		if (color != null)
			glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
		
		ListIterator<Vector3f> iterator = positionPath.listIterator();
		Vector3f lastPosition = null; // particle.getPosition();
		Vector3f lastNormal = null; // particle.getVelocity();
		Vector3f nextPosition = null;
		Vector3f nextNormal = new Vector3f();
		if (iterator.hasNext()) nextPosition = iterator.next();
		while (iterator.hasNext()) {
			lastPosition = nextPosition;
			lastNormal = nextNormal;
			nextPosition = iterator.next();
			if (nextPosition == null || lastPosition == null) continue;
			Vector3f.sub(nextPosition, lastPosition, nextNormal);
			if (lastNormal == null) continue;
			Float angle = Vector3f.angle(lastNormal, nextNormal);
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
