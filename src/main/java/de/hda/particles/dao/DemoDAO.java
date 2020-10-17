package de.hda.particles.dao;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ListIterator;

import org.newdawn.slick.util.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.ChangeSet;
import de.hda.particles.domain.Demo;
import de.hda.particles.scene.command.Command;

public class DemoDAO {

	private final Logger logger = LoggerFactory.getLogger(DemoDAO.class);
	
	/**
	 * Loads a demo from file.
	 * 
	 * @param demo
	 * @param file
	 * @throws ClassNotFoundException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void load(Demo demo, File file) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		// read json
		InputStream in = new FileInputStream(file);
		ObjectMapper mapper = new ObjectMapper();
		demo = mapper.readValue(in, Demo.class);
	}
	
	public void load(Demo demo, String filename) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		InputStream in = ResourceLoader.getResourceAsStream(filename);
		ObjectMapper mapper = new ObjectMapper();
		demo = mapper.readValue(in, Demo.class);
	}

	/**
	 * Adds the changesets from file into the current demo.
	 *  
	 * @param demo
	 * @param file
	 * @throws ClassNotFoundException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void loadDiff(Demo demo, File file, Integer iterationOffset) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		InputStream in = new FileInputStream(file);
		ObjectMapper mapper = new ObjectMapper();
		Demo diffDemo = mapper.readValue(in, Demo.class);
		ListIterator<ChangeSet> iterator = diffDemo.getChangeSets().listIterator(0);
		while (iterator.hasNext()) {
			ChangeSet changeSet = iterator.next();
			changeSet.setIteration(changeSet.getIteration() + iterationOffset);
			demo.addChangeSet(changeSet);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadDiff(Demo demo, String filename, Integer iterationOffset) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		InputStream in = ResourceLoader.getResourceAsStream(filename);
		ObjectMapper mapper = new ObjectMapper();
		Demo diffDemo = mapper.readValue(in, Demo.class);
		if (diffDemo == null) {
			logger.error("Could not read json demo file "+ filename);
			return;
		}
		Integer loadCount = 0;
		ListIterator<ChangeSet> iterator = diffDemo.getChangeSets().listIterator(0);
		while (iterator.hasNext()) {
			ChangeSet changeSet = iterator.next();
			if (changeSet == null) {
				logger.error("Could not read changeset from demo file at index " + iterator.previousIndex());
				continue;
			}
			try {
				changeSet.setIteration(changeSet.getIteration() + iterationOffset);
				Class<? extends Command> commandClass = (Class<? extends Command>) Class.forName(changeSet.getType());
				changeSet.setCommand(commandClass.newInstance());
				demo.addChangeSet(changeSet);
				loadCount++;
			} catch (Exception e) {
				logger.error("Could not create command instance for changeset " + changeSet.getType() + " from demo file at index " + iterator.previousIndex(), e);
			}
		}
		logger.debug("Loaded " + loadCount + " changesets from demo file " + filename);
	}

	public void save(Demo demo, File file) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		// write json
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(file, demo);
	}

}
