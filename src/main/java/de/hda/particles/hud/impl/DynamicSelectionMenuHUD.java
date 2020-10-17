package de.hda.particles.hud.impl;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.hud.MenuHUD;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.menu.HUDMenuEntry;
import de.hda.particles.menu.MenuControlListener;
import de.hda.particles.scene.Scene;

public class DynamicSelectionMenuHUD extends AbstractMenuHUD implements MenuHUD, MenuControlListener {

	private final static Integer DEFAULT_MARGIN = 10;
	public final static String SELECT_MENU = "Select Menu";

	private final org.newdawn.slick.Color black = new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f);
	private final org.newdawn.slick.Color white = new org.newdawn.slick.Color(1.0f, 1.0f, 1.0f, 1.0f);
	
	private Integer lastKeyCode = -1;

	private List<Texture> textures = new ArrayList<Texture>();
	private Texture texture;

	private final Logger logger = LoggerFactory.getLogger(DynamicSelectionMenuHUD.class);
	
	public DynamicSelectionMenuHUD() {}

	public DynamicSelectionMenuHUD(Scene scene) {
		super(scene);
	}
	
	@Override
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	@Override
	public void update() {
		if (menu == null) return;
		Boolean menuOpened = scene.getMenuManager().isMenuOpen(menu);
		if (menuOpened) {
		    Integer selectedIndex = scene.getMenuManager().getSelectedIndex();
		    HUDMenuEntry entry = menu.childs.get(selectedIndex);
			Integer width = font.getWidth(entry.title);
			Integer height = font.getHeight(entry.title);
			Integer left = scene.getWidth() / 2 - width / 2;
			Integer top = scene.getHeight() / 2 - height / 2 + 64 + DEFAULT_MARGIN;
			font.drawString(left+2, top+2, entry.title, black);
			font.drawString(left, top, entry.title, white);
		}
		// render the title menu
		/*
		Integer top = DEFAULT_MARGIN;
		Integer left = DEFAULT_MARGIN;
		Boolean menuOpened = scene.getMenuManager().isMenuOpen(menu);
		Boolean isBlocked = scene.getMenuManager().isBlocked();
	    Integer selectedIndex = scene.getMenuManager().getSelectedIndex();
		ListIterator<HUDMenuEntry> iterator = menu.childs.listIterator(0);
	    while (iterator.hasNext()) {
	    	HUDMenuEntry entry = iterator.next();
	    	if (menuOpened && !isBlocked && iterator.previousIndex() == selectedIndex) {
			    font.drawString(left, top, entry.title, white);
			    ListIterator<HUDMenuEntry> currentMenuIterator = entry.childs.listIterator(0);
			    Integer top2 = top;
			    while (currentMenuIterator.hasNext()) {
			    	HUDMenuEntry currentMenuEntry = currentMenuIterator.next();
			    	top2 = top2 + font.getHeight(currentMenuEntry.title) + DEFAULT_MARGIN;
				    font.drawString(left, top2, currentMenuEntry.title, black);
			    }
	    	} else {
			    font.drawString(left, top, entry.title, black);
	    	}
	    	left = left + font.getWidth(entry.title) + DEFAULT_MARGIN;
	    }
	    */
	}

	@Override
	public void render2() {
		if (menu == null) return;
		Boolean menuOpened = scene.getMenuManager().isMenuOpen(menu);
		if (menuOpened) {
			try {
				Integer top = scene.getHeight() / 2 - DEFAULT_MARGIN;
				Integer currentTop = top;
				Integer menuWidth = menu.childs.size() * (32 + DEFAULT_MARGIN) + 16;
				Integer left = scene.getWidth() / 2 - menuWidth / 2;
				Integer height = 0;
				Integer width = 0;
			    Integer selectedIndex = scene.getMenuManager().getSelectedIndex();
				ListIterator<HUDMenuEntry> iterator = menu.childs.listIterator(0);
				glEnable(GL_TEXTURE_2D);
				// Texture texture;
			    while (iterator.hasNext()) {
			    	HUDMenuEntry entry = iterator.next();
			    	if (entry.icon != null) {
			    		texture = textures.get(iterator.previousIndex());
		    			// texture = scene.getTextureManager().load("PNG", "images/icons/" + entry.icon + ".png");
		    			texture.bind();
					    glBegin(GL_QUADS);
					    if (iterator.previousIndex() == selectedIndex) {
					    	glColor4f(1.0f, 0.6f, 0.6f, 1.0f);
					    	currentTop = top;
					    	left = left + 10;
					    	width = 48;
					    	height = 48;
					    } else {
				    		glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
					    	currentTop = top + 8;
			    			width = 32;
			    			height = 32;
					    }
			    		glTexCoord2f(0.0f, 0.0f);
					    glVertex2f(left - DEFAULT_MARGIN/2, currentTop - DEFAULT_MARGIN);
					    glTexCoord2f(texture.getWidth(), 0.0f);
						glVertex2f(left + width + DEFAULT_MARGIN/2, currentTop - DEFAULT_MARGIN);
						glTexCoord2f(texture.getWidth(), texture.getHeight());
						glVertex2f(left + width + DEFAULT_MARGIN/2, currentTop + height + DEFAULT_MARGIN);
						glTexCoord2f(0.0f, texture.getHeight());
						glVertex2f(left - DEFAULT_MARGIN/2, currentTop + height + DEFAULT_MARGIN);
					    glEnd();
					    left = left + width + 2*DEFAULT_MARGIN;
					    if (menuOpened && iterator.previousIndex() == selectedIndex) {
					    	left = left + 10;
					    }
			    	}
			    }
			} catch (Exception e) {
				logger.error("Could not render menu: " + e.getMessage(), e);
			}
			glDisable(GL_TEXTURE_2D);
		} else {
			menu = null;
		}
	}

	@Override
	public void input() {
		if (menu == null) return;
		Boolean menuOpened = scene.getMenuManager().isMenuOpen(menu);
		if (menuOpened) {
			if (lastKeyCode >= 0 && Keyboard.isKeyDown(lastKeyCode)) return;
			Integer selectedIndex = scene.getMenuManager().getSelectedIndex();
			HUDMenuEntry entry = menu.childs.get(selectedIndex);
			Integer nextKeyCode = -1;
			Iterator<Integer> iterator = entry.keyCommands.keySet().iterator();
			while (iterator.hasNext()) {
				Integer keyCode = iterator.next();
				if (Keyboard.isKeyDown(keyCode) && lastKeyCode != keyCode) {
					HUDCommand command = entry.keyCommands.get(keyCode);
					logger.debug("key " + keyCode.toString() + " is down: " + command.getType().name() + " " + command.getPayLoad().toString());
					scene.getHudManager().addCommand(command);
					nextKeyCode = keyCode;
				}
			}
			lastKeyCode = nextKeyCode;
		}
	}

	@Override
	public void setup() {
		// register as menu control listener
		scene.getMenuManager().addMenuControlListener(this);
		// setup font
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
	public void setMenu(HUDMenuEntry menu) {
		this.menu = menu;
		textures.clear();
		ListIterator<HUDMenuEntry> iterator = menu.childs.listIterator(0);
	    while (iterator.hasNext()) {
	    	HUDMenuEntry entry = iterator.next();
	    	if (entry.icon != null) {
	    		textures.add(scene.getTextureManager().load("PNG", "images/icons/" + entry.icon + ".png"));
	    	}
	    }
	    logger.debug("loaded " + textures.size() + " menu icons");
	}

	@Override
	public void onOpenMenu(HUDMenuEntry menu) {
		if (menu.equals(this.menu)) {
			scene.getEditorManager().closeCurrentEditor();
		}
	}

	@Override
	public void onCloseMenu(HUDMenuEntry menu) {
		if (menu.equals(this.menu)) {
			this.menu = null;
		}
		
	}

}
