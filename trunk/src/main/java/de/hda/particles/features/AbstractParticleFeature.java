package de.hda.particles.features;

import de.hda.particles.emitter.ParticleEmitter;

public abstract class AbstractParticleFeature implements ParticleFeature {

	protected Integer increaseValue(ParticleEmitter emitter, String featureKey, Integer defaultValue, Integer maxValue) {
		Integer oldValue = (Integer) emitter.getConfiguration().get(featureKey);
		if (oldValue == null) oldValue = defaultValue;
		Integer newValue = oldValue;
		Integer incValue = 1;
		if (oldValue > 20) incValue *= 10;
		if (oldValue > 200) incValue *= 10;
		if (oldValue > 2000) incValue *= 10;
		if (oldValue > 20000) incValue *= 10;
		if (oldValue < maxValue) newValue += incValue;
		emitter.getConfiguration().put(featureKey, newValue);
		return newValue;
	}
	
	protected Integer increaseValue(ParticleEmitter emitter, String featureKey, Integer defaultValue) {
		Integer oldValue = (Integer) emitter.getConfiguration().get(featureKey);
		if (oldValue == null) oldValue = defaultValue;
		Integer newValue = oldValue;
		Integer incValue = 1;
		if (oldValue > 20) incValue *= 10;
		if (oldValue > 200) incValue *= 10;
		if (oldValue > 2000) incValue *= 10;
		if (oldValue > 20000) incValue *= 10;
		newValue += incValue;
		emitter.getConfiguration().put(featureKey, newValue);
		return newValue;
	}
	
	protected Integer increaseIntegerValue(ParticleEmitter emitter, String featureKey) {
		Integer oldValue = (Integer) emitter.getConfiguration().get(featureKey);
		if (oldValue == null) {
			setDefault(emitter, featureKey);
			oldValue = (Integer) emitter.getConfiguration().get(featureKey);
		}
		Integer newValue = oldValue;
		Integer incValue = 1;
		if (oldValue > 20) incValue *= 10;
		if (oldValue > 200) incValue *= 10;
		if (oldValue > 2000) incValue *= 10;
		if (oldValue > 20000) incValue *= 10;
		newValue += incValue;
		emitter.getConfiguration().put(featureKey, newValue);
		return newValue;
	}
	
	protected Integer decreaseValue(ParticleEmitter emitter, String featureKey, Integer defaultValue, Integer minValue) {
		Integer oldValue = (Integer) emitter.getConfiguration().get(featureKey);
		if (oldValue == null) oldValue = defaultValue;
		Integer newValue = oldValue;
		Integer decValue = 1;
		if (oldValue > 30) decValue *= 10;
		if (oldValue > 300) decValue *= 10;
		if (oldValue > 3000) decValue *= 10;
		if (oldValue > 30000) decValue *= 10;
		if (oldValue > minValue) newValue -= decValue;
		emitter.getConfiguration().put(featureKey, newValue);
		return newValue;
	}

	protected Integer decreaseValue(ParticleEmitter emitter, String featureKey, Integer defaultValue) {
		Integer oldValue = (Integer) emitter.getConfiguration().get(featureKey);
		if (oldValue == null) oldValue = defaultValue;
		Integer newValue = oldValue;
		Integer decValue = 1;
		if (oldValue > 30) decValue *= 10;
		if (oldValue > 300) decValue *= 10;
		if (oldValue > 3000) decValue *= 10;
		if (oldValue > 30000) decValue *= 10;
		newValue -= decValue;
		emitter.getConfiguration().put(featureKey, newValue);
		return newValue;
	}

	protected Integer decreaseIntegerValue(ParticleEmitter emitter, String featureKey) {
		Integer oldValue = (Integer) emitter.getConfiguration().get(featureKey);
		if (oldValue == null) {
			setDefault(emitter, featureKey);
			oldValue = (Integer) emitter.getConfiguration().get(featureKey);
		}
		Integer newValue = oldValue;
		Integer decValue = 1;
		if (oldValue > 30) decValue *= 10;
		if (oldValue > 300) decValue *= 10;
		if (oldValue > 3000) decValue *= 10;
		if (oldValue > 30000) decValue *= 10;
		newValue -= decValue;
		emitter.getConfiguration().put(featureKey, newValue);
		return newValue;
	}

	protected Double increaseValue(ParticleEmitter emitter, String featureKey, Double defaultValue, Double maxValue) {
		Double oldValue = (Double) emitter.getConfiguration().get(featureKey);
		if (oldValue == null) oldValue = defaultValue;
		Double newValue = oldValue;
		if (oldValue < maxValue) newValue += getFactoredIncValue(oldValue);
		emitter.getConfiguration().put(featureKey, newValue);
		return newValue;
	}
	
	protected Double increaseValue(ParticleEmitter emitter, String featureKey, Double defaultValue) {
		Double oldValue = (Double) emitter.getConfiguration().get(featureKey);
		if (oldValue == null) oldValue = defaultValue;
		Double newValue = oldValue;
		newValue += getFactoredIncValue(oldValue);
		emitter.getConfiguration().put(featureKey, newValue);
		return newValue;
	}
	
	protected Double increaseDoubleValue(ParticleEmitter emitter, String featureKey) {
		Double oldValue = (Double) emitter.getConfiguration().get(featureKey);
		if (oldValue == null) {
			setDefault(emitter, featureKey);
			oldValue = (Double) emitter.getConfiguration().get(featureKey);
		}
		Double newValue = oldValue;
		newValue += getFactoredIncValue(oldValue);
		emitter.getConfiguration().put(featureKey, newValue);
		return newValue;
	}
	
	protected Double decreaseValue(ParticleEmitter emitter, String featureKey, Double defaultValue, Double minValue) {
		Double oldValue = (Double) emitter.getConfiguration().get(featureKey);
		if (oldValue == null) oldValue = defaultValue;
		Double newValue = oldValue;
		if (oldValue > minValue) newValue -= getFactoredIncValue(oldValue);
		emitter.getConfiguration().put(featureKey, newValue);
		return newValue;
	}

	protected Double decreaseValue(ParticleEmitter emitter, String featureKey, Double defaultValue) {
		Double oldValue = (Double) emitter.getConfiguration().get(featureKey);
		if (oldValue == null) oldValue = defaultValue;
		Double newValue = oldValue;
		newValue -= getFactoredIncValue(oldValue);
		emitter.getConfiguration().put(featureKey, newValue);
		return newValue;
	}

	protected Double decreaseDoubleValue(ParticleEmitter emitter, String featureKey) {
		Double oldValue = (Double) emitter.getConfiguration().get(featureKey);
		if (oldValue == null) {
			setDefault(emitter, featureKey);
			oldValue = (Double) emitter.getConfiguration().get(featureKey);
		}
		Double newValue = oldValue;
		newValue -= getFactoredIncValue(oldValue);
		emitter.getConfiguration().put(featureKey, newValue);
		return newValue;
	}

	private Double getFactoredIncValue(Double oldValue) {
		Double incValue = 0.01;
		if (oldValue > 0.3 || oldValue < -0.3) incValue *= 10.0;
		if (oldValue > 3.0 || oldValue < -3.0) incValue *= 10.0;
		if (oldValue > 30.0 || oldValue < -30.0) incValue *= 10.0;
		if (oldValue > 300.0 || oldValue < -300.0) incValue *= 10.0;
		if (oldValue > 3000.0 || oldValue < -3000.0) incValue *= 10.0;
		if (oldValue > 30000.0 || oldValue < -30000.0) incValue *= 10.0;
		return incValue;
	}
	
	protected String getIntegerValueAsString(ParticleEmitter emitter, String featureKey) {
		if (!validFieldName(featureKey)) return null;
		Integer value = (Integer) emitter.getConfiguration().get(featureKey);
		if (value == null) {
			return "N/A";
		} else {
			return value.toString();
		}
	}
	
	protected String getDoubleValueAsString(ParticleEmitter emitter, String featureKey) {
		if (!validFieldName(featureKey)) return null;
		Double value = (Double) emitter.getConfiguration().get(featureKey);
		if (value == null) {
			return "N/A";
		} else {
			return String.format("%.2f", value);
		}
	}

}
