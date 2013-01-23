package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.ListIterator;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.hud.HUDCommand;
import de.hda.particles.hud.HUDCommandTypes;
import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.modifier.PositionablePlaneModifier;
import de.hda.particles.modifier.PositionablePointModifier;
import de.hda.particles.modifier.collision.CollisionPlane;

public class CollisionPlaneRenderer extends AbstractMovable<CollisionPlane> implements Renderer {

	public CollisionPlaneRenderer() {
	}

	@Override
	public void update() {
		if (!visible) return;
		ListIterator<ParticleModifier> iterator = scene.getParticleSystem().getParticleModifiers().listIterator(0);
		while (iterator.hasNext()) {
			ParticleModifier modifier = iterator.next();
			if (modifier != null) {
				if (modifier.getClass().equals(CollisionPlane.class)) {
					ParticleModifierConfiguration configuration = modifier.getConfiguration();
					if (!configuration.containsKey(PositionablePointModifier.POSITION_X) || !configuration.containsKey(PositionablePointModifier.POSITION_Y) || !configuration.containsKey(PositionablePointModifier.POSITION_Z) || !configuration.containsKey(PositionablePlaneModifier.NORMAL_X) || !configuration.containsKey(PositionablePlaneModifier.NORMAL_Y) || !configuration.containsKey(PositionablePlaneModifier.NORMAL_Z))
						continue;
					Double positionX = (Double) configuration.get(PositionablePointModifier.POSITION_X);
					Double positionY = (Double) configuration.get(PositionablePointModifier.POSITION_Y);
					Double positionZ = (Double) configuration.get(PositionablePointModifier.POSITION_Z);
					Double normalX = (Double) configuration.get(PositionablePlaneModifier.NORMAL_X);
					Double normalY = (Double) configuration.get(PositionablePlaneModifier.NORMAL_Y);
					Double normalZ = (Double) configuration.get(PositionablePlaneModifier.NORMAL_Z);
					glPushMatrix();
					glTranslated(positionX, positionY, positionZ);
					Sphere s = new Sphere();
					if (selected.contains(modifier)) {
						glColor4f(1.0f, 0.3f, 0.3f, 0.8f);
						s.setDrawStyle(GLU.GLU_LINE);
					} else {
						glColor4f(0.3f, 0.8f, 0.8f, 0.8f);
					}
					s.draw(10.0f, 16, 16);
					if (selected.contains(modifier)) {
						Vector3f position = new Vector3f(positionX.floatValue(), positionY.floatValue(), positionZ.floatValue());
						Vector3f normal = new Vector3f(normalX.floatValue(), normalY.floatValue(), normalZ.floatValue());
						Vector3f normalizedNormal = new Vector3f();
						normal.normalise(normalizedNormal);
						Vector3f tx = new Vector3f();
						Vector3f ty = new Vector3f();
						Vector3f.cross(normal, position, tx);
						Vector3f.cross(normal, tx, ty);

						// render plane
						glBegin(GL_QUADS);
						glColor4f(0.3f, 0.8f, 0.8f, 0.1f);
						glVertex3f((tx.x + ty.x), (tx.y + ty.y), (tx.z + ty.z));
						glVertex3f((tx.x - ty.x), (tx.y - ty.y), (tx.z - ty.z));
						glVertex3f((-tx.x - ty.x), (-tx.y - ty.y), (-tx.z - ty.z));
						glVertex3f((-tx.x + ty.x), (-tx.y + ty.y), (-tx.z + ty.z));
						glEnd();

						// render mini sphere to indicate the normal direction
						glTranslated(normalizedNormal.x*30, normalizedNormal.y*30, normalizedNormal.z*30);
						Sphere s2 = new Sphere();
						glColor4f(1.0f, 0.8f, 0.8f, 0.8f);
						s2.setDrawStyle(GLU.GLU_FILL);
						s.draw(2.0f, 16, 16);
					}
					glPopMatrix();
				}
			}
		}
	}

	@Override
	public Boolean select(Vector3f position) {
		Boolean selectionWasEmpty = selected.isEmpty();
		selected.clear();
		ListIterator<ParticleModifier> iterator = scene.getParticleSystem().getParticleModifiers().listIterator(0);
		while (iterator.hasNext()) {
			ParticleModifier modifier = iterator.next();
			if (modifier != null) {
				if (modifier.getClass().equals(CollisionPlane.class)) {
					CollisionPlane collisionPlane = (CollisionPlane) modifier;
					ParticleModifierConfiguration configuration = collisionPlane.getConfiguration();
					Double positionX = (Double) configuration.get(PositionablePointModifier.POSITION_X);
					Double positionY = (Double) configuration.get(PositionablePointModifier.POSITION_Y);
					Double positionZ = (Double) configuration.get(PositionablePointModifier.POSITION_Z);
					Float dx = position.getX() - positionX.floatValue();
					Float dy = position.getY() - positionY.floatValue();
					Float dz = position.getZ() - positionZ.floatValue();
					Float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
					if (distance < 20.0f) {
						selected.add(collisionPlane);
						scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.EDIT, collisionPlane));
						break;
					}
				}
			}
		}
		if (!selected.isEmpty())
			return true;
		if (!selectionWasEmpty)
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.EDIT_DONE));
		return false;
	}

	@Override
	public void remove(Vector3f position) {
		scene.getParticleSystem().beginModification();
		ListIterator<ParticleModifier> iterator = scene.getParticleSystem().getParticleModifiers().listIterator(0);
		while (iterator.hasNext()) {
			ParticleModifier modifier = iterator.next();
			if (modifier.getClass().equals(CollisionPlane.class)) {
				CollisionPlane collisionPlane = (CollisionPlane) modifier;
				ParticleModifierConfiguration configuration = collisionPlane.getConfiguration();
				Double positionX = (Double) configuration.get(PositionablePointModifier.POSITION_X);
				Double positionY = (Double) configuration.get(PositionablePointModifier.POSITION_Y);
				Double positionZ = (Double) configuration.get(PositionablePointModifier.POSITION_Z);
				Float dx = position.getX() - positionX.floatValue();
				Float dy = position.getY() - positionY.floatValue();
				Float dz = position.getZ() - positionZ.floatValue();
				Float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
				if (distance < 20.0f) {
					scene.getParticleSystem().removeParticleModifier(collisionPlane);
					scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Removed Collision Plane"));
					break;
				}
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
		ListIterator<CollisionPlane> iterator = selected.listIterator(0);
		while (iterator.hasNext()) {
			CollisionPlane collisionPlane = iterator.next();
			ParticleModifierConfiguration configuration = collisionPlane.getConfiguration();
			Double positionX = (Double) configuration.get(PositionablePointModifier.POSITION_X);
			Double positionY = (Double) configuration.get(PositionablePointModifier.POSITION_Y);
			Double positionZ = (Double) configuration.get(PositionablePointModifier.POSITION_Z);
			Float dx = cameraPosition.getX() - positionX.floatValue();
			Float dy = cameraPosition.getY() - positionY.floatValue();
			Float dz = cameraPosition.getZ() - positionZ.floatValue();
			Float distanceCameraToObject = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
			Float distanceCameraToTarget = cameraToTarget.length();
			Float scaleFactor = distanceCameraToObject / distanceCameraToTarget;
			cameraToTarget.scale(scaleFactor);
			Vector3f newPosition = new Vector3f();
			Vector3f.add(cameraPosition, cameraToTarget, newPosition);
			configuration.put(PositionablePointModifier.POSITION_X, new Double(newPosition.x));
			configuration.put(PositionablePointModifier.POSITION_Y, new Double(newPosition.y));
			configuration.put(PositionablePointModifier.POSITION_Z, new Double(newPosition.z));
			Vector3f normalizedNormal = new Vector3f();
			cameraToTarget.normalise(normalizedNormal);
			configuration.put(PositionablePlaneModifier.NORMAL_X, new Double(normalizedNormal.x));
			configuration.put(PositionablePlaneModifier.NORMAL_Y, new Double(normalizedNormal.y));
			configuration.put(PositionablePlaneModifier.NORMAL_Z, new Double(normalizedNormal.z));
		}
	}
}
