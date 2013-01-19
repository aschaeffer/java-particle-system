package de.hda.particles.hud;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.editor.Editor;
import de.hda.particles.editor.HUDEditorEntry;
import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.scene.Scene;

/**
 * The EditorHUD provides a fully featured editor for
 * numeric (integer and floating point) values.
 * 
 * @author aschaeffer
 *
 */
public class EditorHUD extends AbstractHUD implements HUD {

	private final static Integer KEYPRESS_REPEAT_THRESHOLD = 10;
	private final static Float DEFAULT_WIDTH_PERCENT = 0.2f;
	private final static Integer DEFAULT_MARGIN_BIG = 10;
	private final static Integer DEFAULT_MARGIN_SMALL = 5;
	private final static Integer DEFAULT_FONT_SIZE_BIG = 13;
	private final static Integer DEFAULT_FONT_SIZE_SMALL = 11;

	private Boolean show = false;

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
	private Integer lastKeyCode = -1;

	private List<HUDEditorEntry> editorEntries;
	private Integer width = 0;
	private Integer height = 0;
	private Integer left = 0;
	private Integer fullHeight = 0;
	private Integer top = 0;
	private Integer centered = 0;
	private Integer halfHeight = 0;
	private Integer top2 = 0;
	private Integer left2 = 0;
	private Integer rightAligned = 0;
	private Integer nextColumn = 0;
	private String currentValue = "";
	private Integer currentMargin = DEFAULT_MARGIN_BIG;

	private UnicodeFont bigFont;
	private UnicodeFont smallFont;

	private final Logger logger = LoggerFactory.getLogger(EditorHUD.class);

	public EditorHUD() {
	}

	public EditorHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void update() {
		if (currentEditor == null || editorEntries == null || !show) return;
		
		if (editorEntries.size() < 20) {
			currentMargin = DEFAULT_MARGIN_BIG;
			font = bigFont;
		} else {
			currentMargin = DEFAULT_MARGIN_SMALL;
			font = smallFont;
		}
		
		width = new Float(scene.getWidth() * DEFAULT_WIDTH_PERCENT).intValue();
		height = font.getHeight(currentEditor.getTitle());
		left = scene.getWidth() - width - 2*currentMargin;
		fullHeight = ((editorEntries.size() + 1) * (height + 3*currentMargin));
		
		if (fullHeight < (scene.getHeight() * 3) / 4) {
			top = (scene.getHeight() / 2) - (((editorEntries.size() + 1) * (height + 3*currentMargin)) / 2);
			centered = left + (width / 2) - (font.getWidth(currentEditor.getTitle()) / 2);

		    font.drawString(centered, top, currentEditor.getTitle(), new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));

		    ListIterator<HUDEditorEntry> iterator = editorEntries.listIterator(0);
		    while (iterator.hasNext()) {
		    	HUDEditorEntry entry = iterator.next();
		    	top = top + height + 3*currentMargin;
		    	currentValue = currentEditor.getValue(entry.key);
				rightAligned = scene.getWidth() - 2*currentMargin - font.getWidth(currentValue);
				height = font.getHeight(entry.label);
			    font.drawString(left, top, entry.label, new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));
			    font.drawString(rightAligned, top, currentValue, new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));
		    }
		} else {
			halfHeight = fullHeight / 2;
			top = (scene.getHeight() / 2) - (halfHeight / 2);
			top2 = top;
    		left2 = left;
    		left = 2*currentMargin;

    		centered = (width / 2) - (font.getWidth(currentEditor.getTitle()) / 2);
		    font.drawString(left + centered, top, currentEditor.getTitle(), new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));
		    font.drawString(left2 + centered, top, currentEditor.getTitle(), new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));

		    nextColumn = (editorEntries.size() / 2) + 1;

		    ListIterator<HUDEditorEntry> iterator = editorEntries.listIterator(0);
		    while (iterator.hasNext()) {
		    	HUDEditorEntry entry = iterator.next();
		    	if (entry == null) continue;
		    	if (currentEditor == null) break;
		    	currentValue = currentEditor.getValue(entry.key);
		    	if (iterator.previousIndex() == nextColumn - 1) {
		    		top = top2;
		    		left = left2;
		    	}
		    	if (iterator.previousIndex() >= nextColumn - 1) {
			    	rightAligned = scene.getWidth() - 2*currentMargin - font.getWidth(currentValue);
		    	} else {
			    	rightAligned = 2*currentMargin + width - font.getWidth(currentValue);
		    	}
		    	top = top + height + 3*currentMargin;
				height = font.getHeight(entry.label);
			    font.drawString(left, top, entry.label, new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));
			    font.drawString(rightAligned, top, currentValue, new org.newdawn.slick.Color(0.0f, 0.0f, 0.0f, 1.0f));
		    }
			
		}

	}
	
	@Override
	public void input() {
		if (currentEditor == null) return;
		editorEntries = currentEditor.getEditorEntries();
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
		if (lastKeyCode >= 0 && Keyboard.isKeyDown(lastKeyCode)) return;
		HUDEditorEntry currentEntry = editorEntries.get(selectedIndex);
		Integer nextKeyCode = -1;
		Iterator<Integer> iterator = currentEntry.keyCommands.keySet().iterator();
		while (iterator.hasNext()) {
			Integer keyCode = iterator.next();
			if (Keyboard.isKeyDown(keyCode) && lastKeyCode != keyCode) {
				HUDCommand command = currentEntry.keyCommands.get(keyCode);
				logger.debug("key " + keyCode.toString() + " is down: " + command.getType().name());
				scene.getHudManager().addCommand(command);
				nextKeyCode = keyCode;
			}
		}
		lastKeyCode = nextKeyCode;
	}
	
	@Override
	public void render2() {
		if (!show || currentEditor == null || editorEntries == null) return;
		
		width = new Float(scene.getWidth() * DEFAULT_WIDTH_PERCENT).intValue();
		height = font.getHeight(currentEditor.getTitle());
		left = scene.getWidth() - width - 2*currentMargin;
		fullHeight = ((editorEntries.size() + 1) * (height + 3*currentMargin));
		if (fullHeight < (scene.getHeight() * 3) / 4) {
			top = (scene.getHeight() / 2) - (fullHeight / 2);

			glColor4f(1.0f, 0.5f, 0.0f, 0.5f);
		    glBegin(GL_QUADS);
		    glVertex2f(left - currentMargin, top - currentMargin);
			glVertex2f(left + width + currentMargin, top - currentMargin);
			glVertex2f(left + width + currentMargin, top + height + currentMargin);
			glVertex2f(left - currentMargin, top + height + currentMargin);
		    glEnd();

		    ListIterator<HUDEditorEntry> iterator = editorEntries.listIterator(0);
		    while (iterator.hasNext()) {
		    	HUDEditorEntry entry = iterator.next();
		    	top = top + height + 3*currentMargin;
				height = font.getHeight(entry.label);
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
		} else {
			halfHeight = fullHeight / 2;
			top = (scene.getHeight() / 2) - (halfHeight / 2);
			top2 = top;
    		left2 = left;
    		left = 2*currentMargin;

			glColor4f(1.0f, 0.5f, 0.0f, 0.5f);
		    glBegin(GL_QUADS);
		    glVertex2f(left2 - currentMargin, top - currentMargin);
			glVertex2f(left2 + width + currentMargin, top - currentMargin);
			glVertex2f(left2 + width + currentMargin, top + height + currentMargin);
			glVertex2f(left2 - currentMargin, top + height + currentMargin);
		    glEnd();

			glColor4f(1.0f, 0.5f, 0.0f, 0.5f);
		    glBegin(GL_QUADS);
		    glVertex2f(left - currentMargin, top - currentMargin);
			glVertex2f(left + width + currentMargin, top - currentMargin);
			glVertex2f(left + width + currentMargin, top + height + currentMargin);
			glVertex2f(left - currentMargin, top + height + currentMargin);
		    glEnd();

		    nextColumn = (editorEntries.size() / 2) + 1;
		    ListIterator<HUDEditorEntry> iterator = editorEntries.listIterator(0);
		    while (iterator.hasNext()) {
		    	HUDEditorEntry entry = iterator.next();
		    	if (iterator.previousIndex() == nextColumn - 1) {
		    		top = top2;
		    		left = left2;
		    	}
		    	top = top + height + 3*currentMargin;
				height = font.getHeight(entry.label);
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
		if (command.getType().equals(HUDCommandTypes.EDIT)) {
			if (command.getPayLoad() instanceof Editor) { // Editor given, search for modifier/...
				Editor editor = (Editor) command.getPayLoad();
				logger.debug("activate editor: " + editor.getTitle());
				if (command.getPayLoad2() == null) {
					logger.debug("search for: " + editor.getAcceptable().getSimpleName());
					ListIterator<ParticleModifier> iterator = scene.getParticleSystem().getParticleModifiers().listIterator(0);
					while(iterator.hasNext()) {
						ParticleModifier modifier = iterator.next();
						logger.debug(modifier.getClass().getSimpleName());
						if (modifier.getClass().equals(editor.getAcceptable())) {
							editor.select(modifier);
							currentEditor = editor;
							show = true;
							selectedIndex = 0;
							return;
						}
					}
				} else {
					editor.select(command.getPayLoad2());
					currentEditor = editor;
					show = true;
					selectedIndex = 0;
					return;
				}
				logger.error("A: No modifier for editor found: " + editor.getAcceptable().getSimpleName());
			} else if (command.getPayLoad() instanceof Class) {
				Class<? extends Object> editorClass = (Class<? extends Object>) command.getPayLoad();
				ListIterator<Editor> iterator = scene.getEditorManager().getEditors().listIterator(0);
				while (iterator.hasNext()) {
					Editor editor = iterator.next();
					if (editor.getClass().equals(editorClass)) {
						editor.select(command.getPayLoad2());
						currentEditor = editor;
						show = true;
						selectedIndex = 0;
						return;
					}
				}
				logger.error("B: No editor found: " + editorClass.getSimpleName());
			} else { // Subject given, search for editor
				Class<? extends Object> objectClass = command.getPayLoad().getClass();
				ListIterator<Editor> iterator = scene.getEditorManager().getEditors().listIterator(0);
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
				logger.error("C: No editor found: " + objectClass.getSimpleName());
			}
		} else if (command.getType().equals(HUDCommandTypes.EDIT_VALUE)) {
			Object value = currentEditor.getObject(currentEditor.getEditorEntries().get(selectedIndex).key);
			Class<? extends Object> objectClass = value.getClass();
			ListIterator<Editor> iterator = scene.getEditorManager().getEditors().listIterator(0);
			while (iterator.hasNext()) {
				Editor editor = iterator.next();
				if (editor.accept(objectClass)) {
					editor.select(value);
					currentEditor = editor;
					show = true;
					selectedIndex = 0;
					return;
				}
			}
			logger.error("D: No editor found: " + objectClass.getSimpleName());
			selectedIndex = 0;
		} else if (command.getType().equals(HUDCommandTypes.EDIT_DONE)) {
			hideEditor();
		}
	}
	
	private void hideEditor() {
		show = false;
		currentEditor = null;
	}
}
