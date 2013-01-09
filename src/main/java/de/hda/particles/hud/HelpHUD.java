package de.hda.particles.hud;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.scene.Scene;

public class HelpHUD extends AbstractHUD implements HUD {

	private final static Integer MARGIN = 80;

	private Boolean blockHelpSelection = true;
	private UnicodeFont smallFont;

    private final Logger logger = LoggerFactory.getLogger(HelpHUD.class);

    public HelpHUD() {}

	public HelpHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void update() {
		if (blockHelpSelection) {
			help();
		}
	}
	
	@Override
	public void input() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			blockHelpSelection = false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setup() {
        // AWT Font in eine UnicodeFont von slick-util umwandeln
        font = new UnicodeFont(new Font("Arial", Font.BOLD, 30));
        font.getEffects().add(new ColorEffect(new java.awt.Color(0.8f, 0.2f, 0.8f)));
        font.addAsciiGlyphs();
        smallFont = new UnicodeFont(new Font("Arial", Font.BOLD, 16));
        smallFont.getEffects().add(new ColorEffect(new java.awt.Color(0.8f, 0.8f, 0.2f)));
        smallFont.addAsciiGlyphs();
        try {
           font.loadGlyphs();
           smallFont.loadGlyphs();
        } catch (SlickException e) {
        	logger.error("could not load font glyphs", e);
        }
	}
	
	@Override
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.HELP) {
			help();
		}
	}

	public void help() {
		blockHelpSelection = true;
	    String header = "Particle System & Editor";
	    Integer height = font.getHeight(header);
		glColor4f(0.8f, 0.3f, 0.8f, 0.15f);
	    glBegin(GL_QUADS);
	    glVertex2f(MARGIN, MARGIN);
		glVertex2f(scene.getWidth() - MARGIN, MARGIN);
		glVertex2f(scene.getWidth() - MARGIN, MARGIN + 2 * height);
		glVertex2f(MARGIN, MARGIN + 2 * height);
	    glEnd();
		glColor4f(0.3f, 0.3f, 0.8f, 0.15f);
	    glBegin(GL_QUADS);
	    glVertex2f(MARGIN, MARGIN);
		glVertex2f(scene.getWidth() - MARGIN, MARGIN);
		glVertex2f(scene.getWidth() - MARGIN, scene.getHeight() - MARGIN);
		glVertex2f(MARGIN, scene.getHeight() - MARGIN);
	    glEnd();
	    font.drawString(
	    	(scene.getWidth() / 2) - (font.getWidth(header) / 2),
	    	MARGIN + (MARGIN / 2) - (height / 2),
	    	header,
	    	new org.newdawn.slick.Color(0.8f, 0.8f, 0.8f, 1.0f)
	    );
	    font.drawString(
    		MARGIN + (MARGIN / 2),
	    	3 * MARGIN,
	    	"Keys",
	    	new org.newdawn.slick.Color(0.8f, 0.2f, 0.8f, 1.0f)
	    );
	    smallFont.drawString(
	    	MARGIN + (MARGIN / 2),
	    	3 * MARGIN + (MARGIN / 2),
	    	"[H]\n[E]\n[M]\n[P]\n\n[ESC]\n[UP]\n[DOWN]\n[LEFT]\n[RIGHT]\n[HOME]\n[END]",
	    	new org.newdawn.slick.Color(0.8f, 0.8f, 0.8f, 1.0f)
	    );
	    smallFont.drawString(
	    	3 * MARGIN,
	    	3 * MARGIN + (MARGIN / 2),
	    	"HUD on/off\nEmitters on/off\nModifiers on/off\nPause on/off\n\nEnter Menu / Exit Menu\nSelect Previous Menu Item\nSelect Next Menu Item\nnDecrease Value\nIncrease Value\nSelect Min Value\nSelect Max Value",
	    	new org.newdawn.slick.Color(0.8f, 0.8f, 0.8f, 1.0f)
	    );
	    font.drawString(
	    	(scene.getWidth() / 2) + (MARGIN / 2),
	    	3 * MARGIN,
	    	"Mouse",
	    	new org.newdawn.slick.Color(0.8f, 0.2f, 0.8f, 1.0f)
	    );
	    smallFont.drawString(
	    	(scene.getWidth() / 2) + (MARGIN / 2),
	    	3 * MARGIN + (MARGIN / 2),
	    	"[LEFT BUTTON]\n[SCROLL WHEEL]",
	    	new org.newdawn.slick.Color(0.8f, 0.8f, 0.8f, 1.0f)
	    );
	    smallFont.drawString(
	    	(scene.getWidth() / 2) + 3 * MARGIN,
	    	3 * MARGIN + (MARGIN / 2),
	    	"Select/Move Objects\nZoom in/out",
	    	new org.newdawn.slick.Color(0.8f, 0.8f, 0.8f, 1.0f)
	    );
	    font.drawString(
	    	(scene.getWidth() / 2) + (MARGIN / 2),
	    	4 * MARGIN + (MARGIN / 2),
	    	"Movement",
	    	new org.newdawn.slick.Color(0.8f, 0.2f, 0.8f, 1.0f)
	    );
	    smallFont.drawString(
	    	(scene.getWidth() / 2) + (MARGIN / 2),
	    	5 * MARGIN,
	    	"[W]\n[A]\n[S]\n[D]",
	    	new org.newdawn.slick.Color(0.8f, 0.8f, 0.8f, 1.0f)
	    );
	    smallFont.drawString(
	    	(scene.getWidth() / 2) + 3 * MARGIN,
	    	5 * MARGIN,
	    	"Forward\nLeft\nBackward\nRight",
	    	new org.newdawn.slick.Color(0.8f, 0.8f, 0.8f, 1.0f)
	    );
	}

}
