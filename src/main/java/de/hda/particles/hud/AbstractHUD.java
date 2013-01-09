package de.hda.particles.hud;

import java.awt.Font;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.renderer.AbstractRenderer;
import de.hda.particles.scene.Scene;

public abstract class AbstractHUD extends AbstractRenderer implements HUD {

	protected UnicodeFont font;

    private final Logger logger = LoggerFactory.getLogger(AbstractHUD.class);

    public AbstractHUD() {
    }

    public AbstractHUD(Scene scene) {
		this.scene = scene;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setup() {
        // AWT Font in eine UnicodeFont von slick-util umwandeln
        font = new UnicodeFont(new Font("Arial", Font.BOLD, 12));
        font.getEffects().add(new ColorEffect(new java.awt.Color(0.8f, 0.2f, 0.8f)));
        font.addAsciiGlyphs();
        try {
           font.loadGlyphs();
        } catch (SlickException e) {
        	logger.error("could not load font glyphs", e);
        }
	}

	@Override
	public void executeCommand(HUDCommand command) {
	}
	
	@Override
	public void render1() {
	}

	@Override
	public void render2() {
	}

	@Override
	public void input() {
	}

}
