package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.ListIterator;

import org.lwjgl.util.glu.Cylinder;

import de.hda.particles.camera.Camera;

public class CameraRenderer extends AbstractSelectable<Camera> implements Renderer {

	public CameraRenderer() {}

	@Override
	public void update() {
		if (!visible) return;
		ListIterator<Camera> pIterator = scene.getCameraManager().getCameras().listIterator(0);
		while (pIterator.hasNext()) {
			Camera camera = pIterator.next();
			if (camera != null && !camera.equals(scene.getCameraManager().getSelectedCamera())) {
				glPushMatrix();
				glColor4f(0.0f, 0.9f, 0.2f, 0.2f);
		        glTranslatef(camera.getPosition().getX(), camera.getPosition().getY(), camera.getPosition().getZ());
				glRotatef(camera.getPitch(), 1.0f, 0.0f, 0.0f);
				glRotatef(camera.getYaw(), 0.0f, 1.0f, 0.0f);
				glRotatef(camera.getRoll(), 0.0f, 0.0f, 1.0f);
		        Cylinder c = new Cylinder();
		        c.draw(20.0f, 10.0f, 40.0f, 16, 16);
		        glPopMatrix();
			}
		}
	}

}
