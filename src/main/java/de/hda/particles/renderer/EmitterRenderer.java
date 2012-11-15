package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.glu.Sphere;

import de.hda.particles.emitter.ParticleEmitter;

public class EmitterRenderer extends AbstractRenderer implements Renderer {

	public EmitterRenderer() {}

	@Override
	public void update() {
		List<ParticleEmitter> currentEmitters = new ArrayList<ParticleEmitter>(scene.getParticleSystem().getParticleEmitters());
		ListIterator<ParticleEmitter> pIterator = currentEmitters.listIterator(0);
		while (pIterator.hasNext()) {
			ParticleEmitter emitter = pIterator.next();
			if (emitter != null) {
				glPushMatrix();
				glColor4f(0.3f, 0.3f, 1.0f, 0.8f);
//				if (emitter.getConfiguration().containsKey("ROTATE_ORIGIN")) {
//					glColor4f(0.3f, 1.0f, 1.0f, 0.8f);
//					Float angle = (Float) emitter.getConfiguration().get("ROTATE_ORIGIN");
//					glRotatef((float) angle*emitter.getPastIterations(), 0.0f, 1.0f, 0.0f);
//				}
		        glTranslatef(emitter.getPosition().x, emitter.getPosition().y, emitter.getPosition().z);
		        Sphere s = new Sphere();
		        s.draw(10.0f, 16, 16);
				glPopMatrix();
			}
		}
	}

}
