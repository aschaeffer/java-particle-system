package de.hda.particles.renderer.types;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.ParticleLifetimeListener;
import de.hda.particles.domain.Particle;
import de.hda.particles.renderer.AbstractRenderer;
import de.hda.particles.renderer.Renderer;
import de.hda.particles.scene.Scene;

public class RenderTypeManager extends AbstractRenderer implements Renderer, ParticleLifetimeListener {

	private final ArrayList<RenderType> renderTypes = new ArrayList<RenderType>();

	/**
	 * The particle cache increases performance. Also, we need a seperate list for
	 * incoming particles. The rare case, that one have to use LinkedList, is needed
	 * for adding and removing particles in constant time. Using ArrayList we could
	 * get a massive slowdown as soon as the first particles get removed.
	 */
	public HashMap<Integer, LinkedList<Particle>> renderTypeParticleCache = new HashMap<Integer, LinkedList<Particle>>();
	public HashMap<Integer, ArrayList<Particle>> newParticles = new HashMap<Integer, ArrayList<Particle>>();

	private final Logger logger = LoggerFactory.getLogger(RenderTypeManager.class);

	public RenderTypeManager() {}

	public RenderTypeManager(Scene scene) {
		this.scene = scene;
	}
	
	public Integer add(RenderType renderType) {
		renderTypes.add(renderType);
		renderTypeParticleCache.put(renderTypes.size(), new LinkedList<Particle>());
		newParticles.put(renderTypes.size(), new ArrayList<Particle>());
		return renderTypes.size(); // index+1
	}

	public Integer add(Class<? extends RenderType> renderTypeClass) {
		try {
			RenderType renderType = renderTypeClass.newInstance();
			renderType.setScene(scene);
			renderTypes.add(renderType);
			renderTypeParticleCache.put(renderTypes.size(), new LinkedList<Particle>());
			newParticles.put(renderTypes.size(), new ArrayList<Particle>());
			return renderTypes.size(); // index+1
		} catch (Exception e) {
			logger.error("could not create render type: " + e.getMessage(), e);
			return 0; // defaults to zero, if render type couldn't be created
		}
	}
	
	public List<RenderType> getRenderTypes() {
		return renderTypes;
	}
	
	public void clear() {
		Iterator<Integer> rIterator = renderTypeParticleCache.keySet().iterator();
		while(rIterator.hasNext()) {
			Integer renderTypeIndex = rIterator.next();
			renderTypeParticleCache.get(renderTypeIndex).clear();
			newParticles.get(renderTypeIndex).clear();
		}
		logger.info("Cleared RenderType Cache");
	}
	
	public void debug() {
		Iterator<Integer> rIterator = renderTypeParticleCache.keySet().iterator();
		while(rIterator.hasNext()) {
			Integer renderTypeIndex = rIterator.next();
			ListIterator<Particle> pIterator = renderTypeParticleCache.get(renderTypeIndex).listIterator(0);
			while (pIterator.hasNext()) {
				Particle p = pIterator.next();
				logger.info("deleted? " + scene.getParticleSystem().getParticles().contains(p) + " --- " + p.toString());

			}
		}
	}
	
	@Override
	public void setup() {
		// register listener to get updates about newly created particles and removed particles
		scene.getParticleSystem().addParticleListener(this);
		ListIterator<RenderType> iterator = renderTypes.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().setScene(scene);
		}
	}
	
	@Override
	public void destroy() {
		scene.getParticleSystem().removeParticleListener(this);
		renderTypes.clear();
		renderTypeParticleCache.clear();
		newParticles.clear();
	}

	@Override
	public void update() {
		ListIterator<RenderType> rIterator = renderTypes.listIterator(0);
		while(rIterator.hasNext()) {
			RenderType renderType = rIterator.next();
			Integer renderTypeIndex = rIterator.nextIndex(); // index+1
			renderType.before();
			// we have to copy the whole arraylist to prevent slowdowns -- not anymore: LinkedList is faster than cloning ArrayLists and prevents synchronization issues
			List<Particle> particlesByIndex = renderTypeParticleCache.get(renderTypeIndex);
			particlesByIndex.addAll(newParticles.get(renderTypeIndex));
			newParticles.get(renderTypeIndex).clear();
			ListIterator<Particle> pIterator = particlesByIndex.listIterator(0);
			while (pIterator.hasNext()) {
				Particle particle = pIterator.next();
				if (particle == null) continue;
				if (particle.getRemainingIterations() <= 0) { // be sure they will be removed
					pIterator.remove();
				} else {
					renderType.render(particle);
				}
			}
			renderType.after();
		}
	}

	/**
	 * Inserts newly created particles into cache
	 */
	@Override
	public void onParticleCreation(Particle particle) {
		if (particle.getRenderTypeIndex() > 0 && particle.getRenderTypeIndex() <= renderTypes.size())
			newParticles.get(particle.getRenderTypeIndex()).add(particle);
	}

	/**
	 * Remove particle from cache
	 */
	@Override
	public void onParticleDeath(Particle particle) {
	}

}
