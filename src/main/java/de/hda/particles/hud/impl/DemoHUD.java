package de.hda.particles.hud.impl;

import de.hda.particles.hud.HUD;
import de.hda.particles.scene.command.impl.AddCamera;
import de.hda.particles.scene.command.impl.AddEditor;
import de.hda.particles.scene.command.impl.AddEmitter;
import de.hda.particles.scene.command.impl.AddFaceRenderer;
import de.hda.particles.scene.command.impl.AddFeature;
import de.hda.particles.scene.command.impl.AddHUD;
import de.hda.particles.scene.command.impl.AddModifier;
import de.hda.particles.scene.command.impl.AddParticleRenderer;
import de.hda.particles.scene.command.impl.AddRenderer;
import de.hda.particles.scene.command.impl.ChangeDisplaySize;
import de.hda.particles.scene.command.impl.HideRenderer;
import de.hda.particles.scene.command.impl.ShowRenderer;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.lwjgl.input.Keyboard;

import de.hda.particles.camera.Camera;
import de.hda.particles.dao.DemoDAO;
import de.hda.particles.domain.ChangeSet;
import de.hda.particles.domain.impl.configuration.CommandConfiguration;
import de.hda.particles.domain.Demo;
import de.hda.particles.editor.Editor;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.features.ParticleFeature;
import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.renderer.Renderer;
import de.hda.particles.renderer.FaceRenderer;
import de.hda.particles.renderer.ParticleRenderer;
import de.hda.particles.scene.Scene;

public class DemoHUD extends AbstractHUD implements HUD {

	private final DemoDAO demoDAO = new DemoDAO();

	// recording change sets
	private Boolean recording = false;
	private Integer offsetIterations = 0;
	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("y-MM-dd_HH-mm-ss");
	private Demo demo;

	private Boolean blockRecordingSelection = false;

	public DemoHUD() {}

	public DemoHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void update() {
	}
	
	@Override
	public void input() {
		if (Keyboard.isKeyDown(Keyboard.KEY_F1)) {
			if (!blockRecordingSelection) {
				if (recording) {
					stopRecording();
				} else {
					startRecording();
				}
				blockRecordingSelection = true;
			}
		} else {
			blockRecordingSelection = false;
		}
	}

	@Override
	public void setup() {
		super.setup();
	}

	public void startRecording() {
		recording = true;
		offsetIterations = scene.getParticleSystem().getPastIterations();
		demo = new Demo();
		addNotice("Starting recording change sets.");
	}
	
	public void stopRecording() {
		recording = false;
		String filename = scene.getName() + "_" + simpleDateFormat.format(new Date()) + ".demo";
		try {
			demo.setName(filename);
			demoDAO.save(demo, new File(filename));
			addNotice("Saved demo to: " + filename);
		} catch (Exception e) {
			addMessage("Error saving demo: " + filename);
		}
	}

	/**
	 * Creating change sets from listening hud commands.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void executeCommand(HUDCommand command) {
		if (!recording) return;
		ChangeSet changeSet = new ChangeSet();
		changeSet.setIteration(scene.getParticleSystem().getPastIterations() - offsetIterations);
		changeSet.setTransitionIterations(0);
		CommandConfiguration configuration = new CommandConfiguration();
		switch(command.getType()) {
			case ADD_CAMERA:
				changeSet.setType(AddCamera.class.getName());
				configuration.put("class", ((Class<? extends Camera>) command.getPayLoad()).getName());
				configuration.put("name", "new cam");
				configuration.put("x", scene.getCameraManager().getX());
				configuration.put("y", scene.getCameraManager().getY());
				configuration.put("z", scene.getCameraManager().getZ());
//				configuration.put("yaw", scene.getCameraManager().getYaw());
//				configuration.put("pitch", scene.getCameraManager().getPitch());
//				configuration.put("roll", scene.getCameraManager().getRoll());
//				configuration.put("fov", scene.getCameraManager().getFov());
				break;
			case ADD_PARTICLE_RENDERER:
				changeSet.setType(AddParticleRenderer.class.getName());
				configuration.put("class", ((Class<? extends ParticleRenderer>) command.getPayLoad()).getName());
				break;
			case ADD_FACE_RENDERER:
				changeSet.setType(AddFaceRenderer.class.getName());
				configuration.put("class", ((Class<? extends FaceRenderer>) command.getPayLoad()).getName());
				break;
			case ADD_FEATURE:
				changeSet.setType(AddFeature.class.getName());
				configuration.put("class", ((Class<? extends ParticleFeature>) command.getPayLoad()).getName());
				break;
			case ADD_HUD:
				changeSet.setType(AddHUD.class.getName());
				configuration.put("class", ((Class<? extends HUD>) command.getPayLoad()).getName());
				break;
			case ADD_RENDERER:
				changeSet.setType(AddRenderer.class.getName());
				configuration.put("class", ((Class<? extends Renderer>) command.getPayLoad()).getName());
				break;
			case CHANGE_DISPLAY_SIZE:
				changeSet.setType(ChangeDisplaySize.class.getName());
				configuration.put(Scene.WIDTH, command.getPayLoad());
				configuration.put(Scene.HEIGHT, command.getPayLoad2());
				break;
			case ADD_EDITOR:
				changeSet.setType(AddEditor.class.getName());
				configuration.put("class", ((Class<? extends Editor>) command.getPayLoad()).getName());
				break;
			case ADD_EMITTER:
				changeSet.setType(AddEmitter.class.getName());
				configuration.put("class", ((Class<? extends ParticleEmitter>) command.getPayLoad()).getName());
				break;
			case ADD_MODIFIER:
				changeSet.setType(AddModifier.class.getName());
				configuration.put("class", ((Class<? extends ParticleModifier>) command.getPayLoad()).getName());
				break;
			case SELECT_SKYBOX:
				changeSet.setType(AddModifier.class.getName());
				configuration.put("name", command.getPayLoad());
				break;
			case HIDE_RENDERER:
				changeSet.setType(HideRenderer.class.getName());
				configuration.put("class", ((Class<? extends Renderer>) command.getPayLoad()).getName());
				break;
			case SHOW_RENDERER:
				changeSet.setType(ShowRenderer.class.getName());
				configuration.put("class", ((Class<? extends Renderer>) command.getPayLoad()).getName());
				break;
			default:
				break;
		}
		changeSet.setConfiguration(configuration);
		demo.addChangeSet(changeSet);
	}
//	
//	public void setDemoManager(DemoManager demoManager) {
//		this.demoManager = demoManager;
//	}

}
