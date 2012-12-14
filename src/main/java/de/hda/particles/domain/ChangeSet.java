package de.hda.particles.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.hda.particles.scene.command.Command;
import de.hda.particles.scene.command.NoOpCommand;

public class ChangeSet {

	private Integer iteration;
	
	private String type;
	
	@JsonIgnore
	private Command command;

	private CommandConfiguration configuration;
	
	public ChangeSet() {
	}

	public ChangeSet(final Integer iteration, final String type, final CommandConfiguration configuration) {
		this.setIteration(iteration);
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
