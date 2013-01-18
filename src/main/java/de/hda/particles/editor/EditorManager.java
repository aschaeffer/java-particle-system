package de.hda.particles.editor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.hud.HUDCommand;
import de.hda.particles.hud.HUDCommandTypes;
import de.hda.particles.scene.Scene;

public class EditorManager {

	private Scene scene;

	private List<Editor> editors = new ArrayList<Editor>();

	private final Logger logger = LoggerFactory.getLogger(EditorManager.class);

	public EditorManager() {
	}

	public EditorManager(Scene scene) {
		this.scene = scene;
	}
	
	public void add(Editor editor) {
		editors.add(editor);
	}
	
	public void add(Class<? extends Editor> editorClass) {
		try {
			Editor editor = editorClass.newInstance();
			editor.setScene(scene);
			editor.setup();
			editors.add(editor);
		} catch (Exception e) {
			logger.error("Could not add editor: " + e.getMessage(), e);
		}
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public List<Editor> getEditors() {
		return editors;
	}

	public void setEditors(List<Editor> editors) {
		this.editors = editors;
	}
	
	public void closeCurrentEditor() {
		try {
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.EDIT_DONE));
		} catch (Exception e) {
			logger.error("Could not close current editor: " + e.getMessage(), e);
		}
	}
	
}
