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
			fileChooser.setFileFilter(new FileNameExtensionFilter("Particle System Configuration Files", "json"));
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				particleSystemDAO.load(this, file);
			}
		} catch (Exception e) {
			logger.error("Failed loading scene: " + e.getMessage(), e);
		}
		Mouse.setGrabbed(true);
		pause();
	}

	private void saveDialog() {
		Mouse.setGrabbed(false);
		try {
			JFileChooser fileChooser = new JFileChooser("~/workspace4/particles/src/test/resources/config");
			fileChooser.setFileFilter(new FileNameExtensionFilter("Particle System Configuration Files", "json"));
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				particleSystemDAO.save(this, file);
			}
		} catch (Exception e) {
			logger.debug("failed to save particle system: " + e.getMessage(), e);
		}
		Mouse.setGrabbed(true);
	}
	
	public void openLoadDialog() {
		loadDialog();
	}

	public void openSaveDialog() {
		saveDialog();
	}

}
