package de.hda.particles.hud;

import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ENABLE_BIT;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.input.Keyboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.scene.Scene;

public class HUDManager extends AbstractHUD implements HUD { // genius: HUDManager is a HUD by himself

	private final List<HUD> huds = new ArrayList<HUD>();
	
	private Boolean activated = true;
	private Boolean blockActivatedSelection = false;
	
	private final Logger logger = LoggerFactory.getLogger(HUDManager.class);
	
	public HUDManager() {
		super();
	}

	public HUDManager(Scene scene) {
		super(scene);
	}

	public void add(HUD hud) {
		huds.add(hud);
	}
	
	public void add(Class<? extends HUD> hudClass) {
		try {
			HUD hud = hudClass.newInstance();
			hud.setScene(scene);
			huds.add(hud);
		} catch (Exception e) {
			logger.error("could not create HUD: " + e.getMessage(), e);
		}
	}
	
	public List<HUD> getHUDs() {
		return huds;
	}
	
	public void addCommand(HUDCommand command) {
		ListIterator<HUD> iterator = huds.listIterator(0);
		while(iterator.hasNext()) {
			iterator.next().executeCommand(command);
		}
	}

	@Override
	public void update() {
		Keyboard.next();
		if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
			if (!blockActivatedSelection) {
				activated = !activated;
				blockActivatedSelection = true;
			}
		} else {
			blockActivatedSelection = false;
		}

		if (!activated) return;

		enterOrtho();

	    // draw quads
//	    glMatrixMode(GL_MODELVIEW); <----- NEVER!
//	    glLoadIdentity();           <----- NEVER!
	    glTranslatef(0.375f, 0.375f, 0.0f);

	    glPushMatrix();
		// render all managed huds
	    ListIterator<HUD> iterator = huds.listIterator(0);
		while(iterator.hasNext()) {
			iterator.next().render1();
		}
	    glColor4f(1.0f, 1.0f, 0.8f, 0.95f);
	    glBegin(GL_QUADS);
	    glVertex2f(0, 0);
		glVertex2f(scene.getWidth(), 0);
		glVertex2f(scene.getWidth(), 30);
		glVertex2f(0, 30);
	    glEnd();
	    glBegin(GL_QUADS);
	    glVertex2f(0, scene.getHeight()-30);
		glVertex2f(scene.getWidth(), scene.getHeight()-30);
		glVertex2f(scene.getWidth(), scene.getHeight());
		glVertex2f(0, scene.getHeight());
	    glEnd();
		// render all managed huds
	    iterator = huds.listIterator(0);
		while(iterator.hasNext()) {
			iterator.next().render2();
		}
	    glPopMatrix();

		// render all managed huds
	    iterator = huds.listIterator(0);
		while(iterator.hasNext()) {
			iterator.next().update();
		}
	    leaveOrtho();
	}

	@Override
	public void setup() {
		ListIterator<HUD> iterator = huds.listIterator(0);
		while(iterator.hasNext()) {
			iterator.next().setup();
		}
	}

	@Override
	public void destroy() {
		ListIterator<HUD> iterator = huds.listIterator(0);
		while(iterator.hasNext()) {
			iterator.next().destroy();
		}
		huds.clear();
	}

	@Override
	public Boolean isFinished() {
		return false;
	}

	private void enterOrtho() {
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
    }
 
	private void leaveOrtho() {
        // restore the state of the renderer
		glEnable(GL_DEPTH_TEST);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
        glPopMatrix();
        glPopAttrib();
    }
}
