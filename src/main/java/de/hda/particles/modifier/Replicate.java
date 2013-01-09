package de.hda.particles.modifier;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.FixedPosition;
import de.hda.particles.modifier.AbstractParticleModifier;
import de.hda.particles.modifier.ParticleModifier;

/**
 * 
 * * modifier / feature: replication
** ein partikel pflanzt sich selbst fort (ein oder mehrere childs)
** chance in prozent pro iteration ein child zu erzeugen
*** z.b. particle hat lifetime von 1000 und chance ist 0.01 => ca. 10 childs
** chance reduktionsfaktor (0.0 - 1.0)
*** 0.0 -> replikations-chance bleibt gleich
*** 1.0 -> child repliziert nicht mehr
*** chance des childs ist: chance des parents * (1.0 - reduktionsfaktor des parents)
** lifetime reduktionsfaktor (0.0 - 1.0)
*** 0.0 -> lifetime bleibt gleich
*** 1.0 -> child lifetime wird 0
*** lifetime des childs ist: lifetime des parents * (1.0 - reduktionsfaktor des parents)
** child velocity: random, using parent normal, fractal (random ifs transformation)

 * @author aschaeffer
 *
 */
public class Replicate extends AbstractParticleModifier implements ParticleModifier {

	public Replicate() {}

	@Override
	public void update(Particle particle) {
		
		
		// this.particleSystem.addParticle();
		
//		if (particle.containsKey(FixedPosition.POSITION_FIXED)) {
//			if ((Boolean) particle.get(FixedPosition.POSITION_FIXED)) {
//				particle.setX(particle.getX() - particle.getVelX());
//				particle.setY(particle.getY() - particle.getVelY());
//				particle.setZ(particle.getZ() - particle.getVelZ());
//			}
//		}
	}

}
