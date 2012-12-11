package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.hud.HUDCommand;
import de.hda.particles.hud.HUDCommandTypes;

public class EmitterRenderer extends AbstractMovable<ParticleEmitter> implements Renderer {

	private final Logger logger = LoggerFactory.getLogger(EmitterRenderer.class);

	public EmitterRenderer() {}

	@Override
	public void update() {
		if (!visible) return;

		List<ParticleEmitter> currentEmitters = scene.getParticleSystem().getParticleEmitters();
		ListIterator<ParticleEmitter> pIterator = currentEmitters.listIterator(0);
		while (pIterator.hasNext()) {
			ParticleEmitter emitter = pIterator.next();
			if (emitter != null) {
				glPushMatrix();
		        glTranslatef(emitter.getPosition().x, emitter.getPosition().y, emitter.getPosition().z);
		        Sphere s = new Sphere();
				if (emitter.equals(selected)) {
					glColor4f(1.0f, 0.3f, 0.3f, 0.8f);
			        s.setDrawStyle(GLU.GLU_LINE);
				} else {
					glColor4f(0.3f, 0.3f, 1.0f, 0.8f);
				}
		        s.draw(10.0f, 16, 16);
		        glPopMatrix();
		        renderTitle(emitter.getPosition(), "Emitter\nLifetime: " + emitter.getParticleLifetime() + "\nRate: " + emitter.getRate(), 300.0f, false);
			}
		}
	}

	@Override
	public Boolean select(Vector3f position) {
		ParticleEmitter oldSelected = selected;
		selected = null;
		List<ParticleEmitter> currentEmitters = scene.getParticleSystem().getParticleEmitters();
		ListIterator<ParticleEmitter> pIterator = currentEmitters.listIterator(0);
		while (pIterator.hasNext()) {
			ParticleEmitter emitter = pIterator.next();
			Float dx = position.getX() - emitter.getPosition().x;
			Float dy = position.getY() - emitter.getPosition().y;
			Float dz = position.getZ() - emitter.getPosition().z;
			Float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
			System.out.println("emitter: x:" + emitter.getPosition().x + " y:" + emitter.getPosition().y + " z:" + emitter.getPosition().z);
			System.out.println("distance: " + distance);
			if (distance < 20.0f) {
				selected = emitter;
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.EDIT, emitter));
				break;
			}
		}
		if (selected != null)
			return true;
		if (oldSelected != null)
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.EDIT_DONE));
		return false;
	}
	
	@Override
	public void remove(Vector3f position) {
		scene.getParticleSystem().beginModification();
		List<ParticleEmitter> currentEmitters = scene.getParticleSystem().getParticleEmitters();
		ListIterator<ParticleEmitter> pIterator = currentEmitters.listIterator(0);
		while (pIterator.hasNext()) {
			ParticleEmitter emitter = pIterator.next();
			Float dx = position.getX() - emitter.getPosition().x;
			Float dy = position.getY() - emitter.getPosition().y;
			Float dz = position.getZ() - emitter.getPosition().z;
			Float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
			if (distance < 20.0f) {
				scene.getParticleSystem().removeParticleEmitter(emitter);
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Removed Emitter"));
				break;
			}
		}
		scene.getParticleSystem().endModification();
	}
	
	@Override
	public void move(Vector3f pointerPosition) {
		if (selected == null) return;
		
		Vector3f cameraPosition = scene.getCameraManager().getPosition();
		
		Vector3f cameraToTarget = new Vector3f();
		Vector3f.sub(pointerPosition, cameraPosition, cameraToTarget);
		Float dx = cameraPosition.getX() - selected.getPosition().x;
		Float dy = cameraPosition.getY() - selected.getPosition().y;
		Float dz = cameraPosition.getZ() - selected.getPosition().z;
		Float distanceCameraToObject = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
		Float distanceCameraToTarget = cameraToTarget.length();
		Float scaleFactor = distanceCameraToObject / distanceCameraToTarget;
		cameraToTarget.scale(scaleFactor);
		Vector3f newPosition = new Vector3f();
		Vector3f.add(cameraPosition, cameraToTarget, newPosition);
		selected.setPosition(newPosition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setup() {
		super.setup();
        // AWT Font in eine UnicodeFont von slick-util umwandeln
        font = new UnicodeFont(new Font("Arial", Font.BOLD, 12));
        font.getEffects().add(new ColorEffect(new java.awt.Color(0.8f, 0.2f, 0.8f)));
        font.addAsciiGlyphs();
        try {
           font.loadGlyphs();
        } catch (SlickException e) {
        	logger.error("could not load font glyphs", e);
        }

	}

}
