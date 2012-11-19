package de.hda.particles.hud;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.util.ListIterator;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.scene.ConfigurableScene;
import de.hda.particles.scene.Scene;

public class MenuHUD extends AbstractHUD implements HUD {

	private final static Float DEFAULT_WIDTH_PERCENT = 0.3f;
	private final static Integer margin = 10;

	private Boolean show = false;
	private Boolean blocked = false;
	
	protected HUDMenuEntry menu;
	protected HUDMenuEntry currentMenu;
	protected Integer selectedIndex = 0;

	private Boolean blockEscSelection = false;
	private Boolean blockUpSelection = false;
	private Boolean blockDownSelection = false;
	private Boolean blockInSelection = false;
	private Boolean blockOutSelection = false;
	private Boolean blockSelectSelection = false;
	
	private final Logger logger = LoggerFactory.getLogger(MenuHUD.class);

	public MenuHUD() {
	}

	public MenuHUD(Scene scene) {
		super(scene);
		menu = new DefaultHUDMenu().createMenu(scene);
		currentMenu = menu;
	}
	
	@Override
	public void setScene(Scene scene) {
		this.scene = scene;
		menu = new DefaultHUDMenu().createMenu(scene);
	}

	@Override
	public void update() {
		// camera selection
		Keyboard.next();
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			if (!blockEscSelection) {
				if (!blocked) {
					if (show) {
						show = false;
					} else {
						show = true;
						currentMenu = menu;
						selectedIndex = 0;
					}
				}
				blockEscSelection = true;
			}
		} else {
			blockEscSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			if (!blockUpSelection) {
				if (show && !blocked && selectedIndex > 0) {
					selectedIndex--;
				}
				blockUpSelection = true;
			}
		} else {
			blockUpSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			if (!blockDownSelection) {
				if (show && !blocked && selectedIndex+1 < currentMenu.childs.size()) {
					selectedIndex++;
				}
				blockDownSelection = true;
			}
		} else {
			blockDownSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if (!blockInSelection) {
				if (show && !blocked) {
					if (currentMenu.equals(currentMenu.parent)) {
						show = false;
					} else {
						selectedIndex = currentMenu.parent.childs.indexOf(currentMenu);
						currentMenu = currentMenu.parent;
					}
				}
				blockInSelection = true;
			}
		} else {
			blockInSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if (!blockOutSelection) {
				if (!blocked) {
					if (!show) {
						show = true;
						currentMenu = menu;
						selectedIndex = 0;
					} else {
						if (currentMenu != null && currentMenu.childs.size() > selectedIndex) {
							if (currentMenu.childs.get(selectedIndex).command.getType().equals(HUDCommandTypes.MENU)) {
								if (currentMenu.childs.get(selectedIndex) != null) {
									currentMenu = currentMenu.childs.get(selectedIndex);
									selectedIndex = 0;
								}
							} else {
								scene.getHudManager().addCommand(currentMenu.childs.get(selectedIndex).command);
								show = false;
							}
						}
					}
				}
				blockOutSelection = true;
			}
		} else {
			blockOutSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			if (!blockSelectSelection) {
				if (show && !blocked && currentMenu.childs.size() > selectedIndex) {
					if (!currentMenu.childs.get(selectedIndex).command.getType().equals(HUDCommandTypes.MENU)) {
						scene.getHudManager().addCommand(currentMenu.childs.get(selectedIndex).command);
						show = false;
					}
				}
				blockSelectSelection = true;
			}
		} else {
			blockSelectSelection = false;
		}
		
		if (!show) return;
		
		Integer left = (scene.getWidth() / 2) - (font.getWidth(currentMenu.title) / 2);
		Integer height = font.getHeight(currentMenu.title);
		Integer top = (scene.getHeight() / 2) - (((currentMenu.childs.size() + 1) * (height + 3*margin)) / 2);

	    font.drawString(left, top, currentMenu.title, new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));

	    ListIterator<HUDMenuEntry> iterator = currentMenu.childs.listIterator(0);
	    while (iterator.hasNext()) {
	    	HUDMenuEntry entry = iterator.next();
	    	top = top + height + 3*margin;
	    	left = (scene.getWidth() / 2) - (font.getWidth(entry.title) / 2);
			height = font.getHeight(entry.title);
		    font.drawString(left, top, entry.title, new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));
	    	
	    }

	}
	
	@Override
	public void render2() {
		if (!show) return;
		
		Integer width = new Float(scene.getWidth() * DEFAULT_WIDTH_PERCENT).intValue();
		Integer height = font.getHeight(currentMenu.title);
		Integer left = (scene.getWidth() / 2) - (width / 2);
		Integer top = (scene.getHeight() / 2) - (((currentMenu.childs.size() + 1) * (height + 3*margin)) / 2);

		glColor4f(1.0f, 0.5f, 0.0f, 0.5f);
	    glBegin(GL_QUADS);
	    glVertex2f(left - margin, top - margin);
		glVertex2f(left + width + margin, top - margin);
		glVertex2f(left + width + margin, top + height + margin);
		glVertex2f(left - margin, top + height + margin);
	    glEnd();
	    
	    ListIterator<HUDMenuEntry> iterator = currentMenu.childs.listIterator(0);
	    while (iterator.hasNext()) {
	    	HUDMenuEntry entry = iterator.next();
	    	top = top + height + 3*margin;
			height = font.getHeight(entry.title);
			if (iterator.previousIndex() == selectedIndex) {
				glColor4f(1.0f, 0.0f, 0.0f, 0.8f);
			} else {
				glColor4f(1.0f, 0.0f, 0.0f, 0.35f);
			}
		    glBegin(GL_QUADS);
		    glVertex2f(left - margin, top - margin);
			glVertex2f(left + width + margin, top - margin);
			glVertex2f(left + width + margin, top + height + margin);
			glVertex2f(left - margin, top + height + margin);
		    glEnd();
	    	
	    }

	}

	@SuppressWarnings("unchecked")
	@Override
	public void setup() {
        // AWT Font in eine UnicodeFont von slick-util umwandeln
        font = new UnicodeFont(new Font("Arial", Font.BOLD, 20));
        font.getEffects().add(new ColorEffect(new java.awt.Color(0.8f, 0.8f, 0.8f)));
        font.addAsciiGlyphs();
        try {
           font.loadGlyphs();
        } catch (SlickException e) {
        	logger.error("could not load font glyphs", e);
        }
	}
	
	@Override
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.EDIT) {
			blocked = true;
		}
		if (command.getType() == HUDCommandTypes.EDIT_DONE) {
			blocked = false;
		}
		if (command.getType() == HUDCommandTypes.EXIT) {
			scene.exit();
		}
		if (command.getType() == HUDCommandTypes.LOAD_SCENE) {
			ConfigurableScene cScene = (ConfigurableScene) scene;
			cScene.openLoadDialog();
		}
		if (command.getType() == HUDCommandTypes.SAVE_SCENE) {
			ConfigurableScene cScene = (ConfigurableScene) scene;
			cScene.openSaveDialog();
		}
	}
}
