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
import de.hda.particles.camera.CameraManager;
import de.hda.particles.camera.FirstPersonCamera;
import de.hda.particles.hud.CameraHUD;
import de.hda.particles.hud.FpsHUD;
import de.hda.particles.hud.HUDManager;
import de.hda.particles.hud.ParticleCountHUD;
import de.hda.particles.renderer.AxisRenderer;
import de.hda.particles.renderer.EmitterRenderer;
import de.hda.particles.renderer.ExtendedParticleRenderer;
import de.hda.particles.renderer.GravityPointRenderer;
import de.hda.particles.renderer.RendererManager;
import de.hda.particles.renderer.SkyBoxRenderer;

public class ParticleSystemScene extends AbstractScene implements Scene {

	private ParticleSystem particleSystem;
	
    private final Logger logger = LoggerFactory.getLogger(ParticleSystemScene.class);
 
	public ParticleSystemScene(ParticleSystem particleSystem, Integer width, Integer height) {
		super(width, height);
		this.particleSystem = particleSystem;
	}

	public void setup() {
		running = true;
		try {
			Display.setDisplayMode(new DisplayMode(this.width, this.height));
			Display.setFullscreen(fullscreen);
			Display.setTitle("ParticleRenderer");
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
 
        GLU.gluPerspective(fov, (float) width / (float) height, 0.1f, 5000.0f);

        glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        
		// init camera manager and the first person camera
        cameraManager = new CameraManager();
        cameraManager.add(new FirstPersonCamera("default cam", 425.0f, 195.0f, 345.0f, 125.0f, -10.0f, 0.0f));
        cameraManager.add(new FirstPersonCamera("origin cam", 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
        cameraManager.add(new FirstPersonCamera("third cam", 270.0f, 0.0f, 270.0f, 122.0f, 120.0f, 104.0f));

		// init hud manager and some HUDs
		hudManager = new HUDManager(width, height);
		hudManager.add(new CameraHUD(cameraManager, width, height));
		hudManager.add(new FpsHUD(this, particleSystem, width, height));
		hudManager.add(new ParticleCountHUD(particleSystem, width, height));

        // init renderer manager and some renderer
		rendererManager = new RendererManager();
		rendererManager.add(cameraManager);
		rendererManager.add(new SkyBoxRenderer(cameraManager));
		rendererManager.add(new AxisRenderer());
		rendererManager.add(new EmitterRenderer(particleSystem));
		rendererManager.add(new GravityPointRenderer(particleSystem));
		// rendererManager.add(new ParticleRenderer(particleSystem));
		rendererManager.add(new ExtendedParticleRenderer(particleSystem));
		rendererManager.add(hudManager);

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
			fullscreen = !fullscreen;
			try {
				if (fullscreen) {
					Display.setDisplayMode(Display.getDesktopDisplayMode());
				}
				Display.setFullscreen(fullscreen);
			} catch (LWJGLException e) {
				logger.error("coulnd not change fullscreen mode", e);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_N)) {
			maxFps++;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
			maxFps--;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
			setFov(fov*1.2f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
			setFov(fov/1.2f);
		}


        // Clear the screen and depth buffer
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// first look through the camera
	    // cameraManager.update(); vvvv

		// rendering
	    rendererManager.update();

	    // render huds ^^^^
	    // hudManager.update();

		Display.update();

		super.update();

	}

	@Override
	public void setFov(Float fov) {
		super.setFov(fov);
		logger.debug("Set FOV to " + fov);
	}

	@Override
	public Boolean isFinished() {
		return !running;
	}

}
