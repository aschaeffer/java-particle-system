package de.hda.particles.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class RendererManager extends AbstractRenderer implements Renderer {

	private List<Renderer> renderers = new ArrayList<Renderer>();

	public void add(Renderer renderer) {
		renderers.add(renderer);
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
