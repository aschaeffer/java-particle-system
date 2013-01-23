package de.hda.particles.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.hud.HUDCommand;
import de.hda.particles.hud.HUDCommandTypes;

public class RendererManager extends AbstractRenderer implements Renderer {

	private final static Integer NUMBER_FRONT_RENDERERS = 2;
	private final static Double BLOCK_MOVEMENT_FOR_SECONDS = 0.25;
	private final static Double BLOCK_SELECTION_FOR_SECONDS = 0.1;

	private final List<Renderer> renderers = new ArrayList<Renderer>();

	private Boolean buffersUpdated = false;
	private final IntBuffer viewport = BufferUtils.createIntBuffer(64);
	private final FloatBuffer modelViewMatrix = BufferUtils.createFloatBuffer(16);
	private final FloatBuffer projectionMatrix = BufferUtils.createFloatBuffer(16);
	private final FloatBuffer worldPosition = BufferUtils.createFloatBuffer(16);
	private final FloatBuffer projectionResult = BufferUtils.createFloatBuffer(24);

	private final FloatBuffer mouseZ = BufferUtils.createFloatBuffer(1);
	private Boolean lastMouseDown = false;
	private Boolean lastMouseDown1 = false;
	private Boolean blockRemoveSelection = false;
	private Integer blockSelection = 0;
	private Integer blockMovement = 0;
	private Boolean lockMovement = false;
	private Boolean startMultiSelection = false;
	private Boolean singleSelection = false;
	private Boolean multiSelection = false;
	private Vector3f selectionPos1 = new Vector3f();
	
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
		buffersUpdated = false;
		if (scene.getTextOverlayManager().getEnabled()) updateBuffers();

		// render all managed renderers
		currentRenderers = new ArrayList<Renderer>(renderers);
	    ListIterator<Renderer> rendererIterator = currentRenderers.listIterator(0);
		while(rendererIterator.hasNext()) {
			rendererIterator.next().update();
		}
		
		// selection operations
		if (Mouse.isButtonDown(0) && lastMouseDown && startMultiSelection) {
			// multi selection mode: draw selection box
			Vector3f selectionPos2 = getPickWorldPosition(scene.getWidth() / 2, scene.getHeight() / 2, 0.2f);
			// drawCube(selectionPos1, selectionPos2);
			drawMultiSelection(selectionPos1, selectionPos2);
		} else if (!Mouse.isButtonDown(0) && lastMouseDown && startMultiSelection) {
			// multi selection mode: selection finished
			Integer selectedItems = 0;
			Vector3f selectionPos2 = getPickWorldPosition(scene.getWidth() / 2, scene.getHeight() / 2, 0.2f);
			logger.debug("end selection: x:"+selectionPos2.x+" y:"+selectionPos2.y+" z:"+selectionPos2.z);
			Vector4f selectionBox = getScreenSelection(getScreenCoordinates(selectionPos1), getScreenCoordinates(selectionPos2));
			logger.debug("selection box: x1:"+selectionBox.x+" y1:"+selectionBox.y+" x2:"+selectionBox.z+" y2:"+selectionBox.w);
			
			Object subject = null;
		    ListIterator<Renderer> iterator2 = renderers.listIterator(0);
			while(iterator2.hasNext()) {
				Renderer renderer = iterator2.next();
				List<? extends Object> selections = renderer.select(selectionBox);
				if (selections != null) {
					if (selectedItems == 0 && selections.size() == 1) {
						subject = selections.get(0);
					}
					selectedItems += selections.size();
				}
			}
			if (selectedItems > 1) {
				// multiple items selected
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.EDIT_DONE));
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "" + selectedItems + " selected"));
				singleSelection = false;
				multiSelection = true;
			} else if (selectedItems == 1) {
				// one item selected
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.EDIT, subject));
				singleSelection = true;
				multiSelection = false;
			} else {
				// no items selected
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.EDIT_DONE));
				singleSelection = false;
				multiSelection = false;
			}
			startMultiSelection = false;
		} else if (Mouse.isButtonDown(0) && blockSelection > 0) {
			blockSelection--;
		} else if (Mouse.isButtonDown(0) && !lastMouseDown && !multiSelection && blockSelection == 0) {
			// check for single or multiple selection
			Boolean somethingSelected = false;
			Boolean wasSelected = singleSelection;
			Vector3f position = getPickWorldPosition(scene.getWidth() / 2, scene.getHeight() / 2);
		    ListIterator<Renderer> iterator2 = renderers.listIterator(0);
			while(iterator2.hasNext()) {
				if (iterator2.next().select(position)) somethingSelected = true;
			}
			if (somethingSelected) {
				blockMovement = new Double(scene.getFps() * BLOCK_MOVEMENT_FOR_SECONDS).intValue(); // block movement
				singleSelection = true;
			} else {
				if (wasSelected) {
					blockSelection = new Double(scene.getFps() * BLOCK_SELECTION_FOR_SECONDS).intValue(); // block selection
				} else {
					startMultiSelection = true;
					selectionPos1 = getPickWorldPosition(scene.getWidth() / 2, scene.getHeight() / 2, 0.998f);
					logger.debug("start selection: x:"+selectionPos1.x+" y:"+selectionPos1.y+" z:"+selectionPos1.z);
				}
				singleSelection = false;
			}
		} else if (Mouse.isButtonDown(0) && blockMovement > 0) {
			blockMovement--;
		} else if ((Mouse.isButtonDown(0) || lockMovement) && blockMovement == 0 && !startMultiSelection) {
			if (Mouse.isButtonDown(1) && !lockMovement && !lastMouseDown1) {
				lockMovement = true;
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Locked Movement"));
			} else if (Mouse.isButtonDown(1) && lockMovement && !lastMouseDown1) {
				lockMovement = false;
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Unlocked Movement"));
			}
			Vector3f position = getPickWorldPosition(scene.getWidth() / 2, scene.getHeight() / 2);
		    ListIterator<Renderer> iterator2 = renderers.listIterator(0);
			while(iterator2.hasNext()) {
				iterator2.next().move(position);
			}
		} else if (Mouse.isButtonDown(1) && !lastMouseDown1) {
			// unselect (right mouse button)
		    ListIterator<Renderer> iterator2 = renderers.listIterator(0);
			while(iterator2.hasNext()) {
				iterator2.next().unselect();
			}
			singleSelection = false;
			multiSelection = false;
			scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "No items selected"));
		}
		// remove selected
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
		lastMouseDown1 = Mouse.isButtonDown(1);
	}
	
	public Vector3f getPickWorldPosition(Integer mouseX, Integer mouseY) {
		updateBuffers();
        glReadPixels(mouseX, mouseY, 2, 2, GL_DEPTH_COMPONENT, GL_FLOAT, mouseZ);
        GLU.gluUnProject(mouseX, mouseY, mouseZ.get(0), modelViewMatrix, projectionMatrix, viewport, worldPosition);
        return new Vector3f(worldPosition.get(0), worldPosition.get(1), worldPosition.get(2));
    }

	public Vector3f getPickWorldPosition(Integer mouseX, Integer mouseY, Float mouseZ) {
		updateBuffers();
        GLU.gluUnProject(mouseX, mouseY, mouseZ, modelViewMatrix, projectionMatrix, viewport, worldPosition);
        return new Vector3f(worldPosition.get(0), worldPosition.get(1), worldPosition.get(2));
    }
	
	public Vector2f getScreenCoordinates(Vector3f position) {
		updateBuffers();
		projectionResult.clear();
		GLU.gluProject(position.x, position.y, position.z, modelViewMatrix, projectionMatrix, viewport, projectionResult);
        projectionResult.rewind();
        Float x = projectionResult.get();
        Float yInv = projectionResult.get();
        Float y = scene.getHeight() - yInv;
        Float z = projectionResult.get();
        if (z == null || z > 1.0f) return null;
        return new Vector2f(x, y);
	}

	/**
	 * Returns a selection box of two 2D coordinates. The box is
	 * sorted.
	 * 
	 * @param position1 2d coords
	 * @param position2 2d coords
	 * @return 2x 2d coords, sorted
	 */
	private Vector4f getScreenSelection(Vector2f position1, Vector2f position2) {
		Vector4f selectionBox = new Vector4f();
		if (position1.x < position2.x) {
			selectionBox.x = position1.x;
			selectionBox.z = position2.x;
		} else {
			selectionBox.z = position1.x;
			selectionBox.x = position2.x;
		}
		if (position1.y < position2.y) {
			selectionBox.y = position1.y;
			selectionBox.w = position2.y;
		} else {
			selectionBox.w = position1.y;
			selectionBox.y = position2.y;
		}
		return selectionBox;
	}

	private void updateBuffers() {
		if (!buffersUpdated) {
	        glGetInteger(GL_VIEWPORT, viewport);
	        glGetFloat(GL_MODELVIEW_MATRIX, modelViewMatrix);
	        glGetFloat(GL_PROJECTION_MATRIX, projectionMatrix);
	        buffersUpdated = true;
		}
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
	
	private void drawMultiSelection(Vector3f pos1, Vector3f pos2) {
		Vector2f coords1 = getScreenCoordinates(pos1);
		Vector2f coords2 = getScreenCoordinates(pos2);
		if (coords1 == null || coords2 == null) return;
		Vector4f selectionBox = getScreenSelection(coords1, coords2);
        
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
        
        glBegin(GL_QUADS);
        glColor4f(0.6f, 0.6f, 0.0f, 0.2f);
        glVertex2f(selectionBox.x, selectionBox.y);
        glVertex2f(selectionBox.z, selectionBox.y);
        glVertex2f(selectionBox.z, selectionBox.w);
        glVertex2f(selectionBox.x, selectionBox.w);
        glEnd();
        glBegin(GL_LINE_LOOP);
        glColor4f(0.6f, 0.6f, 0.0f, 0.8f);
        glVertex2f(selectionBox.x, selectionBox.y);
        glVertex2f(selectionBox.z, selectionBox.y);
        glVertex2f(selectionBox.z, selectionBox.w);
        glVertex2f(selectionBox.x, selectionBox.w);
        glEnd();

        // back to old state
		glEnable(GL_DEPTH_TEST);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
        glPopMatrix();
        glPopAttrib();
    }
	
	private void drawCube(Vector3f pos1, Vector3f pos2) {
		float bbMinX = pos1.getX();
		float bbMinY = pos1.getY();
		float bbMinZ = pos1.getZ();
		float bbMaxX = pos2.getX();
		float bbMaxY = pos2.getY();
		float bbMaxZ = pos2.getZ();

		// glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);

		glColor4f(0.5f, 0.8f, 0.5f, 0.1f);
		
		// FRONT
		glBegin(GL_LINE_LOOP);
	    glVertex3f(bbMinX, bbMinY, bbMinZ);
	    glVertex3f(bbMaxX, bbMinY, bbMinZ);
	    glVertex3f(bbMaxX, bbMaxY, bbMinZ);
	    glVertex3f(bbMinX, bbMaxY, bbMinZ);
		glEnd();
	    // BACK
		glBegin(GL_LINE_LOOP);
	    glVertex3f(bbMaxX, bbMaxY, bbMaxZ);
	    glVertex3f(bbMinX, bbMaxY, bbMaxZ);
	    glVertex3f(bbMinX, bbMinY, bbMaxZ);
	    glVertex3f(bbMaxX, bbMinY, bbMaxZ);
		glEnd();
		// RIGHT
		glBegin(GL_LINE_LOOP);
	    glVertex3f(bbMaxX, bbMaxY, bbMaxZ);
	    glVertex3f(bbMaxX, bbMaxY, bbMinZ);
	    glVertex3f(bbMaxX, bbMinY, bbMinZ);
	    glVertex3f(bbMaxX, bbMinY, bbMaxZ);
		glEnd();
		// LEFT
		glBegin(GL_LINE_LOOP);
	    glVertex3f(bbMinX, bbMinY, bbMinZ);
	    glVertex3f(bbMinX, bbMaxY, bbMinZ);
	    glVertex3f(bbMinX, bbMaxY, bbMaxZ);
	    glVertex3f(bbMinX, bbMinY, bbMaxZ);
		glEnd();
		// TOP
		glBegin(GL_LINE_LOOP);
	    glVertex3f(bbMaxX, bbMaxY, bbMaxZ);
	    glVertex3f(bbMinX, bbMaxY, bbMaxZ);
	    glVertex3f(bbMinX, bbMaxY, bbMinZ);
	    glVertex3f(bbMaxX, bbMaxY, bbMinZ);
		glEnd();
		// BOTTOM
		glBegin(GL_LINE_LOOP);
	    glVertex3f(bbMinX, bbMinY, bbMinZ);
	    glVertex3f(bbMinX, bbMinY, bbMaxZ);
	    glVertex3f(bbMaxX, bbMinY, bbMaxZ);
	    glVertex3f(bbMaxX, bbMinY, bbMinZ);

		glEnd();
	}

}
