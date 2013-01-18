package de.hda.particles.scene;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.ParticleSystem;
import de.hda.particles.camera.FirstPersonCamera;
import de.hda.particles.hud.CameraHUD;
import de.hda.particles.hud.CrosshairHUD;
import de.hda.particles.hud.FpsHUD;
import de.hda.particles.hud.ParticleSystemControlHUD;
import de.hda.particles.renderer.AxisRenderer;
import de.hda.particles.renderer.EmitterRenderer;
import de.hda.particles.renderer.GravityPointRenderer;
import de.hda.particles.renderer.SkyBoxRenderer;
import de.hda.particles.renderer.particles.*;

public class DefaultScene extends AbstractScene implements Scene {

	public final static String SYSTEM_NAME = "rendering";

	private Boolean blockFullscreenSelection = false;

	private final Logger logger = LoggerFactory.getLogger(DefaultScene.class);

    public DefaultScene(ParticleSystem particleSystem) {
    	super();
    	this.particleSystem = particleSystem;
    }

    public DefaultScene(ParticleSystem particleSystem, Integer width, Integer height) {
		super(width, height);
		this.particleSystem = particleSystem;
	}

	@Override
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

		logger.info("width: " + width + " height: " + height);
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
        
		// init camera manager and the first person camera
        cameraManager.add(new FirstPersonCamera("default cam", this, new Vector3f(425.0f, 195.0f, 345.0f), 125.0f, -10.0f, 0.0f, 90.0f));
        cameraManager.add(new FirstPersonCamera("origin cam", this, new Vector3f(0.0f, 0.0f, 0.0f), 0.0f, 0.0f, 0.0f, 90.0f));
        cameraManager.add(new FirstPersonCamera("third cam", this, new Vector3f(270.0f, 0.0f, 270.0f), 122.0f, 120.0f, 104.0f, 90.0f));

        // init fps information instances
		fpsInformationInstances.add(this);
		fpsInformationInstances.add(particleSystem);

		// init hud manager and some HUDs
		hudManager.setScene(this);
		hudManager.add(new CrosshairHUD(this));
		hudManager.add(new CameraHUD(this));
		hudManager.add(new FpsHUD(this));
		hudManager.add(new ParticleSystemControlHUD(this));
		
		// init editor manager
		editorManager.setScene(this);
		menuManager.setScene(this);
		
		// init render type manager and some render types
		particleRendererManager.setScene(this);
		try {
			particleRendererManager.add(SimplePointParticleRenderer.class); // idx 1
			particleRendererManager.add(ComplexPointParticleRenderer.class); // idx 2
			particleRendererManager.add(SimpleRainParticleRenderer.class); // idx 3
			particleRendererManager.add(ColoredPointParticleRenderer.class); // idx 4
			particleRendererManager.add(SimpleTriangleParticleRenderer.class); // idx 5
			particleRendererManager.add(SimpleTriangleStripParticleRenderer.class); // idx 6
			particleRendererManager.add(SimpleTriangleFanParticleRenderer.class); // idx 7
			particleRendererManager.add(SimpleQuadsParticleRenderer.class); // idx 8
			particleRendererManager.add(SimpleLineStripParticleRenderer.class); // idx 9
			particleRendererManager.add(BallParticleRenderer.class); // idx 10
			// particleRendererManager.setup(); <-- hier oder unten?
		} catch (Exception e) {
			
		}

        // init renderer manager and some renderer
		rendererManager.setScene(this);
		rendererManager.add(cameraManager);
		rendererManager.add(new SkyBoxRenderer());
		rendererManager.add(new AxisRenderer());
		rendererManager.add(new EmitterRenderer());
		rendererManager.add(new GravityPointRenderer());
		// old style (still working)
		//   rendererManager.add(new SimpleParticleRenderer(particleSystem));
		//   rendererManager.add(new ExtendedParticleRenderer(particleSystem));
		// the render type manager can handle multiple particle renderers (render types)
		// it delegates particle rendering to the specific render type of the single particle
		rendererManager.add(particleRendererManager); 
		rendererManager.add(hudManager);

		rendererManager.setup();
	}
	
	@Override
	public void destroy() {
		rendererManager.destroy(); // also destroys all renderers, the cam and the hud
		Display.destroy();
	}

	@Override
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
