package de.hda.particles.domain;

/**
 * This interface gives an object a name.
 */
public interface Named {

  /**
   * Returns the name.
   * @return The name.
   */
  String getName();

  /**
   * Sets the name.
   * @param name The name.
   */
  default void setName(String name) {
  }

}
