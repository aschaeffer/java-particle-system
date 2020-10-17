package de.hda.particles.editor.impl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import de.hda.particles.editor.Editor;
import java.util.ArrayList;
import java.util.List;

import de.hda.particles.scene.Scene;

/**
 * 
 * Experiment a litte:
 * http://www.andersriggelsen.dk/glblendfunc.php
 * 
 * @author aschaeffer
 *
 */
public class SystemInfoEditor implements Editor {

	private final static String title = "System Info";
	
	private final static String OPENGL_VERSION = "openGLVersion";
	private final static String OPENGL_VENDOR = "openGLVendor";
	private final static String OPENGL_RENDERER = "openGLRenderer";
	private final static String OPENGL_SHADING_LANGUAGE_VERSION = "openGLShadingLanguageVersion";
	
	protected Scene scene;
	protected Object subject;
	protected List<HUDEditorEntry> editorEntries = new ArrayList<HUDEditorEntry>();;

	@Override
	public void setup() {
		editorEntries.add(HUDEditorEntry.create(OPENGL_VERSION, "OpenGL Version"));
		editorEntries.add(HUDEditorEntry.create(OPENGL_VENDOR, "OpenGL Vendor"));
		editorEntries.add(HUDEditorEntry.create(OPENGL_RENDERER, "OpenGL Renderer"));
		editorEntries.add(HUDEditorEntry.create(OPENGL_SHADING_LANGUAGE_VERSION, "OpenGL GLSL Version"));
	}

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(SystemInfoEditor.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return this.getClass();
	}

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		return editorEntries;
	}
	
	@Override
	public void decrease(String fieldName) {
		if (fieldName.equals(OPENGL_VERSION)) {
		}
	}

	@Override
	public void setMin(String fieldName) {
		if (fieldName.equals(OPENGL_VERSION)) {
		}
	}

	@Override
	public void increase(String fieldName) {
		if (fieldName.equals(OPENGL_VERSION)) {
		}
	}

	@Override
	public void setMax(String fieldName) {
		if (fieldName.equals(OPENGL_VERSION)) {
		}
	}

	@Override
	public String getValue(String fieldName) {
		if (fieldName.equals(OPENGL_VERSION)) {
			return glGetString(GL_VERSION);
		} else if (fieldName.equals(OPENGL_VENDOR)) {
			return glGetString(GL_VENDOR);
		} else if (fieldName.equals(OPENGL_RENDERER)) {
			return glGetString(GL_RENDERER);
		} else if (fieldName.equals(OPENGL_SHADING_LANGUAGE_VERSION)) {
			return glGetString(GL_SHADING_LANGUAGE_VERSION);
		} else {
			return "N/A";
		}
	}
	
	@Override
	public Object getObject(String fieldName) {
		if (fieldName.equals(OPENGL_VERSION)) {
			return glGetString(GL_VERSION);
		} else if (fieldName.equals(OPENGL_VENDOR)) {
			return glGetString(GL_VENDOR);
		} else if (fieldName.equals(OPENGL_RENDERER)) {
			return glGetString(GL_RENDERER);
		} else if (fieldName.equals(OPENGL_SHADING_LANGUAGE_VERSION)) {
			return glGetString(GL_SHADING_LANGUAGE_VERSION);
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
	
}
