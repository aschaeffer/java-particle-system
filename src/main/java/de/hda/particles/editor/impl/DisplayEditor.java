package de.hda.particles.editor.impl;

import de.hda.particles.editor.Editor;
import java.util.ArrayList;
import java.util.List;

import de.hda.particles.hud.impl.HUDCommand;
import de.hda.particles.hud.impl.HUDCommandTypes;
import de.hda.particles.scene.Scene;

/**
 * 
 * Experiment a litte:
 * http://www.andersriggelsen.dk/glblendfunc.php
 * 
 * @author aschaeffer
 *
 */
public class DisplayEditor implements Editor {

	private final static String title = "Display";
	
	private final static String WINDOW_SIZE = "windowSize";
	private final static String FULLSCREEN = "fullscreen";
	private final static String MAX_FPS = "maxFps";
	
	protected Scene scene;
	protected Object subject;
	protected List<HUDEditorEntry> editorEntries = new ArrayList<>();

	@Override
	public void setup() {
		editorEntries.add(HUDEditorEntry.create(WINDOW_SIZE, "Window Size"));
		editorEntries.add(HUDEditorEntry.create(FULLSCREEN, "Fullscreen"));
		editorEntries.add(HUDEditorEntry.create(MAX_FPS, "Max FPS"));
	}

	@Override
	public Boolean accept(Class<?> clazz) {
		return clazz.equals(DisplayEditor.class);
	}
	
	@Override
	public Class<?> getAcceptable() {
		return this.getClass();
	}

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		return editorEntries;
	}
	
	@Override
	public void decrease(String fieldName) {
		if (fieldName.equals(WINDOW_SIZE)) {
			if (scene.getWidth() == 640 && scene.getHeight() == 480) {
				// min
			} else if (scene.getWidth() == 800 && scene.getHeight() == 600) {
				changeWindowSize(640, 480);
			} else if (scene.getWidth() == 1024 && scene.getHeight() == 768) {
				changeWindowSize(800, 600);
			} else if (scene.getWidth() == 1280 && scene.getHeight() == 720) {
				changeWindowSize(1024, 768);
			} else if (scene.getWidth() == 1280 && scene.getHeight() == 1024) {
				changeWindowSize(1280, 720);
			} else if (scene.getWidth() == 1366 && scene.getHeight() == 768) {
				changeWindowSize(1280, 1024);
			} else if (scene.getWidth() == 1400 && scene.getHeight() == 1050) {
				changeWindowSize(1366, 768);
			} else if (scene.getWidth() == 1680 && scene.getHeight() == 1050) {
				changeWindowSize(1400, 1050);
			} else if (scene.getWidth() == 1920 && scene.getHeight() == 1080) {
				changeWindowSize(1680, 1050);
			} else {
				changeWindowSize(1280, 720);
			}
		} else if (fieldName.equals(FULLSCREEN)) {
			setFullscreen(false);
		} else if (fieldName.equals(MAX_FPS)) {
			if (scene.getMaxFps() > 1) {
				scene.setMaxFps(scene.getMaxFps() - 1);
			}
		}
	}

	@Override
	public void setMin(String fieldName) {
		if (fieldName.equals(WINDOW_SIZE)) {
			changeWindowSize(640, 480);
		} else if (fieldName.equals(FULLSCREEN)) {
			setFullscreen(false);
		} else if (fieldName.equals(MAX_FPS)) {
			scene.setMaxFps(1);
		}
	}

	@Override
	public void increase(String fieldName) {
		if (fieldName.equals(WINDOW_SIZE)) {
			if (scene.getWidth() == 640 && scene.getHeight() == 480) {
				changeWindowSize(800, 600);
			} else if (scene.getWidth() == 800 && scene.getHeight() == 600) {
				changeWindowSize(1024, 768);
			} else if (scene.getWidth() == 1024 && scene.getHeight() == 768) {
				changeWindowSize(1280, 720);
			} else if (scene.getWidth() == 1280 && scene.getHeight() == 720) {
				changeWindowSize(1280, 1024);
			} else if (scene.getWidth() == 1280 && scene.getHeight() == 1024) {
				changeWindowSize(1366, 768);
			} else if (scene.getWidth() == 1366 && scene.getHeight() == 768) {
				changeWindowSize(1400, 1050);
			} else if (scene.getWidth() == 1400 && scene.getHeight() == 1050) {
				changeWindowSize(1680, 1050);
			} else if (scene.getWidth() == 1680 && scene.getHeight() == 1050) {
				changeWindowSize(1920, 1080);
			} else if (scene.getWidth() == 1920 && scene.getHeight() == 1080) {
				// max
			} else {
				changeWindowSize(1280, 720);
			}
		} else if (fieldName.equals(FULLSCREEN)) {
			setFullscreen(true);
		} else if (fieldName.equals(MAX_FPS)) {
			scene.setMaxFps(scene.getMaxFps() + 1);
		}
	}

	@Override
	public void setMax(String fieldName) {
		if (fieldName.equals(WINDOW_SIZE)) {
			changeWindowSize(1920, 1080);
		} else if (fieldName.equals(FULLSCREEN)) {
			setFullscreen(true);
		} else if (fieldName.equals(MAX_FPS)) {
			scene.setMaxFps(1000);
		}
	}

	@Override
	public String getValue(String fieldName) {
		if (fieldName.equals(WINDOW_SIZE)) {
			return ""+scene.getWidth()+"x"+scene.getHeight();
		} else if (fieldName.equals(FULLSCREEN)) {
			if (scene.getFullscreen()) {
				return "On";
			} else {
				return "Off";
			}
		} else if (fieldName.equals(MAX_FPS)) {
			return scene.getMaxFps().toString();
		} else {
			return "N/A";
		}
	}
	
	@Override
	public Object getObject(String fieldName) {
		if (fieldName.equals(WINDOW_SIZE)) {
			return ""+scene.getWidth()+"x"+scene.getHeight();
		} else if (fieldName.equals(FULLSCREEN)) {
			return scene.getFullscreen();
		} else if (fieldName.equals(MAX_FPS)) {
			return scene.getMaxFps();
		} else {
			return null;
		}
	}

	@Override
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	@Override
	public void select(Object subject) {
		this.subject = subject;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
	private void changeWindowSize(Integer width, Integer height) {
		scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.CHANGE_DISPLAY_SIZE, width, height));
	}
	
	private void setFullscreen(Boolean fullscreen) {
		if (scene.getFullscreen() != fullscreen) {
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.TOGGLE_FULLSCREEN));
		}
	}

}
