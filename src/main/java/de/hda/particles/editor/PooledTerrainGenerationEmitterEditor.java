package de.hda.particles.editor;

import java.util.List;

import de.hda.particles.emitter.PooledTerrainGenerationEmitter;

public class PooledTerrainGenerationEmitterEditor extends AbstractParticleEmitterEditor<PooledTerrainGenerationEmitter> implements Editor {

	private final static String title = "Terrain Generation Particle Emitter";

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(PooledTerrainGenerationEmitter.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return PooledTerrainGenerationEmitter.class;
	}

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		return editorEntries;
	}
	
	@Override
	public void decrease(String fieldName) {
		super.decrease(fieldName);
//		if (fieldName.equals(PooledClothParticleEmitter.COLORED_CLOTH)
//			|| fieldName.equals(PooledClothParticleEmitter.STRONG_CLOTH)
//			|| fieldName.equals(PooledClothParticleEmitter.FIXING_AFTER_GAP)
//		) {
//			subject.getConfiguration().put(fieldName, false);
//		}
	}

	@Override
	public void setMin(String fieldName) {
		super.setMin(fieldName);
//		if (fieldName.equals(PooledClothParticleEmitter.COLORED_CLOTH)
//			|| fieldName.equals(PooledClothParticleEmitter.STRONG_CLOTH)
//			|| fieldName.equals(PooledClothParticleEmitter.FIXING_AFTER_GAP)
//		) {
//			subject.getConfiguration().put(fieldName, false);
//		}
	}

	@Override
	public void increase(String fieldName) {
		super.increase(fieldName);
//		if (fieldName.equals(PooledClothParticleEmitter.COLORED_CLOTH)
//			|| fieldName.equals(PooledClothParticleEmitter.STRONG_CLOTH)
//			|| fieldName.equals(PooledClothParticleEmitter.FIXING_AFTER_GAP)
//		) {
//			subject.getConfiguration().put(fieldName, true);
//		}
	}

	@Override
	public void setMax(String fieldName) {
		super.setMax(fieldName);
//		if (fieldName.equals(PooledClothParticleEmitter.COLORED_CLOTH)
//			|| fieldName.equals(PooledClothParticleEmitter.STRONG_CLOTH)
//			|| fieldName.equals(PooledClothParticleEmitter.FIXING_AFTER_GAP)
//		) {
//			subject.getConfiguration().put(fieldName, true);
//		}
	}

	@Override
	public String getValue(String fieldName) {
		String superValue = super.getValue(fieldName);
		if (! "N/A".equals(superValue)) {
			return superValue;
//		} else if (fieldName.equals(PooledClothParticleEmitter.COLORED_CLOTH)) {
//			return getBooleanStringValue(subject.getConfiguration().get(PooledClothParticleEmitter.COLORED_CLOTH));
//		} else if (fieldName.equals(PooledClothParticleEmitter.STRONG_CLOTH)) {
//			return getBooleanStringValue(subject.getConfiguration().get(PooledClothParticleEmitter.STRONG_CLOTH));
//		} else if (fieldName.equals(PooledClothParticleEmitter.FIXING_AFTER_GAP)) {
//			return getBooleanStringValue(subject.getConfiguration().get(PooledClothParticleEmitter.FIXING_AFTER_GAP));
		} else {
			return "N/A";
		}
	}

	@Override
	public String getTitle() {
		return title;
	}
	
}
