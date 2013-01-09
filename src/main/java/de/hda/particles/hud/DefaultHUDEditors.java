package de.hda.particles.hud;

import java.util.ArrayList;
import java.util.List;

import de.hda.particles.editor.*;

/**
 * Loads editors which are enabled by default.
 * 
 * @author aschaeffer
 *
 */
public class DefaultHUDEditors {

	public static List<Editor> createEditors() {
		List<Editor> editors = new ArrayList<Editor>();
		editors.add(new PointParticleEmitterEditor());
		editors.add(new PlaneParticleEmitterEditor());
		editors.add(new PulseRatePointParticleEmitterEditor());
		editors.add(new StaticPointParticleEmitterEditor());
		editors.add(new PooledPointParticleEmitterEditor());
		editors.add(new PooledClothParticleEmitterEditor());
		editors.add(new PooledSoftBodyEmitterEditor());
		editors.add(new PooledTerrainGenerationEmitterEditor());
		editors.add(new PooledTetrahedronParticleEmitterEditor());
		editors.add(new RingParticleEmitterEditor());
		editors.add(new SphereParticleEmitterEditor());
		editors.add(new GravityPointEditor());
		editors.add(new GravityPulsarEditor());
		editors.add(new GravityPlaneEditor());
		editors.add(new BlackHoleEditor());
		editors.add(new CollisionPlaneEditor());
		return editors;
	}
}
