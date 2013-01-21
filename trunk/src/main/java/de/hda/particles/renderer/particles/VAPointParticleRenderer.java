package de.hda.particles.renderer.particles;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;


import static org.lwjgl.opengl.ARBBufferObject.*;
import static org.lwjgl.opengl.ARBVertexBufferObject.*;

import java.io.ObjectOutputStream.PutField;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.Particle;

public class VAPointParticleRenderer extends AbstractParticleRenderer implements ParticleRenderer {

	public final static String NAME = "Vertex Array Point Particle Renderer";
	
	public final static Integer SIZE_PER_VERTICE = 3;
	public final static Integer MAX_PARTICLES = 200000;
	public final static Integer BUFFER_SIZE = MAX_PARTICLES * SIZE_PER_VERTICE;

    private int vertexBufferID;
    private int vertexArrayID;
    private FloatBuffer vertices = BufferUtils.createFloatBuffer(BUFFER_SIZE);

//	private FloatBuffer vertices; // = ByteBuffer.allocateDirect(16).order(ByteOrder.nativeOrder()).asFloatBuffer();
//	protected final FloatBuffer flippedBbBuffer;
	
//	private int run = 0;
	private int size = 0;
//	private int vboVertexHandle;
//	private int VaoId;
//	private int displayList;
	
    
//	private static int buffer_id;
//	private static FloatBuffer vertices;
	private ByteBuffer mapped_buffer;
	private FloatBuffer mapped_float_buffer; 

	private final Logger logger = LoggerFactory.getLogger(VAPointParticleRenderer.class);

	public VAPointParticleRenderer() {
		super();
		
//		VaoId = glGenVertexArrays();
//		Util.checkGLError();
//		
//		glBindVertexArray(VaoId);
//        Util.checkGLError();
//        
//        vertices = BufferUtils.createFloatBuffer(VerticesArray.length);
		
//		bbBuffer = BufferUtils.createFloatBuffer(MAX_PARTICLES * 3);
//	    vboVertexHandle = glGenBuffers();
//	    glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
//	    glBufferData(GL_ARRAY_BUFFER, bbBuffer, GL_STATIC_DRAW);
//	    glBindBuffer(GL_ARRAY_BUFFER, 0);
//		displayList = glGenLists(1);
		
//		buffer_id = glGenBuffersARB();
//		glBindBufferARB(GL_ARRAY_BUFFER_ARB, buffer_id);
//		vertices = BufferUtils.createFloatBuffer(BUFFER_SIZE); // ByteBuffer.allocateDirect(BUFFER_SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer();
//		glBufferDataARB(GL_ARRAY_BUFFER_ARB, BUFFER_SIZE, GL_STREAM_DRAW_ARB);
//		vertices.put(-50).put(-50).put(50).put(-50).put(50).put(50).put(-50).put(50);
//		glEnableClientState(GL_VERTEX_ARRAY);
//		glVertexPointer(3, GL_FLOAT, 0, 0);
		
		
//		vertices = BufferUtils.createFloatBuffer(BUFFER_SIZE);
//		vertices.put(new float[] {
//        		-1.0f, -1.0f, 1.0f,
//        		1.0f, -1.0f, 1.0f,
//        		1.0f, 1.0f, 1.0f,
//        		-1.0f, 1.0f, 1.0f,
//        		
//        		-1.0f, -1.0f, -1.0f,
//        		-1.0f, 1.0f, -1.0f,
//        		1.0f, 1.0f, -1.0f,
//        		1.0f, -1.0f, -1.0f,
//        		
//        		-1.0f, 1.0f, -1.0f,
//        		-1.0f, 1.0f, 1.0f,
//        		1.0f, 1.0f, 1.0f,
//        		1.0f, 1.0f, -1.0f,
//        		
//        		-1.0f, -1.0f, -1.0f,
//        		1.0f, -1.0f, -1.0f,
//        		1.0f, -1.0f, 1.0f,
//        		-1.0f, -1.0f, 1.0f,
//        		
//        		1.0f, -1.0f, -1.0f,
//        		1.0f, 1.0f, -1.0f,
//        		1.0f, 1.0f, 1.0f,
//        		1.0f, -1.0f, 1.0f,
//        		
//        		-1.0f, -1.0f, -1.0f,
//        		-1.0f, -1.0f, 1.0f,
//        		-1.0f, 1.0f, 1.0f,
//        		-1.0f, 1.0f, -1.0f});
//		vertices.rewind();

//		vertexBufferID = createVBOID();
//		bufferData(vertexBufferID, vertices);
		
		
		
//		float[] vertices2 = {
//				// Left bottom triangle
//				-0.5f, 0.5f, 0f,
//				-0.5f, -0.5f, 0f,
//				0.5f, -0.5f, 0f,
//				// Right top triangle
//				0.5f, -0.5f, 0f,
//				0.5f, 0.5f, 0f,
//				-0.5f, 0.5f, 0f
//				};
//		// Sending data to OpenGL requires the usage of (flipped) byte buffers
//		vertices.put(vertices2);
//		vertices.flip();
//		size = 6;

		
		
		vertexArrayID = glGenVertexArrays();
		glBindVertexArray(vertexArrayID);
		// A VBO is a collection of Vectors which in this case resemble the location of each vertex.
		vertexBufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		// Put the VBO in the attributes list at index 0
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		// Deselect (bind to 0) the VBO
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		// Deselect (bind to 0) the VAO
		glBindVertexArray(0);

	}

//	// glDeleteBuffersARB(buffer_id);
//
//	public static int createVBOID() {
//		IntBuffer buffer = BufferUtils.createIntBuffer(1);
//		glGenBuffers(buffer);
//		return buffer.get(0);
//	}
//	
//	public int createVAOID() {
//		return glGenVertexArrays();
//	}
//	
//	public static void bufferData(int id, FloatBuffer buffer) {
//		// glBindBufferARB(GL_ARRAY_BUFFER_ARB, id);
//		// glBufferDataARB(GL_ARRAY_BUFFER_ARB, buffer, GL_STATIC_DRAW_ARB);
//		// glBufferDataARB(GL_ARRAY_BUFFER_ARB, buffer, GL_STREAM_DRAW_ARB);
//		glBindBuffer(GL_ARRAY_BUFFER, id);
//		// glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
//		glBufferData(GL_ARRAY_BUFFER, BUFFER_SIZE, GL_DYNAMIC_DRAW);
//	}

	@Override
	public void before() {
		size = 0;
//		vertices.flip();
//		vertices.flip();
		// bbBuffer.clear();
//		vertices.rewind();
//		size = 0;
//		glNewList(displayList, GL_COMPILE);
		
	}
	
	@Override
	public void after() {
//		VboId = glGenBuffers();
//        Util.checkGLError();
//
//        glBindBuffer(GL_ARRAY_BUFFER, VboId);
//        Util.checkGLError();
//
//        // Transfer all data to the OpenGL-object, saying that it will
//        // be used for "static drawing", meaning we will not modify
//        // our vertex data in the future (so OpenGL can do optimizations).
//        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
//        Util.checkGLError();
		
//		if (size == 0) return;
//		bbBuffer.flip();
////		glEnableClientState(GL_VERTEX_ARRAY);
////		// glVertexPointer(3, 3 << 2,  bbBuffer);
////		glVertexPointer(3, 0,  bbBuffer);
////		logger.debug("run " + run + " size " + size);
//////		glPushMatrix();
//////		glEnable(GL_BLEND);
//////		glPointSize(5.0f);
//////		glColor4f(0.8f, 0.8f, 0.8f, 0.3f);
////		glDrawArrays(GL_POINTS, 0, size);
//////		glPopMatrix();
////		glDisableClientState(GL_VERTEX_ARRAY);
//		
//	
//		glPushMatrix();
//
//	    glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
//	    glVertexPointer(3, GL_FLOAT, 0, 0L);
//
//	    glEnableClientState(GL_VERTEX_ARRAY);
//	    glDrawArrays(GL_POINTS, 0, 3);
//	    glDisableClientState(GL_VERTEX_ARRAY);
//
//	    glPopMatrix();
//		
//		run++;
		
//		glPushMatrix();

//		ByteBuffer new_mapped_buffer = glMapBufferARB(GL_ARRAY_BUFFER_ARB, GL_WRITE_ONLY_ARB, mapped_buffer);
//		if (new_mapped_buffer != mapped_buffer) {
//			mapped_float_buffer = new_mapped_buffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
//		}
//		mapped_buffer = new_mapped_buffer;
//		mapped_float_buffer.rewind();
//		mapped_float_buffer.put(vertices);

		
		// int size = vertices.position();
		// vertices.rewind();
		
		/*

		if (size < 2) return;

		glBufferData(GL_ARRAY_BUFFER, BUFFER_SIZE, GL_DYNAMIC_DRAW);
		ByteBuffer driverSideBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, null);
		
//		ByteBuffer new_mapped_buffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, mapped_buffer);
//		if (new_mapped_buffer != mapped_buffer) {
//			mapped_float_buffer = new_mapped_buffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
//		}
//		mapped_buffer = new_mapped_buffer;
//		mapped_float_buffer.rewind();
//		mapped_float_buffer.put(vertices);
		
		glPushMatrix();
//		glEnableClientState(GL_VERTEX_ARRAY);
//		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID);
//		if (glUnmapBuffer(GL_ARRAY_BUFFER)) {
        glUnmapBuffer(GL_ARRAY_BUFFER);
        // glVertexPointer(3, GL_FLOAT, 0, 0);
        glVertexPointer(3, 0, vertices);
		logger.debug("draw " + vertexBufferID + " size: " + size);
		glDrawArrays(GL_POINTS, 0, size); // MAX_PARTICLES // *SIZE_PER_VERTICE
		driverSideBuffer = glMapBuffer(GL_ARRAY_BUFFER_ARB, GL_WRITE_ONLY, driverSideBuffer);
//		}
//		glDisableClientState(GL_VERTEX_ARRAY);
	    glPopMatrix();
	    
	    
	    */
	    
		// vertices.rewind();
		
		
		if (size < 2) return;
		vertices.rewind();
		// logger.debug("draw array " + vertexArrayID + " buffer " + vertexBufferID + " size: " + size);
	    glBindVertexArray(vertexArrayID);
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	    glEnableVertexAttribArray(0);
	    glDrawArrays(GL_POINTS, 0, size);
	    glDisableVertexAttribArray(0);
	    glBindVertexArray(0);
	}

	@Override
	public void render(Particle particle) {
//		vertices.put(particle.getX()).put(particle.getY()).put(particle.getZ());
		if (vertices.remaining() < 3) return;
//		logger.debug("remaining: " + vertices.remaining() + " capacity: " + vertices.capacity() + " position: " + vertices.position());
//		logger.debug("vert pos: " + vertices.position());
		vertices.put(particle.getX());
		vertices.put(particle.getY());
		vertices.put(particle.getZ());
		size++;
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
