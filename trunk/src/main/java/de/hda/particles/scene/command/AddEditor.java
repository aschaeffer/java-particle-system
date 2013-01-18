package de.hda.particles.scene.command;

import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.editor.Editor;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.DemoContext;
import de.hda.particles.scene.demo.DemoHandle;

public class AddEditor implements Command {

	private final Logger logger = LoggerFactory.getLogger(AddEditor.class);

	@Override
	public DemoHandle execute(DemoContext context, CommandConfiguration configuration, Integer transitionIterations) {
		String type = (String) configuration.get("class");
		try {
			Class<?> clazz = Class.forName(type);
			List<Scene> scenes = context.getByType(Scene.class);
			ListIterator<Scene> iterator = scenes.listIterator(0);
			while (iterator.hasNext()) {
				Scene scene = iterator.next();
				Editor editor = (Editor) clazz.newInstance();
				editor.setScene(scene);
				editor.setup();
				scene.getEditorManager().add(editor);
				context.add(editor);
			}
		} catch (Exception e) {
			logger.error("could not create new editor of type " + type, e);
		}
		return null;
	}
	
}
