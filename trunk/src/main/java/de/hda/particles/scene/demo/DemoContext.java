package de.hda.particles.scene.demo;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoContext extends HashMap<DemoHandle, Object> {

	private static final long serialVersionUID = 1704734496104255618L;

	private final Logger logger = LoggerFactory.getLogger(DemoContext.class);

	public DemoContext(final DemoManager demoManager) {
		add(demoManager);
	}

	public DemoHandle add(Object o) {
		DemoHandle handle = new DemoHandle();
		put(handle, o);
		return handle;
	}

	public <T extends Object> List<T> getByType(Class<T> type) {
		Collection<Object> objects = this.values();
		// logger.debug("get by type " + type.getName());
		Iterator<Object> iterator = objects.iterator();
		List<T> found = new ArrayList<T>();
		while(iterator.hasNext()) {
			Object o = iterator.next();
			if (type.isInstance(o)) {
				found.add(type.cast(o));
			} else if (o.getClass().equals(type)) {
				found.add(type.cast(o));
			}
		}
		return found;
	}
	
}
