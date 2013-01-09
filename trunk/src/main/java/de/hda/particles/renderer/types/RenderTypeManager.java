package de.hda.particles.renderer.types;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.Particle;
import de.hda.particles.listener.ParticleLifetimeListener;
import de.hda.particles.renderer.AbstractRenderer;
import de.hda.particles.renderer.Renderer;
import de.hda.particles.scene.Scene;

public class RenderTypeManager extends AbstractRenderer implements Renderer, ParticleLifetimeListener {

	private final ArrayList<RenderType> renderTypes = new ArrayList<RenderType>();

	/**
	 * The particle cache increases performance. Also, we need a seperate list for
	 * incoming particles. The rare case, that one have to use LinkedList, is needed
	 * for adding and removing particles in constant time. Using ArrayList we could
	 * get a massive slowdown as soon as the first particles get removed.
	 */
	public HashMap<Integer, LinkedList<Particle>> renderTypeParticleCache = new HashMap<Integer, LinkedList<Particle>>();
	public HashMap<Integer, ArrayList<Particle>> newParticles = new HashMap<Integer, ArrayList<Particle>>();

	private final Logger logger = LoggerFactory.getLogger(RenderTypeManager.class);

	public RenderTypeManager() {}

	public RenderTypeManager(Scene scene) {
		this.scene = scene;
	}
	
	public Integer add(RenderType renderType) {
		renderType.addDependencies();
		renderTypes.add(renderType);
		renderTypeParticleCache.put(renderTypes.size(), new LinkedList<Particle>());
		newParticles.put(renderTypes.size(), new ArrayList<Particle>());
		return renderTypes.size(); // index+1
	}

	public Integer add(Class<? extends RenderType> renderTypeClass) {
		try {
			RenderType renderType = renderTypeClass.newInstance();
			renderType.setScene(scene);
			renderType.addDependencies();
			renderTypes.add(renderType);
			renderTypeParticleCache.put(renderTypes.size(), new LinkedList<Particle>());
			newParticles.put(renderTypes.size(), new ArrayList<Particle>());
			return renderTypes.size(); // index+1
		} catch (Exception e) {
			logger.error("could not create render type: " + e.getMessage(), e);
			return 0; // defaults to zero, if render type couldn't be created
		}
	}
	
	public void replace(Class<? extends RenderType> renderTypeClass, Integer index) {
		if (index < 1 || index > renderTypes.size()) return;
		index--;
		try {
			RenderType renderType = renderTypeClass.newInstance();
			renderType.setScene(scene);
			renderType.addDependencies();
			renderTypes.set(index, renderType);
		} catch (Exception e) {
			logger.error("could not create render type: " + e.getMessage(), e);
		}
	}
	
	public List<RenderType> getRenderTypes() {
		return renderTypes;
	}
	
	public String getRenderTypeName(final Integer index) {
		if (index <= 0) return "DISABLED";
		if (index > renderTypes.size()) return "FREE SLOT (" + index + ")";
		return renderTypes.get(index-1).getName();
	}
	
	public void clear() {
		Iterator<Integer> rIterator = renderTypeParticleCache.keySet().iterator();
		while(rIterator.hasNext()) {
			Integer renderTypeIndex = rIterator.next();
			renderTypeParticleCache.get(renderTypeIndex).clear();
			newParticles.get(renderTypeIndex).clear();
		}
		logger.info("Cleared RenderType Cache");
	}
	
	public void debug() {
		Iterator<Integer> rIterator = renderTypeParticleCache.keySet().iterator();
		while(rIterator.hasNext()) {
			Integer renderTypeIndex = rIterator.next();
			ListIterator<Particle> pIterator = renderTypeParticleCache.get(renderTypeIndex).listIterator(0);
			while (pIterator.hasNext()) {
				Particle p = pIterator.next();
				logger.info("deleted? " + scene.getParticleSystem().getParticles().contains(p) + " --- " + p.toString());

			}
		}
	}
	
	@Override
	public void setup() {
		// register listener to get updates about newly created particles and removed particles
		scene.getParticleSystem().addParticleListener(this);
		ListIterator<RenderType> iterator = renderTypes.listIterator(0);
		while (iterator.hasNext()) {
			iterator.next().setScene(scene);
		}
	}
	
	@Override
	public void destroy() {
		scene.getParticleSystem().removeParticleListener(this);
		renderTypes.clear();
		renderTypeParticleCache.clear();
		newParticles.clear();
	}

	@Override
	public void update() {
		ListIterator<RenderType> rIterator = renderTypes.listIterator(0);
		while(rIterator.hasNext()) {
			RenderType renderType = rIterator.next();
			Integer renderTypeIndex = rIterator.nextIndex(); // index+1
			renderType.before();
			// we have to copy the whole arraylist to prevent slowdowns -- not anymore: LinkedList is faster than cloning ArrayLists and prevents synchronization issues
			List<Particle> particlesByIndex = renderTypeParticleCache.get(renderTypeIndex);
			particlesByIndex.addAll(newParticles.get(renderTypeIndex));
			newParticles.get(renderTypeIndex).clear();
			ListIterator<Particle> pIterator = particlesByIndex.listIterator(0);
			while (pIterator.hasNext()) {
				Particle particle = pIterator.next();
				if (particle == null) continue;
				if (particle.getRemainingIterations() <= 0) { // be sure they will be removed
					pIterator.remove();
				} else {
					renderType.render(particle);
				}
			}
			renderType.after();
		}
	}
	
	public void loadShader(String filename) {
		/*
		ClassLoader loader = RenderTypeManager.class.getClassLoader();
		InputStream in = loader.getResourceAsStream("shader" + filename);
		byte[] shadercode = null;
		try {
		    DataInputStream dis = new DataInputStream(in);
		    dis.readFully(shadercode = new byte[in.available()]);
		    dis.close();
		    in.close();
		    ByteBuffer shader = BufferUtils.createByteBuffer(shadercode.length);
		    shader.put(shadercode);
		    shader.flip();
		    int vertexShaderID = ARBShaderObjects.glCreateShaderObjectARB(ARBVertexShader.GL_VERTEX_SHADER_ARB);
		    int pixelShaderID = ARBShaderObjects.glCreateShaderObjectARB(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
		    ARBShaderObjects.glShaderSourceARB(vertexShaderID, vertexShader);
		    ARBShaderObjects.glCompileShaderARB(vertexShaderID);
		    ARBShaderObjects.glShaderSourceARB(pixelShaderID, pixelShader);
		    ARBShaderObjects.glCompileShaderARB(pixelShaderID);
		} catch (IOException e) {
			logger.error("can't loading shader " + filename, e);
		}
		*/
	}

	/**
	 * Inserts newly created particles into cache
	 */
	@Override
	public void onParticleCreation(Particle particle) {
		if (particle.getRenderTypeIndex() > 0 && particle.getRenderTypeIndex() <= renderTypes.size())
			newParticles.get(particle.getRenderTypeIndex()).add(particle);
	}

	@Override
	public void onParticleDeath(Particle particle) {
		// nothing to do, because the render type manager itself removes particles
		// from the cache
	}

}
