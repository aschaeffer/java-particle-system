package de.hda.particles.emitter;

import java.util.ArrayList;
import java.util.ListIterator;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Face;
import de.hda.particles.domain.Particle;
import de.hda.particles.features.FixedPosition;
import de.hda.particles.features.MassSpring;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.modifier.velocity.MassSpringTransformation;

/**
 * Generates a cloth as a 2D particle network.
 * 
 * @author aschaeffer
 *
 */
public class PooledClothParticleEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	public final static String FIXING_AFTER_GAP = "clothFixingAfterGap";
	public final static String STRONG_CLOTH = "strongCloth";
	public final static String COLORED_CLOTH = "coloredCloth";

	public final static Boolean DEFAULT_FIXING_AFTER_GAP = false;
	public final static Boolean DEFAULT_STRONG_CLOTH = false;
	public final static Boolean DEFAULT_COLORED_CLOTH = false;

	private final ArrayList<Particle> newParticles = new ArrayList<Particle>();
	private final ArrayList<Particle> generatedParticlesAtLastIteration = new ArrayList<Particle>();
	private final ArrayList<Particle> clothBorderParticles = new ArrayList<Particle>();
	
	private Double springLength;
	private Double springFriction;
	private Double springConstant;
	private Boolean fixingAfterGap = DEFAULT_FIXING_AFTER_GAP;
	private Boolean strongCloth = DEFAULT_STRONG_CLOTH;
	private Boolean coloredCloth = DEFAULT_COLORED_CLOTH;
	
	private Integer s_r;
	private Integer s_g;
	private Integer s_b;
	private Integer s_a;
	private Integer e_r;
	private Integer e_g;
	private Integer e_b;
	private Integer e_a;
	private Integer se_modifier;
	
	private int r;
	private int g;
	private int b;
	private int a;
	private float p;
	
	private Integer currentParticleSystemIteration = 0;
	private Integer lastParticleSystemIteration = 0;
	private Boolean broken = true;

	public PooledClothParticleEmitter() {}

	/**
	 * Fetches a particle (new or recylced) from the particle pool,
	 * set default values and constructs particle features.
	 */
	@Override
	public void update() {
		pastIterations++;
		prepare(); // only once per iteration

		// TODO: generate fixed points instead of using particle feature
		// TODO: move to private method
		// detect if emitter was paused
		currentParticleSystemIteration = particleSystem.getPastIterations();
		if (currentParticleSystemIteration > lastParticleSystemIteration + 1) {
			broken = true;
			if (fixingAfterGap) {
				// mark cloth border particles as fixed
				ListIterator<Particle> iterator1 = generatedParticlesAtLastIteration.listIterator(0);
				while (iterator1.hasNext()) {
					iterator1.next().put(FixedPosition.POSITION_FIXED, true);
				}
				ListIterator<Particle> iterator2 = clothBorderParticles.listIterator(0);
				while (iterator2.hasNext()) {
					iterator2.next().put(FixedPosition.POSITION_FIXED, true);
				}
				clothBorderParticles.clear();
			}
		}
		lastParticleSystemIteration = currentParticleSystemIteration;
		
		// generate next row of particles and connect springs
		newParticles.clear();
		for (Integer i = 0; i < rate; i++) {
			Particle particle = generateParticle();
			particle.setX(position.x + i * (springLength.floatValue() * 0.75f)); // shift start positions (spring length)
			ArrayList<Particle> springConnectedParticles = new ArrayList<Particle>();
			particleSystem.addParticle(particle);
			newParticles.add(particle);
			if (fixingAfterGap && (broken || i == 0 || i == rate-1)) clothBorderParticles.add(particle); // mark cloth border particles as fixed
			if (!broken && generatedParticlesAtLastIteration.size() > i) {
				// previous precedessor
				springConnectedParticles.add(generatedParticlesAtLastIteration.get(i));
				if (i > 0) {
					// left precedessor
					springConnectedParticles.add(generatedParticlesAtLastIteration.get(i-1));
					// left neighbour
					springConnectedParticles.add(newParticles.get(i-1));
					// generate a face!
					particleSystem.addFace(generateFace(
						particle,
						newParticles.get(i-1),
						generatedParticlesAtLastIteration.get(i-1),
						generatedParticlesAtLastIteration.get(i)
					));
				}
				if (i+1 < generatedParticlesAtLastIteration.size()) {
					// right precedessor (makes it more stable)
					springConnectedParticles.add(generatedParticlesAtLastIteration.get(i+1));
				}
				if (strongCloth) {
					if (i > 1) {
						// left-left neighbour
						springConnectedParticles.add(newParticles.get(i-2));
						// left-left precedessor
						if (generatedParticlesAtLastIteration.size() >= i) {
							springConnectedParticles.add(generatedParticlesAtLastIteration.get(i-2));
						}
					}
					if (i+2 < generatedParticlesAtLastIteration.size()) {
						// right-right precedessor
						springConnectedParticles.add(generatedParticlesAtLastIteration.get(i+2));
					}
				}
			}
			particle.put(MassSpring.SPRING_CONNECTED_PARTICLES, springConnectedParticles);
			if (coloredCloth) {
				s_r = (Integer) configuration.get(ParticleColor.START_COLOR_R);
				s_g = (Integer) configuration.get(ParticleColor.START_COLOR_G);
				s_b = (Integer) configuration.get(ParticleColor.START_COLOR_G);
				s_a = (Integer) configuration.get(ParticleColor.START_COLOR_A);
				e_r = (Integer) configuration.get(ParticleColor.END_COLOR_R);
				e_g = (Integer) configuration.get(ParticleColor.END_COLOR_G);
				e_b = (Integer) configuration.get(ParticleColor.END_COLOR_B);
				e_a = (Integer) configuration.get(ParticleColor.END_COLOR_A);
				// if (s_r == null || s_g == null || s_b == null || s_a == null || e_r == null || e_g == null || e_b == null || e_a == null) break;
				se_modifier = (pastIterations + i) % rate;
				p = se_modifier.floatValue() / rate.floatValue();
				if (p <= 0.5f) {
					r = (int) (p * e_r + (1.0f - p) * s_r);
					g = (int) (p * e_g + (1.0f - p) * s_g);
					b = (int) (p * e_b + (1.0f - p) * s_b);
					a = (int) (p * e_a + (1.0f - p) * s_a);
				} else {
					r = (int) (p * s_r + (1.0f - p) * e_r);
					g = (int) (p * s_g + (1.0f - p) * e_g);
					b = (int) (p * s_b + (1.0f - p) * e_b);
					a = (int) (p * s_a + (1.0f - p) * e_a);
				}
				particle.put(ParticleColor.CURRENT_COLOR, new Color(r, g, b, a));
			}
		}
		generatedParticlesAtLastIteration.clear();
		generatedParticlesAtLastIteration.addAll(newParticles);
		broken = false;
	}
	
	private Particle generateParticle() {
		Particle particle = particleSystem.getParticlePool().next();
		particle.setPastIterations(0);
		particle.setMass(Particle.DEFAULT_MASS);
		particle.setVisibility(true);
		particle.setIndex(0);
		particle.clear();
		particle.setPosition(position);
		particle.setVelocity(particleDefaultVelocity);
		particle.setRenderTypeIndex(particleRenderTypeIndex);
		particle.setRemainingIterations(particleLifetime);
		particle.put(MassSpring.SPRING_LENGTH, springLength);
		particle.put(MassSpring.SPRING_FRICTION, springFriction);
		particle.put(MassSpring.SPRING_CONSTANT, springConstant);
		return particle;
	}
	
	private Face generateFace(Particle p1, Particle p2, Particle p3, Particle p4) {
		Face face = particleSystem.getFacePool().next();
		face.setFaceRendererIndex(faceRendererIndex);
		face.clear();
		face.add(p1);
		face.add(p2);
		face.add(p3);
		face.add(p4);
		return face;
	}
	
	private void prepare() {
		springLength = (Double) configuration.get(MassSpring.SPRING_LENGTH);
		if (springLength == null) {
			springLength = MassSpring.DEFAULT_SPRING_LENGTH;
			configuration.put(MassSpring.SPRING_LENGTH, MassSpring.DEFAULT_SPRING_LENGTH);
		}
		springFriction = (Double) configuration.get(MassSpring.SPRING_FRICTION);
		if (springFriction == null) {
			springFriction = MassSpring.DEFAULT_SPRING_FRICTION;
			configuration.put(MassSpring.SPRING_FRICTION, MassSpring.DEFAULT_SPRING_FRICTION);
		}
		springConstant = (Double) configuration.get(MassSpring.SPRING_CONSTANT);
		if (springConstant == null) {
			springConstant = MassSpring.DEFAULT_SPRING_CONSTANT;
			configuration.put(MassSpring.SPRING_CONSTANT, MassSpring.DEFAULT_SPRING_CONSTANT);
		}
		fixingAfterGap = (Boolean) configuration.get(FIXING_AFTER_GAP);
		if (fixingAfterGap == null) {
			fixingAfterGap = DEFAULT_FIXING_AFTER_GAP;
			configuration.put(FIXING_AFTER_GAP, DEFAULT_FIXING_AFTER_GAP);
		}
		strongCloth = (Boolean) configuration.get(STRONG_CLOTH);
		if (strongCloth == null) {
			strongCloth = DEFAULT_STRONG_CLOTH;
			configuration.put(STRONG_CLOTH, DEFAULT_STRONG_CLOTH);
		}
		coloredCloth = (Boolean) configuration.get(COLORED_CLOTH);
		if (coloredCloth == null) {
			coloredCloth = DEFAULT_COLORED_CLOTH;
			configuration.put(COLORED_CLOTH, DEFAULT_COLORED_CLOTH);
		}
	}
	
	@Override
	public void setup() {}

	@Override
	public void destroy() {}

	@Override
	public void addDependencies() {
		particleSystem.addParticleFeature(ParticleColor.class);
		particleSystem.addParticleFeature(MassSpring.class);
		particleSystem.addParticleModifier(MassSpringTransformation.class);
		// velocity transformation
		// (velocity damper???)
		// fixed particles velocity blocker 
	}

}
