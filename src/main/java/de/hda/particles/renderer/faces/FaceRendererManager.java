package de.hda.particles.renderer.faces;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.Face;
import de.hda.particles.listener.FaceLifetimeListener;
import de.hda.particles.renderer.AbstractRenderer;
import de.hda.particles.renderer.Renderer;
import de.hda.particles.scene.Scene;

public class FaceRendererManager extends AbstractRenderer implements Renderer, FaceLifetimeListener {

	private final ArrayList<FaceRenderer> faceRenderers = new ArrayList<FaceRenderer>();

	/**
	 * The face cache increases performance. Also, we need a separate list for
	 * incoming face. The rare case, that one have to use LinkedList, is needed
	 * for adding and removing faces in constant time. Using ArrayList we could
	 * get a massive slow down as soon as the first faces get removed.
	 */
	public HashMap<Integer, LinkedList<Face>> faceRendererFaceCache = new HashMap<Integer, LinkedList<Face>>();
	public HashMap<Integer, ArrayList<Face>> newFaces = new HashMap<Integer, ArrayList<Face>>();

	private final Logger logger = LoggerFactory.getLogger(FaceRendererManager.class);

	public FaceRendererManager() {}

	public FaceRendererManager(Scene scene) {
		this.scene = scene;
	}
	
	public Integer add(FaceRenderer renderType) {
		renderType.addDependencies();
		faceRenderers.add(renderType);
		faceRendererFaceCache.put(faceRenderers.size(), new LinkedList<Face>());
		newFaces.put(faceRenderers.size(), new ArrayList<Face>());
		return faceRenderers.size(); // index+1
	}

	public Integer add(Class<? extends FaceRenderer> renderTypeClass) {
		try {
			FaceRenderer renderType = renderTypeClass.newInstance();
			renderType.setScene(scene);
			renderType.addDependencies();
			faceRenderers.add(renderType);
			faceRendererFaceCache.put(faceRenderers.size(), new LinkedList<Face>());
			newFaces.put(faceRenderers.size(), new ArrayList<Face>());
			return faceRenderers.size(); // index+1
		} catch (Exception e) {
			logger.error("could not create render type: " + e.getMessage(), e);
			return 0; // defaults to zero, if render type couldn't be created
		}
	}
	
	public void replace(Class<? extends FaceRenderer> renderTypeClass, Integer index) {
		if (index < 1 || index > faceRenderers.size()) return;
		index--;
		try {
			FaceRenderer renderType = renderTypeClass.newInstance();
			renderType.setScene(scene);
			renderType.addDependencies();
			faceRenderers.set(index, renderType);
		} catch (Exception e) {
			logger.error("could not create render type: " + e.getMessage(), e);
		}
	}
	
	public List<FaceRenderer> getFaceRenderers() {
		return faceRenderers;
	}

	public String getFaceRendererName(final Integer index) {
		if (index <= 0) return "DISABLED";
		if (index > faceRenderers.size()) return "FREE SLOT (" + index + ")";
		return faceRenderers.get(index-1).getName();
	}

	public void clear() {
		Iterator<Integer> rIterator = faceRendererFaceCache.keySet().iterator();
		while(rIterator.hasNext()) {
			Integer faceRendererIndex = rIterator.next();
			faceRendererFaceCache.get(faceRendererIndex).clear();
			newFaces.get(faceRendererIndex).clear();
		}
		logger.info("Cleared Face Renderer Face Cache");
	}
	
	@Override
	public void setup() {
		// register listener to get updates about newly created particles and removed particles
		scene.getParticleSystem().addFaceListener(this);
		ListIterator<FaceRenderer> iterator = faceRenderers.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().setScene(scene);
		}
	}
	
	@Override
	public void destroy() {
		scene.getParticleSystem().removeFaceListener(this);
		faceRenderers.clear();
		faceRendererFaceCache.clear();
		newFaces.clear();
	}

	@Override
	public void update() {
		ListIterator<FaceRenderer> faceRendererIterator = faceRenderers.listIterator(0);
		while(faceRendererIterator.hasNext()) {
			FaceRenderer faceRenderer = faceRendererIterator.next();
			Integer faceRendererIndex = faceRendererIterator.nextIndex(); // index+1
			faceRenderer.before();
			// we have to copy the whole arraylist to prevent slowdowns -- not anymore: LinkedList is faster than cloning ArrayLists and prevents synchronization issues
			List<Face> particlesByIndex = faceRendererFaceCache.get(faceRendererIndex);
			particlesByIndex.addAll(newFaces.get(faceRendererIndex));
			newFaces.get(faceRendererIndex).clear();
			ListIterator<Face> faceIterator = particlesByIndex.listIterator(0);
			while (faceIterator.hasNext()) {
				Face face = faceIterator.next();
				if (face == null) continue;
				if (!face.isAlive()) { // be sure faces will be removed
					faceIterator.remove();
				} else {
					faceRenderer.render(face);
				}
			}
			faceRenderer.after();
		}
	}

	/**
	 * Inserts newly created particles into cache
	 */
	@Override
	public void onFaceCreation(Face face) {
		if (face.getFaceRendererIndex() > 0 && face.getFaceRendererIndex() <= faceRenderers.size())
			newFaces.get(face.getFaceRendererIndex()).add(face);
	}

	@Override
	public void onFaceDeath(Face face) {
		// nothing to do, because the face renderer manager
		// itself removes faces from the cache
	}

}
