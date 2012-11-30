package de.hda.particles.hud;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

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
	private final static Integer lifetime = 2000;
	private final static Integer fadeLength = 1000;

	private String message = "";
	private long endFrame = 0;

	private String notice = "";
	private long noticeEndFrame = 0;
	private UnicodeFont noticeFont;

    private final Logger logger = LoggerFactory.getLogger(MessageHUD.class);

	public MessageHUD() {}

	public MessageHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void update() {
		long frameTimeStamp = Sys.getTime();
		if (frameTimeStamp <= endFrame) {
			Float fade = 1.0f;
			if (frameTimeStamp > (endFrame - fadeLength)) {
				fade = 1.0f - ((frameTimeStamp - (endFrame - fadeLength)) / (1.0f * fadeLength));
			}
			
			Integer left = (scene.getWidth() / 2) - (font.getWidth(message) / 2);
			Integer top = (scene.getHeight() / 2) - (font.getHeight(message) / 2);

		    font.drawString(left, top, message, new org.newdawn.slick.Color(0.8f, 0.8f, 0.8f, fade));
		}
		if (frameTimeStamp <= noticeEndFrame) {
			Float fade = 1.0f;
			if (frameTimeStamp > (noticeEndFrame - fadeLength)) {
				fade = 1.0f - ((frameTimeStamp - (noticeEndFrame - fadeLength)) / (1.0f * fadeLength));
			}
			
			Integer left = (scene.getWidth() / 2) - (noticeFont.getWidth(notice) / 2);
			Integer top = scene.getHeight() - 3*(noticeFont.getHeight(notice) + NOTICE_MARGIN);

			noticeFont.drawString(left, top, notice, new org.newdawn.slick.Color(0.8f, 0.8f, 0.8f, fade));
		}
	}
	
	@Override
	public void render2() {
		long frameTimeStamp = Sys.getTime();
		if (frameTimeStamp <= endFrame) {
			Float fade = 1.0f;
			if (frameTimeStamp > (endFrame - fadeLength)) {
				fade = 1.0f - ((frameTimeStamp - (endFrame - fadeLength)) / (1.0f * fadeLength));
			}
			
			Integer width = font.getWidth(message);
			Integer height = font.getHeight(message);
			Integer left = (scene.getWidth() / 2) - (width / 2);
			Integer top = (scene.getHeight() / 2) - (height / 2);

			glColor4f(0.0f, 0.5f, 1.0f, fade / 4.0f);
		    glBegin(GL_QUADS);
		    glVertex2f(left - MESSAGE_MARGIN, top - MESSAGE_MARGIN);
			glVertex2f(left + width + MESSAGE_MARGIN, top - MESSAGE_MARGIN);
			glVertex2f(left + width + MESSAGE_MARGIN, top + height + MESSAGE_MARGIN);
			glVertex2f(left - MESSAGE_MARGIN, top + height + MESSAGE_MARGIN);
		    glEnd();
		}
		if (frameTimeStamp <= noticeEndFrame) {
			Float fade = 1.0f;
			if (frameTimeStamp > (noticeEndFrame - fadeLength)) {
				fade = 1.0f - ((frameTimeStamp - (noticeEndFrame - fadeLength)) / (1.0f * fadeLength));
			}
			
			Integer width = noticeFont.getWidth(notice);
			Integer height = noticeFont.getHeight(notice);
			Integer left = (scene.getWidth() / 2) - (width / 2);
			Integer top = scene.getHeight() - 3*(height + NOTICE_MARGIN);

			glColor4f(1.0f, 1.0f, 0.2f, fade / 4.0f);
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
			endFrame = Sys.getTime() + lifetime + fadeLength;
			logger.debug("show message (" + message + ") till " + endFrame);
		} else if (command.getType() == HUDCommandTypes.NOTICE) {
			notice = (String) command.getPayLoad();
			noticeEndFrame = Sys.getTime() + lifetime + fadeLength;
			logger.debug("show notice (" + notice + ") till " + endFrame);
		}
	}

}
