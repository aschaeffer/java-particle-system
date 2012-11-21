package de.hda.particles;

import static org.junit.Assert.*;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.dao.SceneDAO;
import de.hda.particles.domain.ParticleEmitterConfiguration;
import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.emitter.PlaneParticleEmitter;
import de.hda.particles.emitter.PointParticleEmitter;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.features.ParticleInitialVelocityScatter;
import de.hda.particles.features.ParticleSize;
import de.hda.particles.modifier.BlackHole;
import de.hda.particles.modifier.GravityPlane;
import de.hda.particles.modifier.GravityPoint;
import de.hda.particles.modifier.BoundingBoxParticleCulling;
import de.hda.particles.modifier.ParticleLinearColorTransformation;
import de.hda.particles.modifier.ParticleLinearSizeTransformation;
import de.hda.particles.modifier.ParticleVelocityTransformation;
import de.hda.particles.modifier.RainbowColorTransformation;
import de.hda.particles.modifier.RandomColorTransformation;
import de.hda.particles.scene.DefaultScene;
import de.hda.particles.scene.Scene;

public class SimpleParticleSystemTest {

	private final Logger logger = LoggerFactory.getLogger(SimpleParticleSystemTest.class);

	@Test
	public void configurableParticleSystemTest() {
		try {
			ParticleSystemManager particleSystemManager = new ParticleSystemManager();
			ParticleSystem particleSystem = particleSystemManager.load("/config/system-empty.json");

			SceneDAO sceneDAO = new SceneDAO();
			Scene scene = sceneDAO.create("/config/scene-empty.json", particleSystem);
			sceneDAO.save(scene, "/tmp/test.json");

			SystemRunner systemRunner = new SystemRunner();
			systemRunner.add(particleSystem.getSystemName(), particleSystem);
			systemRunner.add(scene.getSystemName(), scene);
			systemRunner.start();
			assertTrue(true);
		} catch (Exception e) {
			logger.error("hmm", e);
		}
	}


//	@Test
//	public void particleCreationTest() {
//		IParticleSystem particleSystem = new IParticleSystem();
//		particleSystem.addParticleEmitter(PointParticleEmitter.class, new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 1.0f), 10, ParticleEmitterConfiguration.EMPTY);
//		particleSystem.addParticleModifier(ParticleDebugger.class, ParticleModifierConfiguration.EMPTY);
//		particleSystem.update();
//		assertTrue(true);
//	}

	// @Test
	public void fullParticleSystemTest() throws InterruptedException {
		
		Integer defaultRenderType = 1;
		Integer complexPointRenderType = 2;
		Integer rainRenderType = 3;
		Integer coloredPointRenderType = 4;
		Integer triangleRenderType = 5;
		Integer triangleStripRenderType = 6;
		Integer triangleFanRenderType = 7;
		Integer quadsRenderType = 8;
		Integer lineStripRenderType = 9;
		Integer pointSpriteRenderType = 10;
		Integer velocityRenderType = 11;
		
		ParticleSystem particleSystem = new DefaultParticleSystem();
		particleSystem.addParticleFeature(new ParticleInitialVelocityScatter());
		particleSystem.addParticleFeature(new ParticleColor());
		particleSystem.addParticleFeature(new ParticleSize());
		
//		ParticleEmitterConfiguration pec1 = new ParticleEmitterConfiguration();
////		pec1.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.5f, 0.0f, 0.5f));
//		pec1.put(ParticleColor.START_COLOR, new Color(255, 0, 0, 48));
//		pec1.put(ParticleColor.END_COLOR, new Color(255, 255, 100, 48));
//		pec1.put(ParticleSize.START_SIZE, 5.0f);
//		pec1.put(ParticleSize.END_SIZE, 10.0f);
//		particleSystem.addParticleEmitter(
//				PointParticleEmitter.class,
//				new Vector3f(0.0f, 200.0f, 0.0f),
//				new Vector3f(0.0f, 1.0f, 0.0f),
//				complexPointRenderType,
//				1336,
//				pec1);
//
//		ParticleEmitterConfiguration pec2 = new ParticleEmitterConfiguration();
////		pec2.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(-3.5f, 1.0f, -8.5f));
//		pec2.put(ParticleColor.START_COLOR, new Color(255, 100, 100, 48));
//		pec2.put(ParticleColor.END_COLOR, new Color(255, 100, 255, 48));
//		pec2.put(ParticleSize.START_SIZE, 5.0f);
//		pec2.put(ParticleSize.END_SIZE, 5.0f);
//		particleSystem.addParticleEmitter(
//				PointParticleEmitter.class,
//				new Vector3f(0.0f, -200.0f, 0.0f),
//				new Vector3f(0.0f, -5.0f, 0.0f),
//				triangleFanRenderType,
//				100,
//				pec2);
//
//		ParticleEmitterConfiguration pec3 = new ParticleEmitterConfiguration();
////		pec3.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.0f, 0.8f, 0.8f));
//		pec3.put(ParticleColor.START_COLOR, new Color(255, 255, 100, 48));
//		pec3.put(ParticleColor.END_COLOR, new Color(100, 255, 255, 48));
//		pec3.put(ParticleSize.START_SIZE, 7.0f);
//		pec3.put(ParticleSize.END_SIZE, 5.0f);
//		particleSystem.addParticleEmitter(
//				PointParticleEmitter.class,
//				new Vector3f(200.0f, 0.0f, 0.0f),
//				new Vector3f(1.2f, 0.0f, 0.0f),
//				triangleStripRenderType,
//				200,
//				pec3);
//
//		ParticleEmitterConfiguration pec4 = new ParticleEmitterConfiguration();
////		pec4.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.0f, -0.5f, -0.5f));
//		pec4.put(ParticleColor.START_COLOR, new Color(100, 255, 255, 48));
//		pec4.put(ParticleColor.END_COLOR, new Color(100, 255, 100, 48));
//		pec4.put(ParticleSize.START_SIZE, 5.0f);
//		pec4.put(ParticleSize.END_SIZE, 20.0f);
//		particleSystem.addParticleEmitter(
//				PointParticleEmitter.class,
//				new Vector3f(-200.0f, 0.0f, 0.0f),
//				new Vector3f(-1.0f, 0.0f, 0.0f),
//				coloredPointRenderType,
//				1336,
//				pec4);
//
//		ParticleEmitterConfiguration pec5 = new ParticleEmitterConfiguration();
////		pec5.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.5f, 0.5f, 0.0f));
//		pec5.put(ParticleColor.START_COLOR, new Color(100, 100, 255, 48));
//		pec5.put(ParticleColor.END_COLOR, new Color(100, 255, 255, 48));
//		pec5.put(ParticleSize.START_SIZE, 5.0f);
//		pec5.put(ParticleSize.END_SIZE, 12.0f);
//		particleSystem.addParticleEmitter(
//				PointParticleEmitter.class,
//				new Vector3f(0.0f, 0.0f, 200.0f),
//				new Vector3f(0.0f, 0.0f, 1.0f),
//				triangleRenderType,
//				1336,
//				pec5);
//
//		ParticleEmitterConfiguration pec6 = new ParticleEmitterConfiguration();
//		// pec6.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(-5.5f, -5.5f, 0.0f));
////		pec6.put(ParticleColor.START_COLOR, new Color(255, 100, 255, 48));
////		pec6.put(ParticleColor.END_COLOR, new Color(100, 255, 100, 48));
////		pec6.put(ParticleSize.START_SIZE, 20.0f);
////		pec6.put(ParticleSize.END_SIZE, 1.0f);
////		pec6.put("ROTATE_ORIGIN", 1.0f);
////		pec6.put(PlaneParticleEmitter.POSITION2, new Vector3f(50.0f, 50.0f, -200.0f));
//		particleSystem.addParticleEmitter(
//				PointParticleEmitter.class,
//				new Vector3f(0.0f, 0.0f, -200.0f),
//				new Vector3f(0.0f, 0.0f, -0.1f),
//				pointSpriteRenderType, // lineStripRenderType,
//				1000,
//				pec6);

		/**
		 * Blauer, ins schwarzer gehender Regen.
		 */
//		ParticleEmitterConfiguration pec7 = new ParticleEmitterConfiguration();
//		pec7.put(PlaneParticleEmitter.POSITION2, new Vector3f(600.0f, 0.0f, 600.0f));
////		pec7.put(PlaneParticleEmitter.VELOCITY, 0.0f);
////		pec7.put(ParticleColor.START_COLOR, new Color(222, 222, 255, 255));
////		pec7.put(ParticleColor.END_COLOR, new Color(0, 0, 128, 48));
////		pec7.put(ParticleSize.START_SIZE, 5.0f);
////		pec7.put(ParticleSize.END_SIZE, 5.0f);
//		particleSystem.addParticleEmitter(
//				PlaneParticleEmitter.class,
//				new Vector3f(-600.0f, 0.0f, -600.0f),
//				new Vector3f(0.0f, -4.0f, 0.0f),
//				rainRenderType,
//				1300,
//				pec7);

//		ParticleModifierConfiguration particleCullerConfiguration = new ParticleModifierConfiguration();
//		particleCullerConfiguration.put(BoundingBoxParticleCulling.BOUNDING_BOX_POINT1, new Vector3f(-5000.0f, -5000.0f, -5000.0f));
//		particleCullerConfiguration.put(BoundingBoxParticleCulling.BOUNDING_BOX_POINT2, new Vector3f(5000.0f, 5000.0f, 5000.0f));
//		particleSystem.addParticleModifier(BoundingBoxParticleCulling.class, particleCullerConfiguration);

		particleSystem.addParticleModifier(ParticleLinearColorTransformation.class, ParticleModifierConfiguration.EMPTY);

		particleSystem.addParticleModifier(ParticleLinearSizeTransformation.class, ParticleModifierConfiguration.EMPTY);

//		ParticleModifierConfiguration gravityPlaneConfiguration = new ParticleModifierConfiguration();
//		gravityPlaneConfiguration.put(GravityPlane.POINT_1, new Vector3f(-100.0f, -100.0f, -50.0f));
//		gravityPlaneConfiguration.put(GravityPlane.POINT_2, new Vector3f(100.0f, 100.0f, -50.0f));
//		gravityPlaneConfiguration.put(GravityPlane.GRAVITY, new Float(0.36f));
//		particleSystem.addParticleModifier(GravityPlane.class, gravityPlaneConfiguration);

//		ParticleModifierConfiguration gravityPointConfiguration = new ParticleModifierConfiguration();
//		gravityPointConfiguration.put(GravityPoint.POINT, new Vector3f(-600.0f, 100.0f, 300.0f));
//		gravityPointConfiguration.put(GravityPoint.MASS, new Float(1000.0f));
//		gravityPointConfiguration.put(GravityPoint.GRAVITY, new Float(2.0f));
//		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration);
//
//		ParticleModifierConfiguration gravityPointConfiguration2 = new ParticleModifierConfiguration();
//		gravityPointConfiguration2.put(GravityPoint.POINT, new Vector3f(700.0f, 350.0f, 50.0f));
//		gravityPointConfiguration2.put(GravityPoint.MASS, new Float(1000.0f));
//		gravityPointConfiguration2.put(GravityPoint.GRAVITY, new Float(2.0f));
//		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration2);
//
//		ParticleModifierConfiguration gravityPointConfiguration3 = new ParticleModifierConfiguration();
//		gravityPointConfiguration3.put(GravityPoint.POINT, new Vector3f(400.0f, -350.0f, -750.0f));
//		gravityPointConfiguration3.put(GravityPoint.MASS, new Float(1000.0f));
//		gravityPointConfiguration3.put(GravityPoint.GRAVITY, new Float(2.0f));
//		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration3);
//
//		ParticleModifierConfiguration gravityPointConfiguration4 = new ParticleModifierConfiguration();
//		gravityPointConfiguration4.put(GravityPoint.POINT, new Vector3f(-800.0f, -250.0f, -750.0f));
//		gravityPointConfiguration4.put(GravityPoint.MASS, new Float(1000.0f));
//		gravityPointConfiguration4.put(GravityPoint.GRAVITY, new Float(2.0f));
//		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration4);
//
//		ParticleModifierConfiguration gravityPointConfiguration5 = new ParticleModifierConfiguration();
//		gravityPointConfiguration5.put(GravityPoint.POINT, new Vector3f(0.0f, -450.0f, 350.0f));
//		gravityPointConfiguration5.put(GravityPoint.MASS, new Float(1000.0f));
//		gravityPointConfiguration5.put(GravityPoint.GRAVITY, new Float(2.0f));
//		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration5);

		particleSystem.addParticleModifier(ParticleVelocityTransformation.class, ParticleModifierConfiguration.EMPTY);
		
		DefaultScene particleScene = new DefaultScene(particleSystem);

		SystemRunner systemRunner = new SystemRunner();
		systemRunner.add("ParticleSystem", particleSystem);
		systemRunner.add("ParticleScene", particleScene);
		systemRunner.start();
		assertTrue(true);
		
	}

	public void sceneManagerSystemTest() throws InterruptedException, JsonParseException, JsonMappingException, ClassNotFoundException, IOException {
		
		Integer defaultRenderType = 1;
		Integer complexPointRenderType = 2;
		Integer rainRenderType = 3;
		Integer coloredPointRenderType = 4;
		Integer triangleRenderType = 5;
		Integer triangleStripRenderType = 6;
		Integer triangleFanRenderType = 7;
		Integer quadsRenderType = 8;
		Integer lineStripRenderType = 9;
		Integer pointSpriteRenderType = 10;
		Integer velocityRenderType = 11;
		
		ParticleSystem particleSystem = new DefaultParticleSystem();
		particleSystem.addParticleFeature(new ParticleInitialVelocityScatter());
		particleSystem.addParticleFeature(new ParticleColor());
		particleSystem.addParticleFeature(new ParticleSize());
		
//		ParticleEmitterConfiguration pec1 = new ParticleEmitterConfiguration();
////		pec1.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.5f, 0.0f, 0.5f));
//		pec1.put(ParticleColor.START_COLOR, new Color(255, 0, 0, 48));
//		pec1.put(ParticleColor.END_COLOR, new Color(255, 255, 100, 48));
////		pec1.put(ParticleSize.START_SIZE, 5.0f);
////		pec1.put(ParticleSize.END_SIZE, 10.0f);
//		particleSystem.addParticleEmitter(
//				PointParticleEmitter.class,
//				new Vector3f(0.0f, 200.0f, 0.0f),
//				new Vector3f(0.0f, 1.0f, 0.0f),
//				velocityRenderType,
//				10000,
//				pec1);

//		ParticleEmitterConfiguration pec2 = new ParticleEmitterConfiguration();
//		pec2.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(-0.5f, 0.0f, -0.5f));
//		pec2.put(ParticleColor.START_COLOR, new Color(255, 100, 100, 48));
//		pec2.put(ParticleColor.END_COLOR, new Color(255, 100, 255, 48));
////		pec2.put(ParticleSize.START_SIZE, 5.0f);
////		pec2.put(ParticleSize.END_SIZE, 5.0f);
//		particleSystem.addParticleEmitter(
//				PointParticleEmitter.class,
//				new Vector3f(0.0f, -200.0f, 0.0f),
//				new Vector3f(0.0f, -3.0f, 0.0f),
//				velocityRenderType, // triangleFanRenderType,
//				1000,
//				pec2);

//		ParticleEmitterConfiguration pec3 = new ParticleEmitterConfiguration();
//		pec3.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.0f, 0.8f, 0.8f));
//		pec3.put(ParticleColor.START_COLOR, new Color(255, 255, 100, 48));
//		pec3.put(ParticleColor.END_COLOR, new Color(100, 255, 255, 48));
//		pec3.put(ParticleSize.START_SIZE, 7.0f);
//		pec3.put(ParticleSize.END_SIZE, 5.0f);
//		particleSystem.addParticleEmitter(
//				PointParticleEmitter.class,
//				new Vector3f(200.0f, 0.0f, 0.0f),
//				new Vector3f(1.2f, 0.0f, 0.0f),
//				triangleStripRenderType,
//				200,
//				pec3);

//		ParticleEmitterConfiguration pec4 = new ParticleEmitterConfiguration();
////		pec4.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.0f, -0.5f, -0.5f));
//		pec4.put(ParticleColor.START_COLOR, new Color(100, 255, 255, 48));
//		pec4.put(ParticleColor.END_COLOR, new Color(100, 255, 100, 48));
//		pec4.put(ParticleSize.START_SIZE, 5.0f);
//		pec4.put(ParticleSize.END_SIZE, 20.0f);
//		particleSystem.addParticleEmitter(
//				PointParticleEmitter.class,
//				new Vector3f(-200.0f, 0.0f, 0.0f),
//				new Vector3f(-1.0f, 0.0f, 0.0f),
//				velocityRenderType,
//				1336,
//				pec4);

//		ParticleEmitterConfiguration pec5 = new ParticleEmitterConfiguration();
//		pec5.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.5f, 0.5f, 0.0f));
//		pec5.put(ParticleColor.START_COLOR, new Color(100, 100, 255, 48));
//		pec5.put(ParticleColor.END_COLOR, new Color(100, 255, 255, 48));
//		pec5.put(ParticleSize.START_SIZE, 5.0f);
//		pec5.put(ParticleSize.END_SIZE, 12.0f);
//		particleSystem.addParticleEmitter(
//				PointParticleEmitter.class,
//				new Vector3f(0.0f, 0.0f, 200.0f),
//				new Vector3f(0.0f, 0.0f, 1.0f),
//				triangleRenderType,
//				1336,
//				pec5);

		ParticleEmitterConfiguration pec6 = new ParticleEmitterConfiguration();
		// pec6.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(-5.5f, -5.5f, 0.0f));
		pec6.put(ParticleInitialVelocityScatter.SCATTER_X, 0.0f);
		pec6.put(ParticleInitialVelocityScatter.SCATTER_Y, 0.0f);
		pec6.put(ParticleInitialVelocityScatter.SCATTER_Z, 0.4f);
//		pec6.put(ParticleColor.START_COLOR, new Color(255, 100, 255, 48));
//		pec6.put(ParticleColor.END_COLOR, new Color(100, 255, 100, 48));
//		pec6.put(ParticleSize.START_SIZE, 20.0f);
//		pec6.put(ParticleSize.END_SIZE, 1.0f);
//		pec6.put("ROTATE_ORIGIN", 1.0f);
//		pec6.put(PlaneParticleEmitter.POSITION2, new Vector3f(50.0f, 50.0f, -200.0f));
//		particleSystem.addParticleEmitter(
//				PointParticleEmitter.class,
//				new Vector3f(0.0f, 0.0f, -200.0f),
//				new Vector3f(0.0f, 0.0f, -2.8f),
//				lineStripRenderType, // pointSpriteRenderType, 
//				20000,
//				pec6);

//		/**
//		 * Blauer, ins schwarzer gehender Regen.
//		 */
//		ParticleEmitterConfiguration pec7 = new ParticleEmitterConfiguration();
//		pec7.put(PlaneParticleEmitter.POSITION2, new Vector3f(600.0f, 0.0f, 600.0f));
////		pec7.put(PlaneParticleEmitter.VELOCITY, 0.0f);
////		pec7.put(ParticleColor.START_COLOR, new Color(222, 222, 255, 255));
////		pec7.put(ParticleColor.END_COLOR, new Color(0, 0, 128, 48));
////		pec7.put(ParticleSize.START_SIZE, 5.0f);
////		pec7.put(ParticleSize.END_SIZE, 5.0f);
//		particleSystem.addParticleEmitter(
//				PlaneParticleEmitter.class,
//				new Vector3f(-600.0f, 0.0f, -600.0f),
//				new Vector3f(0.0f, -4.0f, 0.0f),
//				rainRenderType,
//				1300,
//				pec7);

//		ParticleModifierConfiguration particleCullerConfiguration = new ParticleModifierConfiguration();
//		particleCullerConfiguration.put(BoundingBoxParticleCulling.BOUNDING_BOX_POINT1, new Vector3f(-5000.0f, -5000.0f, -5000.0f));
//		particleCullerConfiguration.put(BoundingBoxParticleCulling.BOUNDING_BOX_POINT2, new Vector3f(5000.0f, 5000.0f, 5000.0f));
		// particleSystem.addParticleModifier(ParticleCulling.class, particleCullerConfiguration);

		// particleSystem.addParticleModifier(RainbowColorTransformation.class, ParticleModifierConfiguration.EMPTY);

		// particleSystem.addParticleModifier(RandomColorTransformation.class, ParticleModifierConfiguration.EMPTY);

		particleSystem.addParticleModifier(ParticleLinearColorTransformation.class, ParticleModifierConfiguration.EMPTY);

		// particleSystem.addParticleModifier(ParticleLinearSizeTransformation.class, ParticleModifierConfiguration.EMPTY);

//		ParticleModifierConfiguration gravityPlaneConfiguration = new ParticleModifierConfiguration();
//		gravityPlaneConfiguration.put(GravityPlane.POINT_1, new Vector3f(-100.0f, -100.0f, -50.0f));
//		gravityPlaneConfiguration.put(GravityPlane.POINT_2, new Vector3f(100.0f, 100.0f, -50.0f));
//		gravityPlaneConfiguration.put(GravityPlane.GRAVITY, new Float(0.36f));
//		particleSystem.addParticleModifier(GravityPlane.class, gravityPlaneConfiguration);

//		ParticleModifierConfiguration gravityPointConfiguration = new ParticleModifierConfiguration();
//		gravityPointConfiguration.put(GravityPoint.POINT, new Vector3f(-600.0f, 100.0f, 300.0f));
//		gravityPointConfiguration.put(GravityPoint.MASS, new Float(500.0f));
//		gravityPointConfiguration.put(GravityPoint.GRAVITY, new Float(2.0f));
//		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration);
//
//		ParticleModifierConfiguration gravityPointConfiguration2 = new ParticleModifierConfiguration();
//		gravityPointConfiguration2.put(GravityPoint.POINT, new Vector3f(700.0f, 350.0f, 50.0f));
//		gravityPointConfiguration2.put(GravityPoint.MASS, new Float(500.0f));
//		gravityPointConfiguration2.put(GravityPoint.GRAVITY, new Float(2.0f));
//		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration2);
//
//		ParticleModifierConfiguration gravityPointConfiguration3 = new ParticleModifierConfiguration();
//		gravityPointConfiguration3.put(GravityPoint.POINT, new Vector3f(400.0f, -350.0f, -750.0f));
//		gravityPointConfiguration3.put(GravityPoint.MASS, new Float(500.0f));
//		gravityPointConfiguration3.put(GravityPoint.GRAVITY, new Float(2.0f));
//		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration3);
//
//		ParticleModifierConfiguration gravityPointConfiguration4 = new ParticleModifierConfiguration();
//		gravityPointConfiguration4.put(GravityPoint.POINT, new Vector3f(-800.0f, -250.0f, -750.0f));
//		gravityPointConfiguration4.put(GravityPoint.MASS, new Float(500.0f));
//		gravityPointConfiguration4.put(GravityPoint.GRAVITY, new Float(2.0f));
//		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration4);
//
//		ParticleModifierConfiguration gravityPointConfiguration5 = new ParticleModifierConfiguration();
//		gravityPointConfiguration5.put(GravityPoint.POINT, new Vector3f(0.0f, -450.0f, 350.0f));
//		gravityPointConfiguration5.put(GravityPoint.MASS, new Float(500.0f));
//		gravityPointConfiguration5.put(GravityPoint.GRAVITY, new Float(2.0f));
//		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration5);

//		ParticleModifierConfiguration blackHoleConfiguration1 = new ParticleModifierConfiguration();
//		blackHoleConfiguration1.put(BlackHole.POINT, new Vector3f(0.0f, 0.0f, 0.0f));
//		blackHoleConfiguration1.put(BlackHole.MASS, new Float(250.0f));
//		blackHoleConfiguration1.put(BlackHole.GRAVITY, new Float(1.25f));
//		// blackHoleConfiguration1.put(BlackHole.EVENT_HORIZON, new Float(80.0f));
//		particleSystem.addParticleModifier(BlackHole.class, blackHoleConfiguration1);

		ParticleModifierConfiguration gravityPlaneConfiguration1 = new ParticleModifierConfiguration();
		// Liegt parallel zur x/z-Ebene
		gravityPlaneConfiguration1.put(GravityPlane.POINT, new Vector3f(0.0f, -1500.0f, 0.0f));
		gravityPlaneConfiguration1.put(GravityPlane.VECTOR_1, new Vector3f(1.0f, 0.0f, 0.0f));
		gravityPlaneConfiguration1.put(GravityPlane.VECTOR_2, new Vector3f(0.0f, 0.0f, 1.0f));
		gravityPlaneConfiguration1.put(GravityPlane.MASS, new Float(-50.0f));
		gravityPlaneConfiguration1.put(GravityPlane.GRAVITY, new Float(-2.0f));
		// particleSystem.addParticleModifier(GravityPlane.class, gravityPlaneConfiguration1);

		ParticleModifierConfiguration gravityPlaneConfiguration2 = new ParticleModifierConfiguration();
		// Liegt parallel zur x/z-Ebene
		gravityPlaneConfiguration2.put(GravityPlane.POINT, new Vector3f(0.0f, +1500.0f, 0.0f));
		gravityPlaneConfiguration2.put(GravityPlane.VECTOR_1, new Vector3f(-1.0f, 0.0f, 0.0f));
		gravityPlaneConfiguration2.put(GravityPlane.VECTOR_2, new Vector3f(0.0f, 0.0f, -1.0f));
		gravityPlaneConfiguration2.put(GravityPlane.MASS, new Float(-50.0f));
		gravityPlaneConfiguration2.put(GravityPlane.GRAVITY, new Float(-2.0f));
		// particleSystem.addParticleModifier(GravityPlane.class, gravityPlaneConfiguration2);

		particleSystem.addParticleModifier(ParticleVelocityTransformation.class, ParticleModifierConfiguration.EMPTY);

// Future
//		ParticleSystemManager particleSystemManager = new ParticleSystemManager();
//		ParticleSystem particleSystem = particleSystemManager.create("config/system1.json");
		
		try {
			SceneDAO sceneManager = new SceneDAO();
			Scene scene = sceneManager.create("/config/scene1.json", particleSystem);
			sceneManager.save(scene, "/tmp/test.json");

			SystemRunner systemRunner = new SystemRunner();
			systemRunner.add(particleSystem.getSystemName(), particleSystem);
			systemRunner.add(scene.getSystemName(), scene);
			systemRunner.start();
			assertTrue(true);
		} catch (Exception e) {
			logger.error("hmm", e);
		}

		
	}



}
