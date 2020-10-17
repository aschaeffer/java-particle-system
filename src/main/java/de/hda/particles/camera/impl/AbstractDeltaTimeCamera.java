package de.hda.particles.camera.impl;

import de.hda.particles.scene.Scene;
import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector3f;

public abstract class AbstractDeltaTimeCamera extends AbstractControlCamera {

  protected long time = 0;
  protected Float deltaTime = 0.0f; // length of frame
  protected long lastTime = 0; // when the last frame was

  /**
   * Default no arguments constructor.
   */
  public AbstractDeltaTimeCamera() {
    super();
  }

  // Constructor that takes the starting x, y, z location of the camera
  public AbstractDeltaTimeCamera(String name, Scene scene, Vector3f position) {
    super(name, scene, position);
  }

  // Constructor that takes the starting x, y, z location of the camera
  public AbstractDeltaTimeCamera(String name, Scene scene, Vector3f position, Float yaw, Float pitch, Float roll, Float fov) {
    super(name, scene, position, yaw, pitch, roll, fov);
  }

  public void updateTime() {
    time = Sys.getTime();
    deltaTime = (time - lastTime) / 1000.0f;
    lastTime = time;
  }

}
