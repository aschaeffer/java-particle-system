package de.hda.particles.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RendererManager extends AbstractRenderer implements Renderer {

	private List<Renderer> renderers = new ArrayList<Renderer>();

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
	    ListIterator<Renderer> iterator = renderers.listIterator(0);
		while(iterator.hasNext()) {
			iterator.next().update();
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
	}

	@Override
	public Boolean isFinished() {
		return false;
	}

}
