package de.hda.particles.hud.impl;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.hud.MenuHUD;
import java.awt.Font;
import java.util.ListIterator;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.ConfigurableParticleSystem;
import de.hda.particles.menu.HUDMenuEntry;
import de.hda.particles.menu.factory.MainMenuFactory;
import de.hda.particles.scene.impl.ConfigurableScene;
import de.hda.particles.scene.Scene;

public class MainMenuHUD extends AbstractMenuHUD implements MenuHUD {

	public final static String MAIN_MENU = "Main Menu";

	private final static Float DEFAULT_WIDTH_PERCENT = 0.3f;
	private final static Integer DEFAULT_MARGIN_BIG = 10;
	private final static Integer DEFAULT_MARGIN_SMALL = 8;
	private final static Integer DEFAULT_FONT_SIZE_BIG = 20;
	private final static Integer DEFAULT_FONT_SIZE_SMALL = 15;

	private Boolean blockEscSelection = false;
	
	private Integer currentMargin = DEFAULT_MARGIN_BIG;

	private UnicodeFont bigFont;
	private UnicodeFont smallFont;

	private final Logger logger = LoggerFactory.getLogger(MenuHUDControlHUD.class);

	public MainMenuHUD() {
	}

	public MainMenuHUD(Scene scene) {
		super(scene);
		menu = new MainMenuFactory().createMenu(scene);
		scene.getMenuManager().addMenu(MAIN_MENU, menu);
	}
	
	@Override
	public void setScene(Scene scene) {
		this.scene = scene;
		menu = new MainMenuFactory().createMenu(scene);
		scene.getMenuManager().addMenu(MAIN_MENU, menu);
	}

	@Override
	public void update() {
		if (scene.getMenuManager().isBlocked()) return;
		if (!scene.getMenuManager().isMenuOpen(menu)) return;
		HUDMenuEntry currentMenu = scene.getMenuManager().getCurrentMenu();
		if (currentMenu.childs.size() < 8) {
			currentMargin = DEFAULT_MARGIN_BIG;
			font = bigFont;
		} else {
			currentMargin = DEFAULT_MARGIN_SMALL;
			font = smallFont;
		}

		Integer left = (scene.getWidth() / 2) - (font.getWidth(currentMenu.title) / 2);
		Integer height = font.getHeight(currentMenu.title);
		Integer top = (scene.getHeight() / 2) - (((currentMenu.childs.size() + 1) * (height + 3*currentMargin)) / 2);

	    font.drawString(left, top, currentMenu.title, new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));

	    ListIterator<HUDMenuEntry> iterator = currentMenu.childs.listIterator(0);
	    while (iterator.hasNext()) {
	    	HUDMenuEntry entry = iterator.next();
	    	top = top + height + 3*currentMargin;
	    	left = (scene.getWidth() / 2) - (font.getWidth(entry.title) / 2);
			height = font.getHeight(entry.title);
		    font.drawString(left, top, entry.title, new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));
	    	
	    }

	}
	
	@Override
	public void render2() {
		if (scene.getMenuManager().isBlocked()) return;
		if (!scene.getMenuManager().isMenuOpen(menu)) return;
		HUDMenuEntry currentMenu = scene.getMenuManager().getCurrentMenu();
	    Integer selectedIndex = scene.getMenuManager().getSelectedIndex();
		
		Integer width = new Float(scene.getWidth() * DEFAULT_WIDTH_PERCENT).intValue();
		Integer height = font.getHeight(currentMenu.title);
		Integer left = (scene.getWidth() / 2) - (width / 2);
		Integer top = (scene.getHeight() / 2) - (((currentMenu.childs.size() + 1) * (height + 3*currentMargin)) / 2);

		glColor4f(1.0f, 0.5f, 0.0f, 0.5f);
	    glBegin(GL_QUADS);
	    glVertex2f(left - currentMargin, top - currentMargin);
		glVertex2f(left + width + currentMargin, top - currentMargin);
		glVertex2f(left + width + currentMargin, top + height + currentMargin);
		glVertex2f(left - currentMargin, top + height + currentMargin);
	    glEnd();
	    
	    ListIterator<HUDMenuEntry> iterator = currentMenu.childs.listIterator(0);
	    while (iterator.hasNext()) {
	    	HUDMenuEntry entry = iterator.next();
	    	top = top + height + 3*currentMargin;
			height = font.getHeight(entry.title);
			
			if (iterator.previousIndex() == selectedIndex) {
				glColor4f(1.0f, 0.0f, 0.0f, 0.8f);
			} else {
				glColor4f(1.0f, 0.0f, 0.0f, 0.35f);
			}
		    glBegin(GL_QUADS);
		    glVertex2f(left - currentMargin, top - currentMargin);
			glVertex2f(left + width + currentMargin, top - currentMargin);
			glVertex2f(left + width + currentMargin, top + height + currentMargin);
			glVertex2f(left - currentMargin, top + height + currentMargin);
		    glEnd();
	    	
	    }

	}
	
	@Override
	public void input() {
		if (scene.getMenuManager().isBlocked()) return;
//		if (blocked) return;
//		if (blockedFor > 0) {
//			blockedFor--;
//			return;
//		}
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			if (!blockEscSelection) {
				if (!scene.getMenuManager().isMenuOpen(menu)) {
					scene.getMenuManager().openMenu(menu);
				}
				blockEscSelection = true;
			}
		} else {
			blockEscSelection = false;
		}
//		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
//			if (!blockEscSelection) {
//				if (show) {
//					show = false;
//				} else {
//					show = true;
//					currentMenu = menu;
//					selectedIndex = 0;
//				}
//				blockEscSelection = true;
//			}
//		} else {
//			blockEscSelection = false;
//		}
//		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
//			if (show && (blockUpSelection == 0 || blockUpSelection > KEYPRESS_REPEAT_THRESHOLD)) {
//				if (selectedIndex > 0) {
//					selectedIndex--;
//				} else {
//					selectedIndex = currentMenu.childs.size() - 1;
//				}
//			}
//			blockUpSelection++;
//		} else {
//			blockUpSelection = 0;
//		}
//		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
//			if (show && (blockDownSelection == 0 || blockDownSelection > KEYPRESS_REPEAT_THRESHOLD)) {
//				if (selectedIndex+1 < currentMenu.childs.size()) {
//					selectedIndex++;
//				} else {
//					selectedIndex = 0;
//				}
//			}
//			blockDownSelection++;
//		} else {
//			blockDownSelection = 0;
//		}
//		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
//			if (!blockInSelection) {
//				if (show) {
//					if (currentMenu.equals(currentMenu.parent)) {
//						show = false;
//					} else {
//						selectedIndex = currentMenu.parent.childs.indexOf(currentMenu);
//						currentMenu = currentMenu.parent;
//					}
//				}
//				blockInSelection = true;
//			}
//		} else {
//			blockInSelection = false;
//		}
//		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
//			if (!blockOutSelection) {
//				if (!show) {
//					show = true;
//					currentMenu = menu;
//					selectedIndex = 0;
//				} else {
//					if (currentMenu != null && currentMenu.childs.size() > selectedIndex) {
//						if (currentMenu.childs.get(selectedIndex).command.getType().equals(HUDCommandTypes.MENU)) {
//							if (currentMenu.childs.get(selectedIndex) != null) {
//								currentMenu = currentMenu.childs.get(selectedIndex);
//								selectedIndex = 0;
//							}
//						} else {
//							scene.getHudManager().addCommand(currentMenu.childs.get(selectedIndex).command);
//							logger.debug("added command: " + currentMenu.childs.get(selectedIndex).command.getType().name());
//							show = false;
//						}
//					}
//				}
//				blockOutSelection = true;
//			}
//		} else {
//			blockOutSelection = false;
//		}
//		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
//			if (!blockSelectSelection) {
//				if (show && currentMenu.childs.size() > selectedIndex) {
//					if (!currentMenu.childs.get(selectedIndex).command.getType().equals(HUDCommandTypes.MENU)) {
//						scene.getHudManager().addCommand(currentMenu.childs.get(selectedIndex).command);
//						show = false;
//					}
//				}
//				blockSelectSelection = true;
//			}
//		} else {
//			blockSelectSelection = false;
//		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setup() {
        // loading fonts in two sizes
        bigFont = new UnicodeFont(new Font("Arial", Font.BOLD, DEFAULT_FONT_SIZE_BIG));
        bigFont.getEffects().add(new ColorEffect(new java.awt.Color(0.8f, 0.8f, 0.8f)));
        bigFont.addAsciiGlyphs();
        smallFont = new UnicodeFont(new Font("Arial", Font.BOLD, DEFAULT_FONT_SIZE_SMALL));
        smallFont.getEffects().add(new ColorEffect(new java.awt.Color(0.8f, 0.8f, 0.8f)));
        smallFont.addAsciiGlyphs();
        try {
            bigFont.loadGlyphs();
            smallFont.loadGlyphs();
            font = bigFont;
        } catch (SlickException e) {
        	logger.error("could not load font glyphs", e);
        }
	}
	
	@Override
	public void executeCommand(HUDCommand command) {
//		if (command.getType() == HUDCommandTypes.EDIT) {
//			blocked = true;
//		}
//		if (command.getType() == HUDCommandTypes.EDIT_DONE) {
//			blocked = false;
//			blockedFor = KEYPRESS_REPEAT_THRESHOLD;
//		}
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
		if (command.getType() == HUDCommandTypes.LOAD_SYSTEM) {
			ConfigurableParticleSystem cParticleSystem = (ConfigurableParticleSystem) scene.getParticleSystem();
			scene.getParticleSystem().beginModification();
			cParticleSystem.openLoadDialog();
			scene.getParticleSystem().endModification();
		}
		if (command.getType() == HUDCommandTypes.SAVE_SYSTEM) {
			ConfigurableParticleSystem cParticleSystem = (ConfigurableParticleSystem) scene.getParticleSystem();
			scene.getParticleSystem().beginModification();
			cParticleSystem.openSaveDialog();
			scene.getParticleSystem().endModification();
		}
	}

}
