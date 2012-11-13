package de.hda.particles.camera;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CameraManager extends AbstractCamera implements Camera {

	private List<Camera> cameras = new ArrayList<Camera>();
	private Camera selectedCamera;
	private Boolean blockSelection = false;
	
	private final Logger logger = LoggerFactory.getLogger(CameraManager.class);
	
	public CameraManager() {}

	public void add(Camera camera) {
		cameras.add(camera);
		if(selectedCamera == null) selectedCamera = camera;
	}
	
	public void add(Class<? extends Camera> cameraClass, String name, Vector3f position, Float yaw, Float pitch, Float roll, Float fov) {
		try {
			Camera camera = cameraClass.newInstance();
			camera.setScene(scene);
			camera.setName(name);
			camera.setPosition(position);
			camera.setYaw(yaw);
			camera.setPitch(pitch);
			camera.setRoll(roll);
			camera.setFov(fov);
			cameras.add(camera);
			if(selectedCamera == null) selectedCamera = camera;
		} catch (InstantiationException e) {
			logger.error("could not create camera: InstantiationException: " + e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error("could not create camera: IllegalAccessException: " + e.getMessage(), e);
		}
		
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
		// camera selection
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
		// camera update
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
	
	public List<Camera> getCameras() {
		return cameras;
	}
	
	public Camera getSelectedCamera() {
		return selectedCamera;
	}
	
	public String getName() {
		return selectedCamera.getName();
	}

	public void setName(String name) {
		selectedCamera.setName(name);
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
	
	public Float getFov() {
		return selectedCamera.getFov();
	}
	
	public void setYaw(float yaw) {
		selectedCamera.setYaw(yaw);
	}

	public void setPitch(Float pitch) {
		selectedCamera.setPitch(pitch);
	}

	public void setRoll(Float roll) {
		selectedCamera.setRoll(roll);
	}
	
	public void setFov(Float fov) {
		selectedCamera.setFov(fov);
	}
}
