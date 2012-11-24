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

	private final static Integer KEYPRESS_REPEAT_THRESHOLD = 15;
	private final static Float DEFAULT_WIDTH_PERCENT = 0.2f;
	private final static Integer margin = 10;

	private Boolean show = false;

	protected List<Editor> editors;
	protected Editor currentEditor;
	protected Integer selectedIndex = 0;

	private Boolean blockEscSelection = false;
	private Integer blockUpSelection = 0;
	private Integer blockDownSelection = 0;
	private Integer blockDecreaseSelection = 0;
	private Integer blockIncreaseSelection = 0;
	private Boolean blockDecreaseMinSelection = false;
	private Boolean blockIncreaseMaxSelection = false;
	private Boolean blockRemoveSelection = false;
	
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
		if (currentEditor == null) return;
		List<HUDEditorEntry> editorEntries = currentEditor.getEditorEntries();
		Keyboard.next();
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			if (!blockEscSelection) {
				if (show) {
					hideEditor();
					scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.EDIT_DONE));
				}
				blockEscSelection = true;
			}
		} else {
			blockEscSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			if (show && (blockUpSelection == 0 || blockUpSelection > KEYPRESS_REPEAT_THRESHOLD)) {
				if (selectedIndex > 0) {
					selectedIndex--;
				} else {
					selectedIndex = editorEntries.size() - 1;
				}
			}
			blockUpSelection++;
		} else {
			blockUpSelection = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			if (show && (blockDownSelection == 0 || blockDownSelection > KEYPRESS_REPEAT_THRESHOLD)) {
				if (selectedIndex + 1 < editorEntries.size()) {
					selectedIndex++;
				} else {
					selectedIndex = 0;
				}
			}
			blockDownSelection++;
		} else {
			blockDownSelection = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if (show && (blockDecreaseSelection == 0 || blockDecreaseSelection > KEYPRESS_REPEAT_THRESHOLD)) {
				currentEditor.decrease(editorEntries.get(selectedIndex).key);
				if (blockDecreaseSelection > KEYPRESS_REPEAT_THRESHOLD * 5)
					currentEditor.decrease(editorEntries.get(selectedIndex).key);
			}
			blockDecreaseSelection++;
		} else {
			blockDecreaseSelection = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if (show && (blockIncreaseSelection == 0 || blockIncreaseSelection > KEYPRESS_REPEAT_THRESHOLD)) {
				currentEditor.increase(editorEntries.get(selectedIndex).key);
				if (blockIncreaseSelection > KEYPRESS_REPEAT_THRESHOLD * 5)
					currentEditor.increase(editorEntries.get(selectedIndex).key);
			}
			blockIncreaseSelection++;
		} else {
			blockIncreaseSelection = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_HOME)) {
			if (show && !blockDecreaseMinSelection) {
				currentEditor.setMin(editorEntries.get(selectedIndex).key);
			}
			blockDecreaseMinSelection = true;
		} else {
			blockDecreaseMinSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_END)) {
			if (show && !blockIncreaseMaxSelection) {
				currentEditor.setMax(editorEntries.get(selectedIndex).key);
			}
			blockIncreaseMaxSelection = true;
		} else {
			blockIncreaseMaxSelection = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DELETE)) {
			if (!blockRemoveSelection) {
				hideEditor();
				blockRemoveSelection = true;
			}
		} else {
			blockRemoveSelection = false;
		}
		
		if (!show) return;
		
		Integer width = new Float(scene.getWidth() * DEFAULT_WIDTH_PERCENT).intValue();
		Integer height = font.getHeight(currentEditor.getTitle());
		Integer left = scene.getWidth() - width - 2*margin;
		Integer fullHeight = ((editorEntries.size() + 1) * (height + 3*margin));
		
		if (fullHeight < (scene.getHeight() * 3) / 4) {
			Integer top = (scene.getHeight() / 2) - (((editorEntries.size() + 1) * (height + 3*margin)) / 2);
			Integer centered = left + (width / 2) - (font.getWidth(currentEditor.getTitle()) / 2);

		    font.drawString(centered, top, currentEditor.getTitle(), new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));

		    ListIterator<HUDEditorEntry> iterator = editorEntries.listIterator(0);
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
		    Integer nextColumn = (editorEntries.size() / 2) + 1;
		    ListIterator<HUDEditorEntry> iterator = editorEntries.listIterator(0);
		    while (iterator.hasNext()) {
		    	HUDEditorEntry entry = iterator.next();
		    	String currentValue = currentEditor.getValue(entry.key);
		    	if (iterator.previousIndex() == nextColumn - 1) {
		    		top = top2;
		    		left = left2;
		    	}
		    	if (iterator.previousIndex() >= nextColumn - 1) {
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
		if (!show || currentEditor == null) return;
		List<HUDEditorEntry> editorEntries = currentEditor.getEditorEntries();
		
		Integer width = new Float(scene.getWidth() * DEFAULT_WIDTH_PERCENT).intValue();
		Integer height = font.getHeight(currentEditor.getTitle());
		Integer left = scene.getWidth() - width - 2*margin;
		Integer fullHeight = ((editorEntries.size() + 1) * (height + 3*margin));
		if (fullHeight < (scene.getHeight() * 3) / 4) {
			Integer top = (scene.getHeight() / 2) - (fullHeight / 2);

			glColor4f(1.0f, 0.5f, 0.0f, 0.5f);
		    glBegin(GL_QUADS);
		    glVertex2f(left - margin, top - margin);
			glVertex2f(left + width + margin, top - margin);
			glVertex2f(left + width + margin, top + height + margin);
			glVertex2f(left - margin, top + height + margin);
		    glEnd();

		    ListIterator<HUDEditorEntry> iterator = editorEntries.listIterator(0);
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

		    Integer nextColumn = (editorEntries.size() / 2) + 1;
		    ListIterator<HUDEditorEntry> iterator = editorEntries.listIterator(0);
		    while (iterator.hasNext()) {
		    	HUDEditorEntry entry = iterator.next();
		    	if (iterator.previousIndex() == nextColumn - 1) {
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
		if (command.getType().equals(HUDCommandTypes.EDIT)) {
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
		} else if (command.getType().equals(HUDCommandTypes.EDIT_DONE)) {
			hideEditor();
		}
	}
	
	private void hideEditor() {
		show = false;
		currentEditor = null;
	}
}
