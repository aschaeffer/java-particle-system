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

	private final static Integer NUMBER_FRONT_RENDERERS = 2;

	private final List<Renderer> renderers = new ArrayList<Renderer>();

	private final IntBuffer viewport = BufferUtils.createIntBuffer(64);
	private final FloatBuffer modelViewMatrix = BufferUtils.createFloatBuffer(16);
	private final FloatBuffer projectionMatrix = BufferUtils.createFloatBuffer(16);
	private final FloatBuffer worldPosition = BufferUtils.createFloatBuffer(16);

	private final FloatBuffer mouseZ = BufferUtils.createFloatBuffer(1);
	private Boolean lastMouseDown = false;
	private Boolean blockRemoveSelection = false;
	
	private List<Renderer> currentRenderers;

	private final Logger logger = LoggerFactory.getLogger(RendererManager.class);

	public RendererManager() {}

	public void insert(Renderer renderer) {
		renderer.addDependencies();
		renderers.add(renderer);
		renderer.setScene(scene);
	}

	public void insertAt(Renderer renderer, Integer index) {
		renderer.addDependencies();
		renderers.add(index, renderer);
		renderer.setScene(scene);
	}

	public void insertFirst(Class<? extends Renderer> rendererClass) {
		try {
			insertAt(rendererClass.newInstance(), 0);
		} catch (Exception e) {
			logger.error("could not create renderer: " + e.getMessage(), e);
		}
	}
	
	public void insertLast(Class<? extends Renderer> rendererClass) {
		try {
			insert(rendererClass.newInstance());
		} catch (Exception e) {
			logger.error("could not create renderer: " + e.getMessage(), e);
		}
	}
	
	public void add(Renderer renderer) {
		// we have to insert the renderer one before the HudManager
		Integer index = renderers.size() - (NUMBER_FRONT_RENDERERS+1);
		if (index < 0) index = 0;
		logger.info("insert renderer at index " + index);
		insertAt(renderer, index);
	}
	
	public void add(Class<? extends Renderer> rendererClass) {
		try {
			add(rendererClass.newInstance());
		} catch (Exception e) {
			logger.error("could not create renderer: " + e.getMessage(), e);
		}
	}
	
	public void setVisibility(Class<? extends Renderer> rendererClass, Boolean visibility) {
		ListIterator<Renderer> iterator = renderers.listIterator();
		while (iterator.hasNext()) {
			Renderer renderer = iterator.next();
			if (renderer.getClass().equals(rendererClass)) {
				renderer.setVisible(visibility);
			}
		}
	}
	
	public List<Renderer> getRenderer() {
		return renderers;
	}
	
	public Renderer getRendererByClass(Class<? extends Renderer> rendererClass) {
		ListIterator<Renderer> iterator = renderers.listIterator();
		while (iterator.hasNext()) {
			Renderer renderer = iterator.next();
			if (renderer.getClass().equals(rendererClass)) {
				return renderer;
			}
		}
		return null;
	}

	@Override
	public void update() {

		// update buffers
		if (scene.getTextOverlayManager().getEnabled()) {
	        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
	        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelViewMatrix);
	        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projectionMatrix);
		}

		// render all managed renderers
		currentRenderers = new ArrayList<Renderer>(renderers);
	    ListIterator<Renderer> rendererIterator = currentRenderers.listIterator(0);
		while(rendererIterator.hasNext()) {
			rendererIterator.next().update();
		}
		
		// check for selecting
		if (Mouse.isButtonDown(0) && !lastMouseDown) {
			Boolean somethingSelected = false;
			Vector3f position = getPickWorldPosition(scene.getWidth() / 2, scene.getHeight() / 2);
		    ListIterator<Renderer> iterator2 = renderers.listIterator(0);
			while(iterator2.hasNext()) {
				if (iterator2.next().select(position)) somethingSelected = true;
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
					Renderer renderer = iterator2.next();
					renderer.remove(position);
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

	public IntBuffer getViewport() {
		return viewport;
	}

	public FloatBuffer getModelViewMatrix() {
		return modelViewMatrix;
	}

	public FloatBuffer getProjectionMatrix() {
		return projectionMatrix;
	}

}
