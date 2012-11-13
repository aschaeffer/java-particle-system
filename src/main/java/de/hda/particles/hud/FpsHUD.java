package de.hda.particles.hud;

import java.util.ListIterator;

import de.hda.particles.scene.Scene;
import de.hda.particles.timing.FpsInformation;

public class FpsHUD extends AbstractHUD implements HUD {

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
	public void setup() {
		super.setup();
	}

}
