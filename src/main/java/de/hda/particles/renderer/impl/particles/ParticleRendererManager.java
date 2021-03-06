package de.hda.particles.renderer.impl.particles;

import de.hda.particles.renderer.ParticleRenderer;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.Particle;
import de.hda.particles.listener.FrameListener;
import de.hda.particles.listener.ParticleLifetimeListener;
import de.hda.particles.renderer.impl.AbstractRenderer;
import de.hda.particles.renderer.Renderer;
import de.hda.particles.scene.Scene;

public class ParticleRendererManager extends AbstractRenderer implements Renderer, ParticleLifetimeListener, FrameListener {

	private final ArrayList<ParticleRenderer> particleRenderers = new ArrayList<ParticleRenderer>();

	/**
	 * The particle cache increases performance. Also, we need a seperate list for
	 * incoming particles. The rare case, that one have to use LinkedList, is needed
	 * for adding and removing particles in constant time. Using ArrayList we could
	 * get a massive slowdown as soon as the first particles get removed.
	 */
	public HashMap<Integer, LinkedList<Particle>> particleRendererCache = new HashMap<Integer, LinkedList<Particle>>();
	public HashMap<Integer, ArrayList<Particle>> newParticles = new HashMap<Integer, ArrayList<Particle>>();

	private final Logger logger = LoggerFactory.getLogger(ParticleRendererManager.class);

	public ParticleRendererManager() {}

	public ParticleRendererManager(Scene scene) {
		this.scene = scene;
	}
	
	public Integer add(ParticleRenderer particleRenderer) {
		particleRenderer.addDependencies();
		particleRenderers.add(particleRenderer);
		particleRendererCache.put(particleRenderers.size(), new LinkedList<Particle>());
		newParticles.put(particleRenderers.size(), new ArrayList<Particle>());
		return particleRenderers.size(); // index+1
	}

	public Integer add(Class<? extends ParticleRenderer> particleRendererClass) {
		try {
			ParticleRenderer particleRenderer = particleRendererClass.newInstance();
			particleRenderer.setScene(scene);
			particleRenderer.addDependencies();
			particleRenderers.add(particleRenderer);
			particleRendererCache.put(particleRenderers.size(), new LinkedList<Particle>());
			newParticles.put(particleRenderers.size(), new ArrayList<Particle>());
			return particleRenderers.size(); // index+1
		} catch (Exception e) {
			logger.error("could not create particle renderer: " + e.getMessage(), e);
			return 0; // defaults to zero, if render type couldn't be created
		}
	}
	
	public void remove(Integer index) {
		particleRenderers.remove(index);
	}
	
	public void remove(ParticleRenderer particleRenderer) {
		particleRenderers.remove(particleRenderer);
	}
	
	public void replace(Class<? extends ParticleRenderer> particleRendererClass, Integer index) {
		if (index < 1 || index > particleRenderers.size()) return;
		index--;
		try {
			ParticleRenderer particleRenderer = particleRendererClass.newInstance();
			particleRenderer.setScene(scene);
			particleRenderer.addDependencies();
			particleRenderers.set(index, particleRenderer);
		} catch (Exception e) {
			logger.error("could not create particle renderer: " + e.getMessage(), e);
		}
	}
	
	public List<ParticleRenderer> getParticleRenderers() {
		return particleRenderers;
	}
	
	public ParticleRenderer getParticleRenderer(final Integer index) {
		if (index >= particleRenderers.size()) return null;
		return particleRenderers.get(index-1);
	}
	
	public String getParticleRendererName(final Integer index) {
		if (index <= 0) return "DISABLED";
		if (index > particleRenderers.size()) return "FREE SLOT (" + index + ")";
		return particleRenderers.get(index-1).getName();
	}
	
	public void clear() {
		Iterator<Integer> rIterator = particleRendererCache.keySet().iterator();
		while(rIterator.hasNext()) {
			Integer particleRendererIndex = rIterator.next();
			particleRendererCache.get(particleRendererIndex).clear();
			newParticles.get(particleRendererIndex).clear();
		}
		logger.info("Cleared particle renderer cache");
	}
	
	public void debug() {
		Iterator<Integer> rIterator = particleRendererCache.keySet().iterator();
		while(rIterator.hasNext()) {
			Integer particleRendererIndex = rIterator.next();
			ListIterator<Particle> pIterator = particleRendererCache.get(particleRendererIndex).listIterator(0);
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
		scene.getParticleSystem().addFrameListener(this);
		ListIterator<ParticleRenderer> iterator = particleRenderers.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().setScene(scene);
		}
	}
	
	@Override
	public void destroy() {
		scene.getParticleSystem().removeFrameListener(this);
		scene.getParticleSystem().removeParticleListener(this);
		particleRenderers.clear();
		particleRendererCache.clear();
		newParticles.clear();
	}

	@Override
	public void update() {
		try {
			ListIterator<ParticleRenderer> rIterator = particleRenderers.listIterator(0);
			while(rIterator.hasNext()) {
				ParticleRenderer particleRenderer = rIterator.next();
				Integer particleRendererIndex = rIterator.nextIndex(); // index+1
				particleRenderer.before();
				// we have to copy the whole arraylist to prevent slowdowns -- not anymore: LinkedList is faster than cloning ArrayLists and prevents synchronization issues
				List<Particle> particlesByIndex = particleRendererCache.get(particleRendererIndex);
				particlesByIndex.addAll(newParticles.get(particleRendererIndex));
				newParticles.get(particleRendererIndex).clear();
				ListIterator<Particle> pIterator = particlesByIndex.listIterator(0);
				while (pIterator.hasNext()) {
					Particle particle = pIterator.next();
					if (particle == null) continue;
					if (particle.getRemainingIterations() <= 0) { // be sure they will be removed
						pIterator.remove();
					} else {
						particleRenderer.render(particle);
					}
				}
				particleRenderer.after();
			}
		} catch (ConcurrentModificationException e) {
			logger.error("skipped frame: " + e.getMessage(), e);
		} catch (RuntimeException e) {
			logger.error("could not render particles: " + e.getMessage(), e);
		}
	}
	
	/**
	 * Inserts newly created particles into cache
	 */
	@Override
	public void onParticleCreation(Particle particle) {
		if (particle.getParticleRendererIndex() > 0 && particle.getParticleRendererIndex() <= particleRenderers.size())
			newParticles.get(particle.getParticleRendererIndex()).add(particle);
	}

	@Override
	public void onParticleDeath(Particle particle) {
		// nothing to do, because the render type manager itself removes particles
		// from the cache
	}

	@Override
	public void onFrame() {
		try {
			ListIterator<ParticleRenderer> rIterator = particleRenderers.listIterator(0);
			while(rIterator.hasNext()) {
				rIterator.next().setDirty();
			}
		} catch (NullPointerException e) {
			logger.error("could not update particles: " + e.getMessage(), e);
		}
	}

}
