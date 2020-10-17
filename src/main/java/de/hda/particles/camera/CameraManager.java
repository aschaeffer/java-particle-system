package de.hda.particles.camera;

import de.hda.particles.domain.Identifiable;
import de.hda.particles.renderer.Renderer;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;

public interface CameraManager extends Renderer, Camera, Identifiable {

  /**
   * Returns the currently selected camera.
   * @return The currently selected camera.
   */
  Camera getSelectedCamera();

  /**
   * Selects the next camera.
   */
  void selectNextCamera();

  /**
   * Adds the given camera.
   * @param camera The camera to add.
   */
  void add(Camera camera);

  /**
   * Adds a camara of the given type.
   * @param type The camera type.
   */
  void add(Class<? extends Camera> type);

  /**
   * Adds a camera of the given type.
   * @param type The camera type.
   * @param name The name of the camera.
   * @param position The position of the camera.
   * @param yaw The yaw.
   * @param pitch The pitch.
   * @param roll The roll.
   * @param fov The field of view.
   */
  void add(Class<? extends Camera> type, String name, Vector3f position, Float yaw, Float pitch, Float roll, Float fov);

  /**
   * Removes the currently selected camera.
   */
  void removeSelectedCamera();

  /**
   * Returns all cameras.
   * @return The list of cameras.
   */
  List<Camera> getCameras();

}
