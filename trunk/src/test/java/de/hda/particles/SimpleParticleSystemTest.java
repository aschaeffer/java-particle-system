package de.hda.particles;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.camera.FirstPersonCamera;
import de.hda.particles.camera.ParticleOriginCamera;
import de.hda.particles.camera.ParticlePOVCamera;
import de.hda.particles.camera.TopDownCamera;
import de.hda.particles.dao.ParticleSystemDAO;
import de.hda.particles.dao.SceneDAO;
import de.hda.particles.domain.ChangeSet;
import de.hda.particles.domain.CommandConfiguration;
import de.hda.particles.domain.ParticleEmitterConfiguration;
import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.editor.*;
import de.hda.particles.emitter.PooledClothParticleEmitter;
import de.hda.particles.features.*;
import de.hda.particles.hud.*;
import de.hda.particles.modifier.BoundingBoxParticleCulling;
import de.hda.particles.modifier.ParticleLimiter;
import de.hda.particles.modifier.PositionPathBuffering;
import de.hda.particles.modifier.PositionablePointModifier;
import de.hda.particles.modifier.color.RandomStartColor;
import de.hda.particles.modifier.gravity.GravityBase;
import de.hda.particles.modifier.size.PulseSizeTransformation;
import de.hda.particles.modifier.velocity.MassSpringTransformation;
import de.hda.particles.modifier.velocity.FixedParticlesVelocityBlocker;
import de.hda.particles.modifier.velocity.VelocityDamper;
import de.hda.particles.modifier.velocity.VelocityTransformation;
import de.hda.particles.overlay.AxisOverlay;
import de.hda.particles.overlay.EmitterOverlay;
import de.hda.particles.overlay.PositionablePointModifierOverlay;
import de.hda.particles.renderer.*;
import de.hda.particles.renderer.faces.*;
import de.hda.particles.renderer.particles.*;
import de.hda.particles.scene.Scene;
import de.hda.particles.scene.command.*;
import de.hda.particles.scene.demo.DefaultDemoManager;
import de.hda.particles.timing.FpsInformation;

public class SimpleParticleSystemTest {

	private final Logger logger = LoggerFactory.getLogger(SimpleParticleSystemTest.class);

	@Test
	public void minimalEmitterTest() {
		
		// demo manager
		DefaultDemoManager demoManager = new DefaultDemoManager();

		// particle system
		CommandConfiguration particleSystemConfiguration = new CommandConfiguration();
		particleSystemConfiguration.put("name", "Minimal Particle System");
		demoManager.addChangeSet(new ChangeSet(0, CreateParticleSystem.class.getName(), particleSystemConfiguration));

		// scene
		CommandConfiguration sceneConfiguration = new CommandConfiguration();
		sceneConfiguration.put(Scene.NAME, "Minimal Rendering");
		sceneConfiguration.put(Scene.WIDTH, 1200);
		sceneConfiguration.put(Scene.HEIGHT, 620);
		sceneConfiguration.put(Scene.FULLSCREEN, false);
		sceneConfiguration.put(Scene.VSYNC, false);
		sceneConfiguration.put(FpsInformation.MAX_FPS, 1000);
		demoManager.addChangeSet(new ChangeSet(0, CreateScene.class.getName(), sceneConfiguration));

		// huds
//		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", HelpHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", CrosshairHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", CameraHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", FpsHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", ParticleSystemControlHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", MessageHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", MenuHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", MainMenuHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", TopMenuHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", EmitterHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", ModifierHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", FeatureHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", RendererHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", EditorHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", ParticleRendererHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", FaceRendererHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", CaptureHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", DemoHUD.class.getName())));

		// editors
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", PointParticleEmitterEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", PlaneParticleEmitterEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", PooledPulseRatePointParticleEmitterEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", StaticPointParticleEmitterEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", PooledPointParticleEmitterEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", PooledClothParticleEmitterEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", PooledSoftBodyEmitterEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", PooledTerrainGenerationEmitterEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", PooledTetrahedronParticleEmitterEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", PooledSphereParticleEmitterEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", SphereParticleEmitterEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", GravityPointEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", GravityPulsarEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", GravityPlaneEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", BlackHoleEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", CollisionPlaneEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", ParticleLimiterEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", BoundingBoxParticleCullingEditor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddEditor.class.getName(), new CommandConfiguration("class", ParticleGravityTransformationEditor.class.getName())));

		// renderers
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", SkyBoxRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", EmitterRenderer.class.getName())));

		// system runner
		demoManager.addChangeSet(new ChangeSet(0, CreateSystemRunner.class.getName(), new CommandConfiguration()));

		// modifiers
		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", VelocityTransformation.class.getName())));
		// demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", BoundingBoxParticleCulling.class.getName())));

		// particle renderers
		demoManager.addChangeSet(new ChangeSet(0, AddParticleRenderer.class, new CommandConfiguration("class", SimplePointParticleRenderer.class.getName())));
		// demoManager.addChangeSet(new ChangeSet(0, AddParticleRenderer.class, new CommandConfiguration("class", BallParticleRenderer.class.getName())));

		// face renderers
		demoManager.addChangeSet(new ChangeSet(0, AddFaceRenderer.class, new CommandConfiguration("class", PolygonFaceRenderer.class.getName())));

		CommandConfiguration cam1 = new CommandConfiguration();
		cam1.put("class", FirstPersonCamera.class.getName());
		cam1.put("id", 1000);
		cam1.put("name", "top cam");
		cam1.put("x", 425.0);
		cam1.put("y", 195.0);
		cam1.put("z", 345.0);
		cam1.put("yaw", 305.0);
		cam1.put("pitch", -10.0);
		cam1.put("roll", 0.0);
		cam1.put("fov", 90.0);
		demoManager.addChangeSet(new ChangeSet(0, AddCamera.class.getName(), cam1));

		demoManager.save("minimal.demo");

		try {
			demoManager.run();
		} catch (Exception e) {
			logger.error("Error in executing DemoManager", e);
		}
		assertTrue(true);
	}

//	@Test
	public void terrainGenerationEmitterTest() {
		
		// demo manager
		DefaultDemoManager demoManager = new DefaultDemoManager();

		// particle system
		CommandConfiguration c1 = new CommandConfiguration();
		c1.put("name", "Demo Particle System");
		demoManager.addChangeSet(new ChangeSet(0, CreateParticleSystem.class.getName(), c1));

		// scene
		CommandConfiguration c2 = new CommandConfiguration();
		c2.put("name", "Demo Particle Rendering");
		c2.put("width", 1200);
		c2.put("height", 620);
		c2.put("fullscreen", false);
		demoManager.addChangeSet(new ChangeSet(0, CreateScene.class.getName(), c2));

		// huds
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", HelpHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", CrosshairHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", CameraHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", FpsHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", ParticleSystemControlHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", MessageHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", MenuHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", MainMenuHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", TopMenuHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", EmitterHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", ModifierHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", FeatureHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", RendererHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", ParticleRendererHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", EditorHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", TextOverlayHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", FaceRendererHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", CaptureHUD.class.getName())));

		// renderers
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", SkyBoxRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", AxisRenderer.class.getName())));
//		demoManager.addChangeSet(new ChangeSet(0, CreateRenderer.class.getName(), new CommandConfiguration("class", FixedPointRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", SpringRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", FixedPointSpringRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", EmitterRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", GravityPointRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", GravityPulsarRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", GravityPlaneRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", BlackHoleRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", CollisionPlaneRenderer.class.getName())));

		// text overlays
		demoManager.addChangeSet(new ChangeSet(0, AddTextOverlay.class.getName(), new CommandConfiguration("class", AxisOverlay.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddTextOverlay.class.getName(), new CommandConfiguration("class", EmitterOverlay.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddTextOverlay.class.getName(), new CommandConfiguration("class", PositionablePointModifierOverlay.class.getName())));

		// system runner
		demoManager.addChangeSet(new ChangeSet(0, CreateSystemRunner.class.getName(), new CommandConfiguration()));

		// particle features
		demoManager.addChangeSet(new ChangeSet(0, AddFeature.class.getName(), new CommandConfiguration("class", ParticleColor.class.getName())));
		// demoManager.addChangeSet(new ChangeSet(0, AddFeature.class.getName(), new CommandConfiguration("class", ParticleInitialVelocityScatter.class.getName())));
		
		// modifiers
		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", VelocityTransformation.class.getName())));
//		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", VelocityDamper.class.getName())));
//		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", MassSpringTransformation.class.getName())));
//		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", RandomStartColor.class.getName())));

		// render types
		demoManager.addChangeSet(new ChangeSet(0, AddParticleRenderer.class, new CommandConfiguration("class", SpringPolygonsParticleRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddParticleRenderer.class, new CommandConfiguration("class", BallParticleRenderer.class.getName())));

		CommandConfiguration cam1 = new CommandConfiguration();
		cam1.put("class", FirstPersonCamera.class.getName());
		cam1.put("id", 1000);
		cam1.put("name", "top cam");
		cam1.put("x", 425.0);
		cam1.put("y", 195.0);
		cam1.put("z", 345.0);
		cam1.put("yaw", 305.0);
		cam1.put("pitch", -10.0);
		cam1.put("roll", 0.0);
		cam1.put("fov", 90.0);
		demoManager.addChangeSet(new ChangeSet(0, AddCamera.class.getName(), cam1));

		CommandConfiguration cam2 = new CommandConfiguration();
		cam2.put("class", ParticlePOVCamera.class.getName());
		cam2.put("id", 1001);
		cam2.put("name", "particle surf camera");
		cam2.put("x", 0.0);
		cam2.put("y", 0.0);
		cam2.put("z", 0.0);
		cam2.put("yaw", 0.0);
		cam2.put("pitch", 0.0);
		cam2.put("roll", 0.0);
		cam2.put("fov", 90.0);
		demoManager.addChangeSet(new ChangeSet(0, AddCamera.class.getName(), cam2));

		CommandConfiguration cam3 = new CommandConfiguration();
		cam3.put("class", ParticleOriginCamera.class.getName());
		cam3.put("id", 1002);
		cam3.put("name", "particle to origin camera");
		cam3.put("x", 0.0);
		cam3.put("y", 0.0);
		cam3.put("z", 0.0);
		cam3.put("yaw", 0.0);
		cam3.put("pitch", 0.0);
		cam3.put("roll", 0.0);
		cam3.put("fov", 90.0);
		demoManager.addChangeSet(new ChangeSet(0, AddCamera.class.getName(), cam3));

		CommandConfiguration c10 = new CommandConfiguration();
		c10.put("id", 6);
		c10.put("position_x", 0.0);
		c10.put("position_y", 0.0);
		c10.put("position_z", 0.0);
		c10.put("velocity_x", 0.0);
		c10.put("velocity_y", 0.0);
		c10.put("velocity_z", 0.0);
		c10.put("rate", 1);
		c10.put("lifetime", 200000);
		c10.put("particleRenderer", 1);
		ParticleEmitterConfiguration pec1 = new ParticleEmitterConfiguration();
		// pec1.put(ParticleInitialVelocityScatter.SCATTER_X, 0.2);
		c10.put("configuration", pec1);
		demoManager.addChangeSet(new ChangeSet(30, 0, AddTerrainGenerationEmitter.class.getName(), c10));

		demoManager.save("terrainGenerationEmitter.demo");

		try {
			demoManager.run();
		} catch (Exception e) {
			logger.error("Error in executing DemoManager", e);
		}
		assertTrue(true);
	}

//	@Test
	public void clothTest() {
		DefaultDemoManager demoManager = new DefaultDemoManager();

		CommandConfiguration c1 = new CommandConfiguration();
		c1.put("name", "Demo Particle System");
		demoManager.addChangeSet(new ChangeSet(0, CreateParticleSystem.class.getName(), c1));

		CommandConfiguration c2 = new CommandConfiguration();
		c2.put("name", "Demo Particle Rendering");
		c2.put("width", 1200);
		c2.put("height", 620);
		c2.put("fullscreen", false);
		demoManager.addChangeSet(new ChangeSet(0, CreateScene.class.getName(), c2));

		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", HelpHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", CrosshairHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", CameraHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", FpsHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", ParticleSystemControlHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", MessageHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", MenuHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", MainMenuHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", TopMenuHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", EmitterHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", ModifierHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", FeatureHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", RendererHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", ParticleRendererHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", EditorHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", TextOverlayHUD.class.getName())));

		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", SkyBoxRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", AxisRenderer.class.getName())));
		// demoManager.addChangeSet(new ChangeSet(0, CreateRenderer.class.getName(), new CommandConfiguration("class", SpringRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", EmitterRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", GravityPointRenderer.class.getName())));

		demoManager.addChangeSet(new ChangeSet(0, AddTextOverlay.class.getName(), new CommandConfiguration("class", AxisOverlay.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddTextOverlay.class.getName(), new CommandConfiguration("class", EmitterOverlay.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddTextOverlay.class.getName(), new CommandConfiguration("class", PositionablePointModifierOverlay.class.getName())));

		demoManager.addChangeSet(new ChangeSet(0, CreateSystemRunner.class.getName(), new CommandConfiguration()));

		// particle features
		demoManager.addChangeSet(new ChangeSet(0, AddFeature.class.getName(), new CommandConfiguration("class", ParticleColor.class.getName())));
		
		// modifiers
		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", FixedParticlesVelocityBlocker.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", VelocityTransformation.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", MassSpringTransformation.class.getName())));

		// render types
		demoManager.addChangeSet(new ChangeSet(0, AddParticleRenderer.class, new CommandConfiguration("class", ClothParticleRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddParticleRenderer.class, new CommandConfiguration("class", TexturedClothParticleRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddParticleRenderer.class, new CommandConfiguration("class", BallParticleRenderer.class.getName())));

		// face renderers
		demoManager.addChangeSet(new ChangeSet(0, AddFaceRenderer.class, new CommandConfiguration("class", PolygonFaceRenderer.class.getName())));

		CommandConfiguration cam1 = new CommandConfiguration();
		cam1.put("class", FirstPersonCamera.class.getName());
		cam1.put("id", 1000);
		cam1.put("name", "top cam");
		cam1.put("x", 425.0);
		cam1.put("y", 195.0);
		cam1.put("z", 345.0);
		cam1.put("yaw", 305.0);
		cam1.put("pitch", -10.0);
		cam1.put("roll", 0.0);
		cam1.put("fov", 90.0);
		demoManager.addChangeSet(new ChangeSet(0, AddCamera.class.getName(), cam1));

		CommandConfiguration cam2 = new CommandConfiguration();
		cam2.put("class", ParticlePOVCamera.class.getName());
		cam2.put("id", 1001);
		cam2.put("name", "particle surf camera");
		cam2.put("x", 0.0);
		cam2.put("y", 0.0);
		cam2.put("z", 0.0);
		cam2.put("yaw", 0.0);
		cam2.put("pitch", 0.0);
		cam2.put("roll", 0.0);
		cam2.put("fov", 90.0);
		demoManager.addChangeSet(new ChangeSet(0, AddCamera.class.getName(), cam2));

		CommandConfiguration c9 = new CommandConfiguration();
		c9.put("id", 5);
		c9.put(PositionablePointModifier.POSITION_X, 0.0);
		c9.put(PositionablePointModifier.POSITION_Y, 0.0);
		c9.put(PositionablePointModifier.POSITION_Z, -200.0);
		c9.put(GravityBase.GRAVITY, 0.20);
		c9.put(GravityBase.MAX_FORCE, 0.10);
		demoManager.addChangeSet(new ChangeSet(10, 0, AddGravityPoint.class.getName(), c9));

		CommandConfiguration c11 = new CommandConfiguration();
		c11.put("id", 6);
		c11.put("position_x", 0.0);
		c11.put("position_y", 0.0);
		c11.put("position_z", 0.0);
		c11.put("velocity_x", 0.01);
		c11.put("velocity_y", 0.1);
		c11.put("velocity_z", -7.5);
		c11.put("rate", 90);
		c11.put("lifetime", 2000);
		c11.put("particleRenderer", 0);
		c11.put("faceRenderer", 1);
		ParticleEmitterConfiguration clothEmitterConfig = new ParticleEmitterConfiguration();
		clothEmitterConfig.put(MassSpring.SPRING_LENGTH, 50.0);
		clothEmitterConfig.put(MassSpring.SPRING_FRICTION, 0.01);
		clothEmitterConfig.put(MassSpring.SPRING_CONSTANT, 0.02);
		clothEmitterConfig.put(ParticleColor.START_COLOR_R, 0);
		clothEmitterConfig.put(ParticleColor.START_COLOR_G, 255);
		clothEmitterConfig.put(ParticleColor.START_COLOR_B, 0);
		clothEmitterConfig.put(ParticleColor.START_COLOR_A, 64);
		clothEmitterConfig.put(ParticleColor.END_COLOR_R, 0);
		clothEmitterConfig.put(ParticleColor.END_COLOR_G, 0);
		clothEmitterConfig.put(ParticleColor.END_COLOR_B, 255);
		clothEmitterConfig.put(ParticleColor.END_COLOR_A, 64);
		clothEmitterConfig.put(PooledClothParticleEmitter.COLORED_CLOTH, true);
		c11.put("configuration", clothEmitterConfig);
		demoManager.addChangeSet(new ChangeSet(30, 0, AddClothEmitter.class.getName(), c11));

		demoManager.save("cloth.demo");

		try {
			demoManager.run();
		} catch (Exception e) {
			logger.error("Error in executing DemoManager", e);
		}
		assertTrue(true);
	}

//	@Test
	public void softBodyObjectEmitterTest() {
		
		// demo manager
		DefaultDemoManager demoManager = new DefaultDemoManager();

		// particle system
		CommandConfiguration c1 = new CommandConfiguration();
		c1.put("name", "Demo Particle System");
		demoManager.addChangeSet(new ChangeSet(0, CreateParticleSystem.class.getName(), c1));

		// scene
		CommandConfiguration c2 = new CommandConfiguration();
		c2.put("name", "Demo Particle Rendering");
		c2.put("width", 1200);
		c2.put("height", 620);
		c2.put("fullscreen", false);
		demoManager.addChangeSet(new ChangeSet(0, CreateScene.class.getName(), c2));

		// huds
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", HelpHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", CrosshairHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", CameraHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", FpsHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", ParticleSystemControlHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", MessageHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", MenuHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", MainMenuHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", TopMenuHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", EmitterHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", ModifierHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", FeatureHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", RendererHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", ParticleRendererHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", FaceRendererHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", CaptureHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", EditorHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", TextOverlayHUD.class.getName())));

		// renderers
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", SkyBoxRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", AxisRenderer.class.getName())));
//		demoManager.addChangeSet(new ChangeSet(0, CreateRenderer.class.getName(), new CommandConfiguration("class", FixedPointRenderer.class.getName())));
//		demoManager.addChangeSet(new ChangeSet(0, CreateRenderer.class.getName(), new CommandConfiguration("class", SpringRenderer.class.getName())));
//		demoManager.addChangeSet(new ChangeSet(0, CreateRenderer.class.getName(), new CommandConfiguration("class", FixedPointSpringRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", EmitterRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", GravityPointRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", GravityPulsarRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", GravityPlaneRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", BlackHoleRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", CollisionPlaneRenderer.class.getName())));

		// text overlays
		demoManager.addChangeSet(new ChangeSet(0, AddTextOverlay.class.getName(), new CommandConfiguration("class", AxisOverlay.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddTextOverlay.class.getName(), new CommandConfiguration("class", EmitterOverlay.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddTextOverlay.class.getName(), new CommandConfiguration("class", PositionablePointModifierOverlay.class.getName())));

		// system runner
		demoManager.addChangeSet(new ChangeSet(0, CreateSystemRunner.class.getName(), new CommandConfiguration()));

		// particle features
		demoManager.addChangeSet(new ChangeSet(0, AddFeature.class.getName(), new CommandConfiguration("class", ParticleColor.class.getName())));
		// demoManager.addChangeSet(new ChangeSet(0, AddFeature.class.getName(), new CommandConfiguration("class", ParticleInitialVelocityScatter.class.getName())));
		
		// modifiers
		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", VelocityTransformation.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", VelocityDamper.class.getName())));
//		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", MassSpringTransformation.class.getName())));
//		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", RandomStartColor.class.getName())));

		// particle renderers
		demoManager.addChangeSet(new ChangeSet(0, AddParticleRenderer.class, new CommandConfiguration("class", SpringPolygonsParticleRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddParticleRenderer.class, new CommandConfiguration("class", BallParticleRenderer.class.getName())));

		// face renderers
		demoManager.addChangeSet(new ChangeSet(0, AddFaceRenderer.class, new CommandConfiguration("class", PolygonFaceRenderer.class.getName())));
		
		CommandConfiguration cam1 = new CommandConfiguration();
		cam1.put("class", FirstPersonCamera.class.getName());
		cam1.put("id", 1000);
		cam1.put("name", "top cam");
		cam1.put("x", 425.0);
		cam1.put("y", 195.0);
		cam1.put("z", 345.0);
		cam1.put("yaw", 305.0);
		cam1.put("pitch", -10.0);
		cam1.put("roll", 0.0);
		cam1.put("fov", 90.0);
		demoManager.addChangeSet(new ChangeSet(0, AddCamera.class.getName(), cam1));

		CommandConfiguration cam2 = new CommandConfiguration();
		cam2.put("class", ParticlePOVCamera.class.getName());
		cam2.put("id", 1001);
		cam2.put("name", "particle surf camera");
		cam2.put("x", 0.0);
		cam2.put("y", 0.0);
		cam2.put("z", 0.0);
		cam2.put("yaw", 0.0);
		cam2.put("pitch", 0.0);
		cam2.put("roll", 0.0);
		cam2.put("fov", 90.0);
		demoManager.addChangeSet(new ChangeSet(0, AddCamera.class.getName(), cam2));

		CommandConfiguration cam3 = new CommandConfiguration();
		cam3.put("class", ParticleOriginCamera.class.getName());
		cam3.put("id", 1002);
		cam3.put("name", "particle to origin camera");
		cam3.put("x", 0.0);
		cam3.put("y", 0.0);
		cam3.put("z", 0.0);
		cam3.put("yaw", 0.0);
		cam3.put("pitch", 0.0);
		cam3.put("roll", 0.0);
		cam3.put("fov", 90.0);
		demoManager.addChangeSet(new ChangeSet(0, AddCamera.class.getName(), cam3));

		CommandConfiguration c10 = new CommandConfiguration();
		c10.put("id", 6);
		c10.put("position_x", -1250.0);
		c10.put("position_y", 0.0);
		c10.put("position_z", 0.0);
		c10.put("velocity_x", 0.0);
		c10.put("velocity_y", 0.0);
		c10.put("velocity_z", 0.0);
		c10.put("rate", 1);
		c10.put("lifetime", 200000);
		c10.put("particleRenderer", 2);
		c10.put("faceRenderer", 0);
		c10.put("configuration", new ParticleEmitterConfiguration());
		demoManager.addChangeSet(new ChangeSet(250, 0, AddSoftBodyEmitter.class.getName(), c10));

		demoManager.addChangeSet(new ChangeSet(550, AddModifier.class.getName(), new CommandConfiguration("class", RandomStartColor.class.getName())));

		CommandConfiguration c11 = new CommandConfiguration();
		c11.put("id", 6);
		c11.put("position_x", 1250.0);
		c11.put("position_y", 0.0);
		c11.put("position_z", 0.0);
		c11.put("velocity_x", 0.0);
		c11.put("velocity_y", 0.0);
		c11.put("velocity_z", 0.0);
		c11.put("rate", 1);
		c11.put("lifetime", 200000);
		c11.put("particleRenderer", 0);
		c11.put("faceRenderer", 1);
		c11.put("configuration", new ParticleEmitterConfiguration());
		demoManager.addChangeSet(new ChangeSet(600, 0, AddSoftBodyEmitter.class.getName(), c11));

		CommandConfiguration c12 = new CommandConfiguration();
		c12.put("id", 6);
		c12.put("position_x", 0.0);
		c12.put("position_y", 0.0);
		c12.put("position_z", 0.0);
		c12.put("velocity_x", 0.0);
		c12.put("velocity_y", 0.0);
		c12.put("velocity_z", 0.0);
		c12.put("rate", 1);
		c12.put("lifetime", 200000);
		c12.put("particleRenderer", 2);
		c12.put("faceRenderer", 1);
		c12.put("configuration", new ParticleEmitterConfiguration());
		demoManager.addChangeSet(new ChangeSet(950, 0, AddSoftBodyEmitter.class.getName(), c12));

		CommandConfiguration rrt1 = new CommandConfiguration();
		rrt1.put("class", FireBallParticleRenderer.class.getName());
		rrt1.put("index", 1);
		demoManager.addChangeSet(new ChangeSet(1100, 0, ReplaceParticleRenderer.class.getName(), rrt1));

		CommandConfiguration rrt2 = new CommandConfiguration();
		rrt2.put("class", PoisonParticleRenderer.class.getName());
		rrt2.put("index", 1);
		demoManager.addChangeSet(new ChangeSet(1250, 0, ReplaceParticleRenderer.class.getName(), rrt2));

		demoManager.save("softBodyObjectEmitter.demo");

		try {
			demoManager.run();
		} catch (Exception e) {
			logger.error("Error in executing DemoManager", e);
		}
		assertTrue(true);
	}

//	@Test
	public void coloredParticleGravityTest() {
		DefaultDemoManager demoManager = new DefaultDemoManager();

		CommandConfiguration c1 = new CommandConfiguration();
		c1.put("name", "Demo Particle System");
		demoManager.addChangeSet(new ChangeSet(0, CreateParticleSystem.class.getName(), c1));

		CommandConfiguration c2 = new CommandConfiguration();
		c2.put("name", "Demo Particle Rendering");
		c2.put("width", 1200);
		c2.put("height", 620);
		c2.put("fullscreen", false);
		demoManager.addChangeSet(new ChangeSet(0, CreateScene.class.getName(), c2));

		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", HelpHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", CrosshairHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", CameraHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", FpsHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", ParticleSystemControlHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", MessageHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", MenuHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", MainMenuHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", TopMenuHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", EmitterHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", ModifierHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", FeatureHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", RendererHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", ParticleRendererHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", FaceRendererHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", CaptureHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", EditorHUD.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddHUD.class.getName(), new CommandConfiguration("class", TextOverlayHUD.class.getName())));

		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", SkyBoxRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", AxisRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", EmitterRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", GravityPointRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", GravityPulsarRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", GravityPlaneRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", BlackHoleRenderer.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddRenderer.class.getName(), new CommandConfiguration("class", CollisionPlaneRenderer.class.getName())));

		demoManager.addChangeSet(new ChangeSet(0, AddTextOverlay.class.getName(), new CommandConfiguration("class", AxisOverlay.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddTextOverlay.class.getName(), new CommandConfiguration("class", EmitterOverlay.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddTextOverlay.class.getName(), new CommandConfiguration("class", PositionablePointModifierOverlay.class.getName())));

		demoManager.addChangeSet(new ChangeSet(0, CreateSystemRunner.class.getName(), new CommandConfiguration()));

		// particle features
		demoManager.addChangeSet(new ChangeSet(0, AddFeature.class.getName(), new CommandConfiguration("class", ParticleColor.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddFeature.class.getName(), new CommandConfiguration("class", ParticleInitialVelocityScatter.class.getName())));
		
		// modifiers
		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", FixedParticlesVelocityBlocker.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", VelocityTransformation.class.getName())));
//		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", VelocityDamper.class.getName())));
//		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", MassSpringTransformation.class.getName())));
		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", RandomStartColor.class.getName())));
//		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), new CommandConfiguration("class", PositionPathBuffering.class.getName())));

//		CommandConfiguration m4 = new CommandConfiguration();
//		m4.put("class", ParticleLimiter.class.getName());
//		m4.put("configuration", new ParticleModifierConfiguration(ParticleLimiter.MAX_PARTICLES, 200));
//		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class.getName(), m4));

//		CommandConfiguration m5 = new CommandConfiguration();
//		m5.put("class", PulseSizeTransformation.class.getName());
//		m5.put("configuration", new ParticleModifierConfiguration(PulseSizeTransformation.PULSE_INTERVAL_FACTOR, 10.0));
//		demoManager.addChangeSet(new ChangeSet(0, AddModifier.class, m5));

		// render types
		demoManager.addChangeSet(new ChangeSet(0, AddParticleRenderer.class, new CommandConfiguration("class", BallParticleRenderer.class.getName())));

		CommandConfiguration cam1 = new CommandConfiguration();
		cam1.put("class", FirstPersonCamera.class.getName());
		cam1.put("id", 1000);
		cam1.put("name", "top cam");
		cam1.put("x", 425.0);
		cam1.put("y", 195.0);
		cam1.put("z", 345.0);
		cam1.put("yaw", 305.0);
		cam1.put("pitch", -10.0);
		cam1.put("roll", 0.0);
		cam1.put("fov", 90.0);
		demoManager.addChangeSet(new ChangeSet(0, AddCamera.class.getName(), cam1));

		CommandConfiguration cam2 = new CommandConfiguration();
		cam2.put("class", ParticlePOVCamera.class.getName());
		cam2.put("id", 1001);
		cam2.put("name", "particle surf camera");
		cam2.put("x", 0.0);
		cam2.put("y", 0.0);
		cam2.put("z", 0.0);
		cam2.put("yaw", 0.0);
		cam2.put("pitch", 0.0);
		cam2.put("roll", 0.0);
		cam2.put("fov", 90.0);
		demoManager.addChangeSet(new ChangeSet(0, AddCamera.class.getName(), cam2));

		CommandConfiguration cam3 = new CommandConfiguration();
		cam3.put("class", ParticleOriginCamera.class.getName());
		cam3.put("id", 1002);
		cam3.put("name", "particle to origin camera");
		cam3.put("x", 0.0);
		cam3.put("y", 0.0);
		cam3.put("z", 0.0);
		cam3.put("yaw", 0.0);
		cam3.put("pitch", 0.0);
		cam3.put("roll", 0.0);
		cam3.put("fov", 90.0);
		demoManager.addChangeSet(new ChangeSet(0, AddCamera.class.getName(), cam3));

		CommandConfiguration c6 = new CommandConfiguration();
		c6.put("id", 2);
		c6.put(PositionablePointModifier.POSITION_X, 200.0);
		c6.put(PositionablePointModifier.POSITION_Y, 0.0);
		c6.put(PositionablePointModifier.POSITION_Z, 0.0);
		c6.put(GravityBase.GRAVITY, 0.20);
		c6.put(GravityBase.MAX_FORCE, 0.20);
		demoManager.addChangeSet(new ChangeSet(10, 0, AddGravityPoint.class.getName(), c6));
		
		CommandConfiguration c7 = new CommandConfiguration();
		c7.put("id", 3);
		c7.put(PositionablePointModifier.POSITION_X, -200.0);
		c7.put(PositionablePointModifier.POSITION_Y, 0.0);
		c7.put(PositionablePointModifier.POSITION_Z, 0.0);
		c7.put(GravityBase.GRAVITY, 0.20);
		c7.put(GravityBase.MAX_FORCE, 0.20);
		demoManager.addChangeSet(new ChangeSet(10, 0, AddGravityPoint.class.getName(), c7));
		
		CommandConfiguration c8 = new CommandConfiguration();
		c8.put("id", 4);
		c8.put(PositionablePointModifier.POSITION_X, 0.0);
		c8.put(PositionablePointModifier.POSITION_Y, 0.0);
		c8.put(PositionablePointModifier.POSITION_Z, 200.0);
		c8.put(GravityBase.GRAVITY, 0.20);
		c8.put(GravityBase.MAX_FORCE, 0.20);
		demoManager.addChangeSet(new ChangeSet(10, 0, AddGravityPoint.class.getName(), c8));

		CommandConfiguration c9 = new CommandConfiguration();
		c9.put("id", 5);
		c9.put(PositionablePointModifier.POSITION_X, 0.0);
		c9.put(PositionablePointModifier.POSITION_Y, 0.0);
		c9.put(PositionablePointModifier.POSITION_Z, -200.0);
		c9.put(GravityBase.GRAVITY, 0.20);
		c9.put(GravityBase.MAX_FORCE, 0.20);
		demoManager.addChangeSet(new ChangeSet(10, 0, AddGravityPoint.class.getName(), c9));

		CommandConfiguration c10 = new CommandConfiguration();
		c10.put("id", 6);
		c10.put("position_x", 0.0);
		c10.put("position_y", 0.0);
		c10.put("position_z", 0.0);
		c10.put("velocity_x", 0.7);
		c10.put("velocity_y", 1.1);
		c10.put("velocity_z", -0.9);
		c10.put("rate", 1);
		c10.put("lifetime", 2000);
		c10.put("particleRenderer", 1);
		ParticleEmitterConfiguration pec1 = new ParticleEmitterConfiguration();
		pec1.put(ParticleInitialVelocityScatter.SCATTER_X, 0.2);
		pec1.put(ParticleInitialVelocityScatter.SCATTER_Y, 0.2);
		pec1.put(ParticleInitialVelocityScatter.SCATTER_Z, 0.2);
		pec1.put(PositionPath.NUMBER_OF_BUFFERED_POSITIONS, 30);
		pec1.put(ParticleSize.SIZE_BIRTH, 10.0);
		pec1.put(ParticleSize.SIZE_DEATH, 40.0);
		pec1.put(TubeSegments.NUMBER_OF_SEGMENTS, 12);
		pec1.put(TubeSegments.SEGMENTS_TO_DRAW, 12);
		pec1.put(TubeSegments.SEGMENT_TWIST, 3);
		c10.put("configuration", pec1);
		demoManager.addChangeSet(new ChangeSet(30, 0, AddPointEmitter.class.getName(), c10));

		CommandConfiguration c4 = new CommandConfiguration();
		c4.put("id", 1000);
		c4.put("x", 0.0);
		c4.put("y", 50.0);
		c4.put("z", 0.0);
		demoManager.addChangeSet(new ChangeSet(525, 0, SetCameraPosition.class.getName(), c4));
		
		CommandConfiguration c5 = new CommandConfiguration();
		c5.put("id", 1000);
		demoManager.addChangeSet(new ChangeSet(1000, 250, RotateCamera360.class.getName(), c5));
		
		CommandConfiguration rrt1 = new CommandConfiguration();
		rrt1.put("class", FireBallParticleRenderer.class.getName());
		rrt1.put("index", 1);
		demoManager.addChangeSet(new ChangeSet(780, 0, ReplaceParticleRenderer.class.getName(), rrt1));

		CommandConfiguration rrt2 = new CommandConfiguration();
		rrt2.put("class", PoisonParticleRenderer.class.getName());
		rrt2.put("index", 1);
		demoManager.addChangeSet(new ChangeSet(910, 0, ReplaceParticleRenderer.class.getName(), rrt2));

		CommandConfiguration rrt3 = new CommandConfiguration();
		rrt3.put("class", BallParticleRenderer.class.getName());
		rrt3.put("index", 1);
		demoManager.addChangeSet(new ChangeSet(1040, 0, ReplaceParticleRenderer.class.getName(), rrt3));

		demoManager.save("test");

		try {
			demoManager.run();
		} catch (Exception e) {
			logger.error("Error in executing DemoManager", e);
		}
		assertTrue(true);
	}

	// @Test
	public void configurableParticleSystemTest() {
		try {
			ParticleSystemDAO particleSystemDAO = new ParticleSystemDAO();
			ParticleSystem particleSystem = particleSystemDAO.create("/config/system-empty.json");

			SceneDAO sceneDAO = new SceneDAO();
			Scene scene = sceneDAO.create("/config/scene-empty.json", particleSystem);

			SystemRunner systemRunner = new SystemRunner();
			systemRunner.add(particleSystem.getSystemName(), particleSystem);
			systemRunner.add(scene.getSystemName(), scene);
			systemRunner.start();
			assertTrue(true);
		} catch (Exception e) {
			logger.error("hmm", e);
		}
	}


////	@Test
////	public void particleCreationTest() {
////		IParticleSystem particleSystem = new IParticleSystem();
////		particleSystem.addParticleEmitter(PointParticleEmitter.class, new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 1.0f), 10, ParticleEmitterConfiguration.EMPTY);
////		particleSystem.addParticleModifier(ParticleDebugger.class, ParticleModifierConfiguration.EMPTY);
////		particleSystem.update();
////		assertTrue(true);
////	}
//
//	// @Test
//	public void fullParticleSystemTest() throws InterruptedException {
//		
//		Integer defaultParticleRenderer = 1;
//		Integer complexPointParticleRenderer = 2;
//		Integer rainParticleRenderer = 3;
//		Integer coloredPointParticleRenderer = 4;
//		Integer triangleParticleRenderer = 5;
//		Integer triangleStripParticleRenderer = 6;
//		Integer triangleFanParticleRenderer = 7;
//		Integer quadsParticleRenderer = 8;
//		Integer lineStripParticleRenderer = 9;
//		Integer pointSpriteParticleRenderer = 10;
//		Integer velocityParticleRenderer = 11;
//		
//		ParticleSystem particleSystem = new DefaultParticleSystem();
//		particleSystem.addParticleFeature(new ParticleInitialVelocityScatter());
//		particleSystem.addParticleFeature(new ParticleColor());
//		particleSystem.addParticleFeature(new ParticleSize());
//		
////		ParticleEmitterConfiguration pec1 = new ParticleEmitterConfiguration();
//////		pec1.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.5f, 0.0f, 0.5f));
////		pec1.put(ParticleColor.START_COLOR, new Color(255, 0, 0, 48));
////		pec1.put(ParticleColor.END_COLOR, new Color(255, 255, 100, 48));
////		pec1.put(ParticleSize.START_SIZE, 5.0f);
////		pec1.put(ParticleSize.END_SIZE, 10.0f);
////		particleSystem.addParticleEmitter(
////				PointParticleEmitter.class,
////				new Vector3f(0.0f, 200.0f, 0.0f),
////				new Vector3f(0.0f, 1.0f, 0.0f),
////				complexPointParticleRenderer,
////				1336,
////				pec1);
////
////		ParticleEmitterConfiguration pec2 = new ParticleEmitterConfiguration();
//////		pec2.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(-3.5f, 1.0f, -8.5f));
////		pec2.put(ParticleColor.START_COLOR, new Color(255, 100, 100, 48));
////		pec2.put(ParticleColor.END_COLOR, new Color(255, 100, 255, 48));
////		pec2.put(ParticleSize.START_SIZE, 5.0f);
////		pec2.put(ParticleSize.END_SIZE, 5.0f);
////		particleSystem.addParticleEmitter(
////				PointParticleEmitter.class,
////				new Vector3f(0.0f, -200.0f, 0.0f),
////				new Vector3f(0.0f, -5.0f, 0.0f),
////				triangleFanParticleRenderer,
////				100,
////				pec2);
////
////		ParticleEmitterConfiguration pec3 = new ParticleEmitterConfiguration();
//////		pec3.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.0f, 0.8f, 0.8f));
////		pec3.put(ParticleColor.START_COLOR, new Color(255, 255, 100, 48));
////		pec3.put(ParticleColor.END_COLOR, new Color(100, 255, 255, 48));
////		pec3.put(ParticleSize.START_SIZE, 7.0f);
////		pec3.put(ParticleSize.END_SIZE, 5.0f);
////		particleSystem.addParticleEmitter(
////				PointParticleEmitter.class,
////				new Vector3f(200.0f, 0.0f, 0.0f),
////				new Vector3f(1.2f, 0.0f, 0.0f),
////				triangleStripParticleRenderer,
////				200,
////				pec3);
////
////		ParticleEmitterConfiguration pec4 = new ParticleEmitterConfiguration();
//////		pec4.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.0f, -0.5f, -0.5f));
////		pec4.put(ParticleColor.START_COLOR, new Color(100, 255, 255, 48));
////		pec4.put(ParticleColor.END_COLOR, new Color(100, 255, 100, 48));
////		pec4.put(ParticleSize.START_SIZE, 5.0f);
////		pec4.put(ParticleSize.END_SIZE, 20.0f);
////		particleSystem.addParticleEmitter(
////				PointParticleEmitter.class,
////				new Vector3f(-200.0f, 0.0f, 0.0f),
////				new Vector3f(-1.0f, 0.0f, 0.0f),
////				coloredPointParticleRenderer,
////				1336,
////				pec4);
////
////		ParticleEmitterConfiguration pec5 = new ParticleEmitterConfiguration();
//////		pec5.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.5f, 0.5f, 0.0f));
////		pec5.put(ParticleColor.START_COLOR, new Color(100, 100, 255, 48));
////		pec5.put(ParticleColor.END_COLOR, new Color(100, 255, 255, 48));
////		pec5.put(ParticleSize.START_SIZE, 5.0f);
////		pec5.put(ParticleSize.END_SIZE, 12.0f);
////		particleSystem.addParticleEmitter(
////				PointParticleEmitter.class,
////				new Vector3f(0.0f, 0.0f, 200.0f),
////				new Vector3f(0.0f, 0.0f, 1.0f),
////				triangleParticleRenderer,
////				1336,
////				pec5);
////
////		ParticleEmitterConfiguration pec6 = new ParticleEmitterConfiguration();
////		// pec6.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(-5.5f, -5.5f, 0.0f));
//////		pec6.put(ParticleColor.START_COLOR, new Color(255, 100, 255, 48));
//////		pec6.put(ParticleColor.END_COLOR, new Color(100, 255, 100, 48));
//////		pec6.put(ParticleSize.START_SIZE, 20.0f);
//////		pec6.put(ParticleSize.END_SIZE, 1.0f);
//////		pec6.put("ROTATE_ORIGIN", 1.0f);
//////		pec6.put(PlaneParticleEmitter.POSITION2, new Vector3f(50.0f, 50.0f, -200.0f));
////		particleSystem.addParticleEmitter(
////				PointParticleEmitter.class,
////				new Vector3f(0.0f, 0.0f, -200.0f),
////				new Vector3f(0.0f, 0.0f, -0.1f),
////				pointSpriteParticleRenderer, // lineStripParticleRenderer,
////				1000,
////				pec6);
//
//		/**
//		 * Blauer, ins schwarzer gehender Regen.
//		 */
////		ParticleEmitterConfiguration pec7 = new ParticleEmitterConfiguration();
////		pec7.put(PlaneParticleEmitter.POSITION2, new Vector3f(600.0f, 0.0f, 600.0f));
//////		pec7.put(PlaneParticleEmitter.VELOCITY, 0.0f);
//////		pec7.put(ParticleColor.START_COLOR, new Color(222, 222, 255, 255));
//////		pec7.put(ParticleColor.END_COLOR, new Color(0, 0, 128, 48));
//////		pec7.put(ParticleSize.START_SIZE, 5.0f);
//////		pec7.put(ParticleSize.END_SIZE, 5.0f);
////		particleSystem.addParticleEmitter(
////				PlaneParticleEmitter.class,
////				new Vector3f(-600.0f, 0.0f, -600.0f),
////				new Vector3f(0.0f, -4.0f, 0.0f),
////				rainParticleRenderer,
////				1300,
////				pec7);
//
////		ParticleModifierConfiguration particleCullerConfiguration = new ParticleModifierConfiguration();
////		particleCullerConfiguration.put(BoundingBoxParticleCulling.BOUNDING_BOX_POINT1, new Vector3f(-5000.0f, -5000.0f, -5000.0f));
////		particleCullerConfiguration.put(BoundingBoxParticleCulling.BOUNDING_BOX_POINT2, new Vector3f(5000.0f, 5000.0f, 5000.0f));
////		particleSystem.addParticleModifier(BoundingBoxParticleCulling.class, particleCullerConfiguration);
//
//		particleSystem.addParticleModifier(LinearColorTransformation.class, ParticleModifierConfiguration.EMPTY);
//
//		particleSystem.addParticleModifier(LinearSizeTransformation.class, ParticleModifierConfiguration.EMPTY);
//
////		ParticleModifierConfiguration gravityPlaneConfiguration = new ParticleModifierConfiguration();
////		gravityPlaneConfiguration.put(GravityPlane.POINT_1, new Vector3f(-100.0f, -100.0f, -50.0f));
////		gravityPlaneConfiguration.put(GravityPlane.POINT_2, new Vector3f(100.0f, 100.0f, -50.0f));
////		gravityPlaneConfiguration.put(GravityPlane.GRAVITY, new Float(0.36f));
////		particleSystem.addParticleModifier(GravityPlane.class, gravityPlaneConfiguration);
//
////		ParticleModifierConfiguration gravityPointConfiguration = new ParticleModifierConfiguration();
////		gravityPointConfiguration.put(GravityPoint.POINT, new Vector3f(-600.0f, 100.0f, 300.0f));
////		gravityPointConfiguration.put(GravityPoint.MASS, new Float(1000.0f));
////		gravityPointConfiguration.put(GravityPoint.GRAVITY, new Float(2.0f));
////		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration);
////
////		ParticleModifierConfiguration gravityPointConfiguration2 = new ParticleModifierConfiguration();
////		gravityPointConfiguration2.put(GravityPoint.POINT, new Vector3f(700.0f, 350.0f, 50.0f));
////		gravityPointConfiguration2.put(GravityPoint.MASS, new Float(1000.0f));
////		gravityPointConfiguration2.put(GravityPoint.GRAVITY, new Float(2.0f));
////		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration2);
////
////		ParticleModifierConfiguration gravityPointConfiguration3 = new ParticleModifierConfiguration();
////		gravityPointConfiguration3.put(GravityPoint.POINT, new Vector3f(400.0f, -350.0f, -750.0f));
////		gravityPointConfiguration3.put(GravityPoint.MASS, new Float(1000.0f));
////		gravityPointConfiguration3.put(GravityPoint.GRAVITY, new Float(2.0f));
////		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration3);
////
////		ParticleModifierConfiguration gravityPointConfiguration4 = new ParticleModifierConfiguration();
////		gravityPointConfiguration4.put(GravityPoint.POINT, new Vector3f(-800.0f, -250.0f, -750.0f));
////		gravityPointConfiguration4.put(GravityPoint.MASS, new Float(1000.0f));
////		gravityPointConfiguration4.put(GravityPoint.GRAVITY, new Float(2.0f));
////		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration4);
////
////		ParticleModifierConfiguration gravityPointConfiguration5 = new ParticleModifierConfiguration();
////		gravityPointConfiguration5.put(GravityPoint.POINT, new Vector3f(0.0f, -450.0f, 350.0f));
////		gravityPointConfiguration5.put(GravityPoint.MASS, new Float(1000.0f));
////		gravityPointConfiguration5.put(GravityPoint.GRAVITY, new Float(2.0f));
////		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration5);
//
//		particleSystem.addParticleModifier(VelocityTransformation.class, ParticleModifierConfiguration.EMPTY);
//		
//		DefaultScene particleScene = new DefaultScene(particleSystem);
//
//		SystemRunner systemRunner = new SystemRunner();
//		systemRunner.add("ParticleSystem", particleSystem);
//		systemRunner.add("ParticleScene", particleScene);
//		systemRunner.start();
//		assertTrue(true);
//		
//	}
//
//	public void sceneManagerSystemTest() throws InterruptedException, JsonParseException, JsonMappingException, ClassNotFoundException, IOException {
//		
//		Integer defaultParticleRenderer = 1;
//		Integer complexPointParticleRenderer = 2;
//		Integer rainParticleRenderer = 3;
//		Integer coloredPointParticleRenderer = 4;
//		Integer triangleParticleRenderer = 5;
//		Integer triangleStripParticleRenderer = 6;
//		Integer triangleFanParticleRenderer = 7;
//		Integer quadsParticleRenderer = 8;
//		Integer lineStripParticleRenderer = 9;
//		Integer pointSpriteParticleRenderer = 10;
//		Integer velocityParticleRenderer = 11;
//		
//		ParticleSystem particleSystem = new DefaultParticleSystem();
//		particleSystem.addParticleFeature(new ParticleInitialVelocityScatter());
//		particleSystem.addParticleFeature(new ParticleColor());
//		particleSystem.addParticleFeature(new ParticleSize());
//		
////		ParticleEmitterConfiguration pec1 = new ParticleEmitterConfiguration();
//////		pec1.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.5f, 0.0f, 0.5f));
////		pec1.put(ParticleColor.START_COLOR, new Color(255, 0, 0, 48));
////		pec1.put(ParticleColor.END_COLOR, new Color(255, 255, 100, 48));
//////		pec1.put(ParticleSize.START_SIZE, 5.0f);
//////		pec1.put(ParticleSize.END_SIZE, 10.0f);
////		particleSystem.addParticleEmitter(
////				PointParticleEmitter.class,
////				new Vector3f(0.0f, 200.0f, 0.0f),
////				new Vector3f(0.0f, 1.0f, 0.0f),
////				velocityParticleRenderer,
////				10000,
////				pec1);
//
////		ParticleEmitterConfiguration pec2 = new ParticleEmitterConfiguration();
////		pec2.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(-0.5f, 0.0f, -0.5f));
////		pec2.put(ParticleColor.START_COLOR, new Color(255, 100, 100, 48));
////		pec2.put(ParticleColor.END_COLOR, new Color(255, 100, 255, 48));
//////		pec2.put(ParticleSize.START_SIZE, 5.0f);
//////		pec2.put(ParticleSize.END_SIZE, 5.0f);
////		particleSystem.addParticleEmitter(
////				PointParticleEmitter.class,
////				new Vector3f(0.0f, -200.0f, 0.0f),
////				new Vector3f(0.0f, -3.0f, 0.0f),
////				velocityParticleRenderer, // triangleFanParticleRenderer,
////				1000,
////				pec2);
//
////		ParticleEmitterConfiguration pec3 = new ParticleEmitterConfiguration();
////		pec3.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.0f, 0.8f, 0.8f));
////		pec3.put(ParticleColor.START_COLOR, new Color(255, 255, 100, 48));
////		pec3.put(ParticleColor.END_COLOR, new Color(100, 255, 255, 48));
////		pec3.put(ParticleSize.START_SIZE, 7.0f);
////		pec3.put(ParticleSize.END_SIZE, 5.0f);
////		particleSystem.addParticleEmitter(
////				PointParticleEmitter.class,
////				new Vector3f(200.0f, 0.0f, 0.0f),
////				new Vector3f(1.2f, 0.0f, 0.0f),
////				triangleStripParticleRenderer,
////				200,
////				pec3);
//
////		ParticleEmitterConfiguration pec4 = new ParticleEmitterConfiguration();
//////		pec4.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.0f, -0.5f, -0.5f));
////		pec4.put(ParticleColor.START_COLOR, new Color(100, 255, 255, 48));
////		pec4.put(ParticleColor.END_COLOR, new Color(100, 255, 100, 48));
////		pec4.put(ParticleSize.START_SIZE, 5.0f);
////		pec4.put(ParticleSize.END_SIZE, 20.0f);
////		particleSystem.addParticleEmitter(
////				PointParticleEmitter.class,
////				new Vector3f(-200.0f, 0.0f, 0.0f),
////				new Vector3f(-1.0f, 0.0f, 0.0f),
////				velocityParticleRenderer,
////				1336,
////				pec4);
//
////		ParticleEmitterConfiguration pec5 = new ParticleEmitterConfiguration();
////		pec5.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(0.5f, 0.5f, 0.0f));
////		pec5.put(ParticleColor.START_COLOR, new Color(100, 100, 255, 48));
////		pec5.put(ParticleColor.END_COLOR, new Color(100, 255, 255, 48));
////		pec5.put(ParticleSize.START_SIZE, 5.0f);
////		pec5.put(ParticleSize.END_SIZE, 12.0f);
////		particleSystem.addParticleEmitter(
////				PointParticleEmitter.class,
////				new Vector3f(0.0f, 0.0f, 200.0f),
////				new Vector3f(0.0f, 0.0f, 1.0f),
////				triangleParticleRenderer,
////				1336,
////				pec5);
//
//		ParticleEmitterConfiguration pec6 = new ParticleEmitterConfiguration();
//		// pec6.put(ParticleInitialVelocityScatter.SCATTER, new Vector3f(-5.5f, -5.5f, 0.0f));
//		pec6.put(ParticleInitialVelocityScatter.SCATTER_X, 0.0f);
//		pec6.put(ParticleInitialVelocityScatter.SCATTER_Y, 0.0f);
//		pec6.put(ParticleInitialVelocityScatter.SCATTER_Z, 0.4f);
////		pec6.put(ParticleColor.START_COLOR, new Color(255, 100, 255, 48));
////		pec6.put(ParticleColor.END_COLOR, new Color(100, 255, 100, 48));
////		pec6.put(ParticleSize.START_SIZE, 20.0f);
////		pec6.put(ParticleSize.END_SIZE, 1.0f);
////		pec6.put("ROTATE_ORIGIN", 1.0f);
////		pec6.put(PlaneParticleEmitter.POSITION2, new Vector3f(50.0f, 50.0f, -200.0f));
////		particleSystem.addParticleEmitter(
////				PointParticleEmitter.class,
////				new Vector3f(0.0f, 0.0f, -200.0f),
////				new Vector3f(0.0f, 0.0f, -2.8f),
////				lineStripParticleRenderer, // pointSpriteParticleRenderer, 
////				20000,
////				pec6);
//
////		/**
////		 * Blauer, ins schwarzer gehender Regen.
////		 */
////		ParticleEmitterConfiguration pec7 = new ParticleEmitterConfiguration();
////		pec7.put(PlaneParticleEmitter.POSITION2, new Vector3f(600.0f, 0.0f, 600.0f));
//////		pec7.put(PlaneParticleEmitter.VELOCITY, 0.0f);
//////		pec7.put(ParticleColor.START_COLOR, new Color(222, 222, 255, 255));
//////		pec7.put(ParticleColor.END_COLOR, new Color(0, 0, 128, 48));
//////		pec7.put(ParticleSize.START_SIZE, 5.0f);
//////		pec7.put(ParticleSize.END_SIZE, 5.0f);
////		particleSystem.addParticleEmitter(
////				PlaneParticleEmitter.class,
////				new Vector3f(-600.0f, 0.0f, -600.0f),
////				new Vector3f(0.0f, -4.0f, 0.0f),
////				rainParticleRenderer,
////				1300,
////				pec7);
//
////		ParticleModifierConfiguration particleCullerConfiguration = new ParticleModifierConfiguration();
////		particleCullerConfiguration.put(BoundingBoxParticleCulling.BOUNDING_BOX_POINT1, new Vector3f(-5000.0f, -5000.0f, -5000.0f));
////		particleCullerConfiguration.put(BoundingBoxParticleCulling.BOUNDING_BOX_POINT2, new Vector3f(5000.0f, 5000.0f, 5000.0f));
//		// particleSystem.addParticleModifier(ParticleCulling.class, particleCullerConfiguration);
//
//		// particleSystem.addParticleModifier(RainbowColorTransformation.class, ParticleModifierConfiguration.EMPTY);
//
//		// particleSystem.addParticleModifier(RandomColorTransformation.class, ParticleModifierConfiguration.EMPTY);
//
//		particleSystem.addParticleModifier(LinearColorTransformation.class, ParticleModifierConfiguration.EMPTY);
//
//		// particleSystem.addParticleModifier(ParticleLinearSizeTransformation.class, ParticleModifierConfiguration.EMPTY);
//
////		ParticleModifierConfiguration gravityPlaneConfiguration = new ParticleModifierConfiguration();
////		gravityPlaneConfiguration.put(GravityPlane.POINT_1, new Vector3f(-100.0f, -100.0f, -50.0f));
////		gravityPlaneConfiguration.put(GravityPlane.POINT_2, new Vector3f(100.0f, 100.0f, -50.0f));
////		gravityPlaneConfiguration.put(GravityPlane.GRAVITY, new Float(0.36f));
////		particleSystem.addParticleModifier(GravityPlane.class, gravityPlaneConfiguration);
//
////		ParticleModifierConfiguration gravityPointConfiguration = new ParticleModifierConfiguration();
////		gravityPointConfiguration.put(GravityPoint.POINT, new Vector3f(-600.0f, 100.0f, 300.0f));
////		gravityPointConfiguration.put(GravityPoint.MASS, new Float(500.0f));
////		gravityPointConfiguration.put(GravityPoint.GRAVITY, new Float(2.0f));
////		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration);
////
////		ParticleModifierConfiguration gravityPointConfiguration2 = new ParticleModifierConfiguration();
////		gravityPointConfiguration2.put(GravityPoint.POINT, new Vector3f(700.0f, 350.0f, 50.0f));
////		gravityPointConfiguration2.put(GravityPoint.MASS, new Float(500.0f));
////		gravityPointConfiguration2.put(GravityPoint.GRAVITY, new Float(2.0f));
////		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration2);
////
////		ParticleModifierConfiguration gravityPointConfiguration3 = new ParticleModifierConfiguration();
////		gravityPointConfiguration3.put(GravityPoint.POINT, new Vector3f(400.0f, -350.0f, -750.0f));
////		gravityPointConfiguration3.put(GravityPoint.MASS, new Float(500.0f));
////		gravityPointConfiguration3.put(GravityPoint.GRAVITY, new Float(2.0f));
////		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration3);
////
////		ParticleModifierConfiguration gravityPointConfiguration4 = new ParticleModifierConfiguration();
////		gravityPointConfiguration4.put(GravityPoint.POINT, new Vector3f(-800.0f, -250.0f, -750.0f));
////		gravityPointConfiguration4.put(GravityPoint.MASS, new Float(500.0f));
////		gravityPointConfiguration4.put(GravityPoint.GRAVITY, new Float(2.0f));
////		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration4);
////
////		ParticleModifierConfiguration gravityPointConfiguration5 = new ParticleModifierConfiguration();
////		gravityPointConfiguration5.put(GravityPoint.POINT, new Vector3f(0.0f, -450.0f, 350.0f));
////		gravityPointConfiguration5.put(GravityPoint.MASS, new Float(500.0f));
////		gravityPointConfiguration5.put(GravityPoint.GRAVITY, new Float(2.0f));
////		particleSystem.addParticleModifier(GravityPoint.class, gravityPointConfiguration5);
//
////		ParticleModifierConfiguration blackHoleConfiguration1 = new ParticleModifierConfiguration();
////		blackHoleConfiguration1.put(BlackHole.POINT, new Vector3f(0.0f, 0.0f, 0.0f));
////		blackHoleConfiguration1.put(BlackHole.MASS, new Float(250.0f));
////		blackHoleConfiguration1.put(BlackHole.GRAVITY, new Float(1.25f));
////		// blackHoleConfiguration1.put(BlackHole.EVENT_HORIZON, new Float(80.0f));
////		particleSystem.addParticleModifier(BlackHole.class, blackHoleConfiguration1);
//
//		ParticleModifierConfiguration gravityPlaneConfiguration1 = new ParticleModifierConfiguration();
//		// Liegt parallel zur x/z-Ebene
//		gravityPlaneConfiguration1.put(GravityPlane.POINT, new Vector3f(0.0f, -1500.0f, 0.0f));
//		gravityPlaneConfiguration1.put(GravityPlane.VECTOR_1, new Vector3f(1.0f, 0.0f, 0.0f));
//		gravityPlaneConfiguration1.put(GravityPlane.VECTOR_2, new Vector3f(0.0f, 0.0f, 1.0f));
//		gravityPlaneConfiguration1.put(GravityPlane.MASS, new Float(-50.0f));
//		gravityPlaneConfiguration1.put(GravityPlane.GRAVITY, new Float(-2.0f));
//		// particleSystem.addParticleModifier(GravityPlane.class, gravityPlaneConfiguration1);
//
//		ParticleModifierConfiguration gravityPlaneConfiguration2 = new ParticleModifierConfiguration();
//		// Liegt parallel zur x/z-Ebene
//		gravityPlaneConfiguration2.put(GravityPlane.POINT, new Vector3f(0.0f, +1500.0f, 0.0f));
//		gravityPlaneConfiguration2.put(GravityPlane.VECTOR_1, new Vector3f(-1.0f, 0.0f, 0.0f));
//		gravityPlaneConfiguration2.put(GravityPlane.VECTOR_2, new Vector3f(0.0f, 0.0f, -1.0f));
//		gravityPlaneConfiguration2.put(GravityPlane.MASS, new Float(-50.0f));
//		gravityPlaneConfiguration2.put(GravityPlane.GRAVITY, new Float(-2.0f));
//		// particleSystem.addParticleModifier(GravityPlane.class, gravityPlaneConfiguration2);
//
//		particleSystem.addParticleModifier(VelocityTransformation.class, ParticleModifierConfiguration.EMPTY);
//
//// Future
////		ParticleSystemManager particleSystemManager = new ParticleSystemManager();
////		ParticleSystem particleSystem = particleSystemManager.create("config/system1.json");
//		
//		try {
//			SceneDAO sceneManager = new SceneDAO();
//			Scene scene = sceneManager.create("/config/scene1.json", particleSystem);
//
//			SystemRunner systemRunner = new SystemRunner();
//			systemRunner.add(particleSystem.getSystemName(), particleSystem);
//			systemRunner.add(scene.getSystemName(), scene);
//			systemRunner.start();
//			assertTrue(true);
//		} catch (Exception e) {
//			logger.error("hmm", e);
//		}
//
//		
//	}



}
