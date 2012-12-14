package de.hda.particles.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import de.hda.particles.domain.Demo;

public class DemoDAO {

	public void load(Demo demo, File file) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		// read json
		InputStream in = new FileInputStream(file);
		ObjectMapper mapper = new ObjectMapper();
		demo = mapper.readValue(in, Demo.class);
	}

	public void save(Demo demo, File file) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		// write json
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(file, demo);
	}

}
