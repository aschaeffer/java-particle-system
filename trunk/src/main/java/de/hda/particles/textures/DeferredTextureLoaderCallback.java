package de.hda.particles.textures;

import org.newdawn.slick.opengl.Texture;

public interface DeferredTextureLoaderCallback {

	public void setTexture(String key, Texture object);
	
	public void reportTextureLoadingError(String name);

}
