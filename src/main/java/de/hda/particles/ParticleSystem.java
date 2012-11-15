package de.hda.particles;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.Particle;
import de.hda.particles.domain.ParticleEmitterConfiguration;
import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.features.ParticleFeature;
import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.timing.FpsInformation;

public interface ParticleSystem extends Updateable, FpsInformation {

	public List<Particle> getParticles();
	public List<ParticleFeature> getParticleFeatures();
	public List<ParticleEmitter> getParticleEmitters();
	public List<ParticleModifier> getParticleModifiers();

	public void addParticleEmitter(Class<? extends ParticleEmitter> clazz, Vector3f position, Vector3f velocity, Integer renderTypeIndex, Integer rate, Integer lifetime, ParticleEmitterConfiguration configuration);
	public void addParticleModifier(Class<? extends ParticleModifier> clazz, ParticleModifierConfiguration configuration);
	public void addParticleListener(ParticleLifetimeListener particleListener);
	public void addParticleFeature(ParticleFeature particleFeature);
	public void addParticleFeature(Class<? extends ParticleFeature> clazz);
	public void addParticle(Particle particle);
	public void removeParticle(Particle particle);
	public void removeAllParticles();
	
	public void pause();
	public void toggleEmitters();
	public void toggleModifiers();
	public Boolean isPaused();
	public Boolean areEmittersStopped();
	public Boolean areModifiersStopped();

}
