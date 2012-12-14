package de.hda.particles;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.lwjgl.opengl.Display;

import de.hda.particles.domain.Particle;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.modifier.ParticleModifier;

/**
 * A second abstract implementation of a particle system. This one
 * starts a thread for every modifier. (Sadly, it doesn't boost the
 * performance.)
 * 
 * @author aschaeffer
 *
 */
public abstract class ThreadedAbstractParticleSystem extends AbstractParticleSystem implements ParticleSystem {

	private static final Integer WORKER_THREADS = 3;
	private static final Integer MAX_PARTICLES = 100000;

	protected List<ModifierWorker> modifierWorkers = new ArrayList<ModifierWorker>();
	
	protected Boolean synchronizeWorkers = true;
	protected Integer modifierWorkerReady = 0;
	
	@Override
	public void update() {
		calcFps();
		limitFps();
		
		if (paused) {
			idle = true;
			return;
		}
		idle = false;
		pastIterations++;

		if (emittersEnabled && particles.size() < MAX_PARTICLES) {
			// call every particle emitter
			ListIterator<ParticleEmitter> eIterator = emitters.listIterator(0);
			while (eIterator.hasNext()) eIterator.next().update();
		}

		if (modifiersEnabled) {
			// synchronize modifier workers
			if (synchronizeWorkers) synchronize();
			// modify existing particles
			Integer bucketSize = particles.size() / modifierWorkers.size();
			ListIterator<ModifierWorker> wIterator = modifierWorkers.listIterator(0);
			while (wIterator.hasNext()) {
				wIterator.next().next(bucketSize);
			}
		}

		if (clearParticlesAtNextIteration) clearParticles(); // Thread Safety

		// decrease particle lifetimes and remove death particles
		// if (pastIterations % 500 == 0) {
		for (Integer pIndex = 0; pIndex < particles.size(); pIndex++) {
			Particle particle = particles.get(pIndex);
			particle.decLifetime();
			if (particle.getRemainingIterations() <= 0) {
				removeParticle(particle);
			}
		}
		// }
		
	}

	@Override
	public void setup() {
		for (Integer i = 0; i < WORKER_THREADS; i++) {
			ModifierWorker worker = new ModifierWorker(i);
			Thread modifierWorkersThread = new Thread(worker);
			modifierWorkersThread.start();
			modifierWorkers.add(worker);
			modifierWorkerReady++;
		}
	}
	
	private void synchronize() {
		// sychronize workers
		while (modifierWorkerReady < modifierWorkers.size()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {}
		}
		modifierWorkerReady = 0;
	}

	protected class ModifierWorker implements Runnable {
		
		private Integer id = 0;
		private Integer bucketSize = 0;
		private Boolean stopped = false;
		
		public ModifierWorker(Integer id) {
			this.id = id;
		}
		
		public void next(Integer bucketSize) {
			this.bucketSize = bucketSize;
		}
		
		public void stop() {
			this.stopped = true;
		}
		
		@Override
		public void run() {
			while (!stopped) {
				try {
					while (!modifiersEnabled) Thread.sleep(100);
					ListIterator<ParticleModifier> mIterator = modifiers.listIterator(0);
					while (mIterator.hasNext()) {
						ParticleModifier modifier = mIterator.next();
						Integer startIndex = id * bucketSize;
						Integer endIndex = startIndex + bucketSize;
						if (endIndex >= particles.size() - 1) endIndex = particles.size() - 1;
						try {
							for (Integer pIndex = startIndex; pIndex < endIndex; pIndex++) {
								modifier.update(particles.get(pIndex));
							}
						} catch (Exception e) {}
//						List<Particle> currentParticles = new ArrayList<Particle>(particles);
//						List<Particle> bucket = currentParticles.subList(id * bucketSize, id * bucketSize + bucketSize);
//						ListIterator<Particle> pIterator = bucket.listIterator(0);
//						while (pIterator.hasNext()) modifier.update(pIterator.next());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (synchronizeWorkers) {
					modifierWorkerReady++;
					Display.sync(maxFps);
				}
			}
		}
		
	}

}
