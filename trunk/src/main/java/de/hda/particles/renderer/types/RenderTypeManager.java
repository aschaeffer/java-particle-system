package de.hda.particles.renderer.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.ParticleLifetimeListener;
import de.hda.particles.domain.Particle;
import de.hda.particles.renderer.AbstractRenderer;
import de.hda.particles.renderer.Renderer;
import de.hda.particles.scene.Scene;

public class RenderTypeManager extends AbstractRenderer implements Renderer, ParticleLifetimeListener {

	private List<RenderType> renderTypes = new ArrayList<RenderType>();

	/**
	 * The particle cache increases performance
	 */
	private HashMap<Integer, ArrayList<Particle>> renderTypeParticleCache = new HashMap<Integer, ArrayList<Particle>>();
	
	private final Logger logger = LoggerFactory.getLogger(RenderTypeManager.class);

	public RenderTypeManager() {}

	public RenderTypeManager(Scene scene) {
		this.scene = scene;
	}
	
	public Integer add(RenderType renderType) {
		renderTypes.add(renderType);
		renderTypeParticleCache.put(renderTypes.size(), new ArrayList<Particle>());
		return renderTypes.size(); // index+1
	}

	public Integer add(Class<? extends RenderType> renderTypeClass) {
		try {
			RenderType renderType = renderTypeClass.newInstance();
			renderTypes.add(renderType);
			renderTypeParticleCache.put(renderTypes.size(), new ArrayList<Particle>());
			return renderTypes.size(); // index+1
		} catch (Exception e) {
			logger.error("could not create render type: " + e.getMessage(), e);
			return 0; // defaults to zero, if render type couldn't be created
		}
	}
	
	public List<RenderType> getRenderTypes() {
		return renderTypes;
	}
	
	@Override
	public void setup() {
		scene.getParticleSystem().addParticleListener(this);
		ListIterator<RenderType> iterator = renderTypes.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().setScene(scene);
		}
	}

	@Override
	public void update() {
		ListIterator<RenderType> rIterator = renderTypes.listIterator(0);
		while(rIterator.hasNext()) {
			RenderType renderType = rIterator.next();
			Integer renderTypeIndex = rIterator.nextIndex(); // index+1
			renderType.before();
			// List<Particle> currentParticles = new ArrayList<Particle>(particleSystem.particles);
			List<Particle> currentParticles = new ArrayList<Particle>(renderTypeParticleCache.get(renderTypeIndex));
			ListIterator<Particle> pIterator = currentParticles.listIterator(0);
			while (pIterator.hasNext()) {
				Particle particle = pIterator.next();
				if (particle != null) {
//					if (particle.getRenderTypeIndex() == renderTypeIndex) {
//						renderType.render(particle);
//					}
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
		ArrayList<Particle> cachedParticlesByRenderType = renderTypeParticleCache.get(particle.getRenderTypeIndex());
		if (cachedParticlesByRenderType != null) cachedParticlesByRenderType.add(particle);
	}

	/**
	 * Remove particle from cache
	 */
	@Override
	public void onParticleDeath(Particle particle) {
		ArrayList<Particle> cachedParticlesByRenderType = renderTypeParticleCache.get(particle.getRenderTypeIndex());
		if (cachedParticlesByRenderType != null) cachedParticlesByRenderType.remove(particle);
	}

}
