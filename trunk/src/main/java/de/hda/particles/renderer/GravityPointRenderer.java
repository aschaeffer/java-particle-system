package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.hud.HUDCommand;
import de.hda.particles.hud.HUDCommandTypes;
import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.modifier.PositionablePointModifier;
import de.hda.particles.modifier.gravity.GravityPoint;

public class GravityPointRenderer extends AbstractMovable<GravityPoint> implements Renderer {

	private Double positionX;
	private Double positionY;
	private Double positionZ;

	public GravityPointRenderer() {}

	@Override
	public void update() {
		if (!visible) return;
		ListIterator<ParticleModifier> pIterator = scene.getParticleSystem().getParticleModifiers().listIterator(0);
		while (pIterator.hasNext()) {
			ParticleModifier modifier = pIterator.next();
			if (modifier != null) {
				if (modifier.getClass().equals(GravityPoint.class)) {
					ParticleModifierConfiguration configuration = modifier.getConfiguration();
					if (!configuration.containsKey(PositionablePointModifier.POSITION_X) || !configuration.containsKey(PositionablePointModifier.POSITION_Y) || !configuration.containsKey(PositionablePointModifier.POSITION_Z)) continue;
					positionX = (Double) configuration.get(PositionablePointModifier.POSITION_X);
					positionY = (Double) configuration.get(PositionablePointModifier.POSITION_Y);
					positionZ = (Double) configuration.get(PositionablePointModifier.POSITION_Z);
					glPushMatrix();
			        glTranslated(positionX, positionY, positionZ);
			        Sphere s = new Sphere();
			        if (modifier.equals(selected)) {
						glColor4f(1.0f, 0.3f, 0.3f, 0.8f);
				        s.setDrawStyle(GLU.GLU_LINE);
			        } else {
						glColor4f(0.8f, 0.3f, 0.8f, 0.8f);
			        }
			        s.draw(10.0f, 16, 16);
					glPopMatrix();
				}
			}
		}
	}

	@Override
	public Boolean select(Vector3f position) {
		GravityPoint oldSelected = selected;
		selected = null;
		List<ParticleModifier> currentModifiers = scene.getParticleSystem().getParticleModifiers();
		ListIterator<ParticleModifier> pIterator = currentModifiers.listIterator(0);
		while (pIterator.hasNext()) {
			ParticleModifier modifier = pIterator.next();
			if (modifier != null) {
				if (modifier.getClass().equals(GravityPoint.class)) {
					GravityPoint gravityPoint = (GravityPoint) modifier;
					ParticleModifierConfiguration configuration = gravityPoint.getConfiguration();
					positionX = (Double) configuration.get(PositionablePointModifier.POSITION_X);
					positionY = (Double) configuration.get(PositionablePointModifier.POSITION_Y);
					positionZ = (Double) configuration.get(PositionablePointModifier.POSITION_Z);
					Float dx = position.getX() - positionX.floatValue();
					Float dy = position.getY() - positionY.floatValue();
					Float dz = position.getZ() - positionZ.floatValue();
					Float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
					if (distance < 20.0f) {
						selected = gravityPoint;
						scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.EDIT, gravityPoint));
						break;
					}
				}
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
		List<ParticleModifier> currentModifiers = scene.getParticleSystem().getParticleModifiers();
		ListIterator<ParticleModifier> pIterator = currentModifiers.listIterator(0);
		while (pIterator.hasNext()) {
			ParticleModifier modifier = pIterator.next();
			if (modifier.getClass().equals(GravityPoint.class)) {
				GravityPoint gravityPoint = (GravityPoint) modifier;
				ParticleModifierConfiguration configuration = gravityPoint.getConfiguration();
				positionX = (Double) configuration.get(PositionablePointModifier.POSITION_X);
				positionY = (Double) configuration.get(PositionablePointModifier.POSITION_Y);
				positionZ = (Double) configuration.get(PositionablePointModifier.POSITION_Z);
				Float dx = position.getX() - positionX.floatValue();
				Float dy = position.getY() - positionY.floatValue();
				Float dz = position.getZ() - positionZ.floatValue();
				Float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
				if (distance < 20.0f) {
					scene.getParticleSystem().removeParticleModifier(gravityPoint);
					scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Removed Gravity Point"));
					break;
				}
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
		ParticleModifierConfiguration configuration = selected.getConfiguration();
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
