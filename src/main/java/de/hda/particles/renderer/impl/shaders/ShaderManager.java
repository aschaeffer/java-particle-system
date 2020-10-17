package de.hda.particles.renderer.impl.shaders;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.ARBGeometryShader4.*;

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
	
	private Boolean enableGeometryShaders = true;

	public Shader load(String resource) {
		Shader shader = new Shader();
		shader.setVertexShaderLocation("shaders/" + resource + ".vs");
		if (enableGeometryShaders) shader.setGeometryShaderLocation("shaders/" + resource + ".gs");
		shader.setFragmentShaderLocation("shaders/" + resource + ".fs");
		if (createProgram(shader) > 0) {
	        shaders.add(shader);
		}
        return shader;
	}
	
	public void unload(Shader shader) {
        glDeleteProgram(shader.getShaderProgram());
        glDeleteShader(shader.getVertexShaderId());
		if (enableGeometryShaders) glDeleteShader(shader.getGeometryShaderId());
        glDeleteShader(shader.getFragmentShaderId());
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

	private Integer createProgram(Shader shader) {
		Integer vertexShaderId = createShader(shader, GL_VERTEX_SHADER, loadShaderSource(shader, GL_VERTEX_SHADER));
		Integer geometryShaderId = -1;
		if (enableGeometryShaders) {
			/* if (GLContext.getCapabilities().OpenGL32) {
				logger.info("OpenGL 3.2 support");
				geometryShaderId = createShader(shader, GL_GEOMETRY_SHADER, loadShaderSource(shader, GL_GEOMETRY_SHADER)); // OpenGL 3.2
			} else if (GLContext.getCapabilities().GL_EXT_geometry_shader4) {
				logger.info("GL_EXT_geometry_shader4 support");
				geometryShaderId = createShader(shader, GL_GEOMETRY_SHADER_EXT, loadShaderSource(shader, GL_GEOMETRY_SHADER_EXT)); // EXT
			} else */ if (GLContext.getCapabilities().GL_ARB_geometry_shader4) {
				logger.info("GL_ARB_geometry_shader4 support");
				geometryShaderId = createShader(shader, GL_GEOMETRY_SHADER_ARB, loadShaderSource(shader, GL_GEOMETRY_SHADER_ARB)); // ARB
			} else {
				logger.info("No geometry shader support!");
			}
		}
		Integer fragmentShaderId = createShader(shader, GL_FRAGMENT_SHADER, loadShaderSource(shader, GL_FRAGMENT_SHADER));
		Integer shaderProgramId = glCreateProgram();
		if (vertexShaderId > 0) glAttachShader(shaderProgramId, vertexShaderId);
		if (enableGeometryShaders && geometryShaderId > 0) glAttachShader(shaderProgramId, geometryShaderId);
		if (fragmentShaderId > 0) glAttachShader(shaderProgramId, fragmentShaderId);
		// glBindAttribLocation(shaderProgramId, 0, "position");
        // glBindFragDataLocation(shaderProgramId, 0, "fragColor");
		glProgramParameteriARB(shaderProgramId, GL_GEOMETRY_VERTICES_OUT_ARB, 6);
		glProgramParameteriARB(shaderProgramId, GL_GEOMETRY_INPUT_TYPE_ARB, GL_POINTS);
		glProgramParameteriARB(shaderProgramId, GL_GEOMETRY_OUTPUT_TYPE_ARB, GL_POINTS);
        glLinkProgram(shaderProgramId);
        if (glGetProgrami(shaderProgramId, GL_LINK_STATUS) == GL_FALSE) {
        	logger.error("Failure in linking shader program. Error log:\n" + glGetProgramInfoLog(shaderProgramId, glGetProgrami(shaderProgramId, GL_INFO_LOG_LENGTH)));
        }
        glValidateProgram(shaderProgramId);
        shader.setShaderProgram(shaderProgramId);
        return shaderProgramId;
	}

	private Integer createShader(Shader shader, Integer type, CharSequence shaderSource) {
		if (shaderSource == "") return -1;
		Integer shaderId = glCreateShader(type);
        glShaderSource(shaderId, shaderSource);
        glCompileShader(shaderId);
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
            logger.error("Could not compile " + getShaderTypeName(type) + "! " + shaderSource.toString());
            return -1;
        }
        switch (type) {
        	case GL_VERTEX_SHADER:
                shader.setVertexShaderId(shaderId);
        		break;
        	case GL_GEOMETRY_SHADER_ARB:
                shader.setGeometryShaderId(shaderId);
        		break;
        	case GL_FRAGMENT_SHADER:
                shader.setFragmentShaderId(shaderId);
        		break;
        	default:
        		logger.warn("Unsupported shader type!");
        		break;
        }
        logger.debug("Successfully compiled " + getShaderTypeName(type));
        return shaderId;
	}
	
	private CharSequence loadShaderSource(Shader shader, Integer type) {
		String resourceLocation = "";
        switch (type) {
	    	case GL_VERTEX_SHADER:
	    		resourceLocation = shader.getVertexShaderLocation();
	    		break;
	    	case GL_GEOMETRY_SHADER_ARB:
	    		resourceLocation = shader.getGeometryShaderLocation();
	    		break;
	    	case GL_FRAGMENT_SHADER:
	    		resourceLocation = shader.getFragmentShaderLocation();
	    		break;
	    	default:
	    		logger.warn("Unsupported shader type!");
	    		return "";
	    }
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader shaderReader = null;
        try {
        	shaderReader = new BufferedReader(new InputStreamReader(ResourceLoader.getResourceAsStream(resourceLocation)));
            String line;
            while ((line = shaderReader.readLine()) != null) {
            	shaderSource.append(line).append('\n');
            }
        } catch (IOException e) {
        	logger.error("Could not load " + getShaderTypeName(type) + " from " + resourceLocation + ": " + e.getMessage(), e);
        	return "";
        } finally {
            if (shaderReader != null) {
                try {
                	shaderReader.close();
                } catch (IOException e) {
                	logger.error("Could not load " + getShaderTypeName(type) + " from " + resourceLocation + ": " + e.getMessage(), e);
                	return "";
                }
            }
        }
        logger.debug("Successfully loaded " + getShaderTypeName(type) + " source from " + resourceLocation);
        return shaderSource;
	}
	
	private String getShaderTypeName(Integer type) {
        switch (type) {
	    	case GL_VERTEX_SHADER:
	    		return "vertex shader";
	    	case GL_GEOMETRY_SHADER_ARB:
	    		return "geometry shader";
	    	case GL_FRAGMENT_SHADER:
	    		return "fragment shader";
	    	default:
	    		return "[unknown shader type]";
	    }
		
	}

}
