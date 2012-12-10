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
import de.hda.particles.modifier.gravity.GravityPoint;

public class GravityPointRenderer extends AbstractMovable<GravityPoint> implements Renderer {

	public GravityPointRenderer() {}

	@Override
	public void update() {
		if (!visible) return;

		List<ParticleModifier> currentModifiers = scene.getParticleSystem().getParticleModifiers();
		ListIterator<ParticleModifier> pIterator = currentModifiers.listIterator(0);
		while (pIterator.hasNext()) {
			ParticleModifier modifier = pIterator.next();
			if (modifier != null) {
				if (modifier.getClass().equals(GravityPoint.class)) {
					ParticleModifierConfiguration configuration = modifier.getConfiguration();
					if (!configuration.containsKey(GravityPoint.POSITION_X) || !configuration.containsKey(GravityPoint.POSITION_Y) || !configuration.containsKey(GravityPoint.POSITION_Z)) continue;
					Double gravityPointX = (Double) configuration.get(GravityPoint.POSITION_X);
					Double gravityPointY = (Double) configuration.get(GravityPoint.POSITION_Y);
					Double gravityPointZ = (Double) configuration.get(GravityPoint.POSITION_Z);
					glPushMatrix();
			        glTranslated(gravityPointX, gravityPointY, gravityPointZ);
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
					Double gravityPointX = (Double) configuration.get(GravityPoint.POSITION_X);
					Double gravityPointY = (Double) configuration.get(GravityPoint.POSITION_Y);
					Double gravityPointZ = (Double) configuration.get(GravityPoint.POSITION_Z);
					Float dx = position.getX() - gravityPointX.floatValue();
					Float dy = position.getY() - gravityPointY.floatValue();
					Float dz = position.getZ() - gravityPointZ.floatValue();
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
				Double gravityPointX = (Double) configuration.get(GravityPoint.POSITION_X);
				Double gravityPointY = (Double) configuration.get(GravityPoint.POSITION_Y);
				Double gravityPointZ = (Double) configuration.get(GravityPoint.POSITION_Z);
				Float dx = position.getX() - gravityPointX.floatValue();
				Float dy = position.getY() - gravityPointY.floatValue();
				Float dz = position.getZ() - gravityPointZ.floatValue();
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
		Double gravityPointX = (Double) configuration.get(GravityPoint.POSITION_X);
		Double gravityPointY = (Double) configuration.get(GravityPoint.POSITION_Y);
		Double gravityPointZ = (Double) configuration.get(GravityPoint.POSITION_Z);
		Float dx = cameraPosition.getX() - gravityPointX.floatValue();
		Float dy = cameraPosition.getY() - gravityPointY.floatValue();
		Float dz = cameraPosition.getZ() - gravityPointZ.floatValue();
		Float distanceCameraToObject = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
		Float distanceCameraToTarget = cameraToTarget.length();
		Float scaleFactor = distanceCameraToObject / distanceCameraToTarget;
		cameraToTarget.scale(scaleFactor);
		Vector3f newPosition = new Vector3f();
		Vector3f.add(cameraPosition, cameraToTarget, newPosition);
		configuration.put(GravityPoint.POSITION_X, new Double(newPosition.x));
		configuration.put(GravityPoint.POSITION_Y, new Double(newPosition.y));
		configuration.put(GravityPoint.POSITION_Z, new Double(newPosition.z));
	}
}
