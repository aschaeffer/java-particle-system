package de.hda.particles.overlay.impl;

import de.hda.particles.overlay.TextOverlay;
import java.awt.Font;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AxisOverlay extends AbstractTextOverlay implements TextOverlay {

	private final Logger logger = LoggerFactory.getLogger(AxisOverlay.class);

	@Override
	public void update() {
		if (!visible) return;
		render(scene.getCameraManager().getPosition().x, 0.0f, 0.0f, "X", 0.0f, false);
		render(0.0f, scene.getCameraManager().getPosition().y, 0.0f, "Y", 0.0f, false);
		render(0.0f, 0.0f, scene.getCameraManager().getPosition().z, "Z", 0.0f, false);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setup() {
        // AWT Font in eine UnicodeFont von slick-util umwandeln
        font = new UnicodeFont(new Font("Arial", Font.BOLD, 24));
        font.getEffects().add(new ColorEffect(new java.awt.Color(0.6f, 0.6f, 0.6f)));
        font.addAsciiGlyphs();
        try {
           font.loadGlyphs();
        } catch (SlickException e) {
        	logger.error("could not load font glyphs", e);
        }
	}

}
