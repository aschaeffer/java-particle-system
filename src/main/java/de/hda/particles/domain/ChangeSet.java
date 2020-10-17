package de.hda.particles.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hda.particles.configuration.impl.CommandConfiguration;
import de.hda.particles.scene.command.Command;
import de.hda.particles.scene.command.impl.NoOpCommand;

public class ChangeSet {

	private Integer iteration;
	
	private Integer transitionIterations;
	
	private String type;
	
	@JsonIgnore
	private Command command;

	private CommandConfiguration configuration;
	
	public ChangeSet() {
	}

	public ChangeSet(final Integer iteration, final Class<?> type, final CommandConfiguration configuration) {
		this(iteration, 0, type.getName(), configuration);
	}

	public ChangeSet(final Integer iteration, final String type, final CommandConfiguration configuration) {
		this(iteration, 0, type, configuration);
	}

	public ChangeSet(final Integer iteration, final Integer transitionIterations, final String type, final CommandConfiguration configuration) {
		this.setIteration(iteration);
		this.setTransitionIterations(transitionIterations);
		this.setType(type);
		this.setConfiguration(configuration);
		try {
			Class<?> commandType = Class.forName(type);
			Command command = (Command) commandType.newInstance();
			this.command = command;
		} catch (Exception e) {
			this.command = new NoOpCommand();
		}
	}

	public ChangeSet(Integer iteration, Command command, CommandConfiguration configuration) {
		this.setIteration(iteration);
		this.setType(command.getClass().getName());
		this.setCommand(command);
		this.setConfiguration(configuration);
	}

	public Integer getIteration() {
		return iteration;
	}

	public void setIteration(Integer iteration) {
		this.iteration = iteration;
	}

	public Integer getTransitionIterations() {
		return transitionIterations;
	}

	public void setTransitionIterations(Integer transitionIterations) {
		this.transitionIterations = transitionIterations;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public CommandConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(CommandConfiguration configuration) {
		this.configuration = configuration;
	}
	
}
