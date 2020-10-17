package de.hda.particles.hud.impl;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import de.hda.particles.hud.HUD;
import de.hda.particles.hud.HUDCommandListener;
import java.awt.Font;

import org.lwjgl.Sys;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.scene.Scene;

public class MessageHUD extends AbstractHUD implements HUD, HUDCommandListener {

	private final static Integer MESSAGE_MARGIN = 20;
	private final static Integer NOTICE_MARGIN = 8;
	private final static Integer LIFETIME = 2000;
	private final static Integer FADE_LENGTH = 1000;

	private String message = "";
	private long endFrame = 0;

	private String notice = "";
	private long noticeEndFrame = 0;
	private UnicodeFont noticeFont;
	
	private Float fade;
	private Integer left;
	private Integer top;
	private Integer width;
	private Integer height;
	private Long frameTimeStamp;
	

    private final Logger logger = LoggerFactory.getLogger(MessageHUD.class);

	public MessageHUD() {}

	public MessageHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void update() {
		frameTimeStamp = Sys.getTime();
		if (frameTimeStamp <= endFrame) {
			fade = 1.0f;
			if (frameTimeStamp > (endFrame - FADE_LENGTH)) {
				fade = 1.0f - ((frameTimeStamp - (endFrame - FADE_LENGTH)) / (1.0f * FADE_LENGTH));
			}
			
			left = (scene.getWidth() / 2) - (font.getWidth(message) / 2);
			top = (scene.getHeight() / 2) - (font.getHeight(message) / 2);

		    font.drawString(left, top, message, new org.newdawn.slick.Color(0.8f, 0.8f, 0.8f, fade));
		}
		if (frameTimeStamp <= noticeEndFrame) {
			fade = 1.0f;
			if (frameTimeStamp > (noticeEndFrame - FADE_LENGTH)) {
				fade = 1.0f - ((frameTimeStamp - (noticeEndFrame - FADE_LENGTH)) / (1.0f * FADE_LENGTH));
			}
			
			left = (scene.getWidth() / 2) - (noticeFont.getWidth(notice) / 2);
			top = scene.getHeight() - 3*(noticeFont.getHeight(notice) + NOTICE_MARGIN);

			noticeFont.drawString(left, top, notice, new org.newdawn.slick.Color(0.2f, 0.2f, 0.2f, fade));
		}
	}
	
	@Override
	public void render2() {
		frameTimeStamp = Sys.getTime();
		if (frameTimeStamp <= endFrame) {
			fade = 1.0f;
			if (frameTimeStamp > (endFrame - FADE_LENGTH)) {
				fade = 1.0f - ((frameTimeStamp - (endFrame - FADE_LENGTH)) / (1.0f * FADE_LENGTH));
			}
			
			width = font.getWidth(message);
			height = font.getHeight(message);
			left = (scene.getWidth() / 2) - (width / 2);
			top = (scene.getHeight() / 2) - (height / 2);

			glColor4f(0.0f, 0.5f, 1.0f, fade / 4.0f);
		    glBegin(GL_QUADS);
		    glVertex2f(left - MESSAGE_MARGIN, top - MESSAGE_MARGIN);
			glVertex2f(left + width + MESSAGE_MARGIN, top - MESSAGE_MARGIN);
			glVertex2f(left + width + MESSAGE_MARGIN, top + height + MESSAGE_MARGIN);
			glVertex2f(left - MESSAGE_MARGIN, top + height + MESSAGE_MARGIN);
		    glEnd();
		}
		if (frameTimeStamp <= noticeEndFrame) {
			fade = 1.0f;
			if (frameTimeStamp > (noticeEndFrame - FADE_LENGTH)) {
				fade = 1.0f - ((frameTimeStamp - (noticeEndFrame - FADE_LENGTH)) / (1.0f * FADE_LENGTH));
			}
			
			width = noticeFont.getWidth(notice);
			height = noticeFont.getHeight(notice);
			left = (scene.getWidth() / 2) - (width / 2);
			top = scene.getHeight() - 3*(height + NOTICE_MARGIN);

			glColor4f(0.2f, 1.0f, 1.0f, fade / 4.0f);
		    glBegin(GL_QUADS);
		    glVertex2f(left - NOTICE_MARGIN, top - NOTICE_MARGIN);
			glVertex2f(left + width + NOTICE_MARGIN, top - NOTICE_MARGIN);
			glVertex2f(left + width + NOTICE_MARGIN, top + height + NOTICE_MARGIN);
			glVertex2f(left - NOTICE_MARGIN, top + height + NOTICE_MARGIN);
		    glEnd();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setup() {
        // AWT Font in eine UnicodeFont von slick-util umwandeln
        font = new UnicodeFont(new Font("Arial", Font.BOLD, 30));
        font.getEffects().add(new ColorEffect(new java.awt.Color(0.8f, 0.8f, 0.8f)));
        font.addAsciiGlyphs();
        noticeFont = new UnicodeFont(new Font("Arial", Font.BOLD, 18));
        noticeFont.getEffects().add(new ColorEffect(new java.awt.Color(0.8f, 0.8f, 0.8f)));
        noticeFont.addAsciiGlyphs();
        try {
           font.loadGlyphs();
           noticeFont.loadGlyphs();
        } catch (SlickException e) {
        	logger.error("could not load font glyphs", e);
        }
	}
	
	@Override
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.MESSAGE) {
			message = (String) command.getPayLoad();
			endFrame = Sys.getTime() + LIFETIME + FADE_LENGTH;
			logger.debug("show message (" + message + ") till " + endFrame);
		} else if (command.getType() == HUDCommandTypes.NOTICE) {
			notice = (String) command.getPayLoad();
			noticeEndFrame = Sys.getTime() + LIFETIME + FADE_LENGTH;
			logger.debug("show notice (" + notice + ") till " + noticeEndFrame);
		}
	}

}
