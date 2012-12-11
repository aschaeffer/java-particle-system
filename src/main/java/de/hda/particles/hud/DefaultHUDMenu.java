package de.hda.particles.hud;

import de.hda.particles.domain.*;
import de.hda.particles.emitter.*;
import de.hda.particles.features.*;
import de.hda.particles.modifier.*;
import de.hda.particles.modifier.collision.CollisionPlane;
import de.hda.particles.modifier.color.*;
import de.hda.particles.modifier.gravity.BlackHole;
import de.hda.particles.modifier.gravity.GravityPlane;
import de.hda.particles.modifier.gravity.GravityPoint;
import de.hda.particles.modifier.gravity.ParticleGravityTransformation;
import de.hda.particles.modifier.size.LinearSizeTransformation;
import de.hda.particles.modifier.size.PulseSizeTransformation;
import de.hda.particles.modifier.velocity.VelocityDamper;
import de.hda.particles.modifier.velocity.VelocityTransformation;
import de.hda.particles.renderer.*;
import de.hda.particles.renderer.types.*;
import de.hda.particles.scene.Scene;

public class DefaultHUDMenu {

	public HUDMenuEntry createMenu(Scene s) {
		HUDMenuEntry root = HUDMenuEntry.createRoot("Particle System Main Menu");
		HUDMenuEntry scene = HUDMenuEntry.create(root, "Scene");
		HUDMenuEntry renderTypes = HUDMenuEntry.create(scene, "Render Types");
		HUDMenuEntry addPointSpriteRenderTypes = HUDMenuEntry.create(renderTypes, "Add Point Sprite Render Type");
		HUDMenuEntry addPrimitiveRenderTypes = HUDMenuEntry.create(renderTypes, "Add Primtive Render Type");
		HUDMenuEntry addComplexRenderTypes = HUDMenuEntry.create(renderTypes, "Add Complex Render Type");
		HUDMenuEntry addMassSpringRenderTypes = HUDMenuEntry.create(renderTypes, "Add Mass Spring Render Type");
		HUDMenuEntry addPositionPathRenderTypes = HUDMenuEntry.create(renderTypes, "Add Position Path Render Type");
		HUDMenuEntry addTechnicalRenderTypes = HUDMenuEntry.create(renderTypes, "Add Technical Render Type");
		HUDMenuEntry.create(addComplexRenderTypes, "Colored Point", HUDCommandTypes.ADD_RENDER_TYPE, ColoredPointRenderType.class);
		HUDMenuEntry.create(addComplexRenderTypes, "Complex Point (Color & Size)", HUDCommandTypes.ADD_RENDER_TYPE, ComplexPointRenderType.class);
		HUDMenuEntry.create(addComplexRenderTypes, "Rain", HUDCommandTypes.ADD_RENDER_TYPE, SimpleRainRenderType.class);
		HUDMenuEntry.create(addComplexRenderTypes, "Cube (Color & Size)", HUDCommandTypes.ADD_RENDER_TYPE, CubeRenderType.class);
		HUDMenuEntry.create(addComplexRenderTypes, "VeloCube (Color & Size & Velocity)", HUDCommandTypes.ADD_RENDER_TYPE, VeloCubeRenderType.class);
		HUDMenuEntry.create(addComplexRenderTypes, "Metaballs", HUDCommandTypes.ADD_RENDER_TYPE, MetaBallsRenderType.class);
		HUDMenuEntry.create(addPrimitiveRenderTypes, "Simple Point", HUDCommandTypes.ADD_RENDER_TYPE, SimplePointRenderType.class);
		HUDMenuEntry.create(addPrimitiveRenderTypes, "Simple Line Strip", HUDCommandTypes.ADD_RENDER_TYPE, SimpleLineStripRenderType.class);
		HUDMenuEntry.create(addPrimitiveRenderTypes, "Simple Triangle", HUDCommandTypes.ADD_RENDER_TYPE, SimpleTriangleRenderType.class);
		HUDMenuEntry.create(addPrimitiveRenderTypes, "Simple Triangle Strip", HUDCommandTypes.ADD_RENDER_TYPE, SimpleTriangleStripRenderType.class);
		HUDMenuEntry.create(addPrimitiveRenderTypes, "Simple Triangle Fan", HUDCommandTypes.ADD_RENDER_TYPE, SimpleTriangleFanRenderType.class);
		HUDMenuEntry.create(addPrimitiveRenderTypes, "Simple Quads", HUDCommandTypes.ADD_RENDER_TYPE, SimpleQuadsRenderType.class);
		HUDMenuEntry.create(addPrimitiveRenderTypes, "Simple Sphere", HUDCommandTypes.ADD_RENDER_TYPE, SimpleSphereRenderType.class);
		HUDMenuEntry.create(addPointSpriteRenderTypes, "Ball", HUDCommandTypes.ADD_RENDER_TYPE, PointSpriteRenderType.class);
		HUDMenuEntry.create(addPointSpriteRenderTypes, "Smoke", HUDCommandTypes.ADD_RENDER_TYPE, SmokeRenderType.class);
		HUDMenuEntry.create(addPointSpriteRenderTypes, "Electric", HUDCommandTypes.ADD_RENDER_TYPE, ElectricRenderType.class);
		HUDMenuEntry.create(addPointSpriteRenderTypes, "Explosion", HUDCommandTypes.ADD_RENDER_TYPE, ExplosionRenderType.class);
		HUDMenuEntry.create(addPointSpriteRenderTypes, "FireBall", HUDCommandTypes.ADD_RENDER_TYPE, FireBallRenderType.class);
		HUDMenuEntry.create(addPointSpriteRenderTypes, "Flames", HUDCommandTypes.ADD_RENDER_TYPE, FlamesRenderType.class);
		HUDMenuEntry.create(addPointSpriteRenderTypes, "Poison", HUDCommandTypes.ADD_RENDER_TYPE, PoisonRenderType.class);
		HUDMenuEntry.create(addPointSpriteRenderTypes, "Ring", HUDCommandTypes.ADD_RENDER_TYPE, RingRenderType.class);
		HUDMenuEntry.create(addPointSpriteRenderTypes, "SFlare", HUDCommandTypes.ADD_RENDER_TYPE, SFlareRenderType.class);
		HUDMenuEntry.create(addPointSpriteRenderTypes, "Snow", HUDCommandTypes.ADD_RENDER_TYPE, SnowRenderType.class);
		HUDMenuEntry.create(addPointSpriteRenderTypes, "Star", HUDCommandTypes.ADD_RENDER_TYPE, StarRenderType.class);
		HUDMenuEntry.create(addPositionPathRenderTypes, "Tube", HUDCommandTypes.ADD_RENDER_TYPE, TubeRenderType.class);
		HUDMenuEntry.create(addPositionPathRenderTypes, "Hair", HUDCommandTypes.ADD_RENDER_TYPE, HairRenderType.class);
		HUDMenuEntry.create(addMassSpringRenderTypes, "Spring Polygons", HUDCommandTypes.ADD_RENDER_TYPE, SpringPolygonsRenderType.class);
		HUDMenuEntry.create(addMassSpringRenderTypes, "Springs Lines", HUDCommandTypes.ADD_RENDER_TYPE, SpringLinesRenderType.class);
		HUDMenuEntry.create(addTechnicalRenderTypes, "Null", HUDCommandTypes.ADD_RENDER_TYPE, SimplePointRenderType.class);
		HUDMenuEntry.create(addTechnicalRenderTypes, "Velocity Indicator", HUDCommandTypes.ADD_RENDER_TYPE, SimpleVelocityRenderType.class);
		HUDMenuEntry renderer = HUDMenuEntry.create(scene, "Renderer");
		HUDMenuEntry rendererOptions = HUDMenuEntry.create(renderer, "Renderer Options");
		HUDMenuEntry rendererOptionsBBPCR = HUDMenuEntry.create(rendererOptions, "Bounding Box Particle Culling Renderer");
		HUDMenuEntry.create(rendererOptionsBBPCR, "Show Particle Culling Box", HUDCommandTypes.SHOW_RENDERER, BoundingBoxParticleCullingRenderer.class);
		HUDMenuEntry.create(rendererOptionsBBPCR, "Hide Particle Culling Box", HUDCommandTypes.HIDE_RENDERER, BoundingBoxParticleCullingRenderer.class);
		HUDMenuEntry rendererOptionsEmitter = HUDMenuEntry.create(rendererOptions, "Emitter Renderer");
		HUDMenuEntry.create(rendererOptionsEmitter, "Show Emitters", HUDCommandTypes.SHOW_RENDERER, EmitterRenderer.class);
		HUDMenuEntry.create(rendererOptionsEmitter, "Hide Emitters", HUDCommandTypes.HIDE_RENDERER, EmitterRenderer.class);
		HUDMenuEntry rendererOptionsGravityPoint = HUDMenuEntry.create(rendererOptions, "Gravity Point Renderer");
		HUDMenuEntry.create(rendererOptionsGravityPoint, "Show Gravity Points", HUDCommandTypes.SHOW_RENDERER, GravityPointRenderer.class);
		HUDMenuEntry.create(rendererOptionsGravityPoint, "Hide Gravity Points", HUDCommandTypes.HIDE_RENDERER, GravityPointRenderer.class);
		HUDMenuEntry rendererOptionsBlackHole = HUDMenuEntry.create(rendererOptions, "Black Hole Renderer");
		HUDMenuEntry.create(rendererOptionsBlackHole, "Show Black Holes", HUDCommandTypes.SHOW_RENDERER, BlackHoleRenderer.class);
		HUDMenuEntry.create(rendererOptionsBlackHole, "Hide Black Holes", HUDCommandTypes.HIDE_RENDERER, BlackHoleRenderer.class);
		HUDMenuEntry rendererOptionsCollisionPlane = HUDMenuEntry.create(rendererOptions, "Collision Plane Renderer");
		HUDMenuEntry.create(rendererOptionsCollisionPlane, "Show Collision Planes", HUDCommandTypes.SHOW_RENDERER, CollisionPlaneRenderer.class);
		HUDMenuEntry.create(rendererOptionsCollisionPlane, "Hide Collision Planes", HUDCommandTypes.HIDE_RENDERER, CollisionPlaneRenderer.class);
		HUDMenuEntry rendererOptionsAxis = HUDMenuEntry.create(rendererOptions, "Axis Renderer");
		HUDMenuEntry.create(rendererOptionsAxis, "Show Axis", HUDCommandTypes.SHOW_RENDERER, AxisRenderer.class);
		HUDMenuEntry.create(rendererOptionsAxis, "Hide Axis", HUDCommandTypes.HIDE_RENDERER, AxisRenderer.class);
		HUDMenuEntry rendererOptionsCamera = HUDMenuEntry.create(rendererOptions, "Camera Renderer");
		HUDMenuEntry.create(rendererOptionsCamera, "Show Cameras", HUDCommandTypes.SHOW_RENDERER, CameraRenderer.class);
		HUDMenuEntry.create(rendererOptionsCamera, "Hide Cameras", HUDCommandTypes.HIDE_RENDERER, CameraRenderer.class);
		HUDMenuEntry rendererOptionsSkyBox = HUDMenuEntry.create(rendererOptions, "SkyBox Renderer");
		HUDMenuEntry.create(rendererOptionsSkyBox, "Harmony Skybox", HUDCommandTypes.SELECT_SKYBOX, "harmony");
		HUDMenuEntry.create(rendererOptionsSkyBox, "Interstellar Skybox", HUDCommandTypes.SELECT_SKYBOX, "interstellar");
		HUDMenuEntry.create(rendererOptionsSkyBox, "Sleepy Hollow Skybox", HUDCommandTypes.SELECT_SKYBOX, "sleepyhollow");
		HUDMenuEntry.create(rendererOptionsSkyBox, "Show SkyBox", HUDCommandTypes.SHOW_RENDERER, SkyBoxRenderer.class);
		HUDMenuEntry.create(rendererOptionsSkyBox, "Hide SkyBox", HUDCommandTypes.HIDE_RENDERER, SkyBoxRenderer.class);
		HUDMenuEntry addRenderer = HUDMenuEntry.create(renderer, "Add Renderer");
		HUDMenuEntry.create(addRenderer, "Bounding Box Particle Culling Renderer", HUDCommandTypes.ADD_RENDERER, BoundingBoxParticleCullingRenderer.class);
		HUDMenuEntry.create(addRenderer, "Emitter Renderer", HUDCommandTypes.ADD_RENDERER, EmitterRenderer.class);
		HUDMenuEntry.create(addRenderer, "Gravity Point Renderer", HUDCommandTypes.ADD_RENDERER, GravityPointRenderer.class);
		HUDMenuEntry.create(addRenderer, "Black Hole Renderer", HUDCommandTypes.ADD_RENDERER, BlackHoleRenderer.class);
		HUDMenuEntry.create(addRenderer, "Collision Plane Renderer", HUDCommandTypes.ADD_RENDERER, CollisionPlaneRenderer.class);
		HUDMenuEntry.create(addRenderer, "Axis Renderer", HUDCommandTypes.ADD_RENDERER, AxisRenderer.class);
		HUDMenuEntry.create(addRenderer, "Camera Renderer", HUDCommandTypes.ADD_RENDERER, CameraRenderer.class);
		HUDMenuEntry.create(addRenderer, "SkyBox Renderer", HUDCommandTypes.ADD_RENDERER, SkyBoxRenderer.class);
		HUDMenuEntry.create(renderer, "Show Text Overlay", HUDCommandTypes.SHOW_TEXT_OVERLAY);
		HUDMenuEntry.create(renderer, "Hide Text Overlay", HUDCommandTypes.HIDE_TEXT_OVERLAY);
		HUDMenuEntry camera = HUDMenuEntry.create(scene, "Cameras");
		HUDMenuEntry.create(camera, "Add Camera", HUDCommandTypes.ADD_CAMERA);
		HUDMenuEntry.create(camera, "Remove Camera", HUDCommandTypes.REMOVE_CAMERA);
		HUDMenuEntry.create(scene, "Load Scene", HUDCommandTypes.LOAD_SCENE);
		HUDMenuEntry.create(scene, "Save Scene", HUDCommandTypes.SAVE_SCENE);
		HUDMenuEntry.create(scene, "Remove all Particles", HUDCommandTypes.REMOVE_ALL_PARTICLES);
		HUDMenuEntry physics = HUDMenuEntry.create(root, "Physics");
		HUDMenuEntry emitters = HUDMenuEntry.create(physics, "Emitters");
		HUDMenuEntry addEmitters = HUDMenuEntry.create(emitters, "Add Emitter");
		HUDMenuEntry.create(addEmitters, "Point", HUDCommandTypes.ADD_EMITTER, PooledPointParticleEmitter.class, ParticleEmitterConfigurationFactory.class);
		HUDMenuEntry.create(addEmitters, "Plane", HUDCommandTypes.ADD_EMITTER, PlaneParticleEmitter.class, ParticleEmitterConfigurationFactory.class);
		HUDMenuEntry.create(addEmitters, "Ring", HUDCommandTypes.ADD_EMITTER, RingParticleEmitter.class, ParticleEmitterConfigurationFactory.class);
		HUDMenuEntry.create(addEmitters, "Sphere", HUDCommandTypes.ADD_EMITTER, SphereParticleEmitter.class, ParticleEmitterConfigurationFactory.class);
		HUDMenuEntry.create(addEmitters, "Wave", HUDCommandTypes.ADD_EMITTER, WaveParticleEmitter.class, ParticleEmitterConfigurationFactory.class);
		HUDMenuEntry.create(addEmitters, "Point (Pulse Rate)", HUDCommandTypes.ADD_EMITTER, PulseRatePointParticleEmitter.class, ParticleEmitterConfigurationFactory.class);
		HUDMenuEntry.create(addEmitters, "Point (Static)", HUDCommandTypes.ADD_EMITTER, StaticPointParticleEmitter.class, ParticleEmitterConfigurationFactory.class);
		HUDMenuEntry.create(addEmitters, "Point (Slow)", HUDCommandTypes.ADD_EMITTER, PointParticleEmitter.class, ParticleEmitterConfigurationFactory.class);
		HUDMenuEntry modifiers = HUDMenuEntry.create(physics, "Modifiers");
		HUDMenuEntry addModifiers = HUDMenuEntry.create(modifiers, "Add Modifier");
		HUDMenuEntry addModifiersGravity = HUDMenuEntry.create(addModifiers, "Gravity");
		HUDMenuEntry addModifiersVelocity = HUDMenuEntry.create(addModifiers, "Velocity");
		HUDMenuEntry addModifiersCollision = HUDMenuEntry.create(addModifiers, "Collision");
		HUDMenuEntry addModifiersColors = HUDMenuEntry.create(addModifiers, "Colors");
		HUDMenuEntry addModifiersSize = HUDMenuEntry.create(addModifiers, "Size");
		HUDMenuEntry addModifiersOther = HUDMenuEntry.create(addModifiers, "Other Modifiers");
		HUDMenuEntry.create(addModifiersGravity, "Gravity Point", HUDCommandTypes.ADD_MODIFIER, GravityPoint.class, GravityPointConfigurationFactory.class);
		HUDMenuEntry.create(addModifiersGravity, "Gravity Plane", HUDCommandTypes.ADD_MODIFIER, GravityPlane.class, GravityPlaneConfigurationFactory.class);
		HUDMenuEntry.create(addModifiersGravity, "Black Hole", HUDCommandTypes.ADD_MODIFIER, BlackHole.class, BlackHoleConfigurationFactory.class);
		HUDMenuEntry.create(addModifiersVelocity, "Velocity Transformation", HUDCommandTypes.ADD_MODIFIER, VelocityTransformation.class);
		HUDMenuEntry.create(addModifiersVelocity, "Velocity Damper", HUDCommandTypes.ADD_MODIFIER, VelocityDamper.class);
		HUDMenuEntry.create(addModifiersVelocity, "Mass Spring Transformation", HUDCommandTypes.ADD_MODIFIER, MassSpringTransformation.class);
		HUDMenuEntry.create(addModifiersVelocity, "Particle Gravity Transformation", HUDCommandTypes.ADD_MODIFIER, ParticleGravityTransformation.class);
		HUDMenuEntry.create(addModifiersCollision, "Collision Plane", HUDCommandTypes.ADD_MODIFIER, CollisionPlane.class, CollisionPlaneConfigurationFactory.class);
		HUDMenuEntry.create(addModifiersColors, "Random Start Color", HUDCommandTypes.ADD_MODIFIER, RandomStartColor.class);
		HUDMenuEntry.create(addModifiersColors, "Linear Color Transformation", HUDCommandTypes.ADD_MODIFIER, LinearColorTransformation.class);
		HUDMenuEntry.create(addModifiersColors, "Rainbow Color Transformation", HUDCommandTypes.ADD_MODIFIER, RainbowColorTransformation.class);
		HUDMenuEntry.create(addModifiersColors, "Random Color Transformation", HUDCommandTypes.ADD_MODIFIER, RandomColorTransformation.class);
		HUDMenuEntry.create(addModifiersColors, "Color Cycle Transformation", HUDCommandTypes.ADD_MODIFIER, ColorCycleTransformation.class);
		HUDMenuEntry.create(addModifiersSize, "Linear Size Transformation", HUDCommandTypes.ADD_MODIFIER, LinearSizeTransformation.class);
		HUDMenuEntry.create(addModifiersSize, "Pulse Size Transformation", HUDCommandTypes.ADD_MODIFIER, PulseSizeTransformation.class);
		HUDMenuEntry.create(addModifiersOther, "Bounding Box Particle Culling", HUDCommandTypes.ADD_MODIFIER, BoundingBoxParticleCulling.class);
		HUDMenuEntry.create(addModifiersOther, "Position Path Buffering", HUDCommandTypes.ADD_MODIFIER, PositionPathBuffering.class);
		HUDMenuEntry.create(addModifiersOther, "Particle Debugger", HUDCommandTypes.ADD_MODIFIER, ParticleDebugger.class);
		HUDMenuEntry features = HUDMenuEntry.create(physics, "Particle Features");
		HUDMenuEntry addFeatures = HUDMenuEntry.create(features, "Add Feature");
		HUDMenuEntry.create(addFeatures, "Color", HUDCommandTypes.ADD_FEATURE, ParticleColor.class);
		HUDMenuEntry.create(addFeatures, "Size", HUDCommandTypes.ADD_FEATURE, ParticleSize.class);
		HUDMenuEntry.create(addFeatures, "Initial Velocity Scatter", HUDCommandTypes.ADD_FEATURE, ParticleInitialVelocityScatter.class);
		HUDMenuEntry.create(addFeatures, "Mass-Spring", HUDCommandTypes.ADD_FEATURE, MassSpring.class);
		HUDMenuEntry.create(addFeatures, "Position Path Buffer", HUDCommandTypes.ADD_FEATURE, PositionPath.class);
		HUDMenuEntry.create(addFeatures, "Tube Segments", HUDCommandTypes.ADD_FEATURE, TubeSegments.class);
		HUDMenuEntry.create(addFeatures, "Replication", HUDCommandTypes.ADD_FEATURE, Replication.class);
		HUDMenuEntry.create(physics, "Load System", HUDCommandTypes.LOAD_SYSTEM);
		HUDMenuEntry.create(physics, "Save System", HUDCommandTypes.SAVE_SYSTEM);
		HUDMenuEntry.create(physics, "Remove all Particles", HUDCommandTypes.REMOVE_ALL_PARTICLES);
		HUDMenuEntry.create(root, "Help", HUDCommandTypes.HELP);
		HUDMenuEntry.create(root, "Exit", HUDCommandTypes.EXIT);
		return root;
	}
}
