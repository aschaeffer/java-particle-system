package de.hda.particles;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.lwjgl.input.Mouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.dao.ParticleSystemDAO;

public class ConfigurableParticleSystem extends AbstractParticleSystem implements ParticleSystem {

	private final ParticleSystemDAO particleSystemDAO;

	private Boolean loadDialogOnNextIteration = false;

	private String name = "Configurable Particle System";
	
	private final Logger logger = LoggerFactory.getLogger(ConfigurableParticleSystem.class);

	public ConfigurableParticleSystem() {
		this.particleSystemDAO = new ParticleSystemDAO();
	}

	public ConfigurableParticleSystem(ParticleSystemDAO particleSystemDAO) {
		this.particleSystemDAO = particleSystemDAO;
	}

	@Override
	public String getSystemName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	private void loadDialog() {
		pause();
		Mouse.setGrabbed(false);
		try {
			JFileChooser fileChooser = new JFileChooser("~/workspace4/particles/src/test/resources/config");
			fileChooser.setFileFilter(new FileNameExtensionFilter("System Configuration Files", "json"));
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				String filename = file.getAbsolutePath();
				particleSystemDAO.load(this, file);
			}
		} catch (Exception e) {
			logger.error("Failed loading scene", e);
		}
		Mouse.setGrabbed(true);
		pause();
	}

	private void saveDialog() {
		Mouse.setGrabbed(false);
		try {
			JFileChooser fileChooser = new JFileChooser("~/workspace4/particles/src/test/resources/config");
			fileChooser.setFileFilter(new FileNameExtensionFilter("Scene Configuration Files", "json"));
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				String filename = file.getAbsolutePath();
				particleSystemDAO.save(this, file);
				// hudManager.addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Successfully saved scene to " + filename));
			}
		} catch (Exception e) {
			// hudManager.addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Failed saving scene"));
		}
		Mouse.setGrabbed(true);
	}
	
	public void openLoadDialog() {
		loadDialogOnNextIteration = true;
		loadDialog();
	}

	public void openSaveDialog() {
		saveDialog();
	}

}
