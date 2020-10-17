package de.hda.particles.dao;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import de.hda.particles.ConfigurableParticleSystem;
import de.hda.particles.ParticleSystem;
import de.hda.particles.domain.impl.configuration.ParticleEmitterConfiguration;
import de.hda.particles.domain.impl.configuration.ParticleModifierConfiguration;
import de.hda.particles.domain.impl.configuration.ParticleSystemConfiguration;
import de.hda.particles.emitter.ParticleEmitter;
import de.hda.particles.features.ParticleFeature;
import de.hda.particles.modifier.ParticleModifier;

public class ParticleSystemDAO {

	@SuppressWarnings("unchecked")
	public ParticleSystem create(String filename) throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
		// read json
		InputStream in = this.getClass().getResourceAsStream(filename);
		ObjectMapper mapper = new ObjectMapper();
		ParticleSystemConfiguration configuration = mapper.readValue(in, ParticleSystemConfiguration.class);
		// create particle system
		ConfigurableParticleSystem particleSystem = new ConfigurableParticleSystem(this);
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
			// String particleRendererClassName = (String) emitterConfiguration.get("particleRenderer");
			// Class<? extends ParticleRenderer> particleRendererClass = (Class<? extends ParticleRenderer>) Class.forName(particleRendererClassName);
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
	
	@SuppressWarnings("unchecked")
	public void load(ConfigurableParticleSystem particleSystem, File file) throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
		// cleanup
		particleSystem.removeAllParticles();
		particleSystem.removeAllFaces();
		ListIterator<ParticleFeature> removeFeaturesIterator = particleSystem.getParticleFeatures().listIterator();
		while (removeFeaturesIterator.hasNext()) {
			particleSystem.removeParticleFeature(removeFeaturesIterator.next());
		}
		ListIterator<ParticleEmitter> removeEmittersIterator = particleSystem.getParticleEmitters().listIterator();
		while (removeEmittersIterator.hasNext()) {
			particleSystem.removeParticleEmitter(removeEmittersIterator.next());
		}
		ListIterator<ParticleModifier> removeModifiersIterator = particleSystem.getParticleModifiers().listIterator();
		while (removeModifiersIterator.hasNext()) {
			particleSystem.removeParticleModifier(removeModifiersIterator.next());
		}
		
		// read json
		InputStream in = new FileInputStream(file);
		ObjectMapper mapper = new ObjectMapper();
		ParticleSystemConfiguration configuration = mapper.readValue(in, ParticleSystemConfiguration.class);
		// init configurable system
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
			// String particleRendererClassName = (String) emitterConfiguration.get("particleRenderer");
			// Class<? extends ParticleRenderer> particleRendererClass = (Class<? extends ParticleRenderer>) Class.forName(particleRendererClassName);
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


	}
	
	public void save(ParticleSystem particleSystem, File file) throws JsonGenerationException, JsonMappingException, IOException {
		ParticleSystemConfiguration systemConfiguration = new ParticleSystemConfiguration();
		systemConfiguration.name = particleSystem.getSystemName();
		// particle features
		systemConfiguration.features = new ArrayList<String>();
		ListIterator<ParticleFeature> featureIterator = particleSystem.getParticleFeatures().listIterator();
		while (featureIterator.hasNext()) {
			ParticleFeature feature = featureIterator.next();
			systemConfiguration.features.add(feature.getClass().getName());
		}
		// particle emitters
		systemConfiguration.emitters = new ArrayList<HashMap<String, Object>>();
		ListIterator<ParticleEmitter> emitterIterator = particleSystem.getParticleEmitters().listIterator();
		while (emitterIterator.hasNext()) {
			ParticleEmitter emitter = emitterIterator.next();
			HashMap<String, Object> emitterConfiguration = new HashMap<String, Object>();
			emitterConfiguration.put("className", emitter.getClass().getName());
			emitterConfiguration.put("rate", emitter.getRate());
			emitterConfiguration.put("lifetime", emitter.getParticleLifetime());
			emitterConfiguration.put("position_x", emitter.getPosition().x);
			emitterConfiguration.put("position_y", emitter.getPosition().y);
			emitterConfiguration.put("position_z", emitter.getPosition().z);
			emitterConfiguration.put("velocity_x", emitter.getParticleDefaultVelocity().x);
			emitterConfiguration.put("velocity_y", emitter.getParticleDefaultVelocity().y);
			emitterConfiguration.put("velocity_z", emitter.getParticleDefaultVelocity().z);
			emitterConfiguration.put("configuration", emitter.getConfiguration());
			systemConfiguration.emitters.add(emitterConfiguration);
		}
		// particle modifiers
		systemConfiguration.modifiers = new ArrayList<HashMap<String, Object>>();
		ListIterator<ParticleModifier> modifierIterator = particleSystem.getParticleModifiers().listIterator();
		while (modifierIterator.hasNext()) {
			ParticleModifier modifier = modifierIterator.next();
			HashMap<String, Object> modifierConfiguration = new HashMap<String, Object>();
			modifierConfiguration.put("className", modifier.getClass().getName());
			modifierConfiguration.put("configuration", modifier.getConfiguration());
			systemConfiguration.modifiers.add(modifierConfiguration);
		}
		// save full system configuration
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(file, systemConfiguration);
	}

}
