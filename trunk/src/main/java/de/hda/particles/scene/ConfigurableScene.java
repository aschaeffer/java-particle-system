package de.hda.particles.scene;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH_HINT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_POINT_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_POINT_SMOOTH_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glViewport;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.ParticleSystem;
import de.hda.particles.dao.SceneDAO;
import de.hda.particles.hud.HUDCommand;
import de.hda.particles.hud.HUDCommandTypes;

public class ConfigurableScene extends AbstractScene implements Scene {

	public final static String SYSTEM_NAME = "rendering";
	
	private final SceneDAO sceneDAO;

	private Boolean blockFullscreenSelection = false;
//	private final Boolean blockLoadSceneSelection = false;
//	private final Boolean blockSaveSceneSelection = false;
	private Boolean loadDialogOnNextIteration = false;

	private final Logger logger = LoggerFactory.getLogger(ConfigurableScene.class);

    public ConfigurableScene(ParticleSystem particleSystem) {
    	super();
    	this.particleSystem = particleSystem;
    	this.sceneDAO = new SceneDAO();
    }

    public ConfigurableScene(ParticleSystem particleSystem, SceneDAO sceneDAO) {
    	super();
    	this.particleSystem = particleSystem;
    	this.sceneDAO = sceneDAO;
    }

    public ConfigurableScene(ParticleSystem particleSystem, SceneDAO sceneDAO, Integer width, Integer height) {
		super(width, height);
		this.particleSystem = particleSystem;
    	this.sceneDAO = sceneDAO;
	}

	@Override
	public void setup() {
		running = true;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			logger.error("could not set native look and feel", e);
		}
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
 
        // starting with defaults...
        GLU.gluPerspective(90.0f, (float) width / (float) height, 0.1f, 5000.0f);

        glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		
		rendererManager.setup(); // also setup all renderers, the cam and the hud
	}
	
	@Override
	public void destroy() {
		rendererManager.destroy(); // also destroys all renderers, the cam and the hud
		Display.destroy();
	}

	@Override
	public void update() {
		Keyboard.next();
		if (Display.isCloseRequested()) { //  || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)
			logger.debug("close requested");
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
//		if (Keyboard.isKeyDown(Keyboard.KEY_F1)) {
//			if (!blockSaveSceneSelection) {
//				saveDialog();
//				blockSaveSceneSelection = true;
//			}
//		} else {
//			blockSaveSceneSelection = false;
//		}
//		if (Keyboard.isKeyDown(Keyboard.KEY_F2)) {
//			if (!blockLoadSceneSelection) {
//				loadDialog();
//				blockLoadSceneSelection = true;
//			}
//		} else {
//			blockLoadSceneSelection = false;
//		}
		if (loadDialogOnNextIteration) {
			loadDialogOnNextIteration = !loadDialogOnNextIteration;
			loadDialog();
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
		return name;
	}
	
	private void loadDialog() {
		particleSystem.pause();
		Mouse.setGrabbed(false);
		try {
			JFileChooser fileChooser = new JFileChooser("~/workspace4/particles/src/test/resources/config");
			fileChooser.setFileFilter(new FileNameExtensionFilter("Scene Configuration Files", "json"));
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				String filename = file.getAbsolutePath();
				sceneDAO.load(this, file);
				hudManager.addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Successfully loaded scene " + filename));
			}
		} catch (Exception e) {
			hudManager.addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Failed loading scene"));
			logger.error("Failed loading scene", e);
		}
		try {
			DisplayMode newDisplayMode = new DisplayMode(width, height);
			Display.setDisplayMode(newDisplayMode);
			glViewport(0, 0, newDisplayMode.getWidth(), newDisplayMode.getHeight());
		} catch (LWJGLException e) {
			logger.error("Failed setting display mode", e);
		}
		Mouse.setGrabbed(true);
		particleSystem.pause();
	}

	private void saveDialog() {
		Mouse.setGrabbed(false);
		try {
			JFileChooser fileChooser = new JFileChooser("~/workspace4/particles/src/test/resources/config");
			fileChooser.setFileFilter(new FileNameExtensionFilter("Scene Configuration Files", "json"));
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				String filename = file.getAbsolutePath();
				sceneDAO.save(this, file);
				hudManager.addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Successfully saved scene to " + filename));
			}
		} catch (Exception e) {
			hudManager.addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Failed saving scene"));
		}
		Mouse.setGrabbed(true);
	}
	
	public void openLoadDialog() {
		loadDialogOnNextIteration = true;
	}

	public void openSaveDialog() {
		saveDialog();
	}

}
