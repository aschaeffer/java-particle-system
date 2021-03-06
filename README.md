# Java Particle System

This project focus on providing a fully featured particle system and editor, which means that you can create your little world. The scene editor let you create, move and edit emitters and modifiers such as gravity points visually. Also you can configure nearly everything.

The particle system was created by Ruth and Andreas and is written in Java using LWJGL. Computer graphics course by Prof. Dr. Elke Hergenröther (winter term 2012/13).

License: GPL3

## Physics

* domain pattern: particles are just data (no logic)
* flexible particles: you can add features to particles
* physic system contains multiple emitters, multiple modifiers, multiple particle features
* multiple types of emitters (PointEmitter?, PlaneEmitter?, RingEmitter?, SphereEmitter?)
* multiple types of modifiers:
  * Velocity Transformation (important!)
  * Gravity Point
  * Gravity Plane
  * Black Hole
  * Color Transformation (linear, random, rainbow ...)
  * Size Transformation (linear, ...)
  * Particle Culling (bounding box, sphere, ...) 
  * listerner pattern: onParticleCreation, onParticleDeath
  * configurable, loading / saving physics 
* Particle Rendering
  * using point sprites
  * using sprite sheets
  * billboarding: textures are aimed to the camera
  * blending
  * multiple render types for particles
    * Simple Point
    * Colored Point
    * Complex Point (Color & Size)
    * Simple Triangle
    * Simple Triangle Strip
    * Simple Line Strip
    * Simple Quad
    * Point Sprite
    * Smoke Particle (point sprite)
    * Fire Particle (point sprite, sprite sheet animation)
    * Electric Particle (point sprite, sprite sheet animation) 

## Scene

* different types of renderers (cameras, huds, RenderTypeRenderer?, EmitterRenderer?, GravityPointRenderer?)
* different types of cameras (FPSCamera)
* cycling through cameras at runtime
* multiple huds (Information about FPS, Cameras, ParticleCount? ...)
* hud command system (for example to display notification messages)
* picking/moving (e.g. selecting and moving emitters, gravity points ...)
* hud editor system (editing emitters, gravity points ...)
* configurable, loading / saving scenes 

## Overall

* Physics independent from rendering
* Implementation in Java style (using interfaces, abstract classes, implementation classes, references, factory methods ...)
* Very flexible
* increased performance: physics and rendering are seperated into its own threads
* (optional:) increased performance: multiple physics threads operating on a bucket of particles

