package de.hda.particles.camera;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.util.vector.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CameraManager extends AbstractCamera implements Camera {

	private final List<Camera> cameras = new ArrayList<Camera>();
	private Camera selectedCamera;
	
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
	
	public void removeSelectedCamera() {
		if (cameras.size() > 1) {
			Integer currentIndex = cameras.lastIndexOf(selectedCamera);
			selectNextCamera();
			cameras.remove(currentIndex);
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
		// camera update
		if (selectedCamera != null)
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
		cameras.clear();
		selectedCamera = null;
	}
	
	public List<Camera> getCameras() {
		return cameras;
	}
	
	public Camera getSelectedCamera() {
		return selectedCamera;
	}
	
	@Override
	public String getName() {
		return selectedCamera.getName();
	}

	@Override
	public void setName(String name) {
		selectedCamera.setName(name);
	}

	@Override
	public Vector3f getPosition() {
		return selectedCamera.getPosition();
	}
	
	@Override
	public void setPosition(Vector3f position) {
		selectedCamera.setPosition(position);
	}
	
	@Override
	public Float getYaw() {
		return selectedCamera.getYaw();
	}
	
	@Override
	public Float getPitch() {
		return selectedCamera.getPitch();
	}

	@Override
	public Float getRoll() {
		return selectedCamera.getRoll();
	}
	
	@Override
	public Float getFov() {
		return selectedCamera.getFov();
	}
	
	@Override
	public Integer getMode() {
		return selectedCamera.getMode();
	}
	
	@Override
	public String getModeName() {
		return selectedCamera.getModeName();
	}
	
	public void setYaw(float yaw) {
		selectedCamera.setYaw(yaw);
	}

	@Override
	public void setPitch(Float pitch) {
		selectedCamera.setPitch(pitch);
	}

	@Override
	public void setRoll(Float roll) {
		selectedCamera.setRoll(roll);
	}
	
	@Override
	public void setFov(Float fov) {
		selectedCamera.setFov(fov);
	}
	
	@Override
	public void setMode(Integer mode) {
		selectedCamera.setMode(mode);
	}

	@Override
	public void reset() {
		selectedCamera.setPosition(new Vector3f(DEFAULT_X, DEFAULT_Y, DEFAULT_Z));
		selectedCamera.setYaw(DEFAULT_YAW);
		selectedCamera.setPitch(DEFAULT_PITCH);
		selectedCamera.setRoll(DEFAULT_ROLL);
		selectedCamera.setFov(DEFAULT_FOV);
		selectedCamera.setMode(DEFAULT_MODE);
	}
	
	@Override
	public void nextMode() {
		selectedCamera.nextMode();
	}

}
