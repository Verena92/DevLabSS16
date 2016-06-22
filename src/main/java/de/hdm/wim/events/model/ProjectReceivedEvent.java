package de.hdm.wim.events.model;

import java.util.Date;

public class ProjectReceivedEvent implements Event {

	private Project project;
	
	public ProjectReceivedEvent( Project project) {
		this.project = project;
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Date getTimestamp() {
		return new Date(); //TODO: use meaningful date
	}

	@Override
	public String getId() {
		return ""; //TODO: use meaningful id
	}

}