package de.hda.particles.editor.impl.modifier;

import de.hda.particles.editor.Editor;
import de.hda.particles.editor.impl.HUDEditorEntry;
import java.util.List;

import de.hda.particles.hud.impl.HUDCommandTypes;
import de.hda.particles.hud.impl.TopMenuHUD;
import de.hda.particles.listener.ModifierLifetimeListener;
import de.hda.particles.modifier.impl.ParticleLimiter;
import de.hda.particles.modifier.ParticleModifier;

public class ParticleLimiterEditor extends AbstractParticleModifierEditor<ParticleLimiter> implements Editor, ModifierLifetimeListener {

	private final static String title = "Particle Limiter";

	private Boolean active = false;

	@Override
	public void setup() {
		editorEntries.add(HUDEditorEntry.create(ParticleLimiter.MAX_PARTICLES, "Max Particles"));
		scene.getParticleSystem().addModifierListener(this);
	}

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(ParticleLimiter.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return ParticleLimiter.class;
	}

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		return editorEntries;
	}
	
	@Override
	public void decrease(String fieldName) {
		if (fieldName.equals(ParticleLimiter.MAX_PARTICLES)) {
			Integer maxParticles = (Integer) subject.getConfiguration().get(ParticleLimiter.MAX_PARTICLES);
			if (maxParticles == null) maxParticles = ParticleLimiter.DEFAULT_MAX_PARTICLES;
			if (maxParticles > ParticleLimiter.MIN_MAX_PARTICLES) {
				subject.getConfiguration().put(ParticleLimiter.MAX_PARTICLES, maxParticles - 1000);
			}
		}
	}

	@Override
	public void setMin(String fieldName) {
		subject.getConfiguration().put(ParticleLimiter.MAX_PARTICLES, ParticleLimiter.MIN_MAX_PARTICLES);
	}

	@Override
	public void increase(String fieldName) {
		if (fieldName.equals(ParticleLimiter.MAX_PARTICLES)) {
			Integer maxParticles = (Integer) subject.getConfiguration().get(ParticleLimiter.MAX_PARTICLES);
			if (maxParticles == null) maxParticles = ParticleLimiter.DEFAULT_MAX_PARTICLES;
			if (maxParticles < ParticleLimiter.MAX_MAX_PARTICLES) {
				subject.getConfiguration().put(ParticleLimiter.MAX_PARTICLES, maxParticles + 1000);
			}
		}
	}

	@Override
	public void setMax(String fieldName) {
		subject.getConfiguration().put(ParticleLimiter.MAX_PARTICLES, ParticleLimiter.MAX_MAX_PARTICLES);
	}

	@Override
	public String getValue(String fieldName) {
		String superValue = super.getValue(fieldName);
		if (! "N/A".equals(superValue)) {
			return superValue;
		} else if (fieldName.equals(ParticleLimiter.MAX_PARTICLES)) {
			Integer maxParticles = (Integer) subject.getConfiguration().get(ParticleLimiter.MAX_PARTICLES);
			if (maxParticles == null) maxParticles = ParticleLimiter.DEFAULT_MAX_PARTICLES;
			return maxParticles.toString();
		} else {
			return "N/A";
		}
	}

	@Override
	public String getTitle() {
		return title;
	}
	
	/**
	 * Let's add this editor to the top menu when ParticleLimiter modifier
	 * is created.
	 */
	@Override
	public void onModifierCreation(ParticleModifier modifier) {
		if (!active && modifier.getClass().equals(ParticleLimiter.class)) {
			scene.getMenuManager().addChildMenuEntryToEntry(TopMenuHUD.TOP_MENU, "ParticleLimiter", "ParticleLimiter", "faenza/dark/view-sort-ascending", HUDCommandTypes.EDIT, this, ParticleLimiter.class);
			active = true;
		}
	}

	@Override
	public void onModifierDeath(ParticleModifier modifier) {}

}
