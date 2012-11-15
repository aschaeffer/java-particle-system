package de.hda.particles.hud;

import java.util.ListIterator;

import org.lwjgl.input.Keyboard;

import de.hda.particles.scene.Scene;
import de.hda.particles.timing.FpsInformation;

public class FpsHUD extends AbstractHUD implements HUD {

	private Boolean blockMaxFpsSelection = false;
	private Integer normalFps = 60;

	public FpsHUD() {}

	public FpsHUD(Scene scene) {
		super(scene);
	}

	@Override
	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_HOME)) {
			if (!blockMaxFpsSelection) {
				normalFps = scene.getParticleSystem().getMaxFps();
				scene.getParticleSystem().setMaxFps(1000);
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Disabled FPS Limitation"));
				blockMaxFpsSelection = true;
			}
		} else {
			if (blockMaxFpsSelection) {
				scene.getParticleSystem().setMaxFps(normalFps);
				scene.getHudManager().addCommand(new HUDCommand(HUDCommandTypes.MESSAGE, "Enabled FPS Limitation"));
				blockMaxFpsSelection = false;
			}
		}

		StringBuilder b = new StringBuilder();
		ListIterator<FpsInformation> iterator = scene.getFpsInformationInstances().listIterator(0);
		while (iterator.hasNext()) {
			FpsInformation fpsInformationInstance = iterator.next();
			b.append(String.format("%s: %.0f fps  ", fpsInformationInstance.getSystemName(), fpsInformationInstance.getFps()));
		}
        font.drawString(10, scene.getHeight() - 20, b.toString());
	}

	@Override
	public void setup() {
		super.setup();
	}

}
