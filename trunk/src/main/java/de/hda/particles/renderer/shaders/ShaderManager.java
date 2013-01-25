package de.hda.particles.renderer.shaders;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.ARBGeometryShader4.*;
import static org.lwjgl.opengl.EXTGeometryShader4.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.util.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.Shader;

public class ShaderManager {

	private final Logger logger = LoggerFactory.getLogger(ShaderManager.class);

	private final List<Shader> shaders = new ArrayList<Shader>();

	public Shader load(String resource) {
		Integer vertexShaderId = glCreateShader(GL_VERTEX_SHADER);
		Integer fragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);
		Integer geometryShaderId = -1;
		if (GLContext.getCapabilities().OpenGL32) {
			logger.info("OpenGL 3.2 support");
			geometryShaderId = glCreateShader(GL_GEOMETRY_SHADER); // OpenGL 3.2
		} else if (GLContext.getCapabilities().GL_EXT_geometry_shader4) {
			logger.info("GL_EXT_geometry_shader4 support");
			geometryShaderId = glCreateShader(GL_GEOMETRY_SHADER_EXT); // EXT
		} else if (GLContext.getCapabilities().GL_ARB_geometry_shader4) {
			logger.info("GL_EXT_geometry_shader4 support");
			geometryShaderId = glCreateShader(GL_GEOMETRY_SHADER_ARB); // ARB
		} else {
			logger.info("No geometry shader support!");
		}
        glShaderSource(vertexShaderId, loadShaderSource(resource + ".vs"));
        glCompileShader(vertexShaderId);
        if (glGetShaderi(vertexShaderId, GL_COMPILE_STATUS) == GL_FALSE) {
            logger.error("Could not compile vertex shader " + resource + ".vs");
            return null;
        } else {
        	logger.debug("Successfully compiled vertex shader " + resource + ".vs");
        }
        glShaderSource(fragmentShaderId, loadShaderSource(resource + ".fs"));
        glCompileShader(fragmentShaderId);
        if (glGetShaderi(fragmentShaderId, GL_COMPILE_STATUS) == GL_FALSE) {
            logger.error("Could not compile fragment shader " + resource + ".fs");
            return null;
        } else {
        	logger.debug("Successfully compiled fragment shader " + resource + ".fs");
        }
        glShaderSource(geometryShaderId, loadShaderSource(resource + ".gs"));
        glCompileShader(geometryShaderId);
        if (glGetShaderi(geometryShaderId, GL_COMPILE_STATUS) == GL_FALSE) {
            logger.error("Could not compile geometry shader " + resource + ".gs");
            return null;
        } else {
        	logger.debug("Successfully compiled geometry shader " + resource + ".gs");
            logger.debug(loadShaderSource(resource + ".gs").toString());
        }
		Integer shaderProgramId = glCreateProgram();
        glAttachShader(shaderProgramId, vertexShaderId);
        glAttachShader(shaderProgramId, fragmentShaderId);
        glAttachShader(shaderProgramId, geometryShaderId);
        glLinkProgram(shaderProgramId);
        if (glGetProgrami(shaderProgramId, GL_LINK_STATUS) == GL_FALSE) {
        	logger.error("Failure in linking shader program. Error log:\n" + glGetProgramInfoLog(shaderProgramId, glGetProgrami(shaderProgramId, GL_INFO_LOG_LENGTH)));
        }
        glValidateProgram(shaderProgramId);
        Shader shader = new Shader(shaderProgramId, vertexShaderId, fragmentShaderId, geometryShaderId);
        shaders.add(shader);
        return shader;
	}
	
	public void unload(Shader shader) {
        glDeleteProgram(shader.getShaderProgram());
        glDeleteShader(shader.getVertexShaderId());
        glDeleteShader(shader.getFragmentShaderId());
        glDeleteShader(shader.getGeometryShaderId());
        shader = null;
	}

	public List<Shader> getShaders() {
		return shaders;
	}
	
	public Shader getShader(Integer id) {
		ListIterator<Shader> iterator = shaders.listIterator(0);
		while (iterator.hasNext()) {
			Shader shader = iterator.next();
			if (shader.getShaderProgram() == id) return shader;
		}
		return null;
	}
	
	public void useProgram(Shader shader) {
        glUseProgram(shader.getShaderProgram());
	}
	
	public void useProgram(Integer id) {
		glUseProgram(id);
	}
	
	private CharSequence loadShaderSource(String resource) {
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader shaderReader = null;
        try {
        	shaderReader = new BufferedReader(new InputStreamReader(ResourceLoader.getResourceAsStream("shaders/" + resource)));
            String line;
            while ((line = shaderReader.readLine()) != null) {
            	shaderSource.append(line).append('\n');
            }
        } catch (IOException e) {
        	logger.error("Could not load shader " + resource + ": " + e.getMessage(), e);
        	return "";
        } finally {
            if (shaderReader != null) {
                try {
                	shaderReader.close();
                } catch (IOException e) {
                	logger.error("Could not load shader " + resource + ": " + e.getMessage(), e);
                	return "";
                }
            }
        }
        return shaderSource;
	}

}
