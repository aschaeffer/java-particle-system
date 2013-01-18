package de.hda.particles.hud;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.input.Keyboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.scene.Scene;

public class HUDManager extends AbstractHUD implements HUD { // genius: HUDManager is a HUD by himself

	public final static Integer DEFAULT_INPUT_POLL_SLEEP = 50;
	public final static Integer DEFAULT_PANEL_SIZE = 30;

	private final List<HUD> huds = new ArrayList<HUD>();
	private final List<HUDCommand> commandQueue = new ArrayList<HUDCommand>();

	// HUDManager settings
	private Integer inputPollSleep = DEFAULT_INPUT_POLL_SLEEP;

	// HUDManager states
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
			hud.setup();
			huds.add(hud);
		} catch (Exception e) {
			logger.error("could not create HUD: " + e.getMessage(), e);
		}
	}
	
	public void remove(HUD hud) {
		if (huds.contains(hud)) {
			huds.remove(hud);
		}
	}

	public void remove(Class<? extends HUD> hudClass) {
		ListIterator<HUD> iterator = huds.listIterator(0);
		while (iterator.hasNext()) {
			HUD hud = iterator.next();
			if (hud.getClass().equals(hudClass)) {
				iterator.remove();
			}
		}
	}

	public List<HUD> getHUDs() {
		return huds;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends HUD> T getByType(Class<T> hudClass) {
		ListIterator<HUD> iterator = huds.listIterator(0);
		while (iterator.hasNext()) {
			HUD hud = iterator.next();
			if (hud.getClass().equals(hudClass)) {
				return (T) hud;
			}
		}
		return null;
	}
	
	public void addCommand(HUDCommand command) {
		commandQueue.add(command);
	}

	@Override
	public void update() {
		// execute all queued commands
		for (Integer queueIndex = 0; queueIndex < commandQueue.size(); queueIndex++) {
			for (Integer hudIndex = 0; hudIndex < huds.size(); hudIndex++) {
				huds.get(hudIndex).executeCommand(commandQueue.get(queueIndex));
			}
			executeCommand(commandQueue.get(queueIndex));
		}
		commandQueue.clear();

		if (!activated) return;

		enterOrtho();

		// draw quads
		// glMatrixMode(GL_MODELVIEW); <----- NEVER!
		// glLoadIdentity();           <----- NEVER!
		glTranslatef(0.375f, 0.375f, 0.0f);
		glPushMatrix();
		// render all managed huds (first pass)
		ListIterator<HUD> iterator = huds.listIterator(0);
		while(iterator.hasNext()) {
			iterator.next().render1();
		}
		// render top hud panel
	    glColor4f(1.0f, 1.0f, 0.8f, 0.95f);
	    glBegin(GL_QUADS);
	    glVertex2f(0, 0);
		glVertex2f(scene.getWidth(), 0);
		glVertex2f(scene.getWidth(), DEFAULT_PANEL_SIZE);
		glVertex2f(0, DEFAULT_PANEL_SIZE);
	    glEnd();
		// render bottom hud panel
	    glBegin(GL_QUADS);
	    glVertex2f(0, scene.getHeight() - DEFAULT_PANEL_SIZE);
		glVertex2f(scene.getWidth(), scene.getHeight() - DEFAULT_PANEL_SIZE);
		glVertex2f(scene.getWidth(), scene.getHeight());
		glVertex2f(0, scene.getHeight());
	    glEnd();
		// render all managed huds (second pass)
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
		// start input polling thread
		new Thread() {
			@Override
			public void run() {
				while (!isFinished()) {
					if (Keyboard.isCreated()) {
						Keyboard.next();
						if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
							if (!blockActivatedSelection) {
								activated = !activated;
								blockActivatedSelection = true;
							}
						} else {
							blockActivatedSelection = false;
						}
						for (HUD hud : huds)
							hud.input();
						try {
							Thread.sleep(inputPollSleep);
						} catch (InterruptedException e) {}
						// Display.sync(20);
					}
				}
			}
		}.start();
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
		return scene.isFinished();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.ADD_HUD) {
			if (command.getPayLoad() != null) {
				add((Class<? extends HUD>) command.getPayLoad());
			}
		}
		if (command.getType() == HUDCommandTypes.REMOVE_HUD) {
			if (command.getPayLoad() != null) {
				remove((Class<? extends HUD>) command.getPayLoad());
			}
		}

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

	public Integer getInputPollSleep() {
		return inputPollSleep;
	}

	public void setInputPollSleep(Integer inputPollSleep) {
		this.inputPollSleep = inputPollSleep;
	}
}
