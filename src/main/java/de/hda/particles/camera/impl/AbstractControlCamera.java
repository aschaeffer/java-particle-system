package de.hda.particles.camera.impl;

import de.hda.particles.scene.Scene;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public abstract class AbstractControlCamera extends AbstractCamera {

  protected Float movementModifier = 1.0f;

  protected Integer deltaWheel = 0;

  /**
   * Default no arguments constructor.
   */
  public AbstractControlCamera() {
    super();
  }

  // Constructor that takes the starting x, y, z location of the camera
  public AbstractControlCamera(String name, Scene scene, Vector3f position) {
    super(name, scene, position);
  }

  // Constructor that takes the starting x, y, z location of the camera
  public AbstractControlCamera(String name, Scene scene, Vector3f position, Float yaw, Float pitch, Float roll, Float fov) {
    super(name, scene, position, yaw, pitch, roll, fov);
  }

  void updateMovementModifier() {
    // when passing in the distance to move
    // we times the movementSpeed with dt this is a time scale
    // so if its a slow frame u move more then a fast frame
    // so on a slow computer you move just as fast as on a fast computer
    movementModifier = 1.0f;

    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
      movementModifier = movementModifier * 10.0f;
    }
    if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
      movementModifier = movementModifier * 10.0f;
    }
  }

  public void zoom() {
    deltaWheel = Mouse.getDWheel();
    if (deltaWheel < 0) {
      zoom(movementModifier * -deltaWheel / 60.0f);
    } else if (deltaWheel > 0) {
      zoom(movementModifier * -deltaWheel / 60.0f);
    }
  }

  @Override
  public void setup() {
    Mouse.setGrabbed(true);
  }

  @Override
  public void destroy() {
    Mouse.setGrabbed(false);
  }

}
