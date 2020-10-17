package de.hda.particles.hud.impl;

import de.hda.particles.hud.HUD;
import java.util.HashMap;
import java.util.ListIterator;

import org.lwjgl.input.Keyboard;

import de.hda.particles.scene.Scene;
import de.hda.particles.timing.FpsInformation;

public class FpsHUD extends AbstractHUD implements HUD {

	private Boolean blockMaxFpsSelection = false;
	private final HashMap<FpsInformation, Integer> normalFps = new HashMap<FpsInformation, Integer>();

	public FpsHUD() {}

	public FpsHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void update() {
		StringBuilder b = new StringBuilder();
		ListIterator<FpsInformation> iterator = scene.getFpsInformationInstances().listIterator(0);
		while (iterator.hasNext()) {
			FpsInformation fpsInformationInstance = iterator.next();
			b.append(String.format("%s: %.0f fps  ", fpsInformationInstance.getSystemName(), fpsInformationInstance.getFps()));
		}
        font.drawString(10, scene.getHeight() - 20, b.toString());
	}
	
	@Override
	public void input() {
		if (Keyboard.isKeyDown(Keyboard.KEY_F7)) {
			if (!blockMaxFpsSelection) {
				normalFps.clear();
				for (FpsInformation fpsInstance : scene.getFpsInformationInstances()) {
					normalFps.put(fpsInstance, fpsInstance.getMaxFps());
					fpsInstance.setMaxFps(1000);
				}
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Disabled FPS Limitation"));
				blockMaxFpsSelection = true;
			}
		} else {
			if (blockMaxFpsSelection) {
				for (FpsInformation fpsInstance : scene.getFpsInformationInstances()) {
					fpsInstance.setMaxFps(normalFps.get(fpsInstance));
				}
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.NOTICE, "Enabled FPS Limitation"));
				blockMaxFpsSelection = false;
			}
		}
	}

	@Override
	public void executeCommand(HUDCommand command) {
		if (command.getType() == HUDCommandTypes.MAX_FPS) {
			scene.setMaxFps((Integer) command.getPayLoad());
		}
	}

	@Override
	public void setup() {
		super.setup();
	}

}
