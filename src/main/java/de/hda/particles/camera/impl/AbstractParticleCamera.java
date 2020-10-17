package de.hda.particles.camera.impl;

import de.hda.particles.domain.Particle;
import java.util.List;
import java.util.Random;

/**
 * The camera is bound to a particle.
 */
public abstract class AbstractParticleCamera extends AbstractControlCamera {

  /**
   * The current particle.
   */
  protected Particle particle;

  private final Random random = new Random();

  @Override
  public void nextMode() {
    getNextParticle();
  }

  public boolean updateParticle() {
    if (particle == null || !particle.isAlive()) {
      getNextParticle();
      // if particle null or not alive: wait for next iteration
      return particle == null || !particle.isAlive();
    }
    return false;
  }

  public void updatePosition() {
    position.x = particle.getX();
    position.y = particle.getY();
    position.z = particle.getZ();
  }

  public void getNextParticle() {
    try {
      List<Particle> particles = scene.getParticleSystem().getParticles();
      Integer randomParticleIndex = random.nextInt(particles.size());
      particle = particles.get(randomParticleIndex);
    } catch (Exception e) {}
  }

}
