package de.hda.particles.dao;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.ParticleSystem;
import de.hda.particles.camera.Camera;
import de.hda.particles.domain.SceneConfiguration;
import de.hda.particles.hud.HUD;
import de.hda.particles.renderer.Renderer;
import de.hda.particles.renderer.faces.FaceRenderer;
import de.hda.particles.renderer.particles.ParticleRenderer;
import de.hda.particles.scene.ConfigurableScene;
import de.hda.particles.scene.Scene;
import de.hda.particles.overlay.TextOverlay;

public class SceneDAO {

	@SuppressWarnings("unchecked")
	public Scene create(String filename, ParticleSystem particleSystem) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		// read json
		InputStream in = this.getClass().getResourceAsStream(filename);
		ObjectMapper mapper = new ObjectMapper();
		SceneConfiguration sceneConfiguration = mapper.readValue(in, SceneConfiguration.class);
		// init configurable scene
		Scene scene = new ConfigurableScene(particleSystem, this);
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
		scene.getParticleRendererManager().setScene(scene);
		scene.getFaceRendererManager().setScene(scene);
		scene.getRendererManager().setScene(scene);
		scene.getTextOverlayManager().setScene(scene);
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
		ListIterator<String> particleRendererIterator = sceneConfiguration.particleRenderer.listIterator(0);
		while (particleRendererIterator.hasNext()) {
			scene.getParticleRendererManager().add((Class<? extends ParticleRenderer>) Class.forName(particleRendererIterator.next()));
		}
		// load face renderer
		ListIterator<String> faceRendererIterator = sceneConfiguration.faceRenderers.listIterator(0);
		while (faceRendererIterator.hasNext()) {
			scene.getFaceRendererManager().add((Class<? extends FaceRenderer>) Class.forName(faceRendererIterator.next()));
		}
		// load obligatory renderers I
		scene.getRendererManager().add(scene.getCameraManager());
		// load dynamic renderers
		ListIterator<String> rendererIterator = sceneConfiguration.renderer.listIterator(0);
		while (rendererIterator.hasNext()) {
			scene.getRendererManager().add((Class<? extends Renderer>) Class.forName(rendererIterator.next()));
		}
		// load text overlay types
		ListIterator<String> textOverlaysIterator = sceneConfiguration.textOverlays.listIterator(0);
		while (textOverlaysIterator.hasNext()) {
			scene.getTextOverlayManager().add((Class<? extends TextOverlay>) Class.forName(textOverlaysIterator.next()));
		}
		// load obligatory renderers II, ordering is important!
		scene.getRendererManager().add(scene.getTextOverlayManager());
		scene.getRendererManager().add(scene.getParticleRendererManager());
		scene.getRendererManager().add(scene.getFaceRendererManager());
		scene.getRendererManager().add(scene.getHudManager());
		return scene;
	}

	@SuppressWarnings("unchecked")
	public void load(Scene scene, File file) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		// read json
		InputStream in = new FileInputStream(file);
		ObjectMapper mapper = new ObjectMapper();
		SceneConfiguration sceneConfiguration = mapper.readValue(in, SceneConfiguration.class);
		// init configurable scene
		scene.setName(sceneConfiguration.name);
		scene.setWidth(sceneConfiguration.width);
		scene.setHeight(sceneConfiguration.height);
		scene.setFullscreen(sceneConfiguration.fullscreen);
		// reset managers
		scene.getRendererManager().destroy(); // calls destroy methods recursive
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
		ListIterator<String> particleRendererIterator = sceneConfiguration.particleRenderer.listIterator(0);
		while (particleRendererIterator.hasNext()) {
			scene.getParticleRendererManager().add((Class<? extends ParticleRenderer>) Class.forName(particleRendererIterator.next()));
		}
		// load face renderer
		ListIterator<String> faceRendererIterator = sceneConfiguration.faceRenderers.listIterator(0);
		while (faceRendererIterator.hasNext()) {
			scene.getFaceRendererManager().add((Class<? extends FaceRenderer>) Class.forName(faceRendererIterator.next()));
		}
		// load obligatory renderers I
		scene.getRendererManager().add(scene.getCameraManager());
		// load dynamic renderers
		ListIterator<String> rendererIterator = sceneConfiguration.renderer.listIterator(0);
		while (rendererIterator.hasNext()) {
			scene.getRendererManager().add((Class<? extends Renderer>) Class.forName(rendererIterator.next()));
		}
		// load text overlay types
		ListIterator<String> textOverlaysIterator = sceneConfiguration.textOverlays.listIterator(0);
		while (textOverlaysIterator.hasNext()) {
			scene.getTextOverlayManager().add((Class<? extends TextOverlay>) Class.forName(textOverlaysIterator.next()));
		}
		// load obligatory renderers II, ordering is important!
		scene.getRendererManager().add(scene.getTextOverlayManager());
		scene.getRendererManager().add(scene.getParticleRendererManager());
		scene.getRendererManager().add(scene.getFaceRendererManager());
		scene.getRendererManager().add(scene.getHudManager());
		// setup all renderers, the cam and the hud
		scene.getRendererManager().setup();
	}
	
	public void save(Scene scene, File file) throws JsonGenerationException, JsonMappingException, IOException {
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
		sceneConfiguration.particleRenderer = new ArrayList<String>();
		ListIterator<ParticleRenderer> particleRendererIterator = scene.getParticleRendererManager().getParticleRenderers().listIterator(0);
		while (particleRendererIterator.hasNext()) {
			ParticleRenderer particleRenderer = particleRendererIterator.next();
			sceneConfiguration.particleRenderer.add(particleRenderer.getClass().getName());
		}
		sceneConfiguration.faceRenderers = new ArrayList<String>();
		ListIterator<FaceRenderer> faceRendererIterator = scene.getFaceRendererManager().getFaceRenderers().listIterator(0);
		while (faceRendererIterator.hasNext()) {
			FaceRenderer faceRenderer = faceRendererIterator.next();
			sceneConfiguration.faceRenderers.add(faceRenderer.getClass().getName());
		}
		sceneConfiguration.renderer = new ArrayList<String>();
		ListIterator<Renderer> rendererIterator = scene.getRendererManager().getRenderer().listIterator(0);
		while (rendererIterator.hasNext()) {
			Renderer renderer = rendererIterator.next();
			sceneConfiguration.renderer.add(renderer.getClass().getName());
		}
		sceneConfiguration.textOverlays = new ArrayList<String>();
		ListIterator<TextOverlay> textOverlayIterator = scene.getTextOverlayManager().getTextOverlays().listIterator(0);
		while (textOverlayIterator.hasNext()) {
			TextOverlay textOverlay = textOverlayIterator.next();
			sceneConfiguration.textOverlays.add(textOverlay.getClass().getName());
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(file, sceneConfiguration);
	}

}
