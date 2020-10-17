package de.hda.particles.overlay.impl;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.overlay.TextOverlay;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.renderer.impl.AbstractRenderer;
import de.hda.particles.renderer.Renderer;
import de.hda.particles.scene.Scene;

public class TextOverlayManager extends AbstractRenderer implements Renderer {

	private final List<TextOverlay> textOverlays = new ArrayList<TextOverlay>();

	private Boolean enabled = false;

	private List<TextOverlay> currentTextOverlays;

	private final Logger logger = LoggerFactory.getLogger(TextOverlayManager.class);
	
	public TextOverlayManager() {
		super();
	}

	public TextOverlayManager(Scene scene) {
		super(scene);
	}

	public void add(TextOverlay textOverlay) {
		textOverlays.add(textOverlay);
		textOverlay.setScene(scene);
	}
	
	public void add(Class<? extends TextOverlay> hudClass) {
		try {
			TextOverlay textOverlay = hudClass.newInstance();
			textOverlay.setScene(scene);
			textOverlays.add(textOverlay);
		} catch (Exception e) {
			logger.error("could not create TextOverlay: " + e.getMessage(), e);
		}
	}
	
	public List<TextOverlay> getTextOverlays() {
		return textOverlays;
	}
	
	/**
	 * Calls all text overlay renderers.
	 */
	@Override
	public void update() {
		if (!enabled) return;

        // store the current state of the renderer
        glPushAttrib(GL_DEPTH_BUFFER_BIT | GL_ENABLE_BIT);
        glPushMatrix();
        glLoadIdentity();
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
            
        // now enter orthographic projection
        glLoadIdentity();
        glOrtho(0, scene.getWidth(), scene.getHeight(), 0, -1, 1);
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_LIGHTING);

        currentTextOverlays = new ArrayList<TextOverlay>(textOverlays);
		ListIterator<TextOverlay> textOverlayIterator = currentTextOverlays.listIterator(0);
		while (textOverlayIterator.hasNext()) {
			textOverlayIterator.next().update();
		}

        // back to old state
		glEnable(GL_DEPTH_TEST);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
        glPopMatrix();
        glPopAttrib();

	}

	@Override
	public void setup() {
		ListIterator<TextOverlay> iterator = textOverlays.listIterator(0);
		while(iterator.hasNext()) {
			iterator.next().setup();
		}
	}

	@Override
	public void destroy() {
		ListIterator<TextOverlay> iterator = textOverlays.listIterator(0);
		while(iterator.hasNext()) {
			iterator.next().destroy();
		}
		textOverlays.clear();
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
