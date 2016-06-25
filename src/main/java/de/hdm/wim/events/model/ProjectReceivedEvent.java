package de.hdm.wim.events.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ProjectReceivedEvent implements Event {

	@Id
	@GeneratedValue
	private long id;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private Project project;
	
	public ProjectReceivedEvent() {
		
	}
	
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
		return "" + this.id;
	}
}