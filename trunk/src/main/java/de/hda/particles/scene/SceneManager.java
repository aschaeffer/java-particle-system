package de.hda.particles.scene;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.HashMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.ParticleSystem;
import de.hda.particles.camera.Camera;
import de.hda.particles.domain.SceneConfiguration;
import de.hda.particles.hud.HUD;
import de.hda.particles.renderer.Renderer;
import de.hda.particles.renderer.types.RenderType;

public class SceneManager {

	private ObjectMapper mapper = new ObjectMapper();

	@SuppressWarnings("unchecked")
	public Scene load(String filename, ParticleSystem particleSystem) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		// read json
		InputStream in = this.getClass().getResourceAsStream(filename);
		SceneConfiguration sceneConfiguration = mapper.readValue(in, SceneConfiguration.class);
		// init configurable scene
		Scene scene = new ConfigurableScene(particleSystem);
		scene.setName(sceneConfiguration.name);
		scene.setWidth(sceneConfiguration.width);
		scene.setHeight(sceneConfiguration.height);
		scene.setFullscreen(sceneConfiguration.fullscreen);
        // init fps information instances
		scene.addFpsInformationInstance(scene);
		scene.addFpsInformationInstance(particleSystem);
		// inject scene into managers
		scene.getCameraManager().setScene(scene);
		scene.getHudManager().setScene(scene);
		scene.getRenderTypeManager().setScene(scene);
		scene.getRendererManager().setScene(scene);
		// load cameras
		ListIterator<HashMap<String, Object>> camerasIterator = sceneConfiguration.cameras.listIterator(0);
		while (camerasIterator.hasNext()) {
			Map<String, Object> cameraConfiguration = camerasIterator.next();
			scene.getCameraManager().add(
				(Class<? extends Camera>) Class.forName((String) cameraConfiguration.get("className")),
				(String) cameraConfiguration.get("name"),
				new Vector3f(
					((Double) cameraConfiguration.get("x")).floatValue(),
					((Double) cameraConfiguration.get("y")).floatValue(),
					((Double) cameraConfiguration.get("z")).floatValue()
				),
				((Double) cameraConfiguration.get("yaw")).floatValue(),
				((Double) cameraConfiguration.get("pitch")).floatValue(),
				((Double) cameraConfiguration.get("roll")).floatValue(),
				((Double) cameraConfiguration.get("fov")).floatValue()
			);
		}
		
		// load huds
		ListIterator<String> hudsIterator = sceneConfiguration.huds.listIterator(0);
		while (hudsIterator.hasNext()) {
			scene.getHudManager().add((Class<? extends HUD>) Class.forName(hudsIterator.next()));
		}
		// load render types
		ListIterator<String> renderTypesIterator = sceneConfiguration.renderTypes.listIterator(0);
		while (renderTypesIterator.hasNext()) {
			scene.getRenderTypeManager().add((Class<? extends RenderType>) Class.forName(renderTypesIterator.next()));
		}
		// load obligatory renderers I
		scene.getRendererManager().add(scene.getCameraManager());
		// load dynamic renderers
		ListIterator<String> rendererIterator = sceneConfiguration.renderer.listIterator(0);
		while (rendererIterator.hasNext()) {
			scene.getRendererManager().add((Class<? extends Renderer>) Class.forName(rendererIterator.next()));
		}
		// load obligatory renderers II
		scene.getRendererManager().add(scene.getRenderTypeManager()); 
		scene.getRendererManager().add(scene.getHudManager());
		// setup scene
		// scene.setup();
		return scene;
	}
	
	public void save(Scene scene, String filename) throws JsonGenerationException, JsonMappingException, IOException {
		SceneConfiguration sceneConfiguration = new SceneConfiguration();
		sceneConfiguration.name = scene.getName();
		sceneConfiguration.width = scene.getWidth();
		sceneConfiguration.height = scene.getHeight();
		sceneConfiguration.fullscreen = scene.getFullscreen();
		sceneConfiguration.cameras = new ArrayList<HashMap<String,Object>>();
		ListIterator<Camera> camerasIterator = scene.getCameraManager().getCameras().listIterator(0);
		while (camerasIterator.hasNext()) {
			Camera camera = camerasIterator.next();
			HashMap<String, Object> cameraProperties = new HashMap<String, Object>();
			cameraProperties.put("className", camera.getClass().getName());
			cameraProperties.put("name", camera.getName());
			cameraProperties.put("x", camera.getPosition().x);
			cameraProperties.put("y", camera.getPosition().y);
			cameraProperties.put("z", camera.getPosition().z);
			cameraProperties.put("yaw", camera.getYaw());
			cameraProperties.put("pitch", camera.getPitch());
			cameraProperties.put("roll", camera.getRoll());
			cameraProperties.put("fov", camera.getFov());
			sceneConfiguration.cameras.add(cameraProperties);
		}
		sceneConfiguration.huds = new ArrayList<String>();
		ListIterator<HUD> hudsIterator = scene.getHudManager().getHUDs().listIterator(0);
		while (hudsIterator.hasNext()) {
			HUD hud = hudsIterator.next();
			sceneConfiguration.huds.add(hud.getClass().getName());
		}
		sceneConfiguration.renderTypes = new ArrayList<String>();
		ListIterator<RenderType> renderTypesIterator = scene.getRenderTypeManager().getRenderTypes().listIterator(0);
		while (renderTypesIterator.hasNext()) {
			RenderType renderType = renderTypesIterator.next();
			sceneConfiguration.renderTypes.add(renderType.getClass().getName());
		}
		sceneConfiguration.renderer = new ArrayList<String>();
		ListIterator<Renderer> rendererIterator = scene.getRendererManager().getRenderer().listIterator(0);
		while (rendererIterator.hasNext()) {
			Renderer renderer = rendererIterator.next();
			sceneConfiguration.renderer.add(renderer.getClass().getName());
		}
		mapper.writeValue(new File(filename), sceneConfiguration);
	}
}
