package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.UnicodeFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.scene.Scene;

public abstract class AbstractRenderer implements Renderer {

	protected Scene scene;
	protected Boolean visible = true;
	protected UnicodeFont font;

	@Override
	public void setup() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public Boolean isFinished() {
		return false;
	}
	
	@Override
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}
	
	@Override
	public Boolean select(Vector3f position) {
		return false;
	}
	
	@Override
	public Object getSelected() {
		return null;
	}
	
	@Override
	public void move(Vector3f position) {
	}

	@Override
	public void remove(Vector3f position) {
	}
	
	@Override
	public void setVisible(Boolean visibility) {
		this.visible = visibility;
	}

	@Override
	public Boolean isVisible() {
		return visible;
	}
	
	public void renderTitle(Vector3f position, String title, Float maxDist, Boolean debug) {
		if (!scene.getRendererManager().getTextOverlay()) return;
		Vector3f cameraToObject = new Vector3f();
		Vector3f.sub(position, scene.getCameraManager().getPosition(), cameraToObject);
		if (Math.abs(cameraToObject.length()) > maxDist && maxDist > 0) return;

        FloatBuffer result = ByteBuffer.allocateDirect(3*8).order(ByteOrder.nativeOrder()).asFloatBuffer();
        GLU.gluProject(position.x, position.y, position.z, scene.getRendererManager().getModelViewMatrix(), scene.getRendererManager().getProjectionMatrix(), scene.getRendererManager().getViewport(), result);
        result.rewind();
        Float x = result.get();
        Float y = result.get();
        Float y2 = scene.getHeight() - y;
        Float z = result.get();
        if (x == null || y == null || z == null || title == null || z > 1.0f) return;

        // store the current state of the renderer
        glPushAttrib(GL_DEPTH_BUFFER_BIT | GL_ENABLE_BIT);
        glPushMatrix();
        glLoadIdentity();
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
            
        // now enter orthographic projection
        glLoadIdentity();
        glOrtho(0, scene.getWidth(), scene.getHeight(), 0, -1, 1);
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_LIGHTING);

        if (!debug) {
            font.drawString(x, y2, title);
        } else {
            font.drawString(x, y2, title + "\nx:"+x+"\ny:"+y+"\ny2:"+y2+"\nz:"+z);
        }
        
        // back to old state
		glEnable(GL_DEPTH_TEST);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
        glPopMatrix();
        glPopAttrib();

	}

}
