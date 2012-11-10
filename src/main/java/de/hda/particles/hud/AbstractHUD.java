package de.hda.particles.hud;

import java.awt.Font;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public abstract class AbstractHUD implements HUD {

	protected Integer width = 800;
	protected Integer height = 600;

	protected UnicodeFont font;

	public AbstractHUD(Integer width, Integer height) {
		this.width = width;
		this.height = height;
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
           e.printStackTrace();
        }
	}

	@Override
	public void destroy() {
	}

	@Override
	public Boolean isFinished() {
		return false;
	}

	@Override
	public void setWidth(Integer width) {
		this.width = width;
	}

	@Override
	public void setHeight(Integer height) {
		this.height = height;
	}

}
