package de.hda.particles.scene;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.ParticleSystem;

public class ConfigurableScene extends AbstractScene implements Scene {

	public final static String SYSTEM_NAME = "rendering";

	private Boolean blockFullscreenSelection = false;

	private final Logger logger = LoggerFactory.getLogger(ConfigurableScene.class);

    public ConfigurableScene(ParticleSystem particleSystem) {
    	super();
    	this.particleSystem = particleSystem;
    }

    public ConfigurableScene(ParticleSystem particleSystem, Integer width, Integer height) {
		super(width, height);
		this.particleSystem = particleSystem;
	}

	public void setup() {
		running = true;
		try {
			Display.setDisplayMode(new DisplayMode(this.width, this.height));
			Display.setFullscreen(fullscreen);
			Display.setTitle("Particle System Editor and Simulation");
			Display.create();
		} catch (LWJGLException e) {
			logger.error("could not open particle renderer!", e);
			return;
		}

		// init OpenGL here
		glDisable(GL_CULL_FACE);
        glEnable(GL_TEXTURE_2D);
        glShadeModel(GL_SMOOTH);

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glClearDepth(1.0f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glEnableClientState(GL_VERTEX_ARRAY);

		glViewport(0, 0, width, height);

		glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnableClientState(GL_COLOR_ARRAY);
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        
		glEnable(GL_POINT_SMOOTH);
        glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);

        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
 
        GLU.gluPerspective(90.0f, (float) width / (float) height, 0.1f, 5000.0f);

        glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		
		rendererManager.setup();
	}
	
	public void destroy() {
		rendererManager.destroy(); // also destroys all renderers, the cam and the hud
		Display.destroy();
	}

	public void update() {
		Keyboard.next();
		if (Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			logger.info("close requested");
			running = false;
			return;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
			if (!blockFullscreenSelection) {
				fullscreen = !fullscreen;
				fullscreen();
				blockFullscreenSelection = true;
			}
		} else {
			blockFullscreenSelection = false;
		}

        // Clear the screen and depth buffer
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// rendering
	    rendererManager.update();
		Display.update();

		super.update();
	}

	@Override
	public Boolean isFinished() {
		return !running;
	}

	@Override
	public String getSystemName() {
		return SYSTEM_NAME;
	}

}
