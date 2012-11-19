package de.hda.particles.hud;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.editor.Editor;
import de.hda.particles.scene.Scene;

public class EditorHUD extends AbstractHUD implements HUD {

	private final static Float DEFAULT_WIDTH_PERCENT = 0.2f;
	private final static Integer margin = 10;

	private Boolean show = false;

	protected List<Editor> editors;
	protected Editor currentEditor;
	protected Integer selectedIndex = 0;

	private Boolean blockEscSelection = false;
	private Boolean blockUpSelection = false;
	private Boolean blockDownSelection = false;
	private Boolean blockInSelection = false;
	private Boolean blockOutSelection = false;
	
	private final Logger logger = LoggerFactory.getLogger(EditorHUD.class);

	public EditorHUD() {
		editors = DefaultHUDEditors.createEditors();
	}

	public EditorHUD(Scene scene) {
		super(scene);
		editors = DefaultHUDEditors.createEditors();
	}

	@Override
	public void update() {
		Keyboard.next();
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			if (!blockEscSelection) {
				if (show) {
					show = false;
					currentEditor = null;
					scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.EDIT_DONE));
				}
				blockEscSelection = true;
			}
		} else {
			blockEscSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			if (!blockUpSelection) {
				if (show && selectedIndex > 0) {
					selectedIndex--;
				}
				blockUpSelection = true;
			}
		} else {
			blockUpSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			if (!blockDownSelection) {
				if (show && selectedIndex+1 < currentEditor.getEditorEntries().size()) {
					selectedIndex++;
				}
				blockDownSelection = true;
			}
		} else {
			blockDownSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if (!blockInSelection) {
				if (show) {
					currentEditor.decrease(currentEditor.getEditorEntries().get(selectedIndex).key);
				}
				blockInSelection = true;
			}
		} else {
			blockInSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if (!blockOutSelection) {
				if (show) {
					currentEditor.increase(currentEditor.getEditorEntries().get(selectedIndex).key);
				}
				blockOutSelection = true;
			}
		} else {
			blockOutSelection = false;
		}
		
		if (!show) return;
		
		Integer width = new Float(scene.getWidth() * DEFAULT_WIDTH_PERCENT).intValue();
		Integer height = font.getHeight(currentEditor.getTitle());
		Integer left = scene.getWidth() - width - 2*margin;
		Integer fullHeight = ((currentEditor.getEditorEntries().size() + 1) * (height + 3*margin));
		
		if (fullHeight < (scene.getHeight() * 3) / 4) {
			Integer top = (scene.getHeight() / 2) - (((currentEditor.getEditorEntries().size() + 1) * (height + 3*margin)) / 2);
			Integer centered = left + (width / 2) - (font.getWidth(currentEditor.getTitle()) / 2);

		    font.drawString(centered, top, currentEditor.getTitle(), new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));

		    ListIterator<HUDEditorEntry> iterator = currentEditor.getEditorEntries().listIterator(0);
		    while (iterator.hasNext()) {
		    	HUDEditorEntry entry = iterator.next();
		    	top = top + height + 3*margin;
		    	String currentValue = currentEditor.getValue(entry.key);
				Integer rightAligned = scene.getWidth() - 2*margin - font.getWidth(currentValue);
				height = font.getHeight(entry.label);
			    font.drawString(left, top, entry.label, new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));
			    font.drawString(rightAligned, top, currentValue, new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));
		    }
		} else {
			Integer halfHeight = fullHeight / 2;
			Integer top = (scene.getHeight() / 2) - (halfHeight / 2);
			Integer top2 = top;
    		Integer left2 = left;
    		left = 2*margin;

    		Integer centered = (width / 2) - (font.getWidth(currentEditor.getTitle()) / 2);
		    font.drawString(left + centered, top, currentEditor.getTitle(), new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));
		    font.drawString(left2 + centered, top, currentEditor.getTitle(), new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));

		    Integer rightAligned;
		    Integer nextColumn = (currentEditor.getEditorEntries().size() / 2) + 1;
		    ListIterator<HUDEditorEntry> iterator = currentEditor.getEditorEntries().listIterator(0);
		    while (iterator.hasNext()) {
		    	HUDEditorEntry entry = iterator.next();
		    	String currentValue = currentEditor.getValue(entry.key);
		    	if (iterator.previousIndex() == nextColumn) {
		    		top = top2;
		    		left = left2;
		    	}
		    	if (iterator.previousIndex() >= nextColumn) {
			    	rightAligned = scene.getWidth() - 2*margin - font.getWidth(currentValue);
		    	} else {
			    	rightAligned = 2*margin + width - font.getWidth(currentValue);
		    	}
		    	top = top + height + 3*margin;
				height = font.getHeight(entry.label);
			    font.drawString(left, top, entry.label, new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));
			    font.drawString(rightAligned, top, currentValue, new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));
		    }
			
		}

	}
	
	@Override
	public void render2() {
		if (!show) return;
		
		Integer width = new Float(scene.getWidth() * DEFAULT_WIDTH_PERCENT).intValue();
		Integer height = font.getHeight(currentEditor.getTitle());
		Integer left = scene.getWidth() - width - 2*margin;
		Integer fullHeight = ((currentEditor.getEditorEntries().size() + 1) * (height + 3*margin));
		if (fullHeight < (scene.getHeight() * 3) / 4) {
			Integer top = (scene.getHeight() / 2) - (fullHeight / 2);

			glColor4f(1.0f, 0.5f, 0.0f, 0.5f);
		    glBegin(GL_QUADS);
		    glVertex2f(left - margin, top - margin);
			glVertex2f(left + width + margin, top - margin);
			glVertex2f(left + width + margin, top + height + margin);
			glVertex2f(left - margin, top + height + margin);
		    glEnd();

		    ListIterator<HUDEditorEntry> iterator = currentEditor.getEditorEntries().listIterator(0);
		    while (iterator.hasNext()) {
		    	HUDEditorEntry entry = iterator.next();
		    	top = top + height + 3*margin;
				height = font.getHeight(entry.label);
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

		} else {
			Integer halfHeight = fullHeight / 2;
			Integer top = (scene.getHeight() / 2) - (halfHeight / 2);
			Integer top2 = top;
    		Integer left2 = left;
    		left = 2*margin;

			glColor4f(1.0f, 0.5f, 0.0f, 0.5f);
		    glBegin(GL_QUADS);
		    glVertex2f(left2 - margin, top - margin);
			glVertex2f(left2 + width + margin, top - margin);
			glVertex2f(left2 + width + margin, top + height + margin);
			glVertex2f(left2 - margin, top + height + margin);
		    glEnd();

			glColor4f(1.0f, 0.5f, 0.0f, 0.5f);
		    glBegin(GL_QUADS);
		    glVertex2f(left - margin, top - margin);
			glVertex2f(left + width + margin, top - margin);
			glVertex2f(left + width + margin, top + height + margin);
			glVertex2f(left - margin, top + height + margin);
		    glEnd();

		    Integer nextColumn = (currentEditor.getEditorEntries().size() / 2) + 1;
		    ListIterator<HUDEditorEntry> iterator = currentEditor.getEditorEntries().listIterator(0);
		    while (iterator.hasNext()) {
		    	HUDEditorEntry entry = iterator.next();
		    	if (iterator.previousIndex() == nextColumn) {
		    		top = top2;
		    		left = left2;
		    	}
		    	top = top + height + 3*margin;
				height = font.getHeight(entry.label);
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


	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setup() {
        // AWT Font in eine UnicodeFont von slick-util umwandeln
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
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.EDIT) {
			Class<? extends Object> objectClass = command.getPayLoad().getClass();
			ListIterator<Editor> iterator = editors.listIterator(0);
			while (iterator.hasNext()) {
				Editor editor = iterator.next();
				if (editor.accept(objectClass)) {
					editor.select(command.getPayLoad());
					currentEditor = editor;
					show = true;
					selectedIndex = 0;
					return;
				}
			}
		}
	}
}
