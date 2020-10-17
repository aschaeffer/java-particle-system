package de.hda.particles.hud.impl;

import static org.lwjgl.opengl.GL11.*;

import de.hda.particles.hud.MenuHUD;
import java.awt.Font;
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

public class TopMenuHUD extends AbstractMenuHUD implements MenuHUD, MenuControlListener {

	private final static Integer DEFAULT_MARGIN = 10;
	public final static String TOP_MENU = "Top Menu";

//	private final org.newdawn.slick.Color black = new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f);
//	private final org.newdawn.slick.Color white = new org.newdawn.slick.Color(1.0f, 1.0f, 1.0f, 1.0f);
	
	private Boolean blockCtrlSelection = false;

	private final Logger logger = LoggerFactory.getLogger(TopMenuHUD.class);

	public TopMenuHUD() {}

	public TopMenuHUD(Scene scene) {
		super(scene);
		menu = HUDMenuEntry.createRoot("Top Menu", false);
		scene.getMenuManager().addMenu(TOP_MENU, menu);
	}
	
	@Override
	public void setScene(Scene scene) {
		this.scene = scene;
		menu = HUDMenuEntry.createRoot("Top Menu", false);
		scene.getMenuManager().addMenu(TOP_MENU, menu);
	}

	@Override
	public void update() {
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
		// render the title menu
		Integer top = DEFAULT_MARGIN;
		Integer left = DEFAULT_MARGIN;
		Integer width = 0;
		Integer height = 0;
		Boolean menuOpened = scene.getMenuManager().isMenuOpen(menu);
	    Integer selectedIndex = scene.getMenuManager().getSelectedIndex();
		ListIterator<HUDMenuEntry> iterator = menu.childs.listIterator(0);
		glEnable(GL_TEXTURE_2D);
	    while (iterator.hasNext()) {
	    	HUDMenuEntry entry = iterator.next();
	    	if (entry.icon != null) { //  && iterator.previousIndex() == selectedIndex //  menuOpened && !isBlocked && && iterator.previousIndex() == selectedIndex
    			Texture texture = scene.getTextureManager().load("PNG", "images/icons/" + entry.icon + ".png");
    			texture.bind();
    			width = 32;
    			height = 32;
			    glBegin(GL_QUADS);
			    if (menuOpened && iterator.previousIndex() == selectedIndex) {
			    	glColor4f(1.0f, 0.6f, 0.6f, 1.0f);
			    	left = left + 10;
			    	width = 48;
			    	height = 48;
			    } else {
		    		glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
			    }
	    		glTexCoord2f(0.0f, 0.0f);
			    glVertex2f(left - DEFAULT_MARGIN/2, top - DEFAULT_MARGIN);
			    glTexCoord2f(texture.getWidth(), 0.0f);
				glVertex2f(left + width + DEFAULT_MARGIN/2, top - DEFAULT_MARGIN);
				glTexCoord2f(texture.getWidth(), texture.getHeight());
				glVertex2f(left + width + DEFAULT_MARGIN/2, top + height + DEFAULT_MARGIN);
				glTexCoord2f(0.0f, texture.getHeight());
				glVertex2f(left - DEFAULT_MARGIN/2, top + height + DEFAULT_MARGIN);
			    glEnd();
			    left = left + width + DEFAULT_MARGIN;
			    if (menuOpened && iterator.previousIndex() == selectedIndex) {
			    	left = left + 10;
			    }
	    	}
	    }
		glDisable(GL_TEXTURE_2D);
	}

	@Override
	public void input() {
		if (Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU)) {
			if (!blockCtrlSelection) {
				if (!scene.getMenuManager().isMenuOpen(menu)) {
					scene.getMenuManager().openMenu(menu);
				}
				blockCtrlSelection = true;
			}
		} else {
			blockCtrlSelection = false;
		}
	}

	@Override
	public void setup() {
		// register as menu control listener
		scene.getMenuManager().addMenuControlListener(this);
		// setup font
        font = new UnicodeFont(new Font("Arial", Font.BOLD, 12));
        font.getEffects().add(new ColorEffect(new java.awt.Color(0.8f, 0.8f, 0.8f)));
        font.addAsciiGlyphs();
        try {
           font.loadGlyphs();
        } catch (SlickException e) {
        	logger.error("could not load font glyphs", e);
        }
	}

	@Override
	public void onOpenMenu(HUDMenuEntry menu) {
		if (menu.equals(this.menu)) {
			scene.getEditorManager().closeCurrentEditor();
		}
	}

	@Override
	public void onCloseMenu(HUDMenuEntry menu) {}

	@Override
	public void onPreviousEntry(HUDMenuEntry menu, HUDMenuEntry entry) {
		if (menu.equals(this.menu)) {
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, entry.title));
		}
	}

	@Override
	public void onNextEntry(HUDMenuEntry menu, HUDMenuEntry entry) {
		if (menu.equals(this.menu)) {
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, entry.title));
		}
	}

}
