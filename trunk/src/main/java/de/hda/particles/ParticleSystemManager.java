package de.hda.particles;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.ListIterator;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.domain.ParticleEmitterConfiguration;
import de.hda.particles.domain.ParticleModifierConfiguration;
import de.hda.particles.domain.ParticleSystemConfiguration;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.modifier.ParticleModifier;
import de.hda.particles.features.ParticleFeature;

public class ParticleSystemManager {

	private ObjectMapper mapper = new ObjectMapper();
	
	@SuppressWarnings("unchecked")
	public ParticleSystem load(String filename) throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
		// read json
		InputStream in = this.getClass().getResourceAsStream(filename);
		ParticleSystemConfiguration configuration = mapper.readValue(in, ParticleSystemConfiguration.class);
		// create particle system
		ConfigurableParticleSystem particleSystem = new ConfigurableParticleSystem();
		particleSystem.setName(configuration.name);
		// init particle features
		ListIterator<String> featuresIterator = configuration.features.listIterator(0);
		while (featuresIterator.hasNext()) {
			String className = featuresIterator.next();
			particleSystem.addParticleFeature((Class<? extends ParticleFeature>) Class.forName(className));
		}
		// init particle emitters
		ListIterator<HashMap<String, Object>> emittersIterator = configuration.emitters.listIterator(0);
		while (emittersIterator.hasNext()) {
			HashMap<String, Object> emitterConfiguration = emittersIterator.next();
			String emitterClassName = (String) emitterConfiguration.get("className");
			Class<? extends ParticleEmitter> emitterClass = (Class<? extends ParticleEmitter>) Class.forName(emitterClassName);
			// String renderTypeClassName = (String) emitterConfiguration.get("renderType");
			// Class<? extends RenderType> renderTypeClass = (Class<? extends RenderType>) Class.forName(renderTypeClassName);
			Integer rate = (Integer) emitterConfiguration.get("rate");
			Integer lifetime = (Integer) emitterConfiguration.get("lifetime");
			Vector3f position = new Vector3f();
			position.x = new Float((Double) emitterConfiguration.get("position_x"));
			position.y = new Float((Double) emitterConfiguration.get("position_y"));
			position.z = new Float((Double) emitterConfiguration.get("position_z"));
			Vector3f velocity = new Vector3f();
			velocity.x = new Float((Double) emitterConfiguration.get("velocity_x"));
			velocity.y = new Float((Double) emitterConfiguration.get("velocity_y"));
			velocity.z = new Float((Double) emitterConfiguration.get("velocity_z"));
			Map<String, Object> configMap = (Map<String, Object>) emitterConfiguration.get("configuration");
			ParticleEmitterConfiguration config = new ParticleEmitterConfiguration();
			config.putAll(configMap);
			System.out.println(emitterClass);
			System.out.println(position);
			System.out.println(velocity);
			System.out.println(lifetime);
			System.out.println(config);
			particleSystem.addParticleEmitter(emitterClass, position, velocity, 1, rate, lifetime, config);
		}
				
		// init particle modifiers
		ListIterator<HashMap<String, Object>> modifiersIterator = configuration.modifiers.listIterator(0);
		while (modifiersIterator.hasNext()) {
			HashMap<String, Object> modifierConfiguration = modifiersIterator.next();
			String modifierClassName = (String) modifierConfiguration.get("className");
			Class<? extends ParticleModifier> modifierClass = (Class<? extends ParticleModifier>) Class.forName(modifierClassName);
			Map<String, Object> configMap = (Map<String, Object>) modifierConfiguration.get("configuration");
			ParticleModifierConfiguration config = new ParticleModifierConfiguration();
			config.putAll(configMap);
			particleSystem.addParticleModifier(modifierClass, config);
		}
		
		return particleSystem;
	}
	
	public void save(String filename) {
		
	}

}
