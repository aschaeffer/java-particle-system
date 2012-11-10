package de.hda.particles;

import static org.junit.Assert.*;

import org.junit.Test;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.ParticleEmitterConfiguration;
import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.emitter.PlaneParticleEmitter;
import de.hda.particles.emitter.PointParticleEmitter;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.features.ParticleInitialVelocityScatter;
import de.hda.particles.features.ParticleSize;
import de.hda.particles.modifier.GravityPoint;
import de.hda.particles.modifier.ParticleCulling;
import de.hda.particles.modifier.ParticleLinearColorTransformation;
import de.hda.particles.modifier.ParticleLinearSizeTransformation;
import de.hda.particles.modifier.ParticleVelocityTransformation;
import de.hda.particles.scene.ParticleSystemScene;

public class SimpleParticleSystemTest {

//	@Test
//	public void particleCreationTest() {
//		ParticleSystem particleSystem = new ParticleSystem();
//		particleSystem.addParticleEmitter(PointParticleEmitter.class, new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 1.0f), 10, ParticleEmitterConfiguration.EMPTY);
//		particleSystem.addParticleModifier(ParticleDebugger.class, ParticleModifierConfiguration.EMPTY);
//		particleSystem.update();
//		assertTrue(true);
//	}

	@Test
	public void fullParticleSystemTest() throws InterruptedException {
		
		ParticleSystem particleSystem = new ParticleSystem();
		particleSystem.addParticleFeature(new ParticleInitialVelocityScatter());
		particleSystem.addParticleFeature(new ParticleColor());
		particleSystem.addParticleFeature(new ParticleSize());
		
		ParticleEmitterConfiguration pec1 = new ParticleEmitterConfiguration();
		pec1.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.5f, 0.0f, 0.5f));
		pec1.put(ParticleColor.START_COLOR, new Color(255, 0, 0, 48));
		pec1.put(ParticleColor.END_COLOR, new Color(255, 255, 100, 48));
		pec1.put(ParticleSize.START_SIZE, 5.0f);
		pec1.put(ParticleSize.END_SIZE, 10.0f);
		particleSystem.addParticleEmitter(
				PointParticleEmitter.class,
				new Vector3f(0.0f, 200.0f, 0.0f),
				new Vector3f(0.0f, 1.0f, 0.0f),
				2336,
				pec1);

		ParticleEmitterConfiguration pec2 = new ParticleEmitterConfiguration();
		pec2.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(-0.5f, 0.0f, -0.5f));
		pec2.put(ParticleColor.START_COLOR, new Color(255, 100, 100, 48));
		pec2.put(ParticleColor.END_COLOR, new Color(255, 100, 255, 48));
		pec2.put(ParticleSize.START_SIZE, 5.0f);
		pec2.put(ParticleSize.END_SIZE, 5.0f);
		particleSystem.addParticleEmitter(
				PointParticleEmitter.class,
				new Vector3f(0.0f, -200.0f, 0.0f),
				new Vector3f(0.0f, -1.0f, 0.0f),
				2336,
				pec2);

		ParticleEmitterConfiguration pec3 = new ParticleEmitterConfiguration();
		pec3.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.0f, 0.5f, 0.5f));
		pec3.put(ParticleColor.START_COLOR, new Color(255, 255, 100, 48));
		pec3.put(ParticleColor.END_COLOR, new Color(100, 255, 255, 48));
		pec3.put(ParticleSize.START_SIZE, 7.0f);
		pec3.put(ParticleSize.END_SIZE, 5.0f);
		particleSystem.addParticleEmitter(
				PointParticleEmitter.class,
				new Vector3f(200.0f, 0.0f, 0.0f),
				new Vector3f(1.0f, 0.0f, 0.0f),
				2336,
				pec3);

		ParticleEmitterConfiguration pec4 = new ParticleEmitterConfiguration();
		pec4.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.0f, -0.5f, -0.5f));
		pec4.put(ParticleColor.START_COLOR, new Color(100, 255, 255, 48));
		pec4.put(ParticleColor.END_COLOR, new Color(100, 255, 100, 48));
		pec4.put(ParticleSize.START_SIZE, 5.0f);
		pec4.put(ParticleSize.END_SIZE, 20.0f);
		particleSystem.addParticleEmitter(
				PointParticleEmitter.class,
				new Vector3f(-200.0f, 0.0f, 0.0f),
				new Vector3f(-1.0f, 0.0f, 0.0f),
				2336,
				pec4);

		ParticleEmitterConfiguration pec5 = new ParticleEmitterConfiguration();
		pec5.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.5f, 0.5f, 0.0f));
		pec5.put(ParticleColor.START_COLOR, new Color(100, 100, 255, 48));
		pec5.put(ParticleColor.END_COLOR, new Color(100, 255, 255, 48));
		pec5.put(ParticleSize.START_SIZE, 5.0f);
		pec5.put(ParticleSize.END_SIZE, 12.0f);
		particleSystem.addParticleEmitter(
				PointParticleEmitter.class,
				new Vector3f(0.0f, 0.0f, 200.0f),
				new Vector3f(0.0f, 0.0f, 1.0f),
				2336,
				pec5);

		ParticleEmitterConfiguration pec6 = new ParticleEmitterConfiguration();
		pec6.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(-0.5f, -0.5f, 0.0f));
		pec6.put(ParticleColor.START_COLOR, new Color(255, 100, 255, 48));
		pec6.put(ParticleColor.END_COLOR, new Color(100, 255, 100, 48));
		pec6.put(ParticleSize.START_SIZE, 20.0f);
		pec6.put(ParticleSize.END_SIZE, 1.0f);
		pec6.put("ROTATE_ORIGIN", 1.0f);
		particleSystem.addParticleEmitter(
				PointParticleEmitter.class,
				new Vector3f(0.0f, 0.0f, -200.0f),
				new Vector3f(0.0f, 0.0f, -1.0f),
				2336,
				pec6);

		/**
		 * Blauer, ins schwarzer gehender Regen.
		 */
		ParticleEmitterConfiguration pec7 = new ParticleEmitterConfiguration();
		pec7.put(PlaneParticleEmitter.POSITION2, new Vector3f(600.0f, 0.0f, 600.0f));
		pec7.put(PlaneParticleEmitter.VELOCITY, 1.2f);
		pec7.put(ParticleColor.START_COLOR, new Color(222, 222, 255, 255));
		pec7.put(ParticleColor.END_COLOR, new Color(0, 0, 128, 48));
		pec7.put(ParticleSize.START_SIZE, 5.0f);
		pec7.put(ParticleSize.END_SIZE, 5.0f);
		particleSystem.addParticleEmitter(
				PlaneParticleEmitter.class,
				new Vector3f(-600.0f, 0.0f, -600.0f),
				new Vector3f(0.0f, -10.0f, 0.0f),
				8336,
				pec7);

		ParticleModifierConfiguration particleCullerConfiguration = new ParticleModifierConfiguration();
		particleCullerConfiguration.put(ParticleCulling.POINT1, new Vector3f(-5000.0f, -5000.0f, -5000.0f));
		particleCullerConfiguration.put(ParticleCulling.POINT2, new Vector3f(5000.0f, 5000.0f, 5000.0f));
		particleSystem.addParticleModifier(ParticleCulling.class, particleCullerConfiguration);

		particleSystem.addParticleModifier(ParticleLinearColorTransformation.class, ParticleModifierConfiguration.EMPTY);

		particleSystem.addParticleModifier(ParticleLinearSizeTransformation.class, ParticleModifierConfiguration.EMPTY);

//		ParticleModifierConfiguration gravityPlaneConfiguration = new ParticleModifierConfiguration();
//		gravityPlaneConfiguration.put(GravityPlane.POINT_1, new Vector3f(-100.0f, -100.0f, -50.0f));
//		gravityPlaneConfiguration.put(GravityPlane.POINT_2, new Vector3f(100.0f, 100.0f, -50.0f));
//		gravityPlaneConfiguration.put(GravityPlane.GRAVITY, new Float(0.36f));
//		particleSystem.addParticleModifier(GravityPlane.class, gravityPlaneConfiguration);

		ParticleModifierConfiguration gravityPointConfiguration = new ParticleModifierConfiguration();
		gravityPointConfiguration.put(GravityPoint.POINT, new Vector3f(100.0f, 100.0f, 100.0f));
		gravityPointConfiguration.put(GravityPoint.MASS, new Float(1.0f));
		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration);

//		ParticleModifierConfiguration gravityPointConfiguration2 = new ParticleModifierConfiguration();
//		gravityPointConfiguration2.put(GravityPoint.POINT, new Vector3f(700.0f, 350.0f, 50.0f));
//		gravityPointConfiguration2.put(GravityPoint.GRAVITY, new Float(0.000175f));
//		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration2);

//		ParticleModifierConfiguration gravityPointConfiguration2 = new ParticleModifierConfiguration();
//		gravityPointConfiguration2.put(GravityPoint.POINT, new Vector3f(-450.0f, 0.0f, -250.0f));
//		gravityPointConfiguration2.put(GravityPoint.GRAVITY, new Float(0.036f));
//		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration2);

		particleSystem.addParticleModifier(ParticleVelocityTransformation.class, ParticleModifierConfiguration.EMPTY);
		
		ParticleSystemScene particleScene = new ParticleSystemScene(particleSystem, 800, 600);

		SystemManager systemManager = new SystemManager();
		systemManager.add("ParticleSystem", particleSystem);
		systemManager.add("ParticleScene", particleScene);
		systemManager.start();
		assertTrue(true);
		
		
		// Notiz:
		//   Masse-Feder-Emitter: Aktuell emittierter Partikel wird mit den vorigen
		//   drei ausgestoßenen partikeln verbunden -> so bildet sich eine Kette
		
		// 
	}


}
