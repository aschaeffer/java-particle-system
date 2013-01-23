package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.hud.HUDCommand;
import de.hda.particles.hud.HUDCommandTypes;

public class EmitterRenderer extends AbstractMovable<ParticleEmitter> implements Renderer {

	public EmitterRenderer() {}

	@Override
	public void update() {
		if (!visible) return;
		ListIterator<ParticleEmitter> pIterator = scene.getParticleSystem().getParticleEmitters().listIterator(0);
		while (pIterator.hasNext()) {
			ParticleEmitter emitter = pIterator.next();
			if (emitter != null) {
				glPushMatrix();
		        glTranslatef(emitter.getPosition().x, emitter.getPosition().y, emitter.getPosition().z);
				glColor4f(0.3f, 0.3f, 1.0f, 0.5f);
		        Sphere s = new Sphere();
				if (selected.contains(emitter)) {
					glLineWidth(1.0f);
					s.setDrawStyle(GLU.GLU_LINE);
				}
		        s.draw(10.0f, 16, 16);
		        glPopMatrix();
			}
		}
	}

	@Override
	public Boolean select(Vector3f position) {
		Boolean selectionWasEmpty = selected.isEmpty();
		selected.clear();
		ListIterator<ParticleEmitter> pIterator = scene.getParticleSystem().getParticleEmitters().listIterator(0);
		while (pIterator.hasNext()) {
			ParticleEmitter emitter = pIterator.next();
			Float dx = position.getX() - emitter.getPosition().x;
			Float dy = position.getY() - emitter.getPosition().y;
			Float dz = position.getZ() - emitter.getPosition().z;
			Float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
			if (distance < 20.0f) {
				selected.add(emitter);
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.EDIT, emitter));
				break;
			}
		}
		if (!selected.isEmpty())
			return true;
		if (!selectionWasEmpty)
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.EDIT_DONE));
		return false;
	}
	
	@Override
	public List<? extends Object> select(Vector4f selectionBox) {
		selected.clear();
		ListIterator<ParticleEmitter> pIterator = scene.getParticleSystem().getParticleEmitters().listIterator(0);
		while (pIterator.hasNext()) {
			ParticleEmitter emitter = pIterator.next();
			try {
				Vector2f screenCoordinates = scene.getRendererManager().getScreenCoordinates(emitter.getPosition());
				if (screenCoordinates.x > selectionBox.x && screenCoordinates.x < selectionBox.z &&
					screenCoordinates.y > selectionBox.y && screenCoordinates.y < selectionBox.w
				) {
					selected.add(emitter);
				}
			} catch(Exception e) {}
		}
		return selected;
	}
	
	@Override
	public void remove(Vector3f position) {
		scene.getParticleSystem().beginModification();
		ListIterator<ParticleEmitter> pIterator = scene.getParticleSystem().getParticleEmitters().listIterator(0);
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
		if (selected.size() == 0) return;
		Vector3f cameraPosition = scene.getCameraManager().getPosition();
		Vector3f cameraToTarget = new Vector3f();
		Vector3f.sub(pointerPosition, cameraPosition, cameraToTarget);
		ListIterator<ParticleEmitter> iterator = selected.listIterator(0);
		while (iterator.hasNext()) {
			ParticleEmitter emitter = iterator.next();
			Float dx = cameraPosition.getX() - emitter.getPosition().x;
			Float dy = cameraPosition.getY() - emitter.getPosition().y;
			Float dz = cameraPosition.getZ() - emitter.getPosition().z;
			Float distanceCameraToObject = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
			Float distanceCameraToTarget = cameraToTarget.length();
			Float scaleFactor = distanceCameraToObject / distanceCameraToTarget;
			cameraToTarget.scale(scaleFactor);
			Vector3f newPosition = new Vector3f();
			Vector3f.add(cameraPosition, cameraToTarget, newPosition);
			emitter.setPosition(newPosition);
		}
	}

}
