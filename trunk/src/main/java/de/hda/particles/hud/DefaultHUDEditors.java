package de.hda.particles.hud;

import java.util.ArrayList;
import java.util.List;

import de.hda.particles.editor.*;

public class DefaultHUDEditors {

	public static List<Editor> createEditors() {
		List<Editor> editors = new ArrayList<Editor>();
		editors.add(new PointParticleEmitterEditor());
		editors.add(new PlaneParticleEmitterEditor());
		editors.add(new PulseRatePointParticleEmitterEditor());
		editors.add(new FastPointParticleEmitterEditor());
		editors.add(new PooledPointParticleEmitterEditor());
		editors.add(new RingParticleEmitterEditor());
		editors.add(new SphereParticleEmitterEditor());
		editors.add(new GravityPointEditor());
		editors.add(new BlackHoleEditor());
		return editors;
	}
}
