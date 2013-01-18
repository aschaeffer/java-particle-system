package de.hda.particles.domain;

import java.util.ArrayList;
import java.util.List;

public class Demo {

	private String name = "";
	private List<ChangeSet> changeSets = new ArrayList<ChangeSet>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ChangeSet> getChangeSets() {
		return changeSets;
	}

	public void setChangeSets(List<ChangeSet> changeSets) {
		this.changeSets = changeSets;
	}
	
	public void addChangeSet(ChangeSet changeSet) {
		changeSets.add(changeSet);
	}
	
	public void addChangeSets(List<ChangeSet> changeSets) {
		changeSets.addAll(changeSets);
	}

}
