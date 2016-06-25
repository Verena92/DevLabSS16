package de.hdm.wim.events.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ProjectReceivedEvent implements Event {

	@Id
	private String id;
	
	@OneToOne(cascade = {CascadeType.ALL})
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
		return this.id; // TODO: use meaningful id
	}

	public void setId(String id) {
		this.id = id;
	}

}