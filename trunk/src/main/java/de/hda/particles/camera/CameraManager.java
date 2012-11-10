package de.hda.particles.camera;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class CameraManager extends AbstractCamera implements Camera {

	private List<Camera> cameras = new ArrayList<Camera>();
	private Camera selectedCamera;
	private Boolean blockSelection = false;
	
	public void add(Camera camera) {
		cameras.add(camera);
		if(selectedCamera == null) selectedCamera = camera;
	}
	
	public void selectNextCamera() {
		Integer currentIndex = cameras.lastIndexOf(selectedCamera);
		if (currentIndex + 1 >= cameras.size()) {
			selectedCamera = cameras.get(0);
		} else {
			selectedCamera = cameras.get(currentIndex + 1);
		}
	}

	@Override
	public void update() {
		Keyboard.next();
		if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
			if (!blockSelection) {
				selectNextCamera();
				blockSelection = true;
			}
		} else {
			blockSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_0)) {
			selectedCamera.reset();
		}
		
		selectedCamera.update();
	}

	@Override
	public void setup() {
		ListIterator<Camera> iterator = cameras.listIterator(0);
		while(iterator.hasNext()) {
			iterator.next().setup();
		}
	}

	@Override
	public void destroy() {
		ListIterator<Camera> iterator = cameras.listIterator(0);
		while(iterator.hasNext()) {
			iterator.next().destroy();
		}
	}

	public Vector3f getPosition() {
		return selectedCamera.getPosition();
	}
	
	public void setPosition(Vector3f position) {
		selectedCamera.setPosition(position);
	}
	
	public Float getYaw() {
		return selectedCamera.getYaw();
	}
	
	public Float getPitch() {
		return selectedCamera.getPitch();
	}

	public Float getRoll() {
		return selectedCamera.getRoll();
	}
	
	public void setYaw(float yaw) {
		selectedCamera.setYaw(yaw);
	}

	public void setPitch(float pitch) {
		selectedCamera.setPitch(pitch);
	}

	public void setRoll(float roll) {
		selectedCamera.setRoll(roll);
	}
	public String getName() {
		return selectedCamera.getName();
	}

	public void setName(String name) {
		selectedCamera.setName(name);
	}

}
