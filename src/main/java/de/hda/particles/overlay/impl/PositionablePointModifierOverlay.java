package de.hda.particles.overlay.impl;

import de.hda.particles.overlay.TextOverlay;
import java.awt.Font;
import java.util.ListIterator;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.modifier.PositionablePointModifier;

public class PositionablePointModifierOverlay extends AbstractTextOverlay implements TextOverlay {

	private ParticleModifier modifier;
	private PositionablePointModifier ppModifier;
	
	private final Logger logger = LoggerFactory.getLogger(PositionablePointModifierOverlay.class);
	
	public PositionablePointModifierOverlay() {
		prepend = "\n\n";
	}

	@Override
	public void update() {
		if (!visible) return;
		ListIterator<ParticleModifier> pIterator = scene.getParticleSystem().getParticleModifiers().listIterator(0);
		while (pIterator.hasNext()) {
			modifier = pIterator.next();
			if (PositionablePointModifier.class.isInstance(modifier)) {
				ppModifier = (PositionablePointModifier) modifier;
				render(ppModifier.getX(), ppModifier.getY(), ppModifier.getZ(), String.format("X: %6.2f  Y: %6.2f  Z: %6.2f", ppModifier.getX(), ppModifier.getY(), ppModifier.getZ()), 300.0f, false);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setup() {
        // AWT Font in eine UnicodeFont von slick-util umwandeln
        font = new UnicodeFont(new Font("Arial", Font.BOLD, 10));
        font.getEffects().add(new ColorEffect(new java.awt.Color(1.0f, 1.0f, 1.0f)));
        font.addAsciiGlyphs();
        try {
           font.loadGlyphs();
        } catch (SlickException e) {
        	logger.error("could not load font glyphs", e);
        }
	}

}
