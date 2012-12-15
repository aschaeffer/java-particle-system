package de.hda.particles.overlay;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.scene.Scene;

public abstract class AbstractTextOverlay implements TextOverlay {

	protected Scene scene;
	protected Boolean visible = true;
	protected UnicodeFont font;

	private final Logger logger = LoggerFactory.getLogger(AbstractTextOverlay.class);

	public void render(Vector3f position, String title, Float maxDist, Boolean debug) {
		render(position.x, position.y, position.z, title, maxDist, debug);
	}

	public void render(float objX, float objY, float objZ, String title, Float maxDist, Boolean debug) {
		if (maxDist > 0.0f) {
			Vector3f cameraPosition = scene.getCameraManager().getPosition();
			Vector3f cameraToObject = new Vector3f(objX - cameraPosition.x, objY - cameraPosition.y, objZ - cameraPosition.z);
			if (Math.abs(cameraToObject.length()) > maxDist) return;
		}
        FloatBuffer result = ByteBuffer.allocateDirect(3*8).order(ByteOrder.nativeOrder()).asFloatBuffer();
        GLU.gluProject(objX, objY, objZ, scene.getRendererManager().getModelViewMatrix(), scene.getRendererManager().getProjectionMatrix(), scene.getRendererManager().getViewport(), result);
        result.rewind();
        Float x = result.get();
        Float y = result.get();
        Float y2 = scene.getHeight() - y;
        Float z = result.get();
        if (x == null || y == null || z == null || title == null || z > 1.0f) return;
        if (!debug) {
            font.drawString(x, y2, title);
        } else {
            font.drawString(x, y2, title + "\nx:"+x+"\ny:"+y+"\ny2:"+y2+"\nz:"+z);
        }
	}

	@Override
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	@Override
	public void setVisible(Boolean visibility) {
		this.visible = visibility;
	}

	@Override
	public Boolean isVisible() {
		return visible;
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
	public void destroy() {
	}

	@Override
	public Boolean isFinished() {
		return false;
	}
	
}
