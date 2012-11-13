package de.hda.particles.textures;

import static org.lwjgl.opengl.GL11.GL_LINEAR;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TextureManager {

	private List<Texture> cache = new ArrayList<Texture>();

	public TextureManager() {}

	public Texture load(String format, String filename) {
		try {
			return TextureLoader.getTexture(format, ResourceLoader.getResourceAsStream(filename), GL_LINEAR);
		} catch (IOException e) {
			throw new RuntimeException("texture loading error: " + filename);
		}
	}
	
}
