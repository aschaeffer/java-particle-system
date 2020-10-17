package de.hda.particles.scene.command.impl;

import de.hda.particles.scene.command.Command;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.configuration.impl.CommandConfiguration;
import de.hda.particles.editor.Editor;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.demo.impl.DemoContext;
import de.hda.particles.scene.demo.impl.DemoHandle;

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
