package de.hda.particles.renderer.impl;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.renderer.Renderer;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.configuration.impl.ParticleModifierConfiguration;
import de.hda.particles.hud.impl.HUDCommand;
import de.hda.particles.hud.impl.HUDCommandTypes;
import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.modifier.PositionablePointModifier;
import de.hda.particles.modifier.impl.gravity.GravityPulsar;

public class GravityPulsarRenderer extends AbstractSelectable<GravityPulsar> implements Renderer {

	private Double positionX;
	private Double positionY;
	private Double positionZ;
	private Double pulse = 0.0;

	public GravityPulsarRenderer() {}

	@Override
	public void update() {
		if (!visible) return;
		ListIterator<ParticleModifier> pIterator = scene.getParticleSystem().getParticleModifiers().listIterator(0);
		while (pIterator.hasNext()) {
			ParticleModifier modifier = pIterator.next();
			if (modifier != null) {
				if (modifier.getClass().equals(GravityPulsar.class)) {
					ParticleModifierConfiguration configuration = modifier.getConfiguration();
					if (!configuration.containsKey(PositionablePointModifier.POSITION_X) || !configuration.containsKey(PositionablePointModifier.POSITION_Y) || !configuration.containsKey(PositionablePointModifier.POSITION_Z)) continue;
					positionX = (Double) configuration.get(PositionablePointModifier.POSITION_X);
					positionY = (Double) configuration.get(PositionablePointModifier.POSITION_Y);
					positionZ = (Double) configuration.get(PositionablePointModifier.POSITION_Z);
					glPushMatrix();
			        glTranslated(positionX, positionY, positionZ);
			        Sphere s = new Sphere();
					glColor4f(0.5f, 0.3f, 0.8f, 0.8f);
			        if (selected.contains(modifier)) {
						glLineWidth(1.0f);
				        s.setDrawStyle(GLU.GLU_LINE);
				        pulse = 8.5 + Math.sin(scene.getParticleSystem().getPastIterations() * 0.03) * 3.0;
				        s.draw(pulse.floatValue(), 16, 16);
			        } else {
				        s.draw(10.0f, 16, 16);
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
		List<ParticleModifier> currentModifiers = scene.getParticleSystem().getParticleModifiers();
		ListIterator<ParticleModifier> pIterator = currentModifiers.listIterator(0);
		while (pIterator.hasNext()) {
			ParticleModifier modifier = pIterator.next();
			if (modifier != null) {
				if (modifier.getClass().equals(GravityPulsar.class)) {
					GravityPulsar gravityPulsar = (GravityPulsar) modifier;
					ParticleModifierConfiguration configuration = gravityPulsar.getConfiguration();
					positionX = (Double) configuration.get(PositionablePointModifier.POSITION_X);
					positionY = (Double) configuration.get(PositionablePointModifier.POSITION_Y);
					positionZ = (Double) configuration.get(PositionablePointModifier.POSITION_Z);
					Float dx = position.getX() - positionX.floatValue();
					Float dy = position.getY() - positionY.floatValue();
					Float dz = position.getZ() - positionZ.floatValue();
					Float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
					if (distance < 20.0f) {
						selected.add(gravityPulsar);
						scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.EDIT, gravityPulsar));
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
		List<ParticleModifier> currentModifiers = scene.getParticleSystem().getParticleModifiers();
		ListIterator<ParticleModifier> pIterator = currentModifiers.listIterator(0);
		while (pIterator.hasNext()) {
			ParticleModifier modifier = pIterator.next();
			if (modifier.getClass().equals(GravityPulsar.class)) {
				GravityPulsar gravityPulsar = (GravityPulsar) modifier;
				ParticleModifierConfiguration configuration = gravityPulsar.getConfiguration();
				positionX = (Double) configuration.get(PositionablePointModifier.POSITION_X);
				positionY = (Double) configuration.get(PositionablePointModifier.POSITION_Y);
				positionZ = (Double) configuration.get(PositionablePointModifier.POSITION_Z);
				Float dx = position.getX() - positionX.floatValue();
				Float dy = position.getY() - positionY.floatValue();
				Float dz = position.getZ() - positionZ.floatValue();
				Float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
				if (distance < 20.0f) {
					scene.getParticleSystem().removeParticleModifier(gravityPulsar);
					scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Removed Gravity Pulsar"));
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
		ListIterator<GravityPulsar> iterator = selected.listIterator(0);
		while (iterator.hasNext()) {
			GravityPulsar gravityPulsar = iterator.next();
			ParticleModifierConfiguration configuration = gravityPulsar.getConfiguration();
			positionX = (Double) configuration.get(PositionablePointModifier.POSITION_X);
			positionY = (Double) configuration.get(PositionablePointModifier.POSITION_Y);
			positionZ = (Double) configuration.get(PositionablePointModifier.POSITION_Z);
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
		}
	}

}
