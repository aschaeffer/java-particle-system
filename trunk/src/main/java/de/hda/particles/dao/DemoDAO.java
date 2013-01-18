package de.hda.particles.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ListIterator;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.newdawn.slick.util.ResourceLoader;

import de.hda.particles.domain.ChangeSet;
import de.hda.particles.domain.Demo;

public class DemoDAO {

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
	
	public void loadDiff(Demo demo, String filename, Integer iterationOffset) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		InputStream in = ResourceLoader.getResourceAsStream(filename);
		ObjectMapper mapper = new ObjectMapper();
		Demo diffDemo = mapper.readValue(in, Demo.class);
		ListIterator<ChangeSet> iterator = diffDemo.getChangeSets().listIterator(0);
		while (iterator.hasNext()) {
			ChangeSet changeSet = iterator.next();
			changeSet.setIteration(changeSet.getIteration() + iterationOffset);
			demo.addChangeSet(changeSet);
		}
	}

	public void save(Demo demo, File file) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		// write json
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(file, demo);
	}

}
