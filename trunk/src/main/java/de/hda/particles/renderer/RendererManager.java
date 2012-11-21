package de.hda.particles.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RendererManager extends AbstractRenderer implements Renderer {

	private final List<Renderer> renderers = new ArrayList<Renderer>();

	private final IntBuffer viewport = BufferUtils.createIntBuffer(64);
	private final FloatBuffer modelViewMatrix = BufferUtils.createFloatBuffer(16);
	private final FloatBuffer projectionMatrix = BufferUtils.createFloatBuffer(16);
	private final FloatBuffer worldPosition = BufferUtils.createFloatBuffer(16);

	private final FloatBuffer mouseZ = BufferUtils.createFloatBuffer(1);
	private Boolean lastMouseDown = false;
	private Boolean blockRemoveSelection = false;

	private final Logger logger = LoggerFactory.getLogger(RendererManager.class);

	public RendererManager() {}

	public void add(Renderer renderer) {
		renderers.add(renderer);
		renderer.setScene(scene);
	}
	
	public void add(Class<? extends Renderer> rendererClass) {
		try {
			Renderer renderer = rendererClass.newInstance();
			renderers.add(renderer);
			renderer.setScene(scene);
		} catch (Exception e) {
			logger.error("could not create renderer: " + e.getMessage(), e);
		}
	}
	
	public List<Renderer> getRenderer() {
		return renderers;
	}

	@Override
	public void update() {

		// render all managed renderers
		List<Renderer> currentRenderers = new ArrayList<Renderer>(renderers);
	    ListIterator<Renderer> iterator = currentRenderers.listIterator(0);
		while(iterator.hasNext()) {
			iterator.next().update();
		}
		
		// check for selecting
		if (Mouse.isButtonDown(0) && !lastMouseDown) {
			Vector3f position = getPickWorldPosition(scene.getWidth() / 2, scene.getHeight() / 2);
		    ListIterator<Renderer> iterator2 = renderers.listIterator(0);
			while(iterator2.hasNext()) {
				iterator2.next().select(position);
			}
		} else if (Mouse.isButtonDown(0)) {
			Vector3f position = getPickWorldPosition(scene.getWidth() / 2, scene.getHeight() / 2);
		    ListIterator<Renderer> iterator2 = renderers.listIterator(0);
			while(iterator2.hasNext()) {
				iterator2.next().move(position);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DELETE)) {
			if (!blockRemoveSelection) {
				Vector3f position = getPickWorldPosition(scene.getWidth() / 2, scene.getHeight() / 2);
			    ListIterator<Renderer> iterator2 = renderers.listIterator(0);
				while(iterator2.hasNext()) {
					iterator2.next().remove(position);
				}
				blockRemoveSelection = true;
			}
		} else {
			blockRemoveSelection = false;
		}

		lastMouseDown = Mouse.isButtonDown(0);

	}
	
	private Vector3f getPickWorldPosition(Integer mouseX, Integer mouseY) {
 
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelViewMatrix);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projectionMatrix);
 
        GL11.glReadPixels(mouseX, mouseY, 2, 2, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, mouseZ);
        GLU.gluUnProject(mouseX, mouseY, mouseZ.get(0), modelViewMatrix, projectionMatrix, viewport, worldPosition);
        
        // System.out.println("mouse: " + mouseX + ", " + mouseY + ", " + mouseZ.get(0));
        // System.out.println("world: " + worldPosition.get(0) + ", " + worldPosition.get(1) + ", " + worldPosition.get(2));

        return new Vector3f(worldPosition.get(0), worldPosition.get(1), worldPosition.get(2));
    }

    @Override
	public void setup() {
		ListIterator<Renderer> iterator = renderers.listIterator(0);
		while(iterator.hasNext()) {
			iterator.next().setup();
		}
	}

	@Override
	public void destroy() {
		ListIterator<Renderer> iterator = renderers.listIterator(0);
		while(iterator.hasNext()) {
			iterator.next().destroy();
		}
		renderers.clear();
	}

	@Override
	public Boolean isFinished() {
		return false;
	}

}
